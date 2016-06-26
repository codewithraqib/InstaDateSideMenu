package com.example.raqib.instadate;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *  Created by RaQib on 18-01-2016.
 */

  // EMPLOYED TO STORE THE DATA OF A USER INSIDE THE PHONE WHETHER HE IS LOGGED IN OR LOGGED OUT

    public class UserLocalStore {

        public static final String SP_NAME = "userDetails";
        SharedPreferences userLocalDatabase;
        public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);
    }

    public void storeUserData(User user){

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.name);
        spEditor.putString("email", user.email);
        spEditor.putString("password", user.password);
        spEditor.commit();
    }

    public User getLoggedInUser(){

        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password", "");
        User storedUser = new User(name,email, password);
        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){

        if(userLocalDatabase.getBoolean("loggedIn",false) == true){
            return  true;
        }else{
            return false;
        }
    }

    public void clearUserData(){

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}