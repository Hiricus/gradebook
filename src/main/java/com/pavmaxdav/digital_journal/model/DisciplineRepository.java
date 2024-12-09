package com.pavmaxdav.digital_journal.model;

import com.pavmaxdav.digital_journal.enitiy.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {
    @Override
    Optional<Discipline> findById(Integer id);
}
