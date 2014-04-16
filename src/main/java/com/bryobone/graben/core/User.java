package com.bryobone.graben.core;

import javax.persistence.*;

@Entity
@Table(name = "users")
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
    name = "com.bryobone.graben.core.User.checkUser",
    query = "SELECT u FROM User u WHERE u.username = :username AND u.hashed_password = :password"
  )
})

public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "hashed_password", nullable = false)
  private String hashed_password;

  @Column(name = "salt", nullable = false)
  private String salt;

  @Column(name = "created_at", nullable = false)
  private String created_at;

  @Column(name = "updated_at", nullable = false)
  private String updated_at;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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

  //Password stuff
}
