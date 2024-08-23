package org.mandulis.mts.controller;

import io.minio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mandulis.mts.attachment.Attachment;
import org.mandulis.mts.attachment.AttachmentRepository;
import org.mandulis.mts.utils.DatabaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AttachmentControllerIT extends BaseMinioIT {

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

    @Test
    @DisplayName("upload, expected 201")
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

    private void removeTestBucket() {

        try {
            boolean exists = MINIO_CLIENT.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
            if (!exists) {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            MINIO_CLIENT.removeBucket(RemoveBucketArgs.builder().bucket(BUCKET_NAME).build());
        } catch (Exception e) {
            throw new RuntimeException("Cannot remove objects from bucket.");
        }
    }
}
