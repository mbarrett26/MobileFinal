package com.example.mobilefinalproject;

import android.accounts.Account;

public class User {
    private long id;
    private String username;
    private String password;

    public User(){

    }

    public User(String userInput, String passInput){
        this.username=userInput;
        this.password=passInput;
    }
    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o){ //Function to compare user object. Used to validate if a user client entered the correct information

        if (this == o)  // checking if the two objects are the same
            return true;


        if (o == null || this.getClass() != o.getClass()) //  checking for two condition: object is pointing to null and  if the objects belong to same class
            return false;

        User Acc = (User)o; // type casting object



        return this.username.equalsIgnoreCase(Acc.username) && this.password.equals(Acc.password); // checking if the two objects share all the same values

    }
}
