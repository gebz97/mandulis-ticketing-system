package org.mandulis.mts.controller;

import io.minio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mandulis.mts.entity.Attachment;
import org.mandulis.mts.exception.AttachmentWithoutNameException;
import org.mandulis.mts.exception.EmptyAttachmentException;
import org.mandulis.mts.exception.StorageException;
import org.mandulis.mts.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MinIOContainer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AttachmentControllerMinioIT extends BaseMinioIT{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private DatabaseHelper databaseHelper;

    // disable security in tests
    @MockBean
    private SecurityFilterChain securityFilterChain;

    @BeforeEach
    void before() {
        databaseHelper.clearCategories();
        databaseHelper.clearTickets();
        databaseHelper.clearUsers();

        removeTestBucket();
    }

    private void removeTestBucket() {

        boolean exists;
        try {
            exists = MINIO_CLIENT.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!exists) {
            return;
        }

        try {
            MINIO_CLIENT.removeBucket(RemoveBucketArgs.builder().bucket(BUCKET_NAME).build());
        } catch (Exception e) {
            throw new RuntimeException("Cannot remove objects from bucket.");
        }
    }

    @Test
        // todo: maybe there is some property to keep sexy names?
    void shouldThrowException_bucketDoesNotExist() throws Exception {
        // given
        var category = databaseHelper.setupCategory();
        var user = databaseHelper.setupUser();
        var ticket = databaseHelper.setupTicket(user, category);

        String filename = "filename.txt";
        byte[] fileContent = "some xml".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", filename, "text/plain", fileContent);

        String url = String.format("/api/v1/user/tickets/%d/attachments", ticket.getId());
        // when, then
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.content[0].error").value(
                        // todo: weird formatting, check out later
                        String.format("%s: Bucket[name=%s] does not exist.", StorageException.class.getCanonicalName(), BUCKET_NAME)));
    }

    @Test
        // todo: maybe there is some property to keep sexy names?
    void shouldUploadAttachment() throws Exception {
        // given
        MINIO_CLIENT.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());

        var category = databaseHelper.setupCategory();
        var user = databaseHelper.setupUser();
        var ticket = databaseHelper.setupTicket(user, category);

        String filename = "filename.txt";
        byte[] fileContent = "some xml".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", filename, "text/plain", fileContent);

        String url = String.format("/api/v1/user/tickets/%d/attachments", ticket.getId());
        // when, then
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isCreated());

        var attachments = attachmentRepository.findAll();
        assertEquals(1, attachments.size());
        Attachment dbAttachment = attachments.getFirst();
        assertEquals(filename, dbAttachment.getFileName());
        String storedFilePath = BUCKET_NAME + "/" + filename;
        assertEquals(storedFilePath, dbAttachment.getFilePath());
        assertEquals(ticket.getId(), dbAttachment.getTicket().getId());

        GetObjectResponse response = MINIO_CLIENT.getObject(GetObjectArgs.builder().bucket(BUCKET_NAME).object(filename).build());
        assertArrayEquals(fileContent, response.readAllBytes());
    }

    @Test
    void shouldThrowExceptionWhenNoSuchTicket() throws Exception {
        // given
        String filename = "filename.txt";
        byte[] fileContent = "some xml".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", filename, "text/plain", fileContent);

        Long notExistingTicketId = 123L;
        String url = String.format("/api/v1/user/tickets/%d/attachments", notExistingTicketId);
        // when
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.content[0].error").value(String.format("Ticket[id=%d] not found.", notExistingTicketId)));
    }

    @Test
    void shouldThrowExceptionWhenEmptyFile() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", new byte[0]);

        String url = String.format("/api/v1/user/tickets/%d/attachments", 123L);

        // when, then
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.content[0].error").value(EmptyAttachmentException.MESSAGE));
    }

    @Test
    void shouldThrowExceptionWhenNoAttachmentName() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile("file", "", "text/plain", new byte[0]);

        String url = String.format("/api/v1/user/tickets/%d/attachments", 123L);

        // when, then
        mockMvc.perform(multipart(url).file(file))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.content[0].error").value(AttachmentWithoutNameException.MESSAGE));
    }
}
