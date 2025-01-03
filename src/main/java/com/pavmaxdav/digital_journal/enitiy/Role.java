package com.pavmaxdav.digital_journal.enitiy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pavmaxdav.digital_journal.dto.RoleDTO;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    // Конструкторы
    public Role() {}
    public Role(String name) {
        this.name = name;
    }
    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Для сравнения используется только название роли
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String getAuthority() {
        return getName();
    }

    public Set<User> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }


    // Создать DTO
    public RoleDTO constructDTO() {
        return new RoleDTO(this.getId(), this.getName());
    }
}
