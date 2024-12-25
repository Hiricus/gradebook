package com.pavmaxdav.digital_journal.service;

import com.pavmaxdav.digital_journal.enitiy.*;
import com.pavmaxdav.digital_journal.model.RoleRepository;
import com.pavmaxdav.digital_journal.model.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StudentService {
    private AdminService adminService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public StudentService(AdminService adminService, UserRepository userRepository, RoleRepository roleRepository) {
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Получить пользователя по логину (Надо переделать чтобы получать данные только о себе)
    @Transactional
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    // Получить группу пользователя
    @Transactional
    public Optional<Group> getStudentsGroup(String login) {
        Optional<Group> optionalGroup = adminService.getStudentsGroup(login);

        return optionalGroup;
    }

    // Получить дисциплины студента
    @Transactional
    public Set<Discipline> getStudentsDisciplines(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User " + login + " does not exist");
        }

        User user = optionalUser.get();
        Group group = user.getGroup();
        return group.getDisciplines();
    }

    // Получить все оценки студента
    @Transactional
    public Set<Grade> getAllStudentsGrades(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User " + login + " does not exist");
        }

        User user = optionalUser.get();
        return user.getGrades();
    }

    @Transactional
    public Set<Grade> getStudentsGradesOnDiscipline(String login, Integer disciplineId) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User " + login + " does not exist");
        }

        User user = optionalUser.get();
        Set<Grade> unfilteredGrades = user.getGrades();

        Set<Grade> neededGrades = new HashSet<>();

        for (Grade grade : unfilteredGrades) {
            if (Objects.equals(grade.getDiscipline().getId(), disciplineId)) {
                neededGrades.add(grade);
            }
        }

        return neededGrades;
    }
}
