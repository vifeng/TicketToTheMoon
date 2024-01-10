package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

// REFACTOR: use the heritance strategy, and create a new class for the customer and the employee.
@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "invalid username. Username is null or empty")
    private String username;
    @NotNull(message = "invalid password. Password is null or empty")
    private String password;
    @NotNull(message = "invalid email. Email is null or empty")
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

    // Private methods
    /**
     * Checks if the username meets the requirements.
     * 
     * @param username
     * @return true if the username meets the requirements.
     */
    private boolean checkUsername(String username) {
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Username is null or empty");
        int lengthMin = 4;
        if (username.length() < lengthMin)
            throw new IllegalArgumentException(
                    "Username must be at least " + lengthMin + " characters long");
        return true;
    }

    /**
     * Checks if the password meets the requirements.
     * 
     * @param password
     * @return true if the password meets the requirements.
     */
    private boolean checkPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password is null or empty");
        }
        int lengthMin = 6;
        if (password.length() < lengthMin) {
            throw new IllegalArgumentException(
                    "Password must be at least " + lengthMin + " characters long");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("Password must contain at least one digit");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException(
                    "Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException(
                    "Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[!@#$%^&*()_+=-].*")) {
            throw new IllegalArgumentException(
                    "Password must contain at least one special character");
        }
        return true;
    }

    private boolean checkEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }
        if (!email.matches(".*[@].*")) {
            throw new IllegalArgumentException("Email must contain @");
        }
        if (!email.matches(".*[.].*")) {
            throw new IllegalArgumentException("Email must contain .");
        }
        return true;
    }

    // Getters, Setters and toString
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
        checkUsername(username);
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        checkPassword(password);
        this.password = password;
    }

    public String getEmail() {
        checkEmail(email);
        return email;
    }

    public void setEmail(String email) {
        checkEmail(email);
        this.email = email;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Administrator [id=" + id + ", username=" + username + ", password=" + password
                + ", email=" + email + "]";
    }

}
