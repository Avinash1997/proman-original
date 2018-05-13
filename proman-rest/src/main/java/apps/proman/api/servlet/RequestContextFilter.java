package apps.proman.api.servlet;

import static apps.proman.api.data.ResourceConstants.*;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import apps.proman.api.data.ApiRequestContext;
import apps.proman.service.common.data.DateTimeProvider;
import apps.proman.service.common.data.RequestContext;

@WebFilter(filterName = "RequestContextFilter", urlPatterns = BASE_URL_PATTERN)
public class RequestContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            httpResponse.setStatus(200);
        } else {
            final RequestContext requestContext = buildRequestContext(httpRequest);
            request.setAttribute(REQUEST_ATTR_REQUEST_CONTEXT, requestContext);
            httpResponse.addHeader(HEADER_REQUEST_ID, requestContext.getRequestId());
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        //do nothing
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
