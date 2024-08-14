package org.mandulis.mts.controller;

import jakarta.validation.Valid;
import org.mandulis.mts.dto.GroupRequest;
import org.mandulis.mts.dto.GroupResponse;
import org.mandulis.mts.service.GroupService;
import org.mandulis.mts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user/groups")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    @Autowired
    public GroupController(final GroupService groupService, final UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
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
    public ResponseEntity<?> createNewGroup(@Valid @RequestBody GroupRequest groupRequest) {
        GroupResponse newGroup = groupService.save(groupRequest);
        if (newGroup == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid Input");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(newGroup);
    }
}
