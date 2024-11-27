package users;

import org.example.Discipline;
import org.example.StudentGroup;

import java.util.ArrayList;


public class Teacher extends User {
    private ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
    private ArrayList<StudentGroup> groups = new ArrayList<StudentGroup>();

    // Конструкторы
    public Teacher(int id) {
        super(id);
    }
    public Teacher(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    // Геттеры для групп
    public ArrayList<Discipline> getDisciplines() {
        return disciplines;
    }
    public ArrayList<StudentGroup> getGroups() {
        return groups;
    }

    // Сеттеры для групп
    public void setDisciplines(ArrayList<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
    public void setGroups(ArrayList<StudentGroup> groups) {
        this.groups = groups;
    }

    // Добавить запись в группу
    public void addGroup(StudentGroup group) {
        groups.add(group);
    }
    public void addDiscipline(Discipline discipline) {
        disciplines.add(discipline);
    }
}
