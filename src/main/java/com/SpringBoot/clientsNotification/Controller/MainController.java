package com.SpringBoot.clientsNotification.Controller;

import com.SpringBoot.clientsNotification.Entities.IdAndPassword;
import com.SpringBoot.clientsNotification.Entities.StaffEntity;
import com.SpringBoot.clientsNotification.Service.ClientService;
import com.SpringBoot.clientsNotification.Service.EmailService;
import com.SpringBoot.clientsNotification.Service.ProductService;
import com.SpringBoot.clientsNotification.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    private ProductService productService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmailService emailService;
    @GetMapping("/login")
    public String renderLoginPage()
    {
        return ("loginPage");
    }
    @GetMapping("/home")
    public String staffHomePage()
    {
        return ("adminHome");
    }
    @GetMapping("/admin")
    public String adminHomePage()
    {
        return ("redirect:/home?admin=true");
    }

    /**
     * STAFF RELATED MAPPINGS
     */
    @GetMapping("/register/firstAdmin")
    public String firstAdmin(Model model)
    {
        StaffEntity staff = new StaffEntity();
        model.addAttribute("staff", staff);
        return ("adminReg");
    }
    @PostMapping("/register/firstAdmin")
    public String registerFirstAdmin(@ModelAttribute StaffEntity staff)
    {
        String message = staffService.registerStaff(staff);
        staffService.setPasswordMail(staff);
        return ("loginPage");
    }
    @GetMapping("/setPassword")
    public String staffSetPassword(@RequestParam Long id, Model model)
    {
        IdAndPassword idAndPassword = new IdAndPassword();
        idAndPassword.setId(id);
        model.addAttribute("idAndPassword", idAndPassword);
        return ("setPassword");
    }
    @PostMapping("/setPassword")
    public String saveStaffPassword(@ModelAttribute IdAndPassword idAndPassword)
    {
        System.out.printf("\n\n##########\t%s\t##########\n\n", idAndPassword.getPassword());
        StaffEntity staff = staffService.getStaffById(idAndPassword.getId());
        staff.setPassword(idAndPassword.getPassword());
        //staff.setId(id);
        staffService.saveStaffPassword(staff);

        return ("redirect:/login");

    }
    @PostMapping("/admin/addStaff")
    public String createNewStaff(@RequestBody StaffEntity staff)
    {
        String message = staffService.registerStaff(staff);
        if (message.equals("Staff Created Successfully!")) {
            staffService.setPasswordMail(staff);
            return ("");
        }
        return ("");
    }

}
