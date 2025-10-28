package com.example.crmplus.client.service;

import com.example.crmplus.client.dto.*;
import com.example.crmplus.client.mapper.ClientMapper;
import com.example.crmplus.client.model.Client;
import com.example.crmplus.client.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repo;

    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    /*public List<ClientDto> list(Client.Status status) {
        List<Client> src = (status == null)
                ? repo.findAll()
                : repo.findByStatus(status);
        return src.stream().map(ClientMapper::toDto).toList();
    }*/

    public Page<ClientDto> list(Client.Status status, Pageable pageable) {
        Page<Client> src = (status == null)
                ? repo.findAll(pageable)
                : repo.findByStatus(status, pageable);
        return src.map(ClientMapper::toDto);
    }

    public ClientDto create(CreateClientRequest req) {
        Client saved = repo.save(ClientMapper.fromCreate(req));
        return ClientMapper.toDto(saved);
    }

    public ClientDto getById(Long id) {
        Client c = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));
        return ClientMapper.toDto(c);
    }

    public ClientDto update(Long id, UpdateClientRequest req) {
        Client c = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));

        c.setName(req.name);
        c.setEmail(req.email);
        c.setPhone(req.phone);
        c.setAddress(req.address);
        c.setStatus(req.status);

        Client saved = repo.save(c);
        return ClientMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repo.existsById(id))
            throw new IllegalArgumentException("Client not found: " + id);
        repo.deleteById(id);
    }
}