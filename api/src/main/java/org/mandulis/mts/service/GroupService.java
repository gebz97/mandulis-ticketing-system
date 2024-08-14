package org.mandulis.mts.service;

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

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    public Group update(Group group) {
        return groupRepository.save(group);
    }
}
