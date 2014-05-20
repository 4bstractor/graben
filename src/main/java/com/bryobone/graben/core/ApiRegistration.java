package com.bryobone.graben.core;

/**
 * Created by jarvis on 4/17/14.
 */

//TODO: Move this and the other wrappers to a sub package perhaps
// A Wrapper for the user class to provide convenience methods for api login and session management

public class ApiRegistration {
  public String username;
  public String email;
  public String password;
  public String password_confirmation;
  public String api_key;
  public String api_secret;

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

  public String getPasswordConfirmation() {
    return password_confirmation;
  }

  public void setPasswordConfirmation(String password_confirmation) { this.password_confirmation = password_confirmation; }
}
