package com.bryobone.graben.core;

import javax.persistence.*;

@Entity
@Table(name = "people")
@NamedQueries({
  @NamedQuery(
    name = "com.bryobone.graben.core.Person.findAll",
    query = "SELECT p FROM Person p"
  ),
  @NamedQuery(
    name = "com.bryobone.graben.core.Person.findById",
    query = "SELECT p FROM Person p WHERE p.id = :id"
  )
})

public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "fullName", nullable = false)
  private String fullName;

  @Column(name = "jobTitle", nullable = false)
  private String jobTitle;

  public long getId() {
      return id;
  }

  public void setId(long id) {
      this.id = id;
  }

  public String getFullName() {
      return fullName;
  }

  public void setFullName(String fullName) {
      this.fullName = fullName;
  }

  public String getJobTitle() {
      return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
      this.jobTitle = jobTitle;
  }
}
