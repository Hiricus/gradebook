package example;

import com.pavmaxdav.digital_journal.enitiy.User;

import java.util.ArrayList;

public abstract class Group {
    private String name;
    private ArrayList<User> members = new ArrayList<User>();

    // Конструкторы
    public Group(String name) {
        this.name = name;
    }

    // Геттеры
    public String getName() {
        return name;
    }
    public ArrayList<User> getMembers() {
        return members;
    }

    // Сеттеры для групп
    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    // Добавить пользователя в группу
    public void addMember(User user) {
        members.add(user);
    }


}
