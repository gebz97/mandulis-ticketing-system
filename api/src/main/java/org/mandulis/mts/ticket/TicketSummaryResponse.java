package org.mandulis.mts.ticket;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketSummaryResponse {
    private Long id;
    private String title;
    private String category;
    private Ticket.Priority priority;
    private Ticket.Status status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
