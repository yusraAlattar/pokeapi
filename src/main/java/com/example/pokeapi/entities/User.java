package com.example.pokeapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    @Id
    private String id;
    @NotEmpty(message = "Username should not be empty please!")
    @Size(min = 4, max = 15, message = "Username should be between 4 and 15 characters long")
    private String username;
    @NotEmpty
    @Size(min = 6, message = "password must contain at least 6 characters")
    private String password;
    private List<String> roles;

    public User() {

    }

    public User(@NotEmpty(message = "Username should not be empty please!") @Size(min = 4, max = 15, message = "Username should be between 4 and 15 characters long") String username, @Size(min = 6, message = "password must contain at least 6 characters") String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
