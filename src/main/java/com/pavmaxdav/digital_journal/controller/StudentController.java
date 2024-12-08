package com.pavmaxdav.digital_journal.controller;

import com.pavmaxdav.digital_journal.enitiy.Discipline;
import com.pavmaxdav.digital_journal.enitiy.Grade;
import com.pavmaxdav.digital_journal.enitiy.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    // Смотреть данные о себе
    @GetMapping("/{login}")
    public User getInfoOnSelf(@PathVariable String login) {
        return null;
    }

    // Видеть свою группу (только имена)
    @GetMapping("/groups/{groupName}")
    public List<String> getUsersFromOwnGroup(@PathVariable String groupName) {
        return null;
    }

    // Видеть все свои дисциплины
    @GetMapping("/disciplines/{login}")
    public List<Discipline> getOwnDisciplines(@PathVariable String login) {
        return null;
    }

    // Видеть все свои оценки
    @GetMapping("/grades/{login}/all")
    public List<Grade> getAllOwnGrades(@PathVariable String login) {
        return null;
    }

    // Видеть свои оценки по определённой дисциплине
    @GetMapping("/grades/{login}/{groupName}")
    public List<Grade> getOwnGradesOnDiscipline(@PathVariable String login, @PathVariable String groupName) {
        return null;
    }
}
