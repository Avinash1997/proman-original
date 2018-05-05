/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: ServiceException.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.common.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;

/**
 * User defined exception that is thrown by all core services.
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = -5943745525367919210L;

    private final ErrorCode errorCode;

    private Throwable cause;

    private final Object[] parameters;

    public ServiceException(final ErrorCode errorCode, final Object... parameters) {
        super();
        this.errorCode = errorCode;
        this.parameters = parameters;
    }

    public ServiceException(final ErrorCode errorCode, final Throwable cause, final Object... parameters) {
        super();
        this.errorCode = errorCode;
        this.cause = cause;
        this.parameters = parameters;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format(errorCode.getDefaultMessage(), this.parameters);
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public final void printStackTrace(final PrintStream stream) {
        super.printStackTrace(stream);
    }

    @Override
    public final void printStackTrace(final PrintWriter writer) {
        super.printStackTrace(writer);
    }

}