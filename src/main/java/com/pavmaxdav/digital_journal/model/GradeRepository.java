package com.pavmaxdav.digital_journal.model;

import com.pavmaxdav.digital_journal.enitiy.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    @Override
    Optional<Grade> findById(Integer integer);
}
