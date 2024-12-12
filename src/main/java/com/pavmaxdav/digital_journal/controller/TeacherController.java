package com.pavmaxdav.digital_journal.controller;

import com.pavmaxdav.digital_journal.dto.DisciplineDTO;
import com.pavmaxdav.digital_journal.dto.UserDTO;
import com.pavmaxdav.digital_journal.enitiy.Discipline;
import com.pavmaxdav.digital_journal.enitiy.Grade;
import com.pavmaxdav.digital_journal.enitiy.Group;
import com.pavmaxdav.digital_journal.enitiy.User;
import com.pavmaxdav.digital_journal.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // Смотреть данные о себе
    @GetMapping("/getByLogin/{login}")
    public ResponseEntity<Object> getInfoOnSelf(@PathVariable String login) {
        Optional<User> optionalUser = teacherService.getUserByLogin(login);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserDTO userDTO = optionalUser.get().constructDTO();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // Смотреть данные о своих дисциплинах
    @GetMapping("/disciplines/{login}")
    public ResponseEntity<Object> getAllHeldDisciplines(@PathVariable String login) {
        Set<Discipline> disciplines = teacherService.getAllTeachersDisciplines(login);

        List<DisciplineDTO> disciplineDTOS = new ArrayList<>();
        for (Discipline discipline : disciplines) {
            disciplineDTOS.add(discipline.constructDTO());
        }

        return new ResponseEntity<>(disciplineDTOS, HttpStatus.OK);
    }

    // Смотреть данные о группах, где он ведёт
    @GetMapping("/groups/getByLogin/{login}")
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
