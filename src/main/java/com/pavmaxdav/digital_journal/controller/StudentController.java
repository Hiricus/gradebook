package com.pavmaxdav.digital_journal.controller;

import com.pavmaxdav.digital_journal.dto.DisciplineDTO;
import com.pavmaxdav.digital_journal.dto.GradeDTO;
import com.pavmaxdav.digital_journal.dto.GroupDTO;
import com.pavmaxdav.digital_journal.dto.UserDTO;
import com.pavmaxdav.digital_journal.enitiy.*;
import com.pavmaxdav.digital_journal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/student")
public class StudentController {
    StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Смотреть данные о себе
    @GetMapping("/getByLogin/{login}")
    public ResponseEntity<Object> getInfoOnSelf(@PathVariable String login) {
        Optional<User> optionalUser = studentService.getUserByLogin(login);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = optionalUser.get().constructDTO();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // Видеть свою группу (только имена)
    @GetMapping("/groups/{login}")
    public ResponseEntity<Object> getUsersFromOwnGroup(@PathVariable String login) {
        System.out.println("In method");
        Optional<Group> optionalGroup = studentService.getStudentsGroup(login);
        if (optionalGroup.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GroupDTO groupDTO = optionalGroup.get().constructDTO();
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    // Видеть все свои дисциплины
    @GetMapping("/disciplines/{login}")
    public ResponseEntity<Object> getOwnDisciplines(@PathVariable String login) {
        Set<Discipline> disciplines = studentService.getStudentsDisciplines(login);

        List<DisciplineDTO> disciplineDTOS = new ArrayList<>();
        for (Discipline discipline : disciplines) {
            disciplineDTOS.add(discipline.constructDTO());
        }

        return new ResponseEntity<>(disciplineDTOS, HttpStatus.OK);
    }

    // Видеть все свои оценки
    @GetMapping("/grades/all/{login}")
    public ResponseEntity<Object> getAllOwnGrades(@PathVariable String login) {
        Set<Grade> grades = studentService.getAllStudentsGrades(login);

        List<GradeDTO> gradeDTOS = new ArrayList<>();
        for (Grade grade : grades) {
            gradeDTOS.add(grade.constructDTO());
        }

        return new ResponseEntity<>(gradeDTOS, HttpStatus.OK);
    }

    // Видеть свои оценки по определённой дисциплине
    @GetMapping("/grades/byDiscipline/{login}")
    public ResponseEntity<Object> getOwnGradesOnDiscipline(@PathVariable String login, @RequestParam(value = "Discipline-Id") Integer disciplineId) {
        Set<Grade> grades = studentService.getStudentsGradesOnDiscipline(login, disciplineId);

        List<GradeDTO> gradeDTOS = new ArrayList<>();
        for (Grade grade : grades) {
            gradeDTOS.add(grade.constructDTO());
        }

        System.out.println("In method");
        return new ResponseEntity<>(gradeDTOS, HttpStatus.OK);
    }
}
