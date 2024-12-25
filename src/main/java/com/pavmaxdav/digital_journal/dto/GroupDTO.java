package com.pavmaxdav.digital_journal.dto;

import java.util.Set;

public class GroupDTO {
    private Integer id;
    private String name;
    private Set<UserDTO> userPartialInfo;
    private Set<DisciplineDTO> disciplineDTOS;

    // Конструкторы
    public GroupDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Set<UserDTO> getUserPartialInfo() {
        return userPartialInfo;
    }
    public Set<DisciplineDTO> getDisciplineDTOS() {
        return disciplineDTOS;
    }

    // Сеттеры
    public void setUserPartialInfo(Set<UserDTO> userPartialInfo) {
        this.userPartialInfo = userPartialInfo;
    }
    public void setDisciplineDTOS(Set<DisciplineDTO> disciplineDTOS) {
        this.disciplineDTOS = disciplineDTOS;
    }
}
