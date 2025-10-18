package br.com.fullcycle.hexagonal.infra.dtos;

import br.com.fullcycle.hexagonal.infra.jpa.entities.CustomerEntity;

import java.util.UUID;

public class CustomerDTO {
    private UUID id;
    private String name;
    private String cpf;
    private String email;

    public CustomerDTO() {
    }

    public CustomerDTO(CustomerEntity customerEntity) {
        this.id = customerEntity.getId();
        this.name = customerEntity.getName();
        this.cpf = customerEntity.getCpf();
        this.email = customerEntity.getEmail();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
