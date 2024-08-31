package org.mandulis.mts.ticket;

import jakarta.transaction.Transactional;
import org.mandulis.mts.attachment.Attachment;
import org.mandulis.mts.comment.Comment;
import org.mandulis.mts.category.CategoryRepository;
import org.mandulis.mts.ticket.views.TicketSummaryProjectionView;
import org.mandulis.mts.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public List<TicketResponse> findAll() {
        return ticketRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<TicketResponse> findById(Long id) {
        return ticketRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    @Transactional
    public List<TicketSummaryResponse> findAllSummaries(int page, int size) {
        //it calculates how many summaries should return for each page.
        Pageable pageable = PageRequest.of(page, size);

        return ticketRepository.findAllSummaries(pageable).stream()
                .map(this::convertToSummaryDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TicketResponse save(TicketRequest ticketRequest) {
        Ticket ticket = convertToEntity(ticketRequest);
        return convertToResponseDTO(ticketRepository.save(ticket));
    }

    //@PreAuthorize("@securityService.isTicketOwner(#id, authentication.principal) or" +
    //        " @securityService.isAdmin(authentication.principal)")
    @Transactional
    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    //@PreAuthorize("@securityService.isAssignedToTicket(#id, authentication.principal) or" +
    //        " @securityService.isAdmin(authentication.principal)")
    @Transactional
    public TicketResponse update(TicketRequest ticketRequest, Long id) {
        Ticket ticket = convertToEntity(ticketRequest);
        ticket.setId(id);
        return convertToResponseDTO(ticketRepository.save(ticket));
    }

    @Transactional
    public List<TicketResponse> filterTickets(
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

    private Ticket convertToEntity(TicketRequest ticketRequest) {
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketRequest.getTitle());
        ticket.setDescription(ticketRequest.getDescription());
        ticket.setPriority(ticketRequest.getPriority());
        ticket.setRequester(userRepository.findById(ticketRequest.getUserId()).orElse(null));
        ticket.setCategory(categoryRepository.findById(ticketRequest.getCategoryId()).orElse(null));
        ticket.setStatus(Ticket.Status.OPEN);
        return ticket;
    }

    private TicketResponse convertToResponseDTO(Ticket ticket) {
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setId(ticket.getId());
        ticketResponse.setTitle(ticket.getTitle());
        ticketResponse.setDescription(ticket.getDescription());
        ticketResponse.setPriority(ticket.getPriority());
        ticketResponse.setUsername(ticket.getRequester().getUsername());
        ticketResponse.setCategory(ticket.getCategory().getName());
        if (ticket.getComments() != null) {
            ticketResponse.setComments(ticket.getComments()
                    .stream()
                    .map(Comment::getContent)
                    .collect(Collectors.toList()));
        }
        if (ticket.getAttachments() != null) {
            ticketResponse.setAttachments(ticket.getAttachments()
                    .stream()
                    .map(Attachment::getFileName)
                    .collect(Collectors.toList()));
        }
        ticketResponse.setCreatedDate(ticket.getCreatedAt());
        ticketResponse.setUpdatedDate(ticket.getUpdatedAt());
        return ticketResponse;
    }

    private TicketSummaryResponse convertToSummaryDTO(TicketSummaryProjectionView projection) {
        return TicketSummaryResponse.builder()
                .id(projection.getId())
                .title(projection.getTitle())
                .category(projection.getCategory())
                .priority(projection.getPriority())
                .status(projection.getStatus())
                .createdDate(projection.getCreatedDate())
                .updatedDate(projection.getUpdatedDate())
                .build();
    }
}