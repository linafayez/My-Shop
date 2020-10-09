package com.shop.myshop;

import com.google.firebase.firestore.FirebaseFirestore;

public class UserInfo {
    private FirebaseFirestore firestore ;
    private String Name;
    private String Id;
    private String Type ;
    private String gender;
    private int age;
    private boolean isAdmin;
    private String email;
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

}
