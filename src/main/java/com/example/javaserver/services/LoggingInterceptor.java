package com.example.javaserver.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Arrays;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        StringBuilder sbHeaders = new StringBuilder();
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            sbHeaders.append("\n\t\t").append(headerName).append(": ").append(request.getHeader(headerName));
        });

        logger.info("""
                                
                        ======================== REQUEST START ========================
                            URI: {}
                            Method: {}
                            Headers: {}
                            Body: {}
                        ========================= REQUEST END =========================
                        """,
                request.getRequestURL(),
                request.getMethod(),
                sbHeaders,
                Arrays.toString(request.getInputStream().readAllBytes())
        );
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        byte[] responseBody = responseWrapper.getContentAsByteArray();
        String capturedResponseBody = Arrays.toString(responseBody);


        StringBuilder sbHeaders = new StringBuilder();
        response.getHeaderNames().forEach(headerName -> {
            sbHeaders.append("\n\t\t").append(headerName).append(": ").append(response.getHeaders(headerName));
        });

        logger.info("""
                                
                        ======================== RESPONSE START ========================
                            Status: {}
                            Headers: {}
                            Body: {}
                        ========================= RESPONSE END =========================
                        """,
                response.getStatus(),
                sbHeaders,
                capturedResponseBody
        );
    }

    private void logRequest(HttpRequest request, byte[] body) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================================ REQUEST BEGIN ===================================");
        sb.append("\tURI: ").append(request.getURI());
        sb.append("\tMETHOD: ").append(request.getMethod());
        request.getHeaders().toSingleValueMap().forEach((name, value) -> {
            sb.append(name).append(": ").append(value);
        });
        sb.append("BODY: ").append(Arrays.toString(body));
        sb.append("================================= REQUEST END ====================================");

        logger.info(sb.toString());
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("================================ RESPONSE BEGIN ===================================");
        sb.append("\tSTATUS: ").append(response.getStatusCode());
        response.getHeaders().toSingleValueMap().forEach((name, value) -> {
            sb.append(name).append(": ").append(value);
        });
        sb.append("BODY: ").append(Arrays.toString(response.getBody().readAllBytes()));
        sb.append("================================= RESPONSE END ====================================");

        logger.info(sb.toString());
    }
}
