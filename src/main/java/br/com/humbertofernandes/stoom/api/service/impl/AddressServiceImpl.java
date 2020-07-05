package br.com.humbertofernandes.stoom.api.service.impl;

import br.com.humbertofernandes.stoom.api.model.Address;
import br.com.humbertofernandes.stoom.api.repository.AddressRepository;
import br.com.humbertofernandes.stoom.api.service.AddressService;
import br.com.humbertofernandes.stoom.api.service.exception.AddressNotFoundException;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service("AddressService")
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;

    private Logger LOG = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    public AddressServiceImpl(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Address save(Address address) {
        verifyLatitudeAndLongitude(address);
        return repository.save(address);
    }

    private void verifyLatitudeAndLongitude(Address address) {
        if (StringUtils.isEmpty(address.getLatitude()) || StringUtils.isEmpty(address.getLongitude())) {
            try {
                StringBuffer addressStr = new StringBuffer();
                addressStr.append(address.getNumber());
                addressStr.append(address.getStreetName());
                addressStr.append(address.getNeighbourhood());
                addressStr.append(address.getCity());
                addressStr.append(address.getState());
                addressStr.append(address.getCountry());

                GeoApiContext context = new GeoApiContext.Builder()
                        .apiKey("AIzaSyBm3xh9oZP1ksMWcMzVaZQevWlrtb8tIgc")
                        .build();
                GeocodingResult[] results = GeocodingApi.geocode(context, addressStr.toString()).await();
                LatLng location = results[0].geometry.location;
                address.setLatitude(String.valueOf(location.lat));
                address.setLongitude(String.valueOf(location.lng));
            } catch (Exception e) {
                LOG.error("Error get location address");
            }
        }
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
