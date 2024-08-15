package org.mandulis.mts.service;

import jakarta.persistence.EntityNotFoundException;
import org.mandulis.mts.dto.UserGroupDetails;
import org.mandulis.mts.dto.request.GroupRequest;
import org.mandulis.mts.dto.response.GroupResponse;
import org.mandulis.mts.entity.Group;
import org.mandulis.mts.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
    public static GroupResponse convertEntityToDto(Group entity) {
        GroupResponse dto = new GroupResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public List<GroupResponse> findAll() {
        return groupRepository.findAll()
                .stream().map(GroupService::convertEntityToDto).toList();
    }

    public Optional<GroupResponse> findById(Long id) {
        return groupRepository.findById(id).map(GroupService::convertEntityToDto);
    }

    public GroupResponse save(GroupRequest request) {
        Group entity = new Group();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return convertEntityToDto(groupRepository.save(entity));
    }

    public GroupResponse update(Long id,GroupRequest request) {
        Optional<Group> foundGroup = groupRepository.findById(id);
        if (foundGroup.isEmpty()) {
            throw new EntityNotFoundException("Group with id:" + id + " not found");
        }
        Group entity = foundGroup.get();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return convertEntityToDto(groupRepository.save(entity));
    }

    public void delete(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new EntityNotFoundException("Group with id:" + id + " not found");
        }
        groupRepository.deleteById(id);
    }

    public static UserGroupDetails convertEntityToUserGroupDetailsDto(Group entity) {
        return new UserGroupDetails(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }
}
