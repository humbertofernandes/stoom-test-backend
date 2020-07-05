package br.com.humbertofernandes.stoom.api.service;

import br.com.humbertofernandes.stoom.api.model.Address;
import br.com.humbertofernandes.stoom.api.repository.AddressRepository;
import br.com.humbertofernandes.stoom.api.service.exception.AddressNotFoundException;
import br.com.humbertofernandes.stoom.api.service.exception.BusinessException;
import br.com.humbertofernandes.stoom.api.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AddressServiceTest {

    private static final long ID = 1L;
    private static final String STREET_NAME = "R. Zuneide Aparecida Marin";
    private static final String NUMBER = "43";
    private static final String NEIGHBOURHOOD = "Jardim Santa Genebra II (Barao Geraldo)";
    private static final String CITY = "Campinas";
    private static final String STATE = "SP";
    private static final String COUNTRY = "Brasil";
    private static final String ZIPCODE = "13084780";
    private static final Long ID2 = 2L;
    private static final String STREET_NAME_2 = "Avenida Brigadeiro Faria Lima";
    private static final String NUMBER_2 = "3477";
    private static final String NEIGHBOURHOOD_2 = "Itaim Bibi";
    private static final String CITY_2 = "São Paulo";
    private static final String STATE_2 = "SP";
    private static final String COUNTRY_2 = "Brasil";
    private static final String ZIPCODE_2 = "04538133";

    private Address newAddress;
    private Address updateAddress;
    private Address addressInDatabase;
    private Address updateAddressInDatabase;
    private AddressService service;

    @MockBean
    private AddressRepository repositoryMocked;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new AddressServiceImpl(repositoryMocked);

        createInstanceNewAddress();
        createInstanceUpdateAddress();
        createInstanceAddressInDatabase();
        createInstanceUpdateAddressInDatabase();
    }

    @Test
    public void should_get_all_address() {
        when(repositoryMocked.findAll()).thenReturn(Collections.singletonList(addressInDatabase));
        List<Address> addressAll = service.getAll();

        assertThat(addressAll.size(), equalTo(1));
        assertThat(addressAll.get(0).getId(), equalTo(ID));
        assertThat(addressAll.get(0).getStreetName(), equalTo(STREET_NAME));
        assertThat(addressAll.get(0).getNumber(), equalTo(NUMBER));
        assertThat(addressAll.get(0).getNeighbourhood(), equalTo(NEIGHBOURHOOD));
        assertThat(addressAll.get(0).getCity(), equalTo(CITY));
        assertThat(addressAll.get(0).getState(), equalTo(STATE));
        assertThat(addressAll.get(0).getCountry(), equalTo(COUNTRY));
        assertThat(addressAll.get(0).getZipcode(), equalTo(ZIPCODE));
    }

    @Test
    public void should_get_all_address_and_return_empty_list() {
        List<Address> addresses = service.getAll();
        assertThat(addresses.size(), equalTo(0));
    }


    @Test
    public void should_get_address_by_id() {
        when(repositoryMocked.findById(ID)).thenReturn(Optional.of(addressInDatabase));

        Address addressFound = service.getById(ID);

        assertThat(addressFound.getId(), equalTo(ID));
        assertThat(addressFound.getStreetName(), equalTo(STREET_NAME));
        assertThat(addressFound.getNumber(), equalTo(NUMBER));
        assertThat(addressFound.getNeighbourhood(), equalTo(NEIGHBOURHOOD));
        assertThat(addressFound.getCity(), equalTo(CITY));
        assertThat(addressFound.getState(), equalTo(STATE));
        assertThat(addressFound.getCountry(), equalTo(COUNTRY));
        assertThat(addressFound.getZipcode(), equalTo(ZIPCODE));
    }

    @Test
    public void should_return_exception_of_not_found_when_there_is_no_address_with_id() {
        assertThrows(AddressNotFoundException.class, () -> service.getById(ID));
    }

    @Test
    public void should_create_new_address() {
        when(repositoryMocked.save(newAddress)).thenReturn(addressInDatabase);

        Address addressSaved = service.save(newAddress);

        assertThat(addressSaved.getId(), equalTo(ID));
        assertThat(addressSaved.getStreetName(), equalTo(STREET_NAME));
        assertThat(addressSaved.getNumber(), equalTo(NUMBER));
        assertThat(addressSaved.getNeighbourhood(), equalTo(NEIGHBOURHOOD));
        assertThat(addressSaved.getCity(), equalTo(CITY));
        assertThat(addressSaved.getState(), equalTo(STATE));
        assertThat(addressSaved.getCountry(), equalTo(COUNTRY));
        assertThat(addressSaved.getZipcode(), equalTo(ZIPCODE));
    }

    @Test
    void should_update_address() {

        when(repositoryMocked.findById(ID)).thenReturn(Optional.of(addressInDatabase));
        when(service.save(addressInDatabase)).thenReturn(updateAddressInDatabase);

        Address addressSaved = service.update(ID, updateAddress);

        assertThat(addressSaved.getId(), equalTo(ID));
        assertThat(addressSaved.getStreetName(), equalTo(STREET_NAME_2));
        assertThat(addressSaved.getNumber(), equalTo(NUMBER_2));
        assertThat(addressSaved.getNeighbourhood(), equalTo(NEIGHBOURHOOD_2));
        assertThat(addressSaved.getCity(), equalTo(CITY_2));
        assertThat(addressSaved.getState(), equalTo(STATE_2));
        assertThat(addressSaved.getCountry(), equalTo(COUNTRY_2));
        assertThat(addressSaved.getZipcode(), equalTo(ZIPCODE_2));
    }

    @Test
    public void should_deny_update_of_address_that_not_found() {
        assertThrows(AddressNotFoundException.class, () -> service.update(ID2, updateAddress));
    }

    @Test
    void should_delete_a_address() {
        when(repositoryMocked.findById(ID)).thenReturn(Optional.of(addressInDatabase));
        service.delete(ID);
    }

    @Test
    void should_deny_delete_a_aluno() {
        assertThrows(AddressNotFoundException.class, () -> service.delete(ID));
    }

    private void createInstanceNewAddress() {
        newAddress = new Address();
        newAddress.setStreetName(STREET_NAME);
        newAddress.setNumber(NUMBER);
        newAddress.setNeighbourhood(NEIGHBOURHOOD);
        newAddress.setCity(CITY);
        newAddress.setState(STATE);
        newAddress.setCountry(COUNTRY);
        newAddress.setZipcode(ZIPCODE);
    }

    private void createInstanceUpdateAddress() {
        updateAddress = new Address();
        updateAddress.setStreetName(STREET_NAME_2);
        updateAddress.setNumber(NUMBER_2);
        updateAddress.setNeighbourhood(NEIGHBOURHOOD_2);
        updateAddress.setCity(CITY_2);
        updateAddress.setState(STATE_2);
        updateAddress.setCountry(COUNTRY_2);
        updateAddress.setZipcode(ZIPCODE_2);
    }

    private void createInstanceAddressInDatabase() {
        addressInDatabase = new Address();
        addressInDatabase.setId(ID);
        addressInDatabase.setStreetName(STREET_NAME);
        addressInDatabase.setNumber(NUMBER);
        addressInDatabase.setNeighbourhood(NEIGHBOURHOOD);
        addressInDatabase.setCity(CITY);
        addressInDatabase.setState(STATE);
        addressInDatabase.setCountry(COUNTRY);
        addressInDatabase.setZipcode(ZIPCODE);
    }

    private void createInstanceUpdateAddressInDatabase() {
        updateAddressInDatabase = new Address();
        updateAddressInDatabase.setId(ID);
        updateAddressInDatabase.setStreetName(STREET_NAME_2);
        updateAddressInDatabase.setNumber(NUMBER_2);
        updateAddressInDatabase.setNeighbourhood(NEIGHBOURHOOD_2);
        updateAddressInDatabase.setCity(CITY_2);
        updateAddressInDatabase.setState(STATE_2);
        updateAddressInDatabase.setCountry(COUNTRY_2);
        updateAddressInDatabase.setZipcode(ZIPCODE_2);
    }
}
