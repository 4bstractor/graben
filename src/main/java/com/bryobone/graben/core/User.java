package com.bryobone.graben.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(
  name = "users",
  uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email"})
)
@NamedQueries({
  @NamedQuery(
    name = "com.bryobone.graben.core.User.findAll",
    query = "SELECT u FROM User u"
  ),
  @NamedQuery(
    name = "com.bryobone.graben.core.User.findById",
    query = "SELECT u FROM User u WHERE u.id = :id"
  ),
  @NamedQuery(
    name = "com.bryobone.graben.core.User.findByUsername",
    query = "SELECT u FROM User u WHERE u.username = :username"
  ),
  @NamedQuery(
    name = "com.bryobone.graben.core.User.authenticate",
    query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password"
  )
})

public class User {
  private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

  public User() {}

  public User(String username, String email, String password) {
    this.setUsername(username);
    this.setEmail(email);
    this.setPassword(password);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "created_at", nullable = false)
  private long created_at = System.currentTimeMillis();

  @Column(name = "updated_at", nullable = false)
  private long updated_at = System.currentTimeMillis();

  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;

  // Attribute Handling

  public long getId() { return id; }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) { this.email = email; }

  // Timestamp Handling

  public long getCreatedAt() { return created_at; }

  public void setCreatedAt() { this.created_at = System.currentTimeMillis(); }

  public long getUpdatedAt() { return created_at; }

  public void setUpdatedAt() { this.created_at = System.currentTimeMillis(); }

  // Password Handling

  public String getPassword() { return password; }

  // Make this hash the password on save and generate a salt
  public void setPassword(String unhashed_password) {
    try {
      this.password = hashPassword(unhashed_password);
    } catch(NoSuchAlgorithmException e) {
      LOGGER.error("Undefined algorithm used, super duper error here, no password is saved");
    }
  }

  public boolean verifyPassword(String password) {
    try {
      return this.getPassword().equals(hashPassword(password));
    } catch (NoSuchAlgorithmException e) {
      LOGGER.error("Undefined algorithm used, can't compare passwords");
    }
    return false;
  }

  private String hashPassword(String unhashed_password) throws NoSuchAlgorithmException {
    MessageDigest hashed_password = MessageDigest.getInstance("SHA-256");
    hashed_password.update(unhashed_password.getBytes());
    return new String(hashed_password.digest());
  }
}
