package com.SpringBoot.clientsNotification.Service.ServiceImp;

import com.SpringBoot.clientsNotification.Entities.ClientEntity;
import com.SpringBoot.clientsNotification.Repository.ClientRepository;
import com.SpringBoot.clientsNotification.Service.ClientService;
import com.SpringBoot.clientsNotification.Service.MySmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository repository;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private MySmsService mySmsService;
    @Override
    public String saveClient(ClientEntity client) {
        repository.save(client);
        return ("Client saved successfully");
    }
    @Override
    public ClientEntity findClientByEmail(String email)
    {
        return (repository.findByEmail(email));
    }
    @Override
    public List<ClientEntity> findAllClients()
    {
        return (repository.findAll());
    }
    @Override
    public List<ClientEntity> findAllPerProduct(Long prodId){
        return (repository.findAllByProduct(prodId));
    }
    @Override
    public void deleteClient(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void emailClient(Map<String, ?> mailParts) {
        emailService.sendClientMail(
                (String) mailParts.get("subject"),
                (String) mailParts.get("message"),
                (ClientEntity) mailParts.get("client")
        );
    }

    @Override
    public String smsClient(String message, Long id) throws Exception
    {
        List<ClientEntity> clients = repository.findAllByProduct(id);
        String msg = "";
        String[] contacts = clients.stream()
                .map(ClientEntity::getPhone)
                .toArray(String[]::new);
        try {
            msg = mySmsService.sendSms(message,contacts);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return (msg);
    }
}
