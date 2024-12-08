package com.pavmaxdav.digital_journal.enitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import java.util.Collection;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    // Почта должна быть указана и уникальна
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    // Связь с ролями многие ко многим
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @JsonManagedReference
    @JsonIgnore  // Игнорим роли так как их уже обрабатывает security
    private Set<Role> roles = new HashSet<>();

    // Двусторонняя связь с оценками
    @OneToMany(mappedBy = "gradeOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Grade> grades = new HashSet<>();

    // Двусторонняя связь с дисциплинами, проводимыми пользователем (только для преподавателей)
    @OneToMany(mappedBy = "appointedTeacher", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Discipline> heldDisciplines = new HashSet<>();

    // Двусторонняя связь с учебной группой, в которой учится пользователь (только для студентов)
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "group_id")
    private Group group;



    // Геттеры
    public int getId() {
        return id;
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
    public String getPassword() {
        return password;
    }
    public Group getGroup() {
        return group;
    }
    public Set<Grade> getGrades() {
        return grades;
    }

    // Сеттеры
    public Set<Role> getRoles() {
        return roles;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Тут был private, хз почему
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    // И тут тоже
    public void addRole(Role role) {
        this.roles.add(role);
    }

    // Конструкторы
    public User() {}
    public User(int id) {
        this.id = id;
    }
    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }
    public User(String login, String firstName, String lastName, String password, String email) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    // Для сравнения используется только логин
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(login);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection<? extends GrantedAuthority>) getRoles();
    }

    @Override
    public String getUsername() {
        return firstName + ' ' + lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public boolean hasRole(String roleName) {
        for (Role role : this.getRoles()) {
            if (Objects.equals(role.getName(), roleName)) {
                return true;
            }
        }
        return false;
    }

    public UserBasicInfo getUserBasicInfo() {
        UserBasicInfo basicInfo = new UserBasicInfo(this.getLogin(), this.getFirstName(), this.getLastName());
        return basicInfo;
    }
}
