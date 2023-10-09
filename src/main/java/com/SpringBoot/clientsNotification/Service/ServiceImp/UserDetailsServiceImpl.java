package com.SpringBoot.clientsNotification.Service.ServiceImp;

import com.SpringBoot.clientsNotification.Entities.StaffEntity;
import com.SpringBoot.clientsNotification.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configuration
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private StaffService service;
    @Override
    public UserDetails loadUserByUsername(String email)
    {
        StaffEntity staff = service.getStaffByEmail(email);
        List<GrantedAuthority> authority = List.of(new SimpleGrantedAuthority(staff.getRole()));

        return (
                new org.springframework.security.core.userdetails.User(
                        staff.getEmail(),
                        staff.getPassword(),
                        authority
                )
        );
    }
}
