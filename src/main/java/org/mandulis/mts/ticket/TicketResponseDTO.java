package org.mandulis.mts.ticket;

import lombok.*;
import org.mandulis.mts.ticket.Ticket.Priority;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String userName;
    private String categoryName;
    private Priority priority;
    private List<String> comments;
    private List<String> attachments;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
