package com.example.crmplus.client.web;

import com.example.crmplus.client.dto.*;
import com.example.crmplus.client.model.Client;
import com.example.crmplus.client.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    // GET /api/v1/clients?status=ACTIVE|INACTIVE
    @GetMapping
    public List<ClientDto> list(@RequestParam(required = false) Client.Status status) {
        return service.list(status);
    }

    // POST /api/v1/clients
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto create(@Valid @RequestBody CreateClientRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public ClientDto get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public ClientDto update(@PathVariable Long id,
                            @Valid @RequestBody UpdateClientRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}