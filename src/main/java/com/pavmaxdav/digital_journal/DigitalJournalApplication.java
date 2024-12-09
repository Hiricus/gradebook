package com.pavmaxdav.digital_journal;

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

//		repository.save(createUser1());
//		repository.save(createUser2());

		//adminService.addRoleToUser(createUser2().getLogin(), new Role("Labmem"));
		adminService.removeRoleFromUser("Filoriel", new Role("gaga"));
		//roleService.addRole(new Role("GOAT"));

		//adminService.addUser(createUser3());
		//adminService.removeUser("Loh");
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
		User user = new User("Loh", "name", "surname", "123", "some@mail");

		user.addRole(new Role("User"));
		user.addRole(new Role("Staff"));

		return user;
	}
}
