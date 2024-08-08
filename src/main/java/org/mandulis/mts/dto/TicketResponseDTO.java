package org.mandulis.mts.dto;

import lombok.*;
import org.mandulis.mts.data.entity.Ticket.Priority;

import java.time.LocalDateTime;
import java.util.Date;
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
