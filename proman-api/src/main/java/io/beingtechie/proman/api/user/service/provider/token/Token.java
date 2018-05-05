/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: Token.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.user.service.provider.token;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Specification that defines the generated token.
 */
public class Token {

    private final String requestId;
    private final String clientId;
    private final String userId;
    private final String clientIpAddress;
    private final long issuedTime;
    private final long expiryTime;

    public Token(final String requestId, final String clientId, final String userId, final long issuedTimeInMilliSecs, final long expiryTimeInMilliSecs) {
        this(requestId, clientId, null, userId, issuedTimeInMilliSecs, expiryTimeInMilliSecs);
    }

    public Token(final String requestId, final String clientId, final String clientIpAddress, final String userId, final long issuedTimeInMilliSecs, final long expiryTimeInMilliSecs) {
        this.requestId = requestId;
        this.clientId = clientId;
        this.clientIpAddress = clientIpAddress;
        this.userId = userId;
        this.issuedTime = issuedTimeInMilliSecs;
        this.expiryTime = expiryTimeInMilliSecs;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public String getUserId() {
        return userId;
    }

    public long getIssuedTime() {
        return issuedTime;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Token)) {
            return false;
        }

        final Token that = (Token) obj;
        return Objects.equals(this.getRequestId(), that.getRequestId())
                && Objects.equals(this.getClientId(), that.getClientId())
                && Objects.equals(this.getClientIpAddress(), that.getClientIpAddress())
                && Objects.equals(this.getUserId(), that.getUserId())
                && Objects.equals(this.getIssuedTime(), that.getIssuedTime())
                && Objects.equals(this.getExpiryTime(), that.getExpiryTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getRequestId(), this.getClientId(), this.getClientIpAddress(), this.getUserId(), this.getIssuedTime(), this.getExpiryTime());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}