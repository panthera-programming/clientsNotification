package com.SpringBoot.clientsNotification.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdAndPassword {
    private Long id;
    private String password;
}
