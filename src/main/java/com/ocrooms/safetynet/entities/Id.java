package com.ocrooms.safetynet.entities;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class Id {

    @NotNull
    @Length(min = 2, max = 20)
    protected String firstName;

    @NotNull
    @Length(min = 2, max = 20)
    protected String lastName;

    public String getId() {
        return firstName + "-" + lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }


}
