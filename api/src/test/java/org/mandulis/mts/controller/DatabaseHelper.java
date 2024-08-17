package org.mandulis.mts.controller;

import lombok.RequiredArgsConstructor;
import org.mandulis.mts.entity.Category;
import org.mandulis.mts.entity.Ticket;
import org.mandulis.mts.entity.User;
import org.mandulis.mts.repository.CategoryRepository;
import org.mandulis.mts.repository.TicketRepository;
import org.mandulis.mts.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DatabaseHelper {

    private final TicketRepository ticketRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    Ticket setupTicket(User user, Category category) {
        var ticket = new Ticket();
        ticket.setTitle("");
        ticket.setDescription("");
        ticket.setStatus(Ticket.Status.IN_PROGRESS);
        ticket.setRequester(user);
        ticket.setCategory(category);
        ticket = ticketRepository.save(ticket);
        return ticket;
    }

    User setupUser() {
        var user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setEmail("");
        userRepository.save(user);
        return user;
    }

    Category setupCategory() {
        var category = new Category();
        category.setName("");
        categoryRepository.save(category);
        return category;
    }

    void clearCategories() {
        categoryRepository.deleteAll();
    }

    void clearTickets() {
        ticketRepository.deleteAll();
    }

    void clearUsers() {
        userRepository.deleteAll();
    }

}
