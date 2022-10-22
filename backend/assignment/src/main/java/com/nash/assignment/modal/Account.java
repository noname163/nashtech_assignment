package com.nash.assignment.modal;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nash.assignment.constant.StatusEnum;

@Entity
@Table
public class Account {
    @Id
    @SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "account_sequence")

    private long id;
    @Column(nullable = true, unique = false, length = 13)
    private String phoneNumber;
    @Column
    private String email;
    @Column(nullable = true, unique = false, length = 100)
    private String fullName;
    @Column(nullable = true, unique = false, length = 100)
    private String username;
    @Column(nullable = true, unique = false, length = 100)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.REFRESH)
    private Set<Order> order = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.REFRESH)
    private Set<RateProduct> rate = new HashSet<>();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    public Account() {
    }

    public Account(String phoneNumber, String fullName, String userName, String password, Role role,
            StatusEnum status) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.username = userName;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account [fullName=" + fullName + ", id=" + id + ", password=" + password + ", phoneNumber="
                + phoneNumber + ", role=" + role + ", userName=" + username + "]";
    }

}
