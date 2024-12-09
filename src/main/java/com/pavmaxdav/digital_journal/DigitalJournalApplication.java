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

		//roleService.addRole(new Role("GOAT"));

		//adminService.addUser(createUser3());
//		adminService.addNewGroup("11-A");
		adminService.addUserToGroup("Hiricus", "11-A");
		adminService.addUserToGroup("Filoriel", "11-A");
		adminService.removeUserFromGroup("Bardoon", "11-A");

		//adminService.addUser(createPipets());
		//adminService.removeUser("Yul'ka");
		//adminService.addNewDiscipline("Math", "Yul'ka");
		//adminService.addDisciplineToGroup("11-A", "Math", "Yul'ka");

		adminService.removeGroup("11-A");


		//adminService.removeDisciplineFromGroup("11-A", "Math", "Yul'ka");

		//adminService.removeDiscipline("Math", "Yul'ka");
	}

	public static User createUser1() {
		User user = new User("Hiricus", "Алавел", "Павидлов", "2556145", "yandex@mail");
		Role role1 = new Role("Labmem");
		Role role2 = new Role("Staff");
		Role role3 = new Role("GOAT");

		user.addRole(role1);
		user.addRole(role2);
		user.addRole(role3);

		return user;
	}

	public static User createUser2() {
		User user = new User("Filoriel", "Павдрей", "Сверлюхев", "227", "mail@ru");
		Role role1 = new Role("User");
		Role role2 = new Role("Akakan");
		Role role3 = new Role("gaga");

		user.addRole(role1);
		user.addRole(role2);
		user.addRole(role3);

		return user;
	}

	public static User createUser3() {
		User user = new User("Bardoon", "Bardoon the 1st", "Kozlov", "123", "some@mail");

		user.addRole(new Role("User"));
		user.addRole(new Role("Staff"));
		user.addRole(new Role("GOAT"));

		return user;
	}

	public static User createPipets() {
		User user = new User("Yul'ka", "Юлия", "Александровна", "1488", "mail@ig");

		user.addRole(new Role("User"));
		user.addRole(new Role("Staff"));
		user.addRole(new Role("teacher"));

		return user;
	}
}
