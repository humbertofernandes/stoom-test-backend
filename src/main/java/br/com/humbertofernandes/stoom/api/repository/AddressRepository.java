package br.com.humbertofernandes.stoom.api.repository;

import br.com.humbertofernandes.stoom.api.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
