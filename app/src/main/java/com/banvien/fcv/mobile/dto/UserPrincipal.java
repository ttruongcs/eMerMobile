package com.banvien.fcv.mobile.dto;

import com.banvien.fcv.mobile.utils.DataBinder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Hieu Le
 * Date: 21/06/16
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserPrincipal implements Serializable {
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String code;

    private String avatar;

    private String group;

    private String jwt;

    private List<String> authorities;

    public UserPrincipal() {
    }

    public UserPrincipal(Long userId, String firstName, String lastName, String email, String phoneNumber, String code) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.code = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String toJsonString() {
        return DataBinder.toJsonString(this);
    }

    public List<String> getAuthorities() {
        if (authorities == null) {
            authorities = new ArrayList<>();
        }
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", code='" + code + '\'' +
                ", avatar='" + avatar + '\'' +
                ", group='" + group + '\'' +
                ", jwt='" + jwt + '\''+
                '}';
    }
}
