package com.pavmaxdav.digital_journal;

import com.pavmaxdav.digital_journal.enitiy.Discipline;
import com.pavmaxdav.digital_journal.enitiy.Role;
import com.pavmaxdav.digital_journal.enitiy.User;
import com.pavmaxdav.digital_journal.model.RoleRepository;
import com.pavmaxdav.digital_journal.model.UserRepository;
import com.pavmaxdav.digital_journal.service.RoleService;
import com.pavmaxdav.digital_journal.service.AdminService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DigitalJournalApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(DigitalJournalApplication.class, args);
		UserRepository repository = context.getBean(UserRepository.class);
		AdminService adminService = context.getBean(AdminService.class);
		RoleService roleService = context.getBean(RoleService.class);

//		roleService.addRole(new Role("student"));
//		adminService.addRoleToUser("Hiricus", new Role("student"));
//		adminService.addRoleToUser("Filoriel", new Role("student"));
//		adminService.addRoleToUser("Bardoon", new Role("student"));

//		adminService.addUser(createUser1());
//		adminService.addNewGroup("ПИ-б-о-212");
//		adminService.addUserToGroup("Hiricus", "ПИ-б-о-212");
//		adminService.setGrade("S", "Hiricus", "Math", "Yul'ka");



//		adminService.addNewGroup("groupIg");
//		adminService.addUserToGroup("Bardoon", "groupIg");
		//adminService.removeGroup("11-A");
//		adminService.addUserToGroup("Filoriel", "11-A");
//		adminService.removeUserFromGroup("Bardoon", "11-A");

		//adminService.addUser(createPipets());
		//adminService.removeUser("Yul'ka");

//		adminService.addNewDiscipline("Math", "Yul'ka");
//		adminService.addDisciplineToGroup("ПИ-б-о-212", "Math", "Yul'ka");

//		adminService.setGrade("A", "Filoriel", "Math", "Yul'ka");
//		adminService.setGrade("S", "Filoriel", "Math", "Yul'ka");
//		adminService.setGrade("B", "Filoriel", "Math", "Yul'ka");



		//adminService.removeDisciplineFromGroup("11-A", "Math", "Yul'ka");
		//adminService.removeDiscipline("Math", "Yul'ka");

//		adminService.removeUser("Hiricus");
//		adminService.removeUser("Hiricus");


		//adminService.removeGrade(1602);




		//adminService.addUser(createUser1());
//		adminService.removeRoleFromUser("Hiricus", new Role("ROLE_TEACHER"));
//		adminService.removeRoleFromUser("Hiricus", new Role("ROLE_ADMIN"));
//		adminService.addNewGroup("ПИ-б-о-212");
//		adminService.addNewGroup("ПИ-б-о-211");


//		adminService.addUserToGroup("Hiricus", "ПИ-б-о-212");
//		adminService.addUserToGroup("Bananaws", "ПИ-б-о-212");
//		adminService.addUserToGroup("Maximk4", "ПИ-б-о-212");
//		adminService.addUserToGroup("TimIri", "ПИ-б-о-212");
//		adminService.addUserToGroup("KPoro", "ПИ-б-о-212");

//		adminService.addUserToGroup("Okneas", "ПИ-б-о-211");
//		adminService.addUserToGroup("Tolik228", "ПИ-б-о-211");
//		adminService.addUserToGroup("DanS", "ПИ-б-о-211");

//		adminService.addNewDiscipline("Управление разработкой", "VVChab");
//		adminService.addNewDiscipline("СТП", "VVChab");
//		adminService.addDisciplineToGroup("ПИ-б-о-212", "Управление разработкой", "VVChab");
//		adminService.addDisciplineToGroup("ПИ-б-о-211", "Управление разработкой", "VVChab");
//		adminService.addDisciplineToGroup("ПИ-б-о-212", "СТП", "VVChab");
//		adminService.addDisciplineToGroup("ПИ-б-о-211", "СТП", "VVChab");

//		adminService.setGrade("5", "Hiricus", "Управление разработкой", "VVChab");
//		adminService.setGrade("5", "Bananaws", "Управление разработкой", "VVChab");
//		adminService.setGrade("5", "Maximk4", "Управление разработкой", "VVChab");
//		adminService.setGrade("5", "TimIri", "Управление разработкой", "VVChab");
//		adminService.setGrade("5", "KPoro", "Управление разработкой", "VVChab");
//
//		adminService.setGrade("5", "Okneas", "Управление разработкой", "VVChab");
//		adminService.setGrade("5", "Tolik228", "Управление разработкой", "VVChab");
//		adminService.setGrade("5", "DanS", "Управление разработкой", "VVChab");

//		adminService.setGrade("2", "Hiricus", "СТП", "VVChab");
//		adminService.setGrade("3", "Bananaws", "СТП", "VVChab");
//		adminService.setGrade("2", "Maximk4", "СТП", "VVChab");
//		adminService.setGrade("3", "TimIri", "СТП", "VVChab");
//		adminService.setGrade("2", "KPoro", "СТП", "VVChab");
//
//		adminService.setGrade("3", "Okneas", "СТП", "VVChab");
//		adminService.setGrade("2", "Tolik228", "СТП", "VVChab");
//		adminService.setGrade("3", "DanS", "СТП", "VVChab");
	}

	public static User createUser1() {
		User user = new User("Hiricus", "Павел", "Свиридов", "2556145", "hexaeder@yandex.ru");
		Role role1 = new Role("ROLE_STUDENT");
		Role role2 = new Role("ROLE_TEACHER");
		Role role3 = new Role("ROLE_ADMIN");

		user.addRole(role1);
		user.addRole(role2);
		user.addRole(role3);

		return user;
	}
}
