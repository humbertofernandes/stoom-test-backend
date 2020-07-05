package br.com.humbertofernandes.stoom.api.repository;

import br.com.humbertofernandes.stoom.api.model.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Humberto Tadeu de Paiva Gomes Fernandes
 */
@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class AddressRepositoryTest {

    private static final long ID = 1L;
    private static final String STREET_NAME = "R. Zuneide Aparecida Marin";
    private static final String NUMBER = "43";
    private static final String NEIGHBOURHOOD = "Jardim Santa Genebra II (Barao Geraldo)";
    private static final String CITY = "Campinas";
    private static final String STATE = "SP";
    private static final String COUNTRY = "Brasil";
    private static final String ZIPCODE = "13084780";

    @Autowired
    private AddressRepository repository;

    @Test
    public void should_find_address_by_id() {
        Optional<Address> addressSaved = repository.findById(1L);
        assertThat(addressSaved.isPresent(), equalTo(Boolean.TRUE));

        Address address = addressSaved.get();
        assertThat(address.getId(), equalTo(ID));
        assertThat(address.getStreetName(), equalTo(STREET_NAME));
        assertThat(address.getNumber(), equalTo(NUMBER));
        assertThat(address.getNeighbourhood(), equalTo(NEIGHBOURHOOD));
        assertThat(address.getCity(), equalTo(CITY));
        assertThat(address.getState(), equalTo(STATE));
        assertThat(address.getCountry(), equalTo(COUNTRY));
        assertThat(address.getZipcode(), equalTo(ZIPCODE));
    }

    @Test
    public void should_not_find_address_by_id() {
        Optional<Address> alunoSaved = repository.findById(10L);
        assertThat(alunoSaved.isPresent(), equalTo(Boolean.FALSE));
    }
}
