package org.mandulis.mts.service;

import org.mandulis.mts.dto.request.MembershipRequest;
import org.mandulis.mts.dto.response.MembershipResponse;
import org.mandulis.mts.entity.Group;
import org.mandulis.mts.entity.Membership;
import org.mandulis.mts.entity.User;
import org.mandulis.mts.repository.GroupRepository;
import org.mandulis.mts.repository.MembershipRepository;
import org.mandulis.mts.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public MembershipService(
            MembershipRepository membershipRepository,
            UserRepository userRepository,
            GroupRepository groupRepository
    ) {
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public Optional<MembershipResponse> addMembership(MembershipRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Group> group = groupRepository.findById(request.getGroupId());

        if (user.isPresent() && group.isPresent()) {
            // Check if the membership already exists
            boolean membershipExists = membershipRepository.existsByUserAndGroup(user.get(), group.get());

            if (membershipExists) {
                // Return an empty Optional if the membership already exists
                return Optional.empty();
            }

            // Proceed to create and save the new membership
            Membership membership = Membership.builder()
                    .user(user.get())
                    .group(group.get())
                    .build();
            var newMembership = membershipRepository.save(membership);

            MembershipResponse membershipResponse = new MembershipResponse();
            membershipResponse.setGroupId(newMembership.getGroup().getId());
            membershipResponse.setUserId(newMembership.getUser().getId());
            return Optional.of(membershipResponse);
        }

        // Return an empty Optional if the user or group is not found
        return Optional.empty();
    }

    public List<MembershipResponse> addMultipleMemberships(List<MembershipRequest> requests) {
        return requests.stream()
                .map(this::addMembership)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public boolean deleteMembership(MembershipRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Group> group = groupRepository.findById(request.getGroupId());

        if (user.isPresent() && group.isPresent()) {
            Optional<Membership> membership = membershipRepository.findByUserAndGroup(user.get(), group.get());
            if (membership.isPresent()) {
                membershipRepository.delete(membership.get());
                return true;
            }
        }
        return false;
    }

    // Method to delete multiple memberships
    public List<MembershipRequest> deleteMultipleMemberships(List<MembershipRequest> requests) {
        return requests.stream()
                .filter(this::deleteMembership)
                .toList();
    }
}
