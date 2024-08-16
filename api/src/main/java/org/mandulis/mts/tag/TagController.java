package org.mandulis.mts.tag;

import jakarta.validation.Valid;
import org.mandulis.mts.rest.ApiResponse;
import org.mandulis.mts.rest.ErrorMessages;
import org.mandulis.mts.rest.ResponseHandler;
import org.mandulis.mts.rest.SuccessMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTags() {
        return ResponseHandler.handleSuccess(
                tagService.findAll(),
                HttpStatus.OK,
                SuccessMessages.QUERY_SUCCESSFUL
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponse>> getTagById(@PathVariable Long id) {
        return tagService.findById(id)
                .map(response -> ResponseHandler.handleSuccess(
                        response, HttpStatus.OK, SuccessMessages.QUERY_SUCCESSFUL
                ))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, "Tag not found", List.of("Tag not found")
                ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TagResponse>> createTag(@Valid @RequestBody TagRequest tagRequest) {
        return tagService.save(tagRequest)
                .map(response -> ResponseHandler.handleSuccess(response,HttpStatus.CREATED,"Tag created successfully"))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, ErrorMessages.INVALID_TAG, List.of("Tag not found")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponse>> updateTag(@PathVariable Long id, @RequestBody TagRequest tagRequest) {
        return tagService.update(id, tagRequest)
                .map(response -> ResponseHandler.handleSuccess(response, HttpStatus.OK, "Tag updated successfully"))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, "Tag not found", List.of("Tag not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTag(@PathVariable Long id) {
        boolean deleted = tagService.delete(id);
        if (deleted) {
            return ResponseHandler.handleSuccess(null, HttpStatus.NO_CONTENT, "Tag deleted successfully");
        } else {
            return ResponseHandler.handleError(null, HttpStatus.NOT_FOUND, "Tag not found", List.of("Tag not found"));
        }
    }
}
