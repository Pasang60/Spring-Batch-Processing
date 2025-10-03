package com.batch.batchProcessing.security;

import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
public enum WHITE_LIST_URLS {
    AUTH("/api/v1/auth/**", HttpMethod.POST),
    SWAGGER("/v1/**", HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.PATCH),
    MEDIA("/media/**", HttpMethod.POST, HttpMethod.GET),


    //landing page apis
    PUBLIC("/api/v1/public/**");

    private final String url;
    private final HttpMethod[] methods;

//    WHITE_LIST_URLS(String url) {
//        this.url = url;
//    }

    WHITE_LIST_URLS(String url, HttpMethod... methods) {
        this.url = url;
        this.methods = methods;
    }

}