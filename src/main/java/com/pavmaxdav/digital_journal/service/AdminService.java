package com.pavmaxdav.digital_journal.service;

import com.pavmaxdav.digital_journal.enitiy.Group;
import com.pavmaxdav.digital_journal.enitiy.Role;
import com.pavmaxdav.digital_journal.enitiy.User;
import com.pavmaxdav.digital_journal.model.RoleRepository;
import com.pavmaxdav.digital_journal.model.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AdminService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public AdminService(UserRepository userRepository, RoleRepository roleRepository) {
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
    public User addUser(User user) {
        System.out.println("in method");
        if (getUserByLogin(user.getLogin()).isPresent()) {
            throw new EntityExistsException("User " + user.getLogin() + " already exists");
        } else {
            Set<Role> existingRoles = new HashSet<>();
            for (Role role : user.getRoles()) {
                Role existingRole = roleRepository.findRoleByName(role.getName())
                        .orElseGet(() -> roleRepository.save(role));
                existingRoles.add(existingRole);
            }
            user.setRoles(existingRoles);
            return userRepository.save(user);
        }
    }

    // Удалить пользователя
    @Transactional
    public User removeUser(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Отвязываем пользователя от всех его ролей
        for (Role role : user.getRoles()) {
            role.removeUser(user);
        }
        // Удаляем пользователя
        userRepository.delete(user);
        return user;
    }

    // Добавить роль пользователю
    @Transactional
    public void addRoleToUser(String userLogin, Role givenRole) {
        Optional<User> optionalUser = userRepository.findByLogin(userLogin);
        Optional<Role> optionalRole = roleRepository.findRoleByName(givenRole.getName());
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = optionalRole.orElseThrow(() -> new EntityNotFoundException("Role not found"));

        user.addRole(role);
        userRepository.save(user);
    }

    // Удалить роль у пользователя
    @Transactional
    public void removeRoleFromUser(String userLogin, Role givenRole) {
        Optional<User> optionalUser = userRepository.findByLogin(userLogin);
        Optional<Role> optionalRole = roleRepository.findRoleByName(givenRole.getName());
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = optionalRole.orElseThrow(() -> new EntityNotFoundException("Role not found"));

        if (user.hasRole(role.getName())) {
            // Удаляем пользователя из списка соответствующей роли
            role.getUsers().remove(user);

            // Удаляем роль у пользователя
            user.removeRole(role);
        }
    }
}
