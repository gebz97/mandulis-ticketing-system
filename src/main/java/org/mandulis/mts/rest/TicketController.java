package org.mandulis.mts.rest;

import org.mandulis.mts.dto.TicketRequestDTO;
import org.mandulis.mts.dto.TicketResponseDTO;
import org.mandulis.mts.data.entity.Ticket;
import org.mandulis.mts.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<TicketResponseDTO> findAll() {
        return ticketService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<TicketResponseDTO> findById(@PathVariable Long id) {
        return ticketService.findById(id);
    }

    @PostMapping
    public TicketResponseDTO save(@RequestBody TicketRequestDTO ticketRequestDTO) {
        return ticketService.save(ticketRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        ticketService.deleteById(id);
    }

    @PutMapping("/{id}")
    public TicketResponseDTO update(@RequestBody TicketRequestDTO ticketRequestDTO, @PathVariable Long id) {
        return ticketService.update(ticketRequestDTO, id);
    }

    @GetMapping("/filter")
    public List<TicketResponseDTO> filterTickets(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Ticket.Priority priority,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) LocalDateTime createdAfter,
            @RequestParam(required = false) LocalDateTime createdBefore,
            @RequestParam(required = false) LocalDateTime updatedAfter,
            @RequestParam(required = false) LocalDateTime updatedBefore) {
        return ticketService.filterTickets(
                title, categoryName, priority, userName, createdAfter, createdBefore, updatedAfter, updatedBefore
        );
    }
}

