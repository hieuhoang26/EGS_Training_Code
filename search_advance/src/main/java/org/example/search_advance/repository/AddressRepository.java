package org.example.search_advance.repository;

import org.example.search_advance.dto.response.AddressInfoDto;
import org.example.search_advance.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("""
        SELECT a 
        FROM Address a
        join User u
        on u.id = a.user.id
    """)
    List<AddressInfoDto> findAdressInfo();



}