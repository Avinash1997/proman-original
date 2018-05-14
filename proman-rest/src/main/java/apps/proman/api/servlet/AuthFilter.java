package apps.proman.api.servlet;

import static apps.proman.api.data.ResourceConstants.*;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import apps.proman.api.exception.RestErrorCode;
import apps.proman.api.exception.UnauthorizedException;

@WebFilter(filterName = "AuthFilter", urlPatterns = BASE_URL_PATTERN)
public class AuthFilter extends ApiFilter {

    @Override
    public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final String authorization = servletRequest.getHeader(HEADER_AUTHORIZATION);
        if(StringUtils.isEmpty(authorization)) {
            throw new UnauthorizedException(RestErrorCode.ATH_001);
        }
        else if(!authorization.startsWith(BASIC_AUTH_PREFIX) && !authorization.startsWith(BEARER_AUTH_PREFIX)){
            throw new UnauthorizedException(RestErrorCode.ATH_002);
        }
        else {
            servletRequest.getHeaders(HEADER_AUTHORIZATION);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
