package org.mandulis.mts.group;

import jakarta.validation.Valid;
import org.mandulis.mts.rest.ApiResponse;
import org.mandulis.mts.rest.ErrorMessages;
import org.mandulis.mts.rest.ResponseHandler;
import org.mandulis.mts.rest.SuccessMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.http.dsl.Http;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(final GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GroupResponse>>> getAllGroups() {
        List<GroupResponse> groups = groupService.findAll();
        if (groups.isEmpty()) {
            return ResponseHandler.handleError(
                    List.of(),
                    HttpStatus.NOT_FOUND,
                    ErrorMessages.NO_GROUPS_FOUND,
                    List.of("No groups found")
            );
        }
        return ResponseHandler.handleSuccess(
                groups,
                HttpStatus.OK,
                SuccessMessages.QUERY_SUCCESSFUL
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GroupResponse>> findById(@PathVariable Long id) {
        return groupService.findById(id)
                .map(response -> ResponseHandler.handleSuccess(
                        response, HttpStatus.OK, SuccessMessages.QUERY_SUCCESSFUL
                ))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, "Group not found", List.of("Group not found")
                ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GroupResponse>> createGroup(@Valid @RequestBody GroupRequest groupRequest) {
        return ResponseHandler.handleSuccess(
                groupService.save(groupRequest),
                HttpStatus.CREATED,
                "Group created successfully"
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GroupResponse>> updateGroup(
            @PathVariable Long id, @RequestBody GroupRequest request
    ) {
        return groupService.update(id, request)
                .map(response -> ResponseHandler.handleSuccess(
                        response, HttpStatus.OK, "Group updated successfully"
                ))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, "Group not found", List.of("Group not found")
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGroup(@PathVariable Long id) {
        groupService.deleteById(id);
        return ResponseHandler.handleSuccess(null, HttpStatus.NO_CONTENT, "Group deleted successfully");
    }
}
