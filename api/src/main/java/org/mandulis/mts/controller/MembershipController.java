package org.mandulis.mts.controller;

import org.mandulis.mts.dto.request.MembershipRequest;
import org.mandulis.mts.dto.response.MembershipResponse;
import org.mandulis.mts.service.MembershipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public/membership")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping
    public ResponseEntity<Object> addSingle(@RequestBody MembershipRequest request) {
        Optional<MembershipResponse> membershipResponse = membershipService.addMembership(request);
        if (membershipResponse.isEmpty()) {

            Map<String, String> response = new HashMap<>();
            response.put("error", "Either the user/group doesn't exist, or already a member");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(membershipResponse.get());
    }

    @PostMapping("/bulk")
    public ResponseEntity<Object> addMultiple(
            @RequestBody List<MembershipRequest> requests
    ) {
        List<MembershipResponse> membershipResponses = membershipService.addMultipleMemberships(requests);

        if (membershipResponses.isEmpty()) {

            Map<String, String> response = new HashMap<>();
            response.put("error", "One or more invalid user or group");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(membershipResponses);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteMembership(@RequestBody MembershipRequest request) {
        boolean deleted = membershipService.deleteMembership(request);
        if (!deleted) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "One or more invalid user or group");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<Object> deleteMultiple(@RequestBody List<MembershipRequest> requests) {
        List<MembershipRequest> deletedMemberships = membershipService.deleteMultipleMemberships(requests);
        if (deletedMemberships.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "One or more invalid memberships");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(deletedMemberships);
    }
}
