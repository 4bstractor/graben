package com.bryobone.graben.core;

import javax.persistence.*;

@Entity
@Table(name = "schools")
@NamedQueries({
  @NamedQuery(
    name = "com.bryobone.graben.core.School.findAll",
    query = "SELECT s FROM School s"
  ),
  @NamedQuery(
    name = "com.bryobone.graben.core.School.findById",
    query = "SELECT s FROM School s WHERE s.id = :id"
  )
})

public class School {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "note", nullable = false)
  private String note;

  public long getId() {
      return id;
  }

  public void setId(long id) {
      this.id = id;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public String getNote() {
      return note;
  }

  public void setNote(String note) {
      this.note = note;
  }
}
