package org.mandulis.mts.specification;

import org.mandulis.mts.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    private UserSpecification() {
    }

    public static Specification<User> hasUsernameLike(String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%" + username + "%");
    }

    public static Specification<User> hasFirstNameLike(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
    }

    public static Specification<User> hasLastNameLike(String lastName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%");
    }

    public static Specification<User> hasEmailLike(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + email + "%");
    }
}