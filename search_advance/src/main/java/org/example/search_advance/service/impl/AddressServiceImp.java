package org.example.search_advance.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.search_advance.dto.response.AddressValue;
import org.example.search_advance.model.Address;
import org.example.search_advance.repository.AddressRepository;
import org.example.search_advance.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImp implements AddressService {
    private final AddressRepository addressRepository;
    @Override
    public List<AddressValue> getAllProjectedBy() {
        return addressRepository.findAllProjectedBy();
    }
}
