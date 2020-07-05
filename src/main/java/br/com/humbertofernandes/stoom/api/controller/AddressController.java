package br.com.humbertofernandes.stoom.api.controller;

import br.com.humbertofernandes.stoom.api.event.ResourceCreatedEvent;
import br.com.humbertofernandes.stoom.api.model.Address;
import br.com.humbertofernandes.stoom.api.service.AddressService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final ApplicationEventPublisher publisher;
    private final AddressService service;

    public AddressController(ApplicationEventPublisher publisher, AddressService service) {
        this.publisher = publisher;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Address>> readAll() {
        List<Address> list = service.getAll();
        return ResponseEntity.status(list.size() > 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> read(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Address> create(@Valid @RequestBody Address address, HttpServletResponse response) {
        Address addressSave = service.save(address);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, addressSave.getId()));
        return ResponseEntity.ok(addressSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable Long id, @Valid @RequestBody Address address) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
