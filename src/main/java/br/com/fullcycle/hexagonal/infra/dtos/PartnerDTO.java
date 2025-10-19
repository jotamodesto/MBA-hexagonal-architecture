package br.com.fullcycle.hexagonal.infra.dtos;

import br.com.fullcycle.hexagonal.infra.jpa.entities.PartnerEntity;

import java.util.UUID;

public class PartnerDTO {
    private UUID id;
    private String name;
    private String cnpj;
    private String email;

    public PartnerDTO() {
    }

    public PartnerDTO(UUID id) {
        this.id = id;
    }

    public PartnerDTO(PartnerEntity partnerEntity) {
        this.id = partnerEntity.getId();
        this.name = partnerEntity.getName();
        this.cnpj = partnerEntity.getCnpj();
        this.email = partnerEntity.getEmail();
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
