package org.mandulis.mts.group;

import org.mandulis.mts.rest.ApiResponse;
import org.mandulis.mts.rest.ResponseHandler;
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
    public ResponseEntity<ApiResponse<MembershipResponse>> addSingle(@RequestBody MembershipRequest request) {
        return membershipService.addMembership(request)
                .map(response -> ResponseHandler.handleSuccess(
                        response, HttpStatus.CREATED, "Membership created successfully"
                ))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.BAD_REQUEST, "Invalid membership data", List.of("Invalid user or group")
                ));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<MembershipResponse>>> addMultiple(
            @RequestBody List<MembershipRequest> requests
    ) {
        List<MembershipResponse> membershipResponses = membershipService.addMultipleMemberships(requests);
        if (membershipResponses.isEmpty()) {
            return ResponseHandler.handleError(
                    null, HttpStatus.NOT_FOUND, "No memberships created", List.of("Invalid user or group")
            );
        }
        return ResponseHandler.handleSuccess(membershipResponses, HttpStatus.OK, "Memberships created successfully");
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteMembership(@RequestBody MembershipRequest request) {
        boolean deleted = membershipService.deleteMembership(request);
        if (deleted) {
            return ResponseHandler.handleSuccess(null, HttpStatus.NO_CONTENT, "Membership deleted successfully");
        } else {
            return ResponseHandler.handleError(
                    null, HttpStatus.NOT_FOUND, "Invalid user or group", List.of("Invalid user or group")
            );
        }
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<ApiResponse<List<MembershipRequest>>> deleteMultiple(
            @RequestBody List<MembershipRequest> requests
    ) {
        List<MembershipRequest> deletedMemberships = membershipService.deleteMultipleMemberships(requests);
        if (deletedMemberships.isEmpty()) {
            return ResponseHandler.handleError(
                    null, HttpStatus.BAD_REQUEST, "Invalid user or group", List.of("Invalid user or group")
            );
        }
        return ResponseHandler.handleSuccess(deletedMemberships, HttpStatus.OK, "Memberships deleted successfully");
    }
}
