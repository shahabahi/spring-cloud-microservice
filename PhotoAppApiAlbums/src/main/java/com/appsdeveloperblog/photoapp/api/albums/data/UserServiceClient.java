package com.appsdeveloperblog.photoapp.api.albums.data;

import com.appsdeveloperblog.photoapp.api.albums.ui.model.UserResponseModel;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-ws", fallbackFactory = UsersFallbackFactory.class)
public interface UserServiceClient {
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId);
}
    @Component
    class UsersFallbackFactory implements FallbackFactory<UserServiceClient> {

        @Override
        public UserServiceClient create(Throwable cause) {
            return new UserServiceClientFallback(cause);
        }
    }

    class UserServiceClientFallback implements UserServiceClient {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        private final Throwable cause;

        public UserServiceClientFallback(Throwable cause) {
            this.cause = cause;
        }

        @Override
        public ResponseEntity<UserResponseModel> getUser(String id) {
            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                logger.error("404 error took place when getUser was called with userId: "
                        + id + ". Error Message : "
                        + cause.getLocalizedMessage());
            } else {
                logger.error("Other error took place : " + cause.getLocalizedMessage());
            }
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

    }
