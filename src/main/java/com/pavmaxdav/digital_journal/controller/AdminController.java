package com.pavmaxdav.digital_journal.controller;

import com.pavmaxdav.digital_journal.dto.CreateUserDTO;
import com.pavmaxdav.digital_journal.dto.DisciplineDTO;
import com.pavmaxdav.digital_journal.dto.UserDTO;
import com.pavmaxdav.digital_journal.enitiy.*;
import com.pavmaxdav.digital_journal.service.RoleService;
import com.pavmaxdav.digital_journal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            List<UserDTO> userPartialDTOS = users.stream().map(User::constructPartialDTO).toList();
            return new ResponseEntity<>(userPartialDTOS, HttpStatus.OK);
        }
    }

    // Получить пользователя по логину
    @GetMapping("getUser/{login}")
    public ResponseEntity<Object> getUserByLogin(@PathVariable String login) {
        Optional<User> optionalUser = adminService.getUserByLogin(login);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserDTO userDTO = optionalUser.get().constructDTO();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
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

    // Добавить пользователя
    @PostMapping("/users/addUser")
    public ResponseEntity<Object> addNewUser(@RequestBody CreateUserDTO userDTO) {
        User newUser = userDTO.constructUser();
        System.out.println("DTO: " + userDTO.toString());
        System.out.println("User: " + newUser.toString());

        // Если объект не содержит данных о группе
        if (userDTO.getGroup() == null) {
            adminService.addUser(newUser);
            return new ResponseEntity<>(HttpStatus.OK);

        } else {  // Если объект содержит данные о группе
            Optional<Group> optionalGroup = adminService.getGroup(userDTO.getGroup());

            // Если описанная группа есть
            if (optionalGroup.isPresent()) {
                adminService.addUser(newUser);
                adminService.addUserToGroup(newUser.getLogin(), optionalGroup.get().getName());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Удалять пользователей
    @DeleteMapping("/remove/user/{id}")
    public ResponseEntity<Object> removeUser(@PathVariable Integer id) {
        Optional<User> optionalUser = adminService.getUserById(id);
        if (optionalUser.isPresent()) {
            String login = optionalUser.get().getLogin();
            adminService.removeUser(login);
            adminService.removeUser(login);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
            List<DisciplineDTO> disciplineDTOS = disciplines.stream().map(Discipline::constructDTO).toList();
            return new ResponseEntity<>(disciplineDTOS, HttpStatus.OK);
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
    @PostMapping("/add/group")
    public ResponseEntity<Object> addNewGroup(@RequestParam("group-name") String groupName) {
        Optional<Group> optionalGroup = adminService.getGroup(groupName);
        if (optionalGroup.isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        adminService.addNewGroup(groupName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    // Удалять группы
    @DeleteMapping("/remove/group/{id}")
    public ResponseEntity<Object> removeGroup(@PathVariable Integer id) {
        Optional<Group> optionalGroup = adminService.getGroupById(id);
        if (optionalGroup.isPresent()) {
            adminService.removeGroup(optionalGroup.get().getName());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Добавлять пользователей в группы
    @PutMapping("/admin/add/userToGroup")
    public void addStudentToGroup(@RequestParam("group-name") String groupName, @RequestParam("student-login") String login) {
        Optional<Group> optionalGroup = adminService.getGroup(groupName);

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
