package com.pavmaxdav.digital_journal.model;

import com.pavmaxdav.digital_journal.enitiy.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Override
    List<Group> findAll();

    Optional<Group> findByName(String name);
}
