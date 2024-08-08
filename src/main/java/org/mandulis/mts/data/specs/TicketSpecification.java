package org.mandulis.mts.data.specs;

import org.mandulis.mts.data.entity.Ticket;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TicketSpecification {

    public static Specification<Ticket> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Ticket> hasCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("category").get("name"), "%" + categoryName + "%");
    }

    public static Specification<Ticket> hasPriority(Ticket.Priority priority) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority"), priority);
    }

    public static Specification<Ticket> hasUserName(String userName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("user").get("name"), "%" + userName + "%");
    }
    public static Specification<Ticket> createdAfter(LocalDateTime date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), date);
    }

    public static Specification<Ticket> createdBefore(LocalDateTime date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), date);
    }

    public static Specification<Ticket> updatedAfter(LocalDateTime date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("updatedDate"), date);
    }

    public static Specification<Ticket> updatedBefore(LocalDateTime date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("updatedDate"), date);
    }
}