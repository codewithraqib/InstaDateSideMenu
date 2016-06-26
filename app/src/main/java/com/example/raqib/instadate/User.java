package com.example.raqib.instadate;

/**
 * Created by RaQib on 18-01-2016.
 */
public class User {
    String email,password,name;
    public User(String name,String email, String password){
        this.name= name;
        this.email = email;
        this.password = password;
    }
    public User(String email, String password){
        this.name= "";
        this.email = email;
        this.password = password;
    }

}
