package br.com.humbertofernandes.stoom.api.service;

import br.com.humbertofernandes.stoom.api.model.Address;

import java.util.List;

public interface AddressService {

    Address save(Address address);

    Address update(Long id, Address address);

    List<Address> getAll();

    Address getById(Long id);

    void delete(Long id);
}
