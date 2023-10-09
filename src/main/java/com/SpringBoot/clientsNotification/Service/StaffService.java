package com.SpringBoot.clientsNotification.Service;

import com.SpringBoot.clientsNotification.Entities.StaffEntity;


public interface StaffService {
    StaffEntity getStaffByEmail(String email);
    StaffEntity getStaffById(Long id);
    String registerStaff(StaffEntity staff);

    /**
     * send staff email to set account password
     * @param staff
     */
    void setPasswordMail(StaffEntity staff);

    /**
     * update staff(save password)
     * @param staff
     */
    void saveStaffPassword(StaffEntity staff);

}
