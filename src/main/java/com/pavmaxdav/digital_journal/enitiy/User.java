package com.pavmaxdav.digital_journal.enitiy;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Set;

import java.util.Collection;

public abstract class User implements UserDetails {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Role> roles;

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


    public void setEmail(String email) {
        this.email = email;
    }

    public User() {}

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection<? extends GrantedAuthority>) getRoles();
    }


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
