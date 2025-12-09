package org.example.spring_core.repository;

import org.example.spring_core.model.Address;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository {
    public Address findByUserId(Long userId){
        return new Address("HN", "Cau Giay");
    }
}
