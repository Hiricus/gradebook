package com.pavmaxdav.digital_journal;

import com.pavmaxdav.digital_journal.enitiy.Role;
import com.pavmaxdav.digital_journal.enitiy.User;
import com.pavmaxdav.digital_journal.model.UserRepository;
import com.pavmaxdav.digital_journal.service.RoleService;
import com.pavmaxdav.digital_journal.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DigitalJournalApplication {

	public static void main(String[] args) {
		var context =  SpringApplication.run(DigitalJournalApplication.class, args);
		UserRepository repository = context.getBean(UserRepository.class);
		UserService userService = context.getBean(UserService.class);
		RoleService roleService = context.getBean(RoleService.class);

//		repository.save(createUser1());
//		repository.save(createUser2());

		userService.addRoleToUser(createUser2(), new Role("Labmem"));
		roleService.addRole(new Role("GOAT"));
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
}
