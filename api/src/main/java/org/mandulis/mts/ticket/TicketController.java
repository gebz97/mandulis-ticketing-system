package org.mandulis.mts.ticket;

import org.mandulis.mts.rest.ApiResponse;
import org.mandulis.mts.rest.ResponseHandler;
import org.mandulis.mts.rest.SuccessMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TicketResponse>>> findAll() {
        List<TicketResponse> tickets = ticketService.findAll();
        return ResponseHandler.handleSuccess(tickets, HttpStatus.OK, SuccessMessages.QUERY_SUCCESSFUL);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketResponse>> findById(@PathVariable Long id) {
        Optional<TicketResponse> ticket = ticketService.findById(id);
        return ticket.map(t -> ResponseHandler.handleSuccess(t, HttpStatus.OK, "Ticket found"))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, "Ticket not found", List.of("Ticket not found")
                ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TicketResponse>> save(@RequestBody TicketRequest ticketRequest) {
        TicketResponse ticket = ticketService.save(ticketRequest);
        return ResponseHandler.handleSuccess(ticket, HttpStatus.CREATED, "Ticket created successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id) {
        ticketService.deleteById(id);
        return ResponseHandler.handleSuccess(null, HttpStatus.NO_CONTENT, "Ticket deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketResponse>> update(@RequestBody TicketRequest ticketRequest, @PathVariable Long id) {
        Optional<TicketResponse> updatedTicket = Optional.of(ticketService.update(ticketRequest, id));

        return updatedTicket.map(ticket -> ResponseHandler.handleSuccess(
                ticket, HttpStatus.OK, "Ticket updated successfully"
                ))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, "Ticket not found", List.of("Ticket not found")
                ));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<TicketResponse>>> filterTickets(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Ticket.Priority priority,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) LocalDateTime createdAfter,
            @RequestParam(required = false) LocalDateTime createdBefore,
            @RequestParam(required = false) LocalDateTime updatedAfter,
            @RequestParam(required = false) LocalDateTime updatedBefore) {
        List<TicketResponse> tickets = ticketService.filterTickets(
                title, categoryName, priority, userName, createdAfter, createdBefore, updatedAfter, updatedBefore
        );
        return ResponseHandler.handleSuccess(tickets, HttpStatus.OK, SuccessMessages.QUERY_SUCCESSFUL);
    }
}
