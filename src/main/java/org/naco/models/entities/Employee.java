package org.naco.models.entities;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "employees")
@UserDefinition
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String fio;

    private Short rank;

    private Post post;

    @Username
    public String username;
    @Password
    public String password;
    @Roles
    public String role;

    public Long getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Short getRank() {
        return rank;
    }

    public void setRank(Short rank) {
        this.rank = rank;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
