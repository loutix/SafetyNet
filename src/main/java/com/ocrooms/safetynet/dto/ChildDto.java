package com.ocrooms.safetynet.dto;

import com.ocrooms.safetynet.entities.Person;

import java.util.List;

public class ChildDto {
    String firstName;
    String lastName;
    int age;

    List<Person> family;

    public ChildDto() {
    }

    public ChildDto(String firstName, String lastName, int age, List<Person> family) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.family = family;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Person> getFamily() {
        return family;
    }

    public void setFamily(List<Person> family) {
        this.family = family;
    }
}
