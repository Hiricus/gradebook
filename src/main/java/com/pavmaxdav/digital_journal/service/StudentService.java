package com.pavmaxdav.digital_journal.service;

import com.pavmaxdav.digital_journal.enitiy.*;
import com.pavmaxdav.digital_journal.model.RoleRepository;
import com.pavmaxdav.digital_journal.model.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {
    private UserService userService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public StudentService(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Получить пользователя по логину (Надо переделать чтобы получать данные только о себе)
    @Transactional
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    // Получить группу пользователя (в виде объекта с урезанными данными)
    @Transactional
    public List<UserBasicInfo> getStudentsGroupBasicInfo(String login) {
        List<UserBasicInfo> basicInfoList = new ArrayList<>();
        Optional<Group> optionalGroup = userService.getStudentsGroup(login);

        // Если пользователь не в группе - кидаем исключение
        if (optionalGroup.isEmpty()) {
            throw new EntityNotFoundException("The user " + login + " is not assigned to a group");
        }
        // Переваниваем инфу о юзерах в нужный список
        Set<User> students = optionalGroup.get().getStudents();
        for (User student : students) {
            basicInfoList.add(student.getUserBasicInfo());
        }
        // Возвращаем список
        return basicInfoList;
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
}
