package org.mandulis.mts.dto.request;

import lombok.*;
import org.mandulis.mts.entity.Ticket.Priority;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequest {
    private String title;
    private String description;
    private Long userId;
    private Long categoryId;
    private Priority priority;
}
