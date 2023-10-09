package com.SpringBoot.clientsNotification.Repository;

import com.SpringBoot.clientsNotification.Entities.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Long> {
    StaffEntity findByEmail(String email);
}
