package com.pavmaxdav.digital_journal.service;

import com.pavmaxdav.digital_journal.enitiy.*;
import com.pavmaxdav.digital_journal.model.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private GroupRepository groupRepository;
    private DisciplineRepository disciplineRepository;
    private GradeRepository gradeRepository;

    @Autowired
    public AdminService(UserRepository userRepository, RoleRepository roleRepository, GroupRepository groupRepository, DisciplineRepository disciplineRepository, GradeRepository gradeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
        this.disciplineRepository = disciplineRepository;
        this.gradeRepository = gradeRepository;
    }

    // Получить всех пользователей
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Получить пользователя по логину
    @Transactional
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    // Получить группу пользователя (Только если это студент)
    @Transactional
    public Optional<Group> getStudentsGroup(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("No such user with login: " + login);
        }
        if (!(optionalUser.get().hasRole("student"))) {
            throw new EntityTypeException("The user: " + login + " is not a student", "reference idk");
        }
        // Получаем группу
        User user = optionalUser.get();
        return Optional.of(user.getGroup());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User userEntity = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(userEntity.getLogin(), userEntity.getPassword(), mapRolesToAuthorities(userEntity.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }


    // Добавить нового пользователя, возвращает этого же пользователя
    @Transactional
    public User addUser(User user) {
        System.out.println("in method");
        if (getUserByLogin(user.getLogin()).isPresent()) {
            throw new EntityExistsException("User " + user.getLogin() + " already exists");
        } else {
            // Привязываем пользователя к ролям на случай, если они уже есть в бд (а они обычно там есть)
            Set<Role> existingRoles = new HashSet<>();
            for (Role role : user.getRoles()) {
                Role existingRole = roleRepository.findRoleByName(role.getName())
                        .orElseGet(() -> roleRepository.save(role));
                existingRoles.add(existingRole);
            }
            user.setRoles(existingRoles);

            // Сохраняем пользователя
            return userRepository.save(user);
        }
    }

    // Вызывать два раза (да да, это пипец)
    // Удалить пользователя
    // todo
    @Transactional
    public User removeUser(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Отвязываем пользователя от всех его ролей
        for (Role role : new ArrayList<>(user.getRoles())) {
            role.removeUser(user);
            user.removeRole(role);
        }

        // todo
        // Удаляем оценки
        for (Grade grade : new ArrayList<>(user.getGrades())) { // Копируем коллекцию, чтобы избежать ConcurrentModificationException
            user.getGrades().remove(grade);
            grade.setGradeOwner(null);

            Discipline discipline = grade.getDiscipline();
            discipline.getGrades().remove(grade);

            grade.setDiscipline(null);
            gradeRepository.deleteById(grade.getId());
        }

        // Отвязываем преподов от проводимых дисциплин
        for (Discipline discipline : user.getHeldDisciplines()) {
            discipline.setAppointedTeacher(null);
            user.getHeldDisciplines().remove(discipline);
        }
        // Удаляем пользователя из группы
        if (user.getGroup() != null) {
            user.getGroup().removeStudent(user);
            user.setGroup(null);
        }

        // Удаляем пользователя
        user.getLogin();
        System.out.println("User " + user.getId());
        userRepository.deleteById(user.getId());
        System.out.println("Deleted");
        return user;
    }

//    @Transactional
//    public User removeUser(String login) {
//        User user = userRepository.findByLogin(login)
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        // Отвязываем пользователя от ролей
//        for (Role role : new ArrayList<>(user.getRoles())) {
//            role.getUsers().remove(user); // Обновляем обратную связь
//        }
//        user.getRoles().clear();
//
//        // Удаляем оценки
//        for (Grade grade : new ArrayList<>(user.getGrades())) {
//            grade.setGradeOwner(null);
//            grade.setDiscipline(null);
//        }
//        user.getGrades().clear();
//
//        // Отвязываем дисциплины
//        for (Discipline discipline : new ArrayList<>(user.getHeldDisciplines())) {
//            discipline.setAppointedTeacher(null);
//        }
//        user.getHeldDisciplines().clear();
//
//        // Удаляем пользователя из группы
//        if (user.getGroup() != null) {
//            user.getGroup().removeStudent(user);
//            user.setGroup(null);
//        }
//
//        // Удаляем пользователя
//        userRepository.delete(user);
//
//        return user;
//    }

    // Добавить роль пользователю
    @Transactional
    public void addRoleToUser(String userLogin, Role givenRole) {
        Optional<User> optionalUser = userRepository.findByLogin(userLogin);
        Optional<Role> optionalRole = roleRepository.findRoleByName(givenRole.getName());
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = optionalRole.orElseThrow(() -> new EntityNotFoundException("Role not found"));

        user.addRole(role);
        userRepository.save(user);
    }

    // Удалить роль у пользователя
    @Transactional
    public void removeRoleFromUser(String userLogin, Role givenRole) {
        Optional<User> optionalUser = userRepository.findByLogin(userLogin);
        Optional<Role> optionalRole = roleRepository.findRoleByName(givenRole.getName());
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = optionalRole.orElseThrow(() -> new EntityNotFoundException("Role not found"));

        if (user.hasRole(role.getName())) {
            // Удаляем пользователя из списка соответствующей роли
            role.getUsers().remove(user);

            // Удаляем роль у пользователя
            user.removeRole(role);
        }
    }

    // Получить все группы
    @Transactional
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    // Получить группу по названию
    @Transactional
    public Optional<Group> getGroup(String name) {
        return groupRepository.findByName(name);
    }

    // Добавить группу
    @Transactional
    public Group addNewGroup(String name) {
        Optional<Group> optionalGroup = groupRepository.findByName(name);
        if (optionalGroup.isPresent()) {
            throw new EntityExistsException("Group " + name + " already exists");
        } else {
            Group group = new Group(name);
            groupRepository.save(group);
            return group;
        }
    }

    // Удалить группу
    @Transactional
    public Group removeGroup(String name) {
        Group group = groupRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Group " + name + " does not exist"));

        // Отписываем все дисциплины от удаляемой группы
        for (Discipline discipline : group.getDisciplines()) {
            discipline.removeGroup(group);
        }
        // Отписываем всех студентов от удаляемой группы
        for (User student : group.getStudents()) {
            student.setGroup(null);
        }

        // Удаляем группу
        groupRepository.delete(group);
        return group;
    }

    // Добавить пользователя в группу
    @Transactional
    public void addUserToGroup(String login, String groupName) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        Optional<Group> optionalGroup = groupRepository.findByName(groupName);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User " + login + " not found"));
        Group group = optionalGroup.orElseThrow(() -> new EntityNotFoundException("Group " + groupName + " not found"));

        // Формируем связь с двух сторон
        user.setGroup(group);
        group.addStudent(user);
    }

    // Удалить пользователя из группы
    @Transactional
    public void removeUserFromGroup(String login, String groupName) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        Optional<Group> optionalGroup = groupRepository.findByName(groupName);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User " + login + " not found"));
        Group group = optionalGroup.orElseThrow(() -> new EntityNotFoundException("Group " + groupName + " not found"));

        // Удаляем связь с двух сторон
        group.removeStudent(user);
        user.setGroup(null);
    }

    // Найти все дисциплины
    @Transactional
    public List<Discipline> getAllDisciplines() {
        return disciplineRepository.findAll();
    }

    // Найти дисциплину
    @Transactional
    public Optional<Discipline> getDiscipline(String disciplineName, String teacherLogin) {
        User teacher = this.getUserByLogin(teacherLogin).orElseThrow(() -> new EntityNotFoundException("User " + teacherLogin + " not found"));
        return disciplineRepository.findByAppointedTeacherAndName(teacher, disciplineName);
    }

    // Добавить новую дисциплину
    @Transactional
    public Discipline addNewDiscipline(String name, String teacherLogin) {
        User user = this.getUserByLogin(teacherLogin).orElseThrow(() -> new EntityNotFoundException("User " + teacherLogin + " not found"));

        // Если назначенный челик не препод - кидаем исключение хз какого типа
//        if (!(user.hasRole("ROLE_TEACHER"))) {
//            throw new EntityTypeException("User " + teacherLogin + " must be a teacher", "reference idk");
//        }

        // Если такая дисциплина есть - кидаем исключение
        if (disciplineRepository.findByAppointedTeacherAndName(user, name).isPresent()) {
            throw new EntityExistsException("Discipline " + name + ", led by " + teacherLogin + " already exists");
        }

        Discipline discipline = new Discipline(name, user);
        // Добавляем дисциплину в список проводимых пользователем
        user.getHeldDisciplines().add(discipline);
        // Сохраняем дисциплину
        disciplineRepository.save(discipline);
        return discipline;
    }

    // Удалить дисциплину (Что б@#$ь тут произошло? Пять часов фиксил, так и не понял. Поставил в Discipline.grades LAZY и заработало)
    @Transactional
    public Discipline removeDiscipline(String disciplineName, String teacherLogin) {
        User teacher = this.getUserByLogin(teacherLogin).orElseThrow(() -> new EntityNotFoundException("User " + teacherLogin + " not found"));
        Discipline discipline = disciplineRepository.findByAppointedTeacherAndName(teacher, disciplineName)
                .orElseThrow(() -> new EntityNotFoundException("Discipline " + disciplineName + ", led by " + teacherLogin + " does not exist"));

        System.out.println("In method, id: " + discipline.getId());

        // Удаляем инфу о дисциплине у назначенного на неё препода
        User appointedTeacher = discipline.getAppointedTeacher();
        if (appointedTeacher != null) {
            appointedTeacher.getHeldDisciplines().remove(discipline);
        }

        // удаляем инфу о дисциплине у всех групп, у которых она была
        for (Group group : discipline.getGroups()) {
            group.removeDiscipline(discipline);
        }

        // Удаляем оценки
        for (Grade grade : new ArrayList<>(discipline.getGrades())) { // Копируем коллекцию, чтобы избежать ConcurrentModificationException
            //System.out.println("Grade id " + grade.getId());

            User gradeOwner = grade.getGradeOwner();
            if (gradeOwner != null) {
                gradeOwner.getGrades().remove(grade);
                grade.setGradeOwner(null);
            }

            //System.out.println(grade.toString());
            grade.setDiscipline(null);
            discipline.getGrades().remove(grade);
            gradeRepository.deleteById(grade.getId());
        }

        disciplineRepository.delete(discipline);
        System.out.println("Discipline deleted");
        return discipline;
    }

    // Добавить дисциплину группе
    @Transactional
    public void addDisciplineToGroup(String groupName, String disciplineName, String teacherLogin) {
        Group group = groupRepository.findByName(groupName).orElseThrow(() -> new EntityNotFoundException("Group " + groupName +" does not exist"));
        Discipline discipline = this.getDiscipline(disciplineName, teacherLogin).orElseThrow(() -> new EntityNotFoundException("Discipline " + disciplineName + ", led by " + teacherLogin + " does not exist"));

        // Если дисциплина уже добавлена в группу
        for (Group disciplineGroup : this.getDiscipline(disciplineName, teacherLogin).get().getGroups()) {
            if (disciplineGroup.getName().equals(groupName)) {
                throw new EntityExistsException("Discipline is already added to the group " + groupName);
            }
        }

        group.addDiscipline(discipline);
        discipline.addGroup(group);
    }

    // Убрать дисциплину из группы
    @Transactional
    public void removeDisciplineFromGroup(String groupName, String disciplineName, String teacherLogin) {
        Group group = groupRepository.findByName(groupName).orElseThrow(() -> new EntityNotFoundException("Group " + groupName +" does not exist"));
        Discipline discipline = this.getDiscipline(disciplineName, teacherLogin).orElseThrow(() -> new EntityNotFoundException("Discipline " + disciplineName + ", led by " + teacherLogin + " does not exist"));

        group.removeDiscipline(discipline);
        discipline.removeGroup(group);
    }

    // Смотреть оценки пользователя по дисциплине
    @Transactional
    public Set<Grade> getUsersGradesOnDiscipline(String studentLogin, String disciplineName, String teacherLogin) {
        User student = this.getUserByLogin(studentLogin).orElseThrow(() -> new EntityNotFoundException("Student " + studentLogin + " does not exist"));
        Discipline discipline = this.getDiscipline(disciplineName, teacherLogin).orElseThrow(() -> new EntityNotFoundException("Discipline " + disciplineName + " led by " + teacherLogin + " not found"));

        Set<Grade> allGrades = discipline.getGrades();
        Set<Grade> studentsGrades = new HashSet<>();

        for (Grade grade : allGrades) {
            if (grade.getGradeOwner().getLogin().equals(studentLogin)) {
                studentsGrades.add(grade);
            }
        }
        return studentsGrades;
    }

    // Ставить оценки
    @Transactional
    public Grade setGrade(String gradeItself, String studentLogin, String disciplineName, String teacherLogin) {
        // Получаем пользователя и дисциплину
        User student = this.getUserByLogin(studentLogin).orElseThrow(() -> new EntityNotFoundException("Student " + studentLogin + " does not exist"));
        Discipline discipline = this.getDiscipline(disciplineName, teacherLogin).orElseThrow(() -> new EntityNotFoundException("Discipline " + disciplineName + " led by " + teacherLogin + " not found"));

        Grade grade = new Grade(gradeItself, student, discipline);
        gradeRepository.save(grade);
        return grade;
    }

    // Менять оценки
    @Transactional
    public Grade changeGrade(Integer id, String changedGrade) {
        Optional<Grade> optionalGrade = gradeRepository.findById(id);
        Grade grade = optionalGrade.orElseThrow(() -> new EntityNotFoundException("No grade with id " + id));

        grade.setGrade(changedGrade);
        gradeRepository.save(grade);
        return grade;
    }

    // Удалять оценки
    @Transactional
    public Grade removeGrade(Integer id) {
        Grade grade = gradeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No grade with id " + id));

        // Удаляем оценку из списка оценок пользователя
        User gradeOwner = grade.getGradeOwner();
        gradeOwner.getGrades().remove(grade);

        // Удаляем оценку из списка оценок дисциплины
        Discipline gradeDiscipline = grade.getDiscipline();
        gradeDiscipline.getGrades().remove(grade);

        // Удаляем оценку из БД
        gradeRepository.delete(grade);
        return grade;
    }

    // Смотреть все оценки
    @Transactional
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }
}
