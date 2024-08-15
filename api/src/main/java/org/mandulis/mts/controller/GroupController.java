package org.mandulis.mts.controller;

import jakarta.validation.Valid;
import org.mandulis.mts.dto.response.GroupResponse;
import org.mandulis.mts.dto.request.GroupRequest;
import org.mandulis.mts.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @GetMapping("/id={id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<GroupResponse> group = groupService.findById(id);
        if (group.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Group not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(group.get());
    }

    @PostMapping
    public ResponseEntity<?> createGroup(@Valid @RequestBody GroupRequest groupRequest) {
        GroupResponse newGroup = groupService.save(groupRequest);
        if (newGroup == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid Input");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(newGroup);
    }

    @PutMapping(value = "/id={id}")
    public ResponseEntity<?> updateGroup(@PathVariable("id") Long id, @RequestBody GroupRequest request) {
        try {
            GroupResponse group = groupService.update(id, request);
            return ResponseEntity.ok(group);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Category not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping(value = "/id={id}")
    public ResponseEntity<?> deleteGroup(@PathVariable("id") Long id) {
        try {
            groupService.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Group deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Group not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
