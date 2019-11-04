package com.digilytics.user_migration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
public class UserMigrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMigrationApplication.class, args);
	}
}
