package com.pavmaxdav.digital_journal.service;

import com.pavmaxdav.digital_journal.enitiy.Group;
import com.pavmaxdav.digital_journal.enitiy.Role;
import com.pavmaxdav.digital_journal.enitiy.User;
import com.pavmaxdav.digital_journal.enitiy.UserBasicInfo;
import com.pavmaxdav.digital_journal.model.RoleRepository;
import com.pavmaxdav.digital_journal.model.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import org.hibernate.action.internal.EntityActionVetoException;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import users.Student;

import java.util.*;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Получить всех пользователей
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Получить пользователя по логину
    @Transactional
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    // Получить группу пользователя (Только если это студент)
    @Transactional
    public Optional<Group> getStudentsGroup(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("No such user with login: " + login);
        }
        if (!(optionalUser.get().hasRole("student"))) {
            throw new EntityTypeException("The user: " + login + "is not a student", "reference idk");
        }
        // Получаем группу
        User user = optionalUser.get();
        return Optional.of(user.getGroup());
    }


    // Добавить нового пользователя, возвращает этого же пользователя
    @Transactional
    public Optional<User> addUser(User user) {
        if (getUserByLogin(user.getLogin()).isPresent()) {
            throw new EntityExistsException("User " + user.getLogin() + " already exists");
        } else {
            userRepository.save(user);
        }
        return Optional.of(user);
    }

    @Transactional
    public void addRoleToUser(User givenUser, Role givenRole) {
        Optional<User> optionalUser = userRepository.findByLogin(givenUser.getLogin());
        Optional<Role> optionalRole = roleRepository.findRoleByName(givenRole.getName());
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = optionalRole.orElseThrow(() -> new EntityNotFoundException("Role not found"));

        user.addRole(role);
        userRepository.save(user);
    }
}
