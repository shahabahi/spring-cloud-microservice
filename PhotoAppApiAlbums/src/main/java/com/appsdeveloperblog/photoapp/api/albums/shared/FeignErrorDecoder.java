package com.appsdeveloperblog.photoapp.api.albums.shared;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    Environment environment;

    @Autowired
    public FeignErrorDecoder(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                break;
            case 404:
                if (methodKey.contains("getUser")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), environment.getProperty("users.exception.user-not-found"));
                }
                break;
            default:
                return new Exception(response.reason());
        }
        return null;
    }
}
