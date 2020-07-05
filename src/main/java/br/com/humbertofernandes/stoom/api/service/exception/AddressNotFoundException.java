package br.com.humbertofernandes.stoom.api.service.exception;

import org.springframework.http.HttpStatus;

public class AddressNotFoundException extends BusinessException {

    private static final long serialVersionUID = -6109528874432290074L;

    public AddressNotFoundException() {
        super("address-8", HttpStatus.NOT_FOUND);
    }
}
