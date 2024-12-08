package com.pavmaxdav.digital_journal.controller;

import com.pavmaxdav.digital_journal.enitiy.Discipline;
import com.pavmaxdav.digital_journal.enitiy.Grade;
import com.pavmaxdav.digital_journal.enitiy.Group;
import com.pavmaxdav.digital_journal.enitiy.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    // Смотреть данные о себе
    @GetMapping("/{login}")
    public User getInfoOnSelf(@PathVariable String login) {
        return null;
    }

    // Смотреть данные о своих дисциплинах
    @GetMapping("/disciplines/{login}")
    public List<Discipline> getAllHeldDisciplines(@PathVariable String login) {
        return null;
    }

    // Смотреть данные о группах, где он ведёт
    @GetMapping("/groups/{login}")
    public List<Group> getAllHeldGroups(@PathVariable String login) {
        return null;
    }

    // Смотреть оценки
    @GetMapping("/grades/{login}")
    public List<Grade> getAllStudentsGrades(@PathVariable String login) {
        return null;
    }

    // Ставить оценки
    @PostMapping("/grades/set/{login}")
    public Grade setGradeToStudent(@PathVariable String login, @RequestParam Grade grade) {
        return null;
    }

    @DeleteMapping("/grades/delete")
    public Grade deleteGrade(@RequestParam Grade grade) {
        return null;
    }
}
