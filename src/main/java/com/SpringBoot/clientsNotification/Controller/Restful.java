package com.SpringBoot.clientsNotification.Controller;

import com.SpringBoot.clientsNotification.Entities.*;
import com.SpringBoot.clientsNotification.Service.ClientService;
import com.SpringBoot.clientsNotification.Service.EmailService;
import com.SpringBoot.clientsNotification.Service.MySmsService;
import com.SpringBoot.clientsNotification.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class Restful {

    @Autowired
    private ProductService productService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MySmsService mySmsService;
    @PostMapping("/api/client/new")
    public ResponseEntity<HttpResponse> registerClient(@RequestBody ClientEntity client)
    {
        String msg;
        System.out.println("\n\n*****\t"+client+"\t*****\n\n");
        if (client != null)
        {
            /*ProductEntity product = productService.getProductById(client.getProduct().getId());
            client.setProduct(product);*/
            msg = clientService.saveClient(client);
        }
        else
        {
            msg = "Client registration UNSUCCESSFUL";
        }

        return (ResponseEntity.created(URI.create("")).body(HttpResponse.builder()
                .data(Map.of("Client", client))
                .message(msg)
                .requestMethod("POST")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .build())
        );
    }
    @DeleteMapping("/api/client/delete")
    public ResponseEntity<HttpResponse> deleteClient(@RequestParam("clientId") Long id)
    {
        String msg = "Client of id " + id + " deleted successfully";

        return (ResponseEntity.ok().body(HttpResponse.builder()
                .data(Map.of("resp", "Client deleted!"))
                .message(msg)
                .requestMethod("DELETE")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build())
        );
    }
    @GetMapping("/api/client/all")
    public ResponseEntity<HttpResponse> allClientsPerProd(@RequestParam("prodId") Long id)
    {
        String msg = "Successfully Read clients";
        List<ClientEntity> clientEntities = clientService.findAllPerProduct(id);
        return (ResponseEntity.ok().body(HttpResponse.builder()
                .data(Map.of("clients", clientEntities))
                .message(msg)
                .requestMethod("GET")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build())
        );
    }

    @PostMapping("/api/client/mail/all")
    public ResponseEntity<HttpResponse> sendBulkMail(@RequestParam("prodId") Long id, @RequestBody Message message)
    {
        String msg = emailService.sendBulkClientMail("PROPERTY UPDATES", id, message.getMessage());
        return (ResponseEntity.created(URI.create("")).body(HttpResponse.builder()
                .message(msg)
                .requestMethod("POST")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .build())
        );
    }
    @PostMapping("/api/client/sms/all")
    public ResponseEntity<HttpResponse> sendBulkSms(@RequestParam("prodId") Long id, @RequestBody Message message) throws Exception
    {
        String msg = "";
        try
        {
            msg = clientService.smsClient(message.getMessage(), id);
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return (ResponseEntity.created(URI.create("")).body(HttpResponse.builder()
                .message(msg)
                .requestMethod("POST")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .build())
        );
    }
    @PostMapping("/api/product/new")
    public ResponseEntity<HttpResponse> addNewProduct(@RequestBody ProductEntity product)
    {
        String msg;
        System.out.println("\n\n*****\t"+product+"\t*****\n\n");
        if (product != null)
        {
            msg = productService.createNewProduct(product);
        }
        else
            msg = "Product creation UNSUCCESSFUL";

        return (ResponseEntity.created(URI.create("")).body(HttpResponse.builder()
                .data(Map.of("product", product))
                .message(msg)
                .requestMethod("POST")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .build())
        );
    }
    @GetMapping("/api/product/all")
    public ResponseEntity<HttpResponse> allProducts()
    {
        List<ProductEntity> productsList = productService.getAllProducts();
        //System.out.println("\n\n**********\t"+productsList.get(0).getClient()+"\t**********\n\n");
        return (ResponseEntity.ok().body(HttpResponse.builder()
                .data(Map.of("products", productsList))
                .message("All products retrieval successful")
                .requestMethod("GET")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build())
        );
    }
    @PostMapping("/api/staff/new")
    public ResponseEntity<HttpResponse> registerStaff(@RequestBody StaffEntity staff)
    {
        String msg;
        System.out.println("\n\n*****\t"+staff+"\t*****\n\n");
        if (staff != null)
        {
            msg = "Staff registration SUCCESSFUL";
        }
        else
            msg = "Staff registration UNSUCCESSFUL";

        return (ResponseEntity.created(URI.create("")).body(HttpResponse.builder()
                .data(Map.of("staff", staff))
                .message(msg)
                .requestMethod("POST")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .build())
        );
    }
}
