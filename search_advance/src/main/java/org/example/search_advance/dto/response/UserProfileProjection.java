package org.example.search_advance.dto.response;

import org.example.search_advance.util.Gender;
import org.example.search_advance.util.UserType;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public interface UserProfileProjection {
    String getFirstName();
    String getLastName();
    String getEmail();
    String getPhone();
    Date getDateOfBirth();
    Gender getGender();
    UserType getType();

    default String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    default Integer getAge() {
        if (getDateOfBirth() == null) return null;
        return Period.between(
                getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                LocalDate.now()
        ).getYears();
    }
}
