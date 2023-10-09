package com.SpringBoot.clientsNotification;

import com.SpringBoot.clientsNotification.Utility.PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientsNotificationApplication {

	@Autowired
	private PropertiesConfig propertiesConfig;
	public static void main(String[] args) {
		SpringApplication.run(ClientsNotificationApplication.class, args);
	}

	@Autowired
	public void setPropertiesConfig()
	{
		propertiesConfig.setDbName(System.getenv("db_name"));
		propertiesConfig.setDbPass(System.getenv("db_pass"));
		propertiesConfig.setDbUrl(System.getenv("db_url"));

		propertiesConfig.setMailName(System.getenv("mail_name"));
		propertiesConfig.setMailPass(System.getenv("mail_pass"));
		propertiesConfig.setMailHost(System.getenv("mail_host"));

		propertiesConfig.setApiKey(System.getenv("at_key"));
		propertiesConfig.setAtName(System.getenv("db_name"));
	}
}
