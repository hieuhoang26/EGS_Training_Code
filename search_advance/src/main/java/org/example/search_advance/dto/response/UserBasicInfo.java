package org.example.search_advance.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.search_advance.model.User;
import org.example.search_advance.util.UserStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBasicInfo {

//    String getFirstName();
//
//    String getLastName();
//
//    String getEmail();
//
//    String getPhone();
//
//    UserStatus getStatus();

//    default String getFullName() {
//        return getFirstName() + " " + getLastName();
//    }
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String phone;
    private  UserStatus status;
    public String getFullName() {
        return firstName + " " + lastName;
    }



}

