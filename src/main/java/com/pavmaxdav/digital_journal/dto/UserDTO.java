package com.pavmaxdav.digital_journal.dto;

import java.util.Set;

public class UserDTO {
    private Integer id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private Set<RoleDTO> roleDTOS;
    private GroupDTO group;
    private Set<DisciplineDTO> heldDisciplines;

    // Конструкторы
    public UserDTO(Integer id, String login) {
        this.id = id;
        this.login = login;
    }
    public UserDTO(Integer id, String login, String firstName, String lastName, GroupDTO group) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    // Геттеры
    public Integer getId() {
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
    public Set<RoleDTO> getRoleDTOS() {
        return roleDTOS;
    }
    public GroupDTO getGroup() {
        return group;
    }
    public Set<DisciplineDTO> getHeldDisciplines() {
        return heldDisciplines;
    }
    public String getEmail() {
        return email;
    }

    // Сеттеры
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setRoleDTOS(Set<RoleDTO> roleDTOS) {
        this.roleDTOS = roleDTOS;
    }
    public void setGroup(GroupDTO group) {
        this.group = group;
    }
    public void setHeldDisciplines(Set<DisciplineDTO> heldDisciplines) {
        this.heldDisciplines = heldDisciplines;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
