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
@ConfigurationProperties(prefix = "temp")
public class PropertiesConfig {
    private String dbUrl;
    private String dbName;
    private String dbPass;
    private String mailName;
    private String mailPass;
    private String mailHost;
    private String apiKey;
    private String atName;
}
