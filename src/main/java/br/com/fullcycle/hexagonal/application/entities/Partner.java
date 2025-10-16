package br.com.fullcycle.hexagonal.application.entities;

public record Partner(PartnerId partnerId, Name name, Cnpj cnpj, Email email) {
    public Partner(PartnerId partnerId, String name, String cnpj, String email) {
        this(partnerId, new Name(name), new Cnpj(cnpj), new Email(email));
    }

    public static Partner newPartner(String name, String cpf, String email) {
        return new Partner(PartnerId.unique(), name, cpf, email);
    }
}
