package com.nash.assignment.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table
public class Accounts {
    @Id
    @SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "account_sequence")

    private long id;
    @Column(nullable = true, unique = false, length = 20)
    private String avatar;
    @Column(nullable = true, unique = false, length = 13)
    private String phoneNumber;
    @Column(nullable = true, unique = false, length = 100)
    private String fullName;
    @Column(nullable = true, unique = false, length = 100)
    private String username;
    @Column(nullable = true, unique = false, length = 100)
    private String password;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role_id")
    private Roles role;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "status_id")
    private Status status;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.REFRESH)
    private Set<Orders> order = new HashSet<>();
    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.REFRESH)
    private Set<RateProducts> rate = new HashSet<>();

    public Accounts() {
    }

    public Accounts(String phoneNumber, String fullName, String userName, String password, Roles role, Status status) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.username = userName;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Orders> getOrder() {
        return order;
    }

    public void setOrder(Set<Orders> order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Account [fullName=" + fullName + ", id=" + id + ", password=" + password + ", phoneNumber="
                + phoneNumber + ", role=" + role + ", userName=" + username + "]";
    }

}
