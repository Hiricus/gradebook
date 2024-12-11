package com.pavmaxdav.digital_journal.enitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pavmaxdav.digital_journal.dto.DisciplineDTO;
import com.pavmaxdav.digital_journal.dto.GroupDTO;
import com.pavmaxdav.digital_journal.dto.UserDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "groups_table")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> students = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "groups_disciplines", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "discipline_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Discipline> disciplines = new HashSet<>();

    // Конструкторы
    public Group() {}
    public Group(String name) {
        this.name = name;
    }

    // Для равенства используется только название группы
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Set<User> getStudents() {
        return students;
    }
    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    // Сеттеры
    public void setStudents(Set<User> students) {
        this.students = students;
    }
    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    // Изменялки повонялки
    public void addStudent(User student) {
        this.students.add(student);
    }
    public void removeStudent(User student) {
        this.students.remove(student);
    }
    public void addDiscipline(Discipline discipline) {
        this.disciplines.add(discipline);
    }
    public void removeDiscipline(Discipline discipline) {
        this.disciplines.remove(discipline);
    }


    public GroupDTO constructDTO() {
        GroupDTO groupDTO = new GroupDTO(this.getId(), this.getName());

        // Собираем и добавляем частичную инфу о пользователях
        Set<UserDTO> userPartialInfo = this.getStudents().stream().map(User::constructPartialDTO).collect(Collectors.toSet());
        groupDTO.setUserPartialInfo(userPartialInfo);

        // Собираем и добавляем инфу о дисциплинах
        Set<DisciplineDTO> disciplineDTOS = this.getDisciplines().stream().map(Discipline::constructDTO).collect(Collectors.toSet());
        groupDTO.setDisciplineDTOS(disciplineDTOS);

        return groupDTO;
    }
}
