package com.sava.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PK")
    @SequenceGenerator(name = "seq_PK", sequenceName = "SYSTEM_SEQUENCE_1A5127C4_56AA_478D_BFAD_238603ABAC74", allocationSize = 1)
    @Column(name = "pk", updatable = false, nullable = false)
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
