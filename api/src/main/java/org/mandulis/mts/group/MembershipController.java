package org.mandulis.mts.group;

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
    public ResponseEntity<MembershipResponse> addSingle(@RequestBody MembershipRequest request) {
        Optional<MembershipResponse> membershipResponse = membershipService.addMembership(request);
        return membershipResponse
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<MembershipResponse>> addMultiple(@RequestBody List<MembershipRequest> requests) {
        List<MembershipResponse> membershipResponses = membershipService.addMultipleMemberships(requests);
        if (membershipResponses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(membershipResponses);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteMembership(@RequestBody MembershipRequest request) {
        boolean deleted = membershipService.deleteMembership(request);
        if (!deleted) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "One or more invalid user or group");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<List<MembershipRequest>> deleteMultiple(@RequestBody List<MembershipRequest> requests) {
        List<MembershipRequest> deletedMemberships = membershipService.deleteMultipleMemberships(requests);
        if (deletedMemberships.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(deletedMemberships);
    }
}
