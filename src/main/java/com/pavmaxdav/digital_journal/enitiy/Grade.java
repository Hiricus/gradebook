package com.pavmaxdav.digital_journal.enitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pavmaxdav.digital_journal.dto.GradeDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "grade", nullable = false)
    private String grade;

    // Дата и время (без часового пояса)
    @Column(name = "given_date_time", nullable = false)
    private LocalDateTime dateTime;

    // Двусторонняя связь с пользователем получившим оценку
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_owner_id")
    private User gradeOwner;

    // Двусторонняя связь с дисциплиной, по которой выставлены оценки
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "discipline_id", nullable = true)
    @JsonIgnore
    private Discipline discipline;

    // Конструкторы
    public Grade() {}
    public Grade(String grade, User gradeOwner, Discipline discipline) {
        this.grade = grade;
        this.gradeOwner = gradeOwner;
        this.discipline = discipline;
        this.dateTime = LocalDateTime.now();
    }
    public Grade(String grade, LocalDateTime dateTime, User gradeOwner, Discipline discipline) {
        this.grade = grade;
        this.dateTime = dateTime;
        this.gradeOwner = gradeOwner;
        this.discipline = discipline;
    }

    // Для сравнения используется сама оценка, дата выставления, обладатель оценки и дисциплина
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return Objects.equals(grade, grade1.grade) && Objects.equals(dateTime, grade1.dateTime) && Objects.equals(gradeOwner, grade1.gradeOwner) && Objects.equals(discipline, grade1.discipline);
    }
    @Override
    public int hashCode() {
        return Objects.hash(grade, dateTime, gradeOwner, discipline);
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", grade='" + grade + '\'' +
                ", dateTime=" + dateTime +
                ", gradeOwner=" + gradeOwner +
                ", discipline=" + discipline +
                '}';
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public String getGrade() {
        return grade;
    }
    public User getGradeOwner() {
        return gradeOwner;
    }
    public Discipline getDiscipline() {
        return discipline;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // Сеттеры
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public void setGradeOwner(User gradeOwner) {
        this.gradeOwner = gradeOwner;
    }
    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public GradeDTO constructDTO() {
        GradeDTO gradeDTO = new GradeDTO(this.getId(), this.getGrade(), this.getDateTime(), this.getGradeOwner().constructPartialDTO());
        return gradeDTO;
    }
}
