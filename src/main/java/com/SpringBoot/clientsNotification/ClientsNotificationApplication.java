package com.SpringBoot.clientsNotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientsNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientsNotificationApplication.class, args);
	}

	/*@Autowired
	public void setCredConf(){
		credConf.setDbName(System.getenv("db_name"));
		credConf.setDbPass(System.getenv("db_pass"));
		//credConf.setDbUrl(System.getenv("db_url"));

		credConf.setMailName(System.getenv("mail_name"));
		credConf.setMailPass(System.getenv("mail_pass"));
		credConf.setMailHost(System.getenv("mail_host"));

		credConf.setAtKey(System.getenv("at_key"));
		credConf.setAtName(System.getenv("db_name"));
	}*/
}
