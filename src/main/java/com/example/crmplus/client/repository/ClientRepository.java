package com.example.crmplus.client.repository;

import com.example.crmplus.client.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
//    List<Client> findByStatus(Client.Status status);
    Page<Client> findByStatus(Client.Status status, Pageable pageable);
}