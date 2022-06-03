package com.example.finalyearproject;

public class User {

    String firstName;
    String lastName;
    String email;
    String Password;
    String contactNo;
    String address;

    private User(){}

    public User(String firstName, String lastName, String email, String password, String contactNo, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        Password = password;
        this.contactNo = contactNo;
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
