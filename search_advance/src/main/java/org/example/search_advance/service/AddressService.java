package org.example.search_advance.service;

import org.example.search_advance.dto.response.AddressInfoDto;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressService {


    List<AddressInfoDto> getAddressInfo();
}
