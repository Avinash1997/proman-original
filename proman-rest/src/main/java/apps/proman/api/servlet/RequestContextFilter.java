package apps.proman.api.servlet;

import static apps.proman.api.data.ResourceConstants.*;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import apps.proman.api.data.ApiRequestContext;
import apps.proman.service.common.data.DateTimeProvider;
import apps.proman.service.common.data.RequestContext;

@WebFilter(filterName = "RequestContextFilter", urlPatterns = BASE_URL_PATTERN)
public class RequestContextFilter extends ApiFilter {

    @Override
    public void doFilter(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws IOException, ServletException {
        final RequestContext requestContext = buildRequestContext(httpRequest);
        httpRequest.setAttribute(REQUEST_ATTR_REQUEST_CONTEXT, requestContext);
        httpResponse.addHeader(HEADER_REQUEST_ID, requestContext.getRequestId());
        filterChain.doFilter(httpRequest, httpResponse);
    }

    private RequestContext buildRequestContext(final HttpServletRequest httpRequest) {
        final RequestContext requestContext = new ApiRequestContext();
        requestContext.setRequestId(UUID.randomUUID().toString());
        requestContext.setClientId(httpRequest.getHeader(HEADER_CLIENT_ID));
        requestContext.setOriginIpAddress(httpRequest.getHeader(HEADER_CLIENT_IP_ADDRESS));
        requestContext.setRequestTime(DateTimeProvider.getInstance().currentProgramTime());
        return requestContext;
    }

}
