package org.mandulis.mts.utils;

import lombok.RequiredArgsConstructor;

import org.mandulis.mts.category.Category;
import org.mandulis.mts.category.CategoryRepository;
import org.mandulis.mts.ticket.Ticket;
import org.mandulis.mts.ticket.TicketRepository;
import org.mandulis.mts.user.User;
import org.mandulis.mts.user.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseHelper {

    private final TicketRepository ticketRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Ticket setupTicket(User user, Category category) {
        var ticket = new Ticket();
        ticket.setTitle("");
        ticket.setDescription("");
        ticket.setStatus(Ticket.Status.IN_PROGRESS);
        ticket.setRequester(user);
        ticket.setCategory(category);
        ticket = ticketRepository.save(ticket);
        return ticket;
    }

    public User setupUser() {
        var user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setEmail("");
        userRepository.save(user);
        return user;
    }

    public Category setupCategory() {
        var category = new Category();
        category.setName("");
        categoryRepository.save(category);
        return category;
    }

    public void clearCategories() {
        categoryRepository.deleteAll();
    }

    public void clearTickets() {
        ticketRepository.deleteAll();
    }

    public void clearUsers() {
        userRepository.deleteAll();
    }

}
