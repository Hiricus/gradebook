package com.pavmaxdav.digital_journal.enitiy;

import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "grade")
    private String grade;

    // Двусторонняя связь с пользователем получившим оценку
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_owner_id")
    private User gradeOwner;

    // Двусторонняя связь с дисциплиной, по которой выставлены оценки
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    public Grade() {}
    public Grade(String grade, User gradeOwner, Discipline discipline) {
        this.grade = grade;
        this.gradeOwner = gradeOwner;
        this.discipline = discipline;
    }

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
}
