package com.pavmaxdav.digital_journal.model;

import com.pavmaxdav.digital_journal.enitiy.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    // create

    // read
    Optional<Role> findRoleByName(String name);
}
