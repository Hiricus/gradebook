package com.pavmaxdav.digital_journal.archDTO;

import java.util.List;

public class UserWithGradesDTO {
    private Integer id;
    private String login;
    private String firstName;
    private String lastName;

    private List<GradesOnDisciplineDTO> gradesOnDisciplines;

    public UserWithGradesDTO(Integer id, String login, String firstName, String lastName) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }

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
    public List<GradesOnDisciplineDTO> getGradesOnDisciplines() {
        return gradesOnDisciplines;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
