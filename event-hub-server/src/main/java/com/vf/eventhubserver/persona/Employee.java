package com.vf.eventhubserver.persona;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
public class Employee implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "invalid username. Username is null or empty")
  @Pattern(
      regexp = "^[a-zA-Z0-9]{4,}$",
      message =
          "invalid username. Username must contain only letters and digits and be at least 4 characters long")
  private String username;

  @NotNull(message = "invalid password. Password is null or empty")
  @Pattern(
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=-]).{6,}$",
      message =
          "invalid password. Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character and be at least 6 characters long")
  private String password;

  @NotNull(message = "invalid email. Email is null or empty")
  @Email(message = "invalid email. Email must contain @ and .")
  private String email;

  public Employee() {}

  public Employee(Long id, String username, String password, String email) {
    setId(id);
    setUsername(username);
    setPassword(password);
    setEmail(email);
  }

  public Employee(String username, String password, String email) {
    setUsername(username);
    setPassword(password);
    setEmail(email);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Employee other = (Employee) obj;
    if (username == null) {
      if (other.username != null) return false;
    } else if (!username.equals(other.username)) return false;
    if (email == null) {
      if (other.email != null) return false;
    } else if (!email.equals(other.email)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "id="
        + id
        + ", username="
        + username
        + ", password="
        + password
        + ", email="
        + email
        + "";
  }
}
