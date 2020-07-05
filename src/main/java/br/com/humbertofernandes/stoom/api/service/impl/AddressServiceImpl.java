package br.com.humbertofernandes.stoom.api.service.impl;

import br.com.humbertofernandes.stoom.api.model.Address;
import br.com.humbertofernandes.stoom.api.repository.AddressRepository;
import br.com.humbertofernandes.stoom.api.service.AddressService;
import br.com.humbertofernandes.stoom.api.service.exception.AddressNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("AddressService")
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;

    @Autowired
    public AddressServiceImpl(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Address save(Address address) {
        return repository.save(address);
    }

    @Override
    public Address update(Long id, Address address) {
        Address addressSaved = getById(id);
        BeanUtils.copyProperties(address, addressSaved, "id");
        return repository.save(addressSaved);
    }

    @Override
    public List<Address> getAll() {
        return repository.findAll();
    }

    @Override
    public Address getById(Long id) {
        Optional<Address> addressOptional = repository.findById(id);
        return addressOptional.orElseThrow(AddressNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        Address addressSave = getById(id);
        repository.delete(addressSave);
    }
}
