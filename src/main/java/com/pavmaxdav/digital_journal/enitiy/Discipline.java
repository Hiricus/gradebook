package com.pavmaxdav.digital_journal.enitiy;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity()
@Table(name = "disciplines")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointed_teacher_id")
    private User appointedTeacher;

    @ManyToMany(mappedBy = "disciplines")
    private Set<Group> groups = new HashSet<>();

    // Двусторонняя связь с оценками, выставленными по этой дисциплине
    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Grade> grades = new HashSet<>();

    // Конструкторы
    public Discipline() {}
    public Discipline(String name, User appointedTeacher) {
        this.name = name;
        this.appointedTeacher = appointedTeacher;
    }

    // Для сравнения используются название и назначенный преподаватель
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return Objects.equals(name, that.name) && Objects.equals(appointedTeacher, that.appointedTeacher);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, appointedTeacher);
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public User getAppointedTeacher() {
        return appointedTeacher;
    }
    public Set<Group> getGroups() {
        return groups;
    }
    public Set<Grade> getGrades() {
        return grades;
    }

    // Сеттеры
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    // Для добавления в множества
    public void addGroup(Group group) {
        groups.add(group);
    }
    public void addGrade(Grade grade) {
        grades.add(grade);
    }
}
