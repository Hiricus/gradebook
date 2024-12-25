package com.pavmaxdav.digital_journal.model;

import com.pavmaxdav.digital_journal.enitiy.Discipline;
import com.pavmaxdav.digital_journal.enitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {
    @Override
    List<Discipline> findAll();

    @Override
    Optional<Discipline> findById(Integer id);
    Optional<Discipline> findByAppointedTeacherAndName(User appointedTeacher, String name);
}
