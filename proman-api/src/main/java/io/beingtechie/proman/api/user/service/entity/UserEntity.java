/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserEntity.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.user.service.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.beingtechie.proman.api.common.entity.ExternalIdentifier;
import io.beingtechie.proman.api.common.entity.Identifier;
import io.beingtechie.proman.api.common.entity.MutableEntity;
import io.beingtechie.proman.api.common.entity.ext.EntityEqualsBuilder;
import io.beingtechie.proman.api.common.entity.ext.EntityHashCodeBuilder;

/**
 * Entity mapping for USERS table.
 */
@Entity
@Table(name = "USERS")
@NamedQueries({ @NamedQuery(name = UserEntity.BY_EMAIL, query = "select u from UserEntity u where u.email = :email") })
public class UserEntity extends MutableEntity implements Identifier<Long>, ExternalIdentifier<String>, Serializable {

    private static final long serialVersionUID = 7821286494206402080L;

    public static final String BY_EMAIL = "UserEntity.byEmail";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "UUID")
    @NotNull
    @Size(max = 64)
    private String uuid;

    @Column(name = "ROLE_ID")
    @NotNull
    private int roleId;

    @Column(name = "EMAIL")
    @NotNull
    @Size(max = 200)
    private String email;

    @Column(name = "PASSWORD")
    @ToStringExclude
    private String password;

    @Column(name = "FIRST_NAME")
    @NotNull
    @Size(max = 200)
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    @Size(max = 200)
    private String middleName;

    @Column(name = "LAST_NAME")
    @NotNull
    @Size(max = 200)
    private String lastName;

    @Column(name = "OFFICE_PHONE")
    @NotNull
    @Size(max = 50)
    private String officePhone;

    @Column(name = "MOBILE_PHONE")
    @NotNull
    @Size(max = 50)
    private String mobilePhone;

    @Column(name = "FAX")
    @Size(max = 50)
    private String fax;

    @Column(name = "STATUS")
    @NotNull
    private int status;

    @Column(name = "FAILED_LOGIN_COUNT")
    @Min(0)
    @Max(5)
    private int failedLoginCount;

    @Column(name = "LAST_PASSWORD_CHANGE_AT")
    private ZonedDateTime lastPasswordChangeAt;

    @Column(name = "LAST_LOGIN_AT")
    private ZonedDateTime lastLoginAt;

    @Column(name = "SALT")
    @NotNull
    @Size(max = 200)
    @ToStringExclude
    private String salt;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(final String fax) {
        this.fax = fax;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public int getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(final int failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public ZonedDateTime getLastPasswordChangeAt() {
        return lastPasswordChangeAt;
    }

    public void setLastPasswordChangeAt(final ZonedDateTime lastPwdChgDate) {
        this.lastPasswordChangeAt = lastPwdChgDate;
    }

    public ZonedDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(final ZonedDateTime lastLoginTime) {
        this.lastLoginAt = lastLoginTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object obj) {
        return new EntityEqualsBuilder<Long>().equalsById(this, obj);
    }

    @Override
    public int hashCode() {
        return new EntityHashCodeBuilder<Long>().hashCodeById(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
