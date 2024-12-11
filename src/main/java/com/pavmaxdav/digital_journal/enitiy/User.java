package com.pavmaxdav.digital_journal.enitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pavmaxdav.digital_journal.dto.DisciplineDTO;
import com.pavmaxdav.digital_journal.dto.RoleDTO;
import com.pavmaxdav.digital_journal.dto.UserDTO;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import java.util.Collection;
import java.util.stream.Collectors;

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

    // Двусторонняя связь с оценками. Если удаляется пользователь - удаляются и оценки
    @OneToMany(mappedBy = "gradeOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Grade> grades = new HashSet<>();

    // Двусторонняя связь с дисциплинами, проводимыми пользователем (только для преподавателей)
    @OneToMany(mappedBy = "appointedTeacher", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
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
    public Set<Discipline> getHeldDisciplines() {
        return heldDisciplines;
    }
    public Set<Role> getRoles() {
        return roles;
    }

    // Сеттеры
    public void setEmail(String email) {
        this.email = email;
    }
    public void setGroup(Group group) {
        this.group = group;
    }

    // Тут был private, хз почему
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    // И тут тоже
    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
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


    public UserDTO constructDTO() {
        UserDTO userDTO = new UserDTO(this.getId(), this.getLogin());
        userDTO.setFirstName(this.getFirstName());
        userDTO.setLastName(this.getLastName());

        // Добавляем роли
        Set<RoleDTO> roleDTOS = this.getRoles().stream().map(Role::constructDTO).collect(Collectors.toSet());
        userDTO.setRoleDTOS(roleDTOS);

        // Добавляем группу
        userDTO.setGroup(this.getGroup().constructDTO());

        // Добавляем проводимые дисциплины
        Set<DisciplineDTO> disciplineDTOS = this.getHeldDisciplines().stream().map(Discipline::constructDTO).collect(Collectors.toSet());
        userDTO.setHeldDisciplines(disciplineDTOS);

        return userDTO;
    }

    // Чтобы избежать рекурсии при построении DTO для групп
    public UserDTO constructPartialDTO() {
        UserDTO userDTO = new UserDTO(this.getId(), this.getLogin());
        userDTO.setFirstName(this.getFirstName());
        userDTO.setLastName(this.getLastName());

        // Добавляем роли
        Set<RoleDTO> roleDTOS = this.getRoles().stream().map(Role::constructDTO).collect(Collectors.toSet());
        userDTO.setRoleDTOS(roleDTOS);
        return userDTO;
    }
}
