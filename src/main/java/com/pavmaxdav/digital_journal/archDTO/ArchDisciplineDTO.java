package com.pavmaxdav.digital_journal.archDTO;

import com.pavmaxdav.digital_journal.enitiy.Group;

import java.util.ArrayList;
import java.util.List;

public class ArchDisciplineDTO {
    private Integer id;
    private String name;
    private List<ArchGroupDTO> archGroupDTOS = new ArrayList<>();

    public ArchDisciplineDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<ArchGroupDTO> getArchGroupDTOS() {
        return archGroupDTOS;
    }


    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setArchGroupDTOS(List<ArchGroupDTO> archGroupDTOS) {
        this.archGroupDTOS = archGroupDTOS;
    }
}
