package org.example.spring_core.service;

import org.example.spring_core.config.UserIdGenerator;
import org.example.spring_core.model.Address;
import org.example.spring_core.model.User;
import org.example.spring_core.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
   private final AddressRepository addressRepository;
   private final UserIdGenerator userIdGenerator;


    // Constructor-based DI
    public UserService(AddressRepository addressRepository, UserIdGenerator userIdGenerator) {
        this.addressRepository = addressRepository;
        this.userIdGenerator = userIdGenerator;
    }

    public User getUserInfo(Long Id){
        Long id = userIdGenerator.generateId();
        Address address =  addressRepository.findByUserId(Id);
        return new User(id,"kkk", address);
    }
}
