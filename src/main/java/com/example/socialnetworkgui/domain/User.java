package com.example.socialnetworkgui.domain;
import java.util.Objects;

// Info necessary when making a new account:
//  First Name - string, starts with capital letter, not null
//  Last Name - string, starts with capital letter, not null
//  Password - string, length greater than 5 characters
//  Age - positive, at least 18
public class User extends Entity<Long> {
    private String firstName;
    private String lastName;
    private  String password;
    private int age;

    // Constructor
    public User(String firstName, String lastName, String password, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
    }

    // getters - for in app usage
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    // setter
    // The user can modify his first name.
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // The user can modify his last name.
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // The user can set a new password.
    public void setPassword(String password) {
        this.password = password;
    }

    // The user can update his age( for example, he was younger when he had joined the social network).
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object obj)
    {
        // compares the references of the 2 objects
        if (this == obj) return true;

        //
        if (!(obj instanceof User)) return  false;

        // We compare the first name and last name
        User user = (User) obj;   // type-cast
        return Objects.equals(getFirstName(), user.firstName) &&
                Objects.equals(getLastName(), user.lastName);
    }
}
