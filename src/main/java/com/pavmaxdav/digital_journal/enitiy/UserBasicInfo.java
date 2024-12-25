package com.pavmaxdav.digital_journal.enitiy;


// Класс нужен для передачи инфы о пользователях, которую можно разглашать
public class UserBasicInfo {
    private Integer id;
    private String login;
    private String firstName;
    private String lastName;

    // Конструкторы
    public UserBasicInfo(String login, String firstName, String lastName) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public UserBasicInfo(Integer id, String login, String firstName, String lastName) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    // Сеттеры
    public void setId(Integer id) {
        this.id = id;
    }
}
