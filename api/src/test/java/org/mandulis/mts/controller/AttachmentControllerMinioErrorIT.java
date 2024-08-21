package org.mandulis.mts.controller;

import io.minio.MinioClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mandulis.mts.repository.AttachmentRepository;
import org.mandulis.mts.service.storage.MinioClientBuilder;
import org.mandulis.mts.utils.DatabaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AttachmentControllerMinioErrorIT extends BaseMinioIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private DatabaseHelper databaseHelper;

    // disable security in tests
    @MockBean
    private SecurityFilterChain securityFilterChain;

    @MockBean
    private MinioClientBuilder mockMinioClientBuilder;

    @MockBean
    private MinioClient mockMinioClient;

    @BeforeEach
    void before() {
        databaseHelper.clearCategories();
        databaseHelper.clearTickets();
        databaseHelper.clearUsers();
    }

    @Test
    @DisplayName("no bucket, expect 500")
    void shouldThrowException_bucketDoesNotExist() throws Exception {
        // given
        var category = databaseHelper.setupCategory();
        var user = databaseHelper.setupUser();
        var ticket = databaseHelper.setupTicket(user, category);

        String filename = "filename.txt";
        byte[] fileContent = "some xml".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", filename, "text/plain", fileContent);

        String url = String.format("/api/v1/user/tickets/%d/attachments", ticket.getId());

        when(mockMinioClientBuilder.build()).thenReturn(mockMinioClient);
        when(mockMinioClient.bucketExists(any())).thenReturn(false);

        // when, then
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.content[0].error").value(
                        String.format("Bucket[name=%s] does not exist.", BUCKET_NAME)));
    }

    @Test
    @DisplayName("cannot build minio client, expect 500")
    void shouldThrowException_cannotBuildMinioClient() throws Exception {
        // given
        var category = databaseHelper.setupCategory();
        var user = databaseHelper.setupUser();
        var ticket = databaseHelper.setupTicket(user, category);

        String filename = "filename.txt";
        byte[] fileContent = "some xml".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", filename, "text/plain", fileContent);

        String url = String.format("/api/v1/user/tickets/%d/attachments", ticket.getId());

        when(mockMinioClientBuilder.build()).thenThrow(RuntimeException.class);

        // when, then
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.content[0].error").value("IO exception, check if bucket exists."));
    }

    @Test
    @DisplayName("exception during file upload, expect 500")
    void shouldThrowException_exceptionOnUploadingFile() throws Exception {
        // given
        var category = databaseHelper.setupCategory();
        var user = databaseHelper.setupUser();
        var ticket = databaseHelper.setupTicket(user, category);

        String filename = "filename.txt";
        byte[] fileContent = "some xml".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", filename, "text/plain", fileContent);

        String url = String.format("/api/v1/user/tickets/%d/attachments", ticket.getId());

        when(mockMinioClientBuilder.build()).thenReturn(mockMinioClient);
        when(mockMinioClient.bucketExists(any())).thenReturn(true);
        when(mockMinioClient.putObject(any())).thenThrow(IOException.class);

        // when, then
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.content[0].error").value("Could not upload file."));
    }
}
