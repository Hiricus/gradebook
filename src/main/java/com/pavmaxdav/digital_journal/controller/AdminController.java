package com.pavmaxdav.digital_journal.controller;

import com.pavmaxdav.digital_journal.enitiy.*;
import com.pavmaxdav.digital_journal.service.RoleService;
import com.pavmaxdav.digital_journal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    private RoleService roleService;

    @Autowired
    public AdminController(AdminService adminService, RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    // Видеть всех юзеров
    @GetMapping("/users/getAll")
    public ResponseEntity<Object> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    // Видеть все роли
    @GetMapping("/roles/getAll")
    public ResponseEntity<Object> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(roles, HttpStatus.OK);
        }
    }

    // Добавлять пользователей
    @PostMapping("/users/add/")
    public User addNewUser(@RequestParam User givenUser) {
        return null;
    }
    // Удалять пользователей
    @DeleteMapping("/users/remove/{login}")
    public User removeUser(@PathVariable String login) {
        return adminService.removeUser(login);
    }

    // Добавлять роли пользователям
    @PutMapping("/users/addRole")
    public void addRoleToUser(@RequestHeader("user-login") String login, @RequestHeader("role") String roleName) {

    }
    // Удалять роли у пользователей
    @PutMapping("/users/removeRole")
    public void removeRoleFromUser(@RequestHeader("user-login") String login, @RequestHeader("role") String roleName) {

    }

    // Видеть все дисциплины
    @GetMapping("/disciplines/getAll")
    public ResponseEntity<Object> getAllDisciplines() {
        List<Discipline> disciplines = adminService.getAllDisciplines();

        if (disciplines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(disciplines, HttpStatus.OK);
        }
    }

    // Добавлять дисциплины (Названия могут повторяться, пока назначены разные преподы)
    @PostMapping("/disciplines/add/{disciplineName}")
    public Discipline addDiscipline(@PathVariable String disciplineName) {
        return null;
    }
    // Удалять дисциплины
    @DeleteMapping("/disciplines/remove/{disciplineName}")
    public Discipline removeDiscipline(@PathVariable String disciplineName) {
        return null;
    }

    // Назначать препода на дисциплину
    @PutMapping("/disciplines/assignTeacher")
    public void assignTeacherToDiscipline(@RequestHeader("discipline") String disciplineName, @RequestHeader("teacher") String login) {

    }
    // Удалять препода с дисциплины
    @PutMapping("/disciplines/removeTeacher")
    public void removeTeacherFromDiscipline(@RequestHeader("discipline") String disciplineName, @RequestHeader("teacher") String login) {

    }

    // Видеть все группы
    @GetMapping("/groups/getAll")
    public List<Group> getAllGroups() {
        return adminService.getAllGroups();
    }

    // Добавлять группы
    @PostMapping("/groups/add/{name}")
    public Group addNewGroup(@PathVariable String name) {
        return null;
    }
    // Удалять группы
    @DeleteMapping("/groups/remove/{name}")
    public Group removeGroup(@PathVariable String name) {
        return null;
    }

    // Добавлять пользователей в группы
    @PutMapping("/groups/addUser")
    public void addStudentToGroup(@RequestHeader("group") String groupName, @RequestHeader("student") String login) {

    }
    // Удалять пользователей из групп
    @PutMapping("/groups/removeUser")
    public void removeStudentFromGroup(@RequestHeader("group") String groupName, @RequestHeader("student") String login) {

    }

    // Видеть все оценки
    @GetMapping("/grades/getAll")
    public List<Grade> getAllGrades() {
        return adminService.getAllGrades();
    }

    // Менять оценки
    // А как однозначно определить оценку я хз
    // Приехали
}
