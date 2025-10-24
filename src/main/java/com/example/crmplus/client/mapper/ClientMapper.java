package com.example.crmplus.client.mapper;

import com.example.crmplus.client.dto.ClientDto;
import com.example.crmplus.client.dto.CreateClientRequest;
import com.example.crmplus.client.model.Client;

public class ClientMapper {

    public static ClientDto toDto(Client c) {
        ClientDto dto = new ClientDto();
        dto.id = c.getId();
        dto.name = c.getName();
        dto.email = c.getEmail();
        dto.phone = c.getPhone();
        dto.address = c.getAddress();
        dto.status = c.getStatus();
        return dto;
    }

    public static Client fromCreate(CreateClientRequest r) {
        Client c = new Client();
        c.setName(r.name);
        c.setEmail(r.email);
        c.setPhone(r.phone);
        c.setAddress(r.address);
        c.setStatus(r.status);
        return c;
    }
}