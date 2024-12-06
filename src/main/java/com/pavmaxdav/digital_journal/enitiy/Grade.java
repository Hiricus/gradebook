package com.pavmaxdav.digital_journal.enitiy;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_owner_id")
    private User gradeOwner;

    // Двусторонняя связь с дисциплиной, по которой выставлены оценки
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    // Конструкторы
    public Grade() {}
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
}
