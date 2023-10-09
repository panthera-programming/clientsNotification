package com.SpringBoot.clientsNotification.Service.ServiceImp;

import com.SpringBoot.clientsNotification.Entities.ClientEntity;
import com.SpringBoot.clientsNotification.Repository.ClientRepository;
import com.SpringBoot.clientsNotification.Service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private ClientRepository clientRepository;
    @Value("spring.mail.username")
    private String from;
    @Override
    public void sendClientMail(String subject, String message, ClientEntity client) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setPriority(1);
            helper.setTo(client.getEmail());
            helper.setFrom(from);
            helper.setSubject(subject);

            System.out.println("\n\n**********\t"+client+"\t**********\n**********\t"+message+"\t**********\n\n");

            Context ctx = new Context();
            ctx.setVariables(Map.of("client", client.getName(), "message", message));
            String mailText = templateEngine.process("clientMailTemplate.html", ctx);

            helper.setText(mailText, true);
            mailSender.send(mimeMessage);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public String sendBulkClientMail(String subject, Long product_id, String msg) {
        List<ClientEntity> clients = clientRepository.findAllByProduct(product_id);

        for (ClientEntity client : clients)
        {
            sendClientMail(subject,msg,client);
        }

        return ("Mail Successfully sent to all clients");
    }
}
