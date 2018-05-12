/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: BearerAuthDecoder.java
 * Date: Nov 10, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package apps.proman.api.rest.provider;

/**
 * Provider to decode bearer token.
 */
public class BearerAuthDecoder {

    private final String accessToken;

    public BearerAuthDecoder(final String bearerToken) {
        this.accessToken = bearerToken.split("Bearer ")[1];
    }

    public String getAccessToken() {
        return accessToken;
    }

}