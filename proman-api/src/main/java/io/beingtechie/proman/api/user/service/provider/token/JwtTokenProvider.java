/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: JwtTokenProvider.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.user.service.provider.token;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.beingtechie.proman.api.common.data.DateTimeProvider;
import io.beingtechie.proman.api.common.exception.GenericErrorCode;
import io.beingtechie.proman.api.common.exception.UnexpectedException;

/**
 * Provider to serialize/deserialize the JWT specification based tokens.
 */
public class JwtTokenProvider implements TokenProvider {

    private static final String TOKEN_ISSUER = "www.redux.com";

    private final Algorithm algorithm;

    public JwtTokenProvider(final String secret) {
        try {
            algorithm = Algorithm.HMAC512(secret);
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            throw new UnexpectedException(GenericErrorCode.GEN_001);
        }
    }

    @Override
    public String serialize(final Token tokenSpec) {
        final ZonedDateTime issuedDateTime = DateTimeProvider.getInstance().fromTime(tokenSpec.getIssuedTime());
        final ZonedDateTime expiryDateTime = DateTimeProvider.getInstance().fromTime(tokenSpec.getExpiryTime());

        final Date issuedAt = new Date(issuedDateTime.getLong(ChronoField.INSTANT_SECONDS));
        final Date expiryAt = new Date(expiryDateTime.getLong(ChronoField.INSTANT_SECONDS));

        return JWT.create().withKeyId(tokenSpec.getRequestId()).withIssuer(TOKEN_ISSUER).withSubject(tokenSpec.getUserId()).withAudience(tokenSpec.getClientId()) //
                .withIssuedAt(issuedAt).withExpiresAt(expiryAt).sign(algorithm);
    }

    @Override
    public Token deserialize(final String rawToken) {
        final JWTVerifier verifier = JWT.require(algorithm).withIssuer(TOKEN_ISSUER).build();
        final DecodedJWT jwt = verifier.verify(rawToken);
        return new Token(jwt.getKeyId(), jwt.getAudience().get(0), jwt.getSubject(), jwt.getIssuedAt().getTime(), jwt.getExpiresAt().getTime());
    }

}