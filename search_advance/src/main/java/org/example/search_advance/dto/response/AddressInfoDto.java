package org.example.search_advance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record AddressInfoDto(
        String apartmentNumber,
        String floor,
        String building,
        String streetNumber,
        String street,
        String city,
        String country,
        Integer addressType,

        Long userId,
        String userFirstName,
        String userLastName

) {}
