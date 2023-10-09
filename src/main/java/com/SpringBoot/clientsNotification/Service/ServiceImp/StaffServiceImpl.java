package com.SpringBoot.clientsNotification.Service.ServiceImp;

import com.SpringBoot.clientsNotification.Entities.StaffEntity;
import com.SpringBoot.clientsNotification.Repository.StaffRepository;
import com.SpringBoot.clientsNotification.Service.StaffService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;


@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${email.host}")
    private String mailHost;
    @Override
    public StaffEntity getStaffByEmail(String email)
    {
        return (staffRepository.findByEmail(email));
    }
    @Override
    public StaffEntity getStaffById(Long id)
    {
        return (staffRepository.findById(id).get());
    }
    @Override
    public String registerStaff(StaffEntity staff)
    {
        staffRepository.save(staff);
        return ("Staff Created Successfully!");
    }

    @Override
    public void setPasswordMail(StaffEntity staff)
    {
       try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(from);
            helper.setTo(staff.getEmail());
            helper.setSubject("Set Password");
            helper.setPriority(1);

           Context ctx = new Context();
           String urlLink = String.format("%s/setPassword?id=%d", mailHost, staff.getId());
           System.out.printf("\n\n**********\t%s\t**********\n\n", urlLink);

           ctx.setVariables(Map.of("passwordLink",urlLink, "staffname", staff.getName()));
           String mailText = templateEngine.process("staffMailTemplate.html", ctx);
           helper.setText(mailText, true);
           mailSender.send(mimeMessage);
       }catch (Exception ex)
       {
           ex.printStackTrace();
       }
    }

    @Override
    public void saveStaffPassword(StaffEntity staff)
    {
        String password = passwordEncoder.encode(staff.getPassword());
        staff.setPassword(password);
        staffRepository.save(staff);
    }
}
