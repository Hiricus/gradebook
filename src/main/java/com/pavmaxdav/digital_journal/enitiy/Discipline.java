package com.pavmaxdav.digital_journal.enitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pavmaxdav.digital_journal.dto.DisciplineDTO;
import com.pavmaxdav.digital_journal.dto.GradeDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity()
@Table(name = "disciplines")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "appointed_teacher_id")
    private User appointedTeacher;

    @ManyToMany(mappedBy = "disciplines")
    @JsonIgnore
    private Set<Group> groups = new HashSet<>();

    // Двусторонняя связь с оценками, выставленными по этой дисциплине
    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
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
    public void setAppointedTeacher(User appointedTeacher) {
        this.appointedTeacher = appointedTeacher;
    }

    // Для добавления в множества
    public void addGroup(Group group) {
        groups.add(group);
    }
    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    // Для удаления из множеств
    public void removeGroup(Group group) {
        groups.remove(group);
    }
    public void removeGrade(Grade grade) {
        grades.remove(grade);
    }


    public DisciplineDTO constructDTO() {
        DisciplineDTO disciplineDTO = new DisciplineDTO(this.getId(), this.getName());

        // Составляем список айдишников групп, у которых проводится эта дисциплина
        Set<Integer> groupIds = new HashSet<>();
        for (Group group : this.getGroups()) {
            groupIds.add(group.getId());
        }
        disciplineDTO.setGroupIds(groupIds);

        // Добавляем частичный DTO преподавателя
        disciplineDTO.setAppointedTeacherPartialDTO(this.getAppointedTeacher().constructPartialDTO());

        // Добавляем список DTO для оценок
        Set<GradeDTO> gradeDTOS = this.getGrades().stream().map(Grade::constructDTO).collect(Collectors.toSet());
        disciplineDTO.setGradeDTOS(gradeDTOS);

        return disciplineDTO;
    }
}
