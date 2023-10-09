package com.SpringBoot.clientsNotification.Service;

import com.SpringBoot.clientsNotification.Entities.ClientEntity;

public interface EmailService {
    void sendClientMail(String subject, String message, ClientEntity client);

    /**
     * send large NO. of mails by invoking sendClientMail on
     * each client from a client collection
     * @param subject
     * @param product_id
     */
    String  sendBulkClientMail(String subject, Long product_id, String msg);

    //void sendStaffSetPasswordMail();
}
