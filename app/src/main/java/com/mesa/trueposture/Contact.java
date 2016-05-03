package com.mesa.trueposture;

/**
 * Created by ucmjunew on 3/5/16.
 */

public class Contact {          //we need name, email address, username, password to be stored in the Contact
    String name, email, username, password;

    //name
    public void setName(String name) { this.name = name; }
    public String getName(){
        return this.name;
    }

    //email
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    //username
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }

    //password
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
}