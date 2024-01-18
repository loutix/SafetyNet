package com.ocrooms.safetynet.entities;

public class Person {

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Person extends Id {


    @NotNull
    @Length(min = 2, max = 20)
    private String address;

    @NotNull
    @Length(min = 2, max = 20)
    private String city;

    @NotNull
    @Length(min = 2, max = 20)
    private String zip;

    @NotNull
    @Length(min = 5, max = 30)
    private String phone;

    @NotNull
    @Email
    private String email;


    public void trimProperties() {
        address = address.trim();
        city = city.trim();
        zip = zip.trim();
        phone = phone.trim();
        email = email.trim();
    }

    public Person() {
    }

    public Person(String firstName, String lastName, String address, String city, int zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
