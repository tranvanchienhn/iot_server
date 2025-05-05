/**
 * Copyright © 2016-2025 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.service.security.auth.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
@Slf4j
public class SkipPathRequestMatcher implements RequestMatcher {
    private OrRequestMatcher skipMatchers;//matchers;
    private RequestMatcher processMatcher;//processingMatcher;

    public SkipPathRequestMatcher(List<String> pathsToSkip, String processingPath) {
        Assert.notNull(pathsToSkip, "List of paths to skip is required.");
        //List<RequestMatcher> m = pathsToSkip.stream().map(path -> new AntPathRequestMatcher(path)).collect(Collectors.toList());
        List<RequestMatcher> m = new ArrayList<>();
        for (String path : pathsToSkip) {
            m.add(new AntPathRequestMatcher(path));
        }
        skipMatchers = new OrRequestMatcher(m);
        processMatcher = new AntPathRequestMatcher(processingPath);
    }

    // @Override
    // public boolean matches(HttpServletRequest request) {
    //     if (matchers.matches(request)) {
    //         return false;
    //     }
    //     return processingMatcher.matches(request) ? true : false;
    // }
    @Override
    public boolean matches(HttpServletRequest request) {
        if (shouldSkip(request)) {
            log.debug("Skipping auth for: {} {}", request.getMethod(), request.getRequestURI());
            return false; // Đảo ngược logic để bỏ qua filter
        }
        if (processMatcher.matches(request)) {
            log.debug("Processing auth for: {} {}", request.getMethod(), request.getRequestURI());
            return true; // Áp dụng filter
        }
        log.debug("Not matching: {} {}", request.getMethod(), request.getRequestURI());
        return false; // Mặc định bỏ qua
    }

    private boolean shouldSkip(HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // Check for POST requests to /api/customer/**
        if (HttpMethod.POST.name().equalsIgnoreCase(method) && uri.startsWith("/api/customer/")) {
            return true; // Skip filter for POST /api/customer/**
        }
        // Check for POST requests to /api/user/**
        if (HttpMethod.POST.name().equalsIgnoreCase(method) && uri.startsWith("/api/user/")) {
            return true; // Skip filter for POST /api/user/**
        }

        // Check if the request matches any of the general skip paths
        if (skipMatchers.matches(request)) {
            return true; // Skip filter for general skip paths
        }

        return false; // Default to not skipping
    }
}
