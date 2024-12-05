package com.pavmaxdav.digital_journal.enitiy;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Set;

import java.util.Collection;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    // Двусторонняя связь с оценками
    @OneToMany(mappedBy = "gradeOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Grade> grades;

    // Двусторонняя связь с дисциплинами, проводимыми пользователем (только для преподавателей)
    @OneToMany(mappedBy = "appointedTeacher", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Discipline> heldDisciplines;

    // Двусторонняя связь с учебной группой, в которой учится пользователь (только для студентов)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Group group;



    // Геттеры
    public int getId() {
        return id;
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

    // Сеттеры
    public Set<Role> getRoles() {
        return roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    private void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    private void addRole(Role role) {
        this.roles.add(role);
    }


    public User() {}
    public User(int id) {
        this.id = id;
    }
    public User(int id, String firstName, String lastName, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
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
}
