package com.example.javaserver.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class CachingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(response);

        // Execution request chain
        filterChain.doFilter(req, resp);

        // Get Cache
        byte[] requestBody = req.getContentAsByteArray();
        byte[] responseBody = resp.getContentAsByteArray();

        StringBuilder sbReqHeaders = new StringBuilder();
        req.getHeaderNames().asIterator().forEachRemaining(headerName ->
                sbReqHeaders.append("\n\t\t").append(headerName).append(": ").append(req.getHeader(headerName)));

        log.info("""

                        ======================== REQUEST START ========================
                            URI: {}
                            Method: {}
                            Headers: {}
                            Body: {}
                        ========================= REQUEST END =========================
                        """,
                req.getRequestURL(),
                req.getMethod(),
                sbReqHeaders,
                new String(requestBody, StandardCharsets.UTF_8)
        );

        StringBuilder sbResHeaders = new StringBuilder();
        resp.getHeaderNames().iterator().forEachRemaining(headerName ->
                sbResHeaders.append("\n\t\t").append(headerName).append(": ").append(resp.getHeader(headerName)));
        log.info("""

                        ======================== RESPONSE START ========================
                            Status: {}
                            Headers: {}
                            Body: {}
                        ========================= RESPONSE END =========================
                        """,
                resp.getStatus(),
                sbResHeaders,
                new String(responseBody, StandardCharsets.UTF_8)
        );

        resp.copyBodyToResponse();
    }
}