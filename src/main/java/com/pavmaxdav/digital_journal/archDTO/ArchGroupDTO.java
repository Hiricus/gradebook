package com.pavmaxdav.digital_journal.archDTO;

import java.util.ArrayList;
import java.util.List;

public class ArchGroupDTO {
    private Integer id;
    private String name;
    private List<UserWithGradesDTO> userWithGradesDTOS = new ArrayList<>();


    public ArchGroupDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<UserWithGradesDTO> getUserWithGradesDTOS() {
        return userWithGradesDTOS;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUserWithGradesDTOS(List<UserWithGradesDTO> userWithGradesDTOS) {
        this.userWithGradesDTOS = userWithGradesDTOS;
    }
}
