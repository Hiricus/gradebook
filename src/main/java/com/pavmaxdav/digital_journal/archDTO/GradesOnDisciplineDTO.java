package com.pavmaxdav.digital_journal.archDTO;

import java.time.LocalDateTime;

public class GradesOnDisciplineDTO {
    private Integer id;
    private String grade;
    private LocalDateTime dateTime;

    public GradesOnDisciplineDTO(Integer id, String grade, LocalDateTime dateTime) {
        this.id = id;
        this.grade = grade;
        this.dateTime = dateTime;
    }
}
