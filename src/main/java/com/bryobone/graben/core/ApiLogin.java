package com.bryobone.graben.core;

/**
 * Created by jarvis on 5/20/14.
 */

//TODO: Move this and the other wrappers to a sub package perhaps
// A Wrapper for the user class to provide convenience methods for api login

public class ApiLogin {
    public String username;
    public String email;
    public String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
