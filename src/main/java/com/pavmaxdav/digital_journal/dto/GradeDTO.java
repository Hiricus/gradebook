package com.pavmaxdav.digital_journal.dto;

import java.time.LocalDateTime;

public class GradeDTO {
    private Integer id;
    private String grade;
    private LocalDateTime dateTime;
    private UserDTO gradeOwnerPartialDTO;

    // Конструкторы
    public GradeDTO(Integer id, String grade, LocalDateTime dateTime, UserDTO gradeOwnerPartialDTO) {
        this.id = id;
        this.grade = grade;
        this.dateTime = dateTime;
        this.gradeOwnerPartialDTO = gradeOwnerPartialDTO;
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public String getGrade() {
        return grade;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public UserDTO getGradeOwnerPartialDTO() {
        return gradeOwnerPartialDTO;
    }

    // Сеттеры
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public void setGradeOwnerPartialDTO(UserDTO gradeOwnerPartialDTO) {
        this.gradeOwnerPartialDTO = gradeOwnerPartialDTO;
    }
}
