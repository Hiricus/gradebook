package com.pavmaxdav.digital_journal.service;

import com.pavmaxdav.digital_journal.enitiy.Role;
import com.pavmaxdav.digital_journal.model.RoleRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void addRole(Role givenRole) {
        Optional<Role> optionalRole = roleRepository.findRoleByName(givenRole.getName());
        if (optionalRole.isPresent()) {
            throw new EntityExistsException("Role already exists");
        }

        roleRepository.save(givenRole);
    }

    @Transactional
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
