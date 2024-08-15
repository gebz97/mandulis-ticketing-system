package org.mandulis.mts.dto.response;

import lombok.*;
import org.mandulis.mts.entity.Ticket.Priority;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private String username;
    private String categoryName;
    private Priority priority;
    private List<String> comments;
    private List<String> attachments;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
