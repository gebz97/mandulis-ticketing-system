package org.mandulis.mts.service;

import org.mandulis.mts.dto.request.TagRequest;
import org.mandulis.mts.dto.response.TagResponse;
import org.mandulis.mts.entity.Tag;
import org.mandulis.mts.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public static TagResponse convertEntityToResponse(Tag entity) {
        TagResponse dto = new TagResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public static Tag convertRequestToEntity(TagRequest request) {
        Tag entity = new Tag();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return entity;
    }

    public Optional<TagResponse> findById(Long id) {
        return tagRepository.findById(id).map(TagService::convertEntityToResponse);
    }

    public List<TagResponse> findAll() {
        return tagRepository
                .findAll().stream()
                .map(TagService::convertEntityToResponse)
                .toList();
    }

    public Optional<TagResponse> save(TagRequest request) {
        try {
            Tag newTag = tagRepository.save(TagService.convertRequestToEntity(request));
            return Optional.of(TagService.convertEntityToResponse(newTag));
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<TagResponse> update(Long id, TagRequest request) {
        try {
            return tagRepository.findById(id).map(existingTag -> {
                existingTag.setName(request.getName());
                existingTag.setDescription(request.getDescription());
                Tag updatedTag = tagRepository.save(existingTag);
                return Optional.of(TagService.convertEntityToResponse(updatedTag));
            }).orElse(Optional.empty());
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public boolean delete(Long id) {
        if (tagRepository.existsById(id)) {
            try {
                tagRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
