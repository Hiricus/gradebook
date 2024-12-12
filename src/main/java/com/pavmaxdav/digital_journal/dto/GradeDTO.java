package com.pavmaxdav.digital_journal.dto;

import java.time.LocalDateTime;

public class GradeDTO {
    private Integer id;
    private String grade;
    private LocalDateTime dateTime;
    private Integer ownerId;
    private String ownerLogin;

    // Конструкторы
    public GradeDTO(Integer id, String grade, LocalDateTime dateTime, Integer ownerId, String ownerLogin) {
        this.id = id;
        this.grade = grade;
        this.dateTime = dateTime;
        this.ownerId = ownerId;
        this.ownerLogin = ownerLogin;
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
    public Integer getOwnerId() {
        return ownerId;
    }
    public String getOwnerLogin() {
        return ownerLogin;
    }

    // Сеттеры
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
