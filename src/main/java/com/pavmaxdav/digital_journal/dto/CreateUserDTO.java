package com.pavmaxdav.digital_journal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pavmaxdav.digital_journal.enitiy.Role;
import com.pavmaxdav.digital_journal.enitiy.User;

import java.util.HashSet;

public class CreateUserDTO {
    String login;
    String firstName;
    String lastName;

    @JsonProperty("password")
    String userPassword;

    @JsonProperty("email")
    String email;
    String role;
    String group;

    public CreateUserDTO() {}
    public CreateUserDTO(String login) {
        this.login = login;
    }

    // Все поля кроме группы
    public CreateUserDTO(String login, String firstName, String lastName, String userPassword, String email, String role) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;

        System.out.println("Password = " + userPassword);
        this.userPassword = userPassword;
        this.email = email;
        this.role = role;
    }
    // Все поля
    public CreateUserDTO(String login, String firstName, String lastName, String userPassword, String email, String role, String group) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;

        System.out.println("Password = " + userPassword);
        this.userPassword = userPassword;
        this.email = email;
        this.role = role;
        this.group = group;
    }

    public String getLogin() {
        return login;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getRole() {
        return role;
    }
    public String getGroup() {
        if (group == null) {
            return null;
        }
        return group;
    }

    @Override
    public String toString() {
        return "CreateUserDTO{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", group='" + group + '\'' +
                '}';
    }

    public User constructUser() {
        User user = new User();
        if (login != null) {
            user.setLogin(login);
        }

        user.setPassword(userPassword);

        if (firstName != null) {
            user.setFirstName(firstName);
        }
        if (lastName != null) {
            user.setLastName(lastName);
        }
        if (email != null) {
            user.setEmail(email);
        }

        // Приделываем роль
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role(role));
        user.setRoles(roles);

        // Группу не добавляем, это лучше сделать в сервисе

        return user;
    }
}
