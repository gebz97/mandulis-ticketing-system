package org.mandulis.mts.ticket;

import lombok.*;
import org.mandulis.mts.ticket.Ticket.Priority;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private String username;
    private String category;
    private Priority priority;
    private List<String> comments;
    private List<String> attachments;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
