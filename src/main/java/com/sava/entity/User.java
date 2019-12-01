package com.sava.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    private int pk;
    private String login;
    private String password;

    public User() {
        // intentionally left blank
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String username) {
        this.login = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return new StringBuilder("User [login: ")
                .append(login)
                .append(" password: ")
                .append(password)
                .append("]")
                .toString();

    }
}
