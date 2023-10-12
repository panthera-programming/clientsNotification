package com.SpringBoot.clientsNotification.Utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "app")
public class CredConf {
    String dbName;
    String dbPass;
    String dbUrl;
    String mailName;
    String mailPass;
    String mailHost;
    String atName;
    String atKey;
}
