package com.SpringBoot.clientsNotification.Service.ServiceImp;

import com.SpringBoot.clientsNotification.Service.MySmsService;
import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Recipient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MySmsServiceImpl implements MySmsService{
    @Value("${USER_NAME}")
    private String username;
    @Value("${API_KEY}")
    private String apikey;


    public String sendSms(String message, String[] contacts) throws Exception
    {
        AfricasTalking.initialize(this.username,this.apikey);
        SmsService sms = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
        //String message = "Hello Nestah\nThis is an sms service test with AfricasTalking";
        try {
            List<Recipient> response = sms.send(message, contacts, true);
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return ("Bulk Sms sent successfully...");
    }
}
