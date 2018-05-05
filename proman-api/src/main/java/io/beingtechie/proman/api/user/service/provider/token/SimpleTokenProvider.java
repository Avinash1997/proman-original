/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: SimpleTokenProvider.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.user.service.provider.token;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import io.beingtechie.proman.api.user.service.provider.TokenCryptographyProvider;

/**
 * Provider to serialize/deserialize the simple tokens.
 */
@Component
public class SimpleTokenProvider implements TokenProvider {

    private static final String SEPARATOR = ".";

    @Override
    public String serialize(final Token tokenSpec) {

        final StringBuilder payloadBuilder = new StringBuilder();
        payloadBuilder.append(tokenSpec.getRequestId());
        payloadBuilder.append(SEPARATOR);
        payloadBuilder.append(tokenSpec.getClientId());
        payloadBuilder.append(SEPARATOR);
        payloadBuilder.append(tokenSpec.getClientIpAddress());
        payloadBuilder.append(SEPARATOR);
        payloadBuilder.append(tokenSpec.getUserId());
        payloadBuilder.append(SEPARATOR);
        payloadBuilder.append(tokenSpec.getIssuedTime());
        payloadBuilder.append(SEPARATOR);
        payloadBuilder.append(tokenSpec.getExpiryTime());
        payloadBuilder.append(SEPARATOR);
        payloadBuilder.append(salt());

        return TokenCryptographyProvider.encrypt(payloadBuilder.toString());
    }

    @Override
    public Token deserialize(final String bearerToken) {
        final String decryptedToken = TokenCryptographyProvider.decrypt(bearerToken);
        final String[] tokenPayload = decryptedToken.split(SEPARATOR);
        return new Token(tokenPayload[0], tokenPayload[1], tokenPayload[2], tokenPayload[3], Long.valueOf(tokenPayload[4]), Long.valueOf(tokenPayload[5]));
    }

    private String salt() {
        return new BigInteger(130, new SecureRandom()).toString(15);
    }

}