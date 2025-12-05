package org.example.search_advance.repository;

import org.example.search_advance.dto.response.AddressValue;
import org.example.search_advance.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {


    List<AddressValue> findAllProjectedBy();

    List<AddressValue> findByUserId(Long userId);

}