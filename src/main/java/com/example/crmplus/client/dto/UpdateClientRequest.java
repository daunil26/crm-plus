package com.example.crmplus.client.dto;

import com.example.crmplus.client.model.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateClientRequest {
    @NotBlank @Size(max = 255)
    public String name;

    @Email
    public String email;

    @Size(max = 64)
    public String phone;

    public String address;

    public Client.Status status;
}