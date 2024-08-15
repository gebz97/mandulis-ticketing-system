package org.mandulis.mts.service;

import org.mandulis.mts.entity.Attachment;
import org.mandulis.mts.entity.Comment;
import org.mandulis.mts.repository.CategoryRepository;
import org.mandulis.mts.entity.Ticket;
import org.mandulis.mts.repository.TicketRepository;
import org.mandulis.mts.repository.UserRepository;
import org.mandulis.mts.dto.request.TicketRequest;
import org.mandulis.mts.dto.response.TicketResponse;
import org.mandulis.mts.entity.spec.TicketSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public List<TicketResponse> findAll() {
        return ticketRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<TicketResponse> findById(Long id) {
        return ticketRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    public TicketResponse save(TicketRequest ticketRequest) {
        Ticket ticket = convertToEntity(ticketRequest);
        return convertToResponseDTO(ticketRepository.save(ticket));
    }

    @PreAuthorize("@securityService.isTicketOwner(#id, authentication.principal) or" +
            " @securityService.isAdmin(authentication.principal)")
    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    @PreAuthorize("@securityService.isAssignedToTicket(#id, authentication.principal) or" +
            " @securityService.isAdmin(authentication.principal)")
    public TicketResponse update(TicketRequest ticketRequest, Long id) {
        Ticket ticket = convertToEntity(ticketRequest);
        ticket.setId(id);
        return convertToResponseDTO(ticketRepository.save(ticket));
    }

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
        ticketResponse.setCategoryName(ticket.getCategory().getName());
        if (ticket.getComments() != null) {
            ticketResponse.setComments(ticket.getComments()
                    .stream()
                    .map(Comment::getContent)
                    .collect(Collectors.toList()));
        }
        ticketResponse.setAttachments(ticket.getAttachments()
                .stream()
                .map(Attachment::getFileName)
                .collect(Collectors.toList()));
        ticketResponse.setCreatedDate(ticket.getCreatedAt());
        ticketResponse.setUpdatedDate(ticket.getUpdatedAt());
        return ticketResponse;
    }
}