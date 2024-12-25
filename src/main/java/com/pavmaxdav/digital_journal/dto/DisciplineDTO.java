package com.pavmaxdav.digital_journal.dto;

import java.util.Set;

public class DisciplineDTO {
    private Integer id;
    private String name;
    private UserDTO appointedTeacherPartialDTO;
    private Set<Integer> groupIds;
    private Set<GradeDTO> gradeDTOS;

    // Конструкторы
    public DisciplineDTO(Integer id, String name) {
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
    public Set<Integer> getGroupIds() {
        return groupIds;
    }
    public UserDTO getAppointedTeacherPartialDTO() {
        return appointedTeacherPartialDTO;
    }
    public Set<GradeDTO> getGradeDTOS() {
        return gradeDTOS;
    }

    // Сеттеры
    public void setGroupIds(Set<Integer> groupIds) {
        this.groupIds = groupIds;
    }
    public void setAppointedTeacherPartialDTO(UserDTO appointedTeacherPartialDTO) {
        this.appointedTeacherPartialDTO = appointedTeacherPartialDTO;
    }
    public void setGradeDTOS(Set<GradeDTO> gradeDTOS) {
        this.gradeDTOS = gradeDTOS;
    }
}
