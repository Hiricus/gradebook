package com.pavmaxdav.digital_journal.service;

import com.pavmaxdav.digital_journal.enitiy.Discipline;
import com.pavmaxdav.digital_journal.enitiy.Grade;
import com.pavmaxdav.digital_journal.enitiy.Group;
import com.pavmaxdav.digital_journal.enitiy.User;
import com.pavmaxdav.digital_journal.model.DisciplineRepository;
import com.pavmaxdav.digital_journal.model.GradeRepository;
import com.pavmaxdav.digital_journal.model.RoleRepository;
import com.pavmaxdav.digital_journal.model.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@Service
public class TeacherService {
    private AdminService adminService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private DisciplineRepository disciplineRepository;
    private GradeRepository gradeRepository;

    public TeacherService(AdminService adminService, UserRepository userRepository, RoleRepository roleRepository, DisciplineRepository disciplineRepository, GradeRepository gradeRepository) {
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.disciplineRepository = disciplineRepository;
        this.gradeRepository = gradeRepository;
    }

    // Смотреть данные о себе
    // Получить пользователя по логину (Надо переделать чтобы получать данные только о себе)
    @Transactional
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    // Смотреть данные о своих дисциплинах
    @Transactional
    public Set<Discipline> getAllTeachersDisciplines(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User " + login + " does not exist");
        }

        User user = optionalUser.get();
        return user.getHeldDisciplines();
    }

    // Смотреть данные о группах, проходящих его дисциплину
    @Transactional
    public Set<Group> getAllGroupsFromDiscipline(Integer id) {
        Optional<Discipline> optionalDiscipline = disciplineRepository.findById(id);
        Discipline discipline = optionalDiscipline.orElseThrow(() -> new EntityNotFoundException("No discipline with id " + id));
        return discipline.getGroups();
    }

    // Смотреть оценки
    //todo

    // Ставить оценки
    @Transactional
    public Grade setGrade(Grade grade) {
        gradeRepository.save(grade);
        return grade;
    }

    // Менять оценки
    @Transactional
    public Grade changeGrade(Integer id, String changedGrade) {
        Optional<Grade> optionalGrade = gradeRepository.findById(id);
        Grade grade = optionalGrade.orElseThrow(() -> new EntityNotFoundException("No grade with id " + id));

        grade.setGrade(changedGrade);
        gradeRepository.save(grade);
        return grade;
    }
}
