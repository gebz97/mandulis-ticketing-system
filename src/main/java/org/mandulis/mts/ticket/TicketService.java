package org.mandulis.mts.ticket;

import org.mandulis.mts.attachment.Attachment;
import org.mandulis.mts.comment.Comment;
import org.mandulis.mts.category.CategoryRepository;
import org.mandulis.mts.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<TicketResponseDTO> findAll() {
        return ticketRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<TicketResponseDTO> findById(Long id) {
        return ticketRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    public TicketResponseDTO save(TicketRequestDTO ticketRequestDTO) {
        Ticket ticket = convertToEntity(ticketRequestDTO);
        return convertToResponseDTO(ticketRepository.save(ticket));
    }

    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    public TicketResponseDTO update(TicketRequestDTO ticketRequestDTO, Long id) {
        Ticket ticket = convertToEntity(ticketRequestDTO);
        ticket.setId(id);
        return convertToResponseDTO(ticketRepository.save(ticket));
    }

    public List<TicketResponseDTO> filterTickets(
            String title,
            String categoryName,
            Ticket.Priority priority,
            String userName,
            LocalDateTime createdAfter,
            LocalDateTime createdBefore,
            LocalDateTime updatedAfter,
            LocalDateTime updatedBefore
    ) {
        Specification<Ticket> spec = Specification.where(null);

        if (title != null) {
            spec = spec.and(TicketSpecification.hasTitle(title));
        }
        if (categoryName != null) {
            spec = spec.and(TicketSpecification.hasCategoryName(categoryName));
        }
        if (priority != null) {
            spec = spec.and(TicketSpecification.hasPriority(priority));
        }
        if (userName != null) {
            spec = spec.and(TicketSpecification.hasUserName(userName));
        }
        if (createdAfter != null) {
            spec = spec.and(TicketSpecification.createdAfter(createdAfter));
        }
        if (createdBefore != null) {
            spec = spec.and(TicketSpecification.createdBefore(createdBefore));
        }
        if (updatedAfter != null) {
            spec = spec.and(TicketSpecification.updatedAfter(updatedAfter));
        }
        if (updatedBefore != null) {
            spec = spec.and(TicketSpecification.updatedBefore(updatedBefore));
        }

        return ticketRepository.findAll(spec).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private Ticket convertToEntity(TicketRequestDTO ticketRequestDTO) {
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketRequestDTO.getTitle());
        ticket.setDescription(ticketRequestDTO.getDescription());
        ticket.setPriority(ticketRequestDTO.getPriority());
        ticket.setRequester(userRepository.findById(ticketRequestDTO.getUserId()).orElse(null));
        ticket.setCategory(categoryRepository.findById(ticketRequestDTO.getCategoryId()).orElse(null));
        ticket.setStatus(Ticket.Status.OPEN);
        return ticket;
    }

    private TicketResponseDTO convertToResponseDTO(Ticket ticket) {
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        ticketResponseDTO.setId(ticket.getId());
        ticketResponseDTO.setTitle(ticket.getTitle());
        ticketResponseDTO.setDescription(ticket.getDescription());
        ticketResponseDTO.setPriority(ticket.getPriority());
        ticketResponseDTO.setUserName(ticket.getRequester().getUsername());
        ticketResponseDTO.setCategoryName(ticket.getCategory().getName());
        ticketResponseDTO.setComments(ticket.getComments()
                .stream()
                .map(Comment::getContent)
                .collect(Collectors.toList()));
        ticketResponseDTO.setAttachments(ticket.getAttachments()
                .stream()
                .map(Attachment::getFileName)
                .collect(Collectors.toList()));
        ticketResponseDTO.setCreatedDate(ticket.getCreatedAt());
        ticketResponseDTO.setUpdatedDate(ticket.getUpdatedAt());
        return ticketResponseDTO;
    }
}