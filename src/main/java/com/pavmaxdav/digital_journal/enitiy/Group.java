package com.pavmaxdav.digital_journal.enitiy;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "groups_table")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> students = new HashSet<>();

    // Тут был cascade ещё, ide сказала убрать
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "groups_disciplines", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "discipline_id", referencedColumnName = "id"))
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
}
