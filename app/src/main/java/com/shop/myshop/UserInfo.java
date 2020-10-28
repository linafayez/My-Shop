package com.shop.myshop;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String Name;
    private String Id;
    private String Type ;
    private String gender;
    private String image;
    private int age;
    private String Address;
    private boolean isAdmin;
    private String email;
    private String phone;
    UserInfo(String Name,String Id,String Type,String email){
        this.Name= Name;
        this.Type =Type;
        this.email = email;
        this.Id = Id;
    }
    UserInfo(String Id,boolean isAdmin ){
        this.isAdmin=isAdmin;
        this.Id = Id;

    }
    public UserInfo(){

    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
