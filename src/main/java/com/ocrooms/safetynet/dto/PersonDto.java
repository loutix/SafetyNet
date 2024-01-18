package com.ocrooms.safetynet.dto;

import com.ocrooms.safetynet.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
public class PersonDto {

    String firstName;
    String lastName;
    String address;
    String phone;

    public PersonDto(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.phone = person.getPhone();
    }

    public String getId() {
        return firstName + "-" + lastName;
    }
}
