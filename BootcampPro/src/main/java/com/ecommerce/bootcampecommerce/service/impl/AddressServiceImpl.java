package com.ecommerce.bootcampecommerce.service.impl;

import com.ecommerce.bootcampecommerce.dto.AddressDTO;
import com.ecommerce.bootcampecommerce.entity.Address;
import com.ecommerce.bootcampecommerce.service.AddressService;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    @Override
    public Address addressConvertDtoToEntity(AddressDTO addressDTO) {

        Address address = new Address();
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setAddressline(addressDTO.getAddressline());
        address.setZipcode(addressDTO.getZipcode());
        address.setLabel(addressDTO.getLabel());
        return address;
    }

    @Override
    public Set<AddressDTO> addressConvertEntityTODto(Set<Address> address) {
        return address.stream().map(o->new AddressDTO(o.getCity(),o.getState(),
                o.getCountry(),o.getAddressline(),o.getZipcode(),o.getLabel())).collect(Collectors.toSet());
    }
}
