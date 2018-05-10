/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: JwtTokenProvider.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package io.beingtechie.proman.api.service.user.provider.token;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
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

    private static final String TOKEN_ISSUER = "https://proman.io";

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
        final Date issuedAt = new Date(tokenSpec.getIssuedTime().getLong(ChronoField.INSTANT_SECONDS));
        final Date expiryAt = new Date(tokenSpec.getExpirationTime().getLong(ChronoField.INSTANT_SECONDS));

        return JWT.create().withIssuer(TOKEN_ISSUER) //
                .withAudience(tokenSpec.getClientId(), tokenSpec.getClientIpAddress()) //
                .withSubject(tokenSpec.getUserId()) //
                .withClaim("user_id", tokenSpec.getUserId()) //
                .withClaim("first_name", tokenSpec.getUserFirstname()) //
                .withClaim("last_name", tokenSpec.getUserLastname()) //
                .withClaim("email_address", tokenSpec.getUserEmailAddress()) //
                .withClaim("mobile_number", tokenSpec.getUserMobilePhone()) //
                .withClaim("last_login_at", tokenSpec.getUserLastLoginAt().getLong(ChronoField.INSTANT_SECONDS)) //
                .withClaim("role_id", tokenSpec.getRoleId()) //
                .withClaim("role_name", tokenSpec.getRoleName()) //
                .withArrayClaim("role_permissions", tokenSpec.getPermissionIds().toArray(new Integer[0])) //
                .withIssuedAt(issuedAt).withExpiresAt(expiryAt).sign(algorithm);
    }

    @Override
    public Token deserialize(final String rawToken) {
        final JWTVerifier verifier = JWT.require(algorithm).withIssuer(TOKEN_ISSUER).build();
        final DecodedJWT jwt = verifier.verify(rawToken);
        return new Token.Builder().clientId(jwt.getAudience().get(0)).clientIpAddress(jwt.getAudience().get(1)).userId(jwt.getSubject()).build();
    }

}