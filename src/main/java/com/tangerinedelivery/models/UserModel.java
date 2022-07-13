package com.tangerinedelivery.models;

import com.tangerinedelivery.repos.entities.UserEntity;

public class UserModel {
    private Long id;

    private String email;

    private String firstName;

    private String middleName;

    private String lastName;

    private Boolean emailConfirmed;

    public UserModel() {
    }

    public static UserModel ToModel(UserEntity user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getUserID());
        userModel.setEmail(user.getEmail());
        userModel.setFirstName(user.getFirstName());
        userModel.setMiddleName(user.getMiddleName());
        userModel.setLastName(user.getLastName());
        userModel.setEmailConfirmed(user.getEmailConfirmed());
        return userModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }


}
