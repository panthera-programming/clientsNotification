package com.SpringBoot.clientsNotification.Service;

public interface MySmsService {
    String sendSms(String message, String[] contacts) throws Exception;
}
