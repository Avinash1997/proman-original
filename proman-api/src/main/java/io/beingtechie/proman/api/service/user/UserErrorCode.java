/*
 * Copyright 2018-2019, https://beingtechie.io
 *
 * File: UserErrorCode.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.service.user;

import java.util.HashMap;
import java.util.Map;

import io.beingtechie.proman.api.common.exception.ErrorCode;

/**
 * Error code for USER module.
 */
public enum UserErrorCode implements ErrorCode {

    USR_001("USR-001", "User with identifier [{0}] does not exist."),

    USR_002("USR-002", "Username does not exist."),

    USR_003("USR-003", "Password match failed."),

    USR_004("USR-004", "Concurrent login attempt by the user from other device(s)."),

    USR_005("USR-005", "Invalid access token.");

    private static final Map<String, UserErrorCode> LOOKUP = new HashMap<String, UserErrorCode>();

    static {
        for (final UserErrorCode enumeration : UserErrorCode.values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final String code;

    private final String defaultMessage;

    private UserErrorCode(final String code, final String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }

}