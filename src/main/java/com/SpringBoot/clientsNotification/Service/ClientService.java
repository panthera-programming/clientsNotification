package com.SpringBoot.clientsNotification.Service;

import com.SpringBoot.clientsNotification.Entities.ClientEntity;

import java.util.List;
import java.util.Map;

public interface ClientService {
    String saveClient(ClientEntity client);
    ClientEntity findClientByEmail(String email);
    List<ClientEntity> findAllClients();
    List<ClientEntity> findAllPerProduct(Long prodId);
    void deleteClient(Long id);
    void emailClient(Map<String, ?> mailParts);
    String smsClient(String message, Long id) throws Exception;
}
