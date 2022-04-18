package com.ecommerce.bootcampecommerce.service;

import com.ecommerce.bootcampecommerce.dto.AddressDTO;
import com.ecommerce.bootcampecommerce.entity.Address;

import java.util.Set;

public interface AddressService {

    Address addressConvertDtoToEntity(AddressDTO addressDTO);

    Set<AddressDTO> addressConvertEntityTODto(Set<Address> address);
}
