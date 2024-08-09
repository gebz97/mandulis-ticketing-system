package org.mandulis.mts.ticket;

import lombok.*;
import org.mandulis.mts.ticket.Ticket.Priority;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequestDTO {
    private String title;
    private String description;
    private Long userId;
    private Long categoryId;
    private Priority priority;
}
