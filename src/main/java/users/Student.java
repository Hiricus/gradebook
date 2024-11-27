package users;

import org.example.Discipline;

import java.util.ArrayList;

public class Student extends User {
    private String group;
    private ArrayList<Discipline> disciplines = new ArrayList<Discipline>();

    public Student(int id) {
        super(id);
    }

    public Student(int id, String firstName, String lastName, String group) {
        super(id, firstName, lastName);
        this.group = group;
    }

    public void setDisciplines(ArrayList<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public void addDiscipline(Discipline discipline) {
        disciplines.add(discipline);
    }
}
