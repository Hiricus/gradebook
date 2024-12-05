package com.pavmaxdav.digital_journal.enitiy;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "grous")
public class Group {
    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<User> students;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "groups_disciplines", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "discipline_id", referencedColumnName = "id"))
    private Set<Discipline> disciplines;

    public Group() {}
    public Group(String name) {
        this.name = name;
    }

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

    public void setStudents(Set<User> students) {
        this.students = students;
    }
    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
}
