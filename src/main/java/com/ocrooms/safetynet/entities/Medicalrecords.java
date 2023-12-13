//package com.ocrooms.safetynet.entities;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//import java.time.LocalDate;
//import java.util.List;
//@Data
//@AllArgsConstructor
//public class Medicalrecords {
//    private String firstName;
//    private String lastName;
//
//    @JsonFormat(pattern = "MM/dd/yyyy")
//    private LocalDate birthdate;
//    private List<String> medications;
//    private List<String> allergies;
//
//
//
//    public int calculateAge() {
//        return birthdate.until(LocalDate.now()).getYears();
//    }
//
//    public boolean isMajor(){
//        return calculateAge() >= 18; //
//    }
//}


package com.ocrooms.safetynet.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Medicalrecords {

    @NotNull
    @Length(min = 2, max = 20)
    private String firstName;

    @NotNull
    @Length(min = 2, max = 20)
    private String lastName;

    @NotNull
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate birthdate;

    private List<String> medications;

    private List<String> allergies;


    public int calculateAge() {
        return birthdate.until(LocalDate.now()).getYears();
    }

    public boolean isMajor() {
        return calculateAge() >= 18;
    }

    public boolean isMinor() {
        return calculateAge() < 18;
    }

    public void trimProperties() {
        this.firstName = this.firstName.trim();
        this.lastName = this.lastName.trim();
    }

}
