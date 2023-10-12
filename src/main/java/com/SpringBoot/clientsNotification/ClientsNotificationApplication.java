package com.SpringBoot.clientsNotification;

import com.SpringBoot.clientsNotification.Utility.CredConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientsNotificationApplication {

	@Autowired
	private CredConf credConf;
	public static void main(String[] args) {
		SpringApplication.run(ClientsNotificationApplication.class, args);
	}

	@Autowired
	public void setCredConf(){
		credConf.setDbName(System.getenv("db_name"));
		credConf.setDbPass(System.getenv("db_pass"));
		credConf.setDbUrl(System.getenv("db_url"));

		credConf.setMailName(System.getenv("mail_name"));
		credConf.setMailPass(System.getenv("mail_pass"));
		credConf.setMailHost(System.getenv("mail_host"));

		credConf.setAtKey(System.getenv("at_key"));
		credConf.setAtName(System.getenv("db_name"));
	}
}
