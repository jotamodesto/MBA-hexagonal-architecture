package br.com.fullcycle.domain.partner;

import br.com.fullcycle.domain.exceptions.ValidationException;
import br.com.fullcycle.domain.person.Cnpj;
import br.com.fullcycle.domain.person.Email;
import br.com.fullcycle.domain.person.Name;

public record Partner(PartnerId partnerId, Name name, Cnpj cnpj, Email email) {
    public Partner {
        if (partnerId == null) {
            throw new ValidationException("Invalid partnerId for the Partner");
        }

//        if (name == null) {
//            throw new ValidationException("Invalid name for the Partner");
//        }
//
//        if (cnpj == null) {
//            throw new ValidationException("Invalid cnpj for the Partner");
//        }
//
//        if (email == null) {
//            throw new ValidationException("Invalid email for the Partner");
//        }
    }

    public Partner(PartnerId partnerId, String name, String cnpj, String email) {
        this(partnerId, new Name(name), new Cnpj(cnpj), new Email(email));
    }

    public static Partner newPartner(String name, String cnpj, String email) {
        return new Partner(PartnerId.unique(), name, cnpj, email);
    }
}
