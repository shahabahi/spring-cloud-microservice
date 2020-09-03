package com.art.photoapp.api.users.data;

import com.art.photoapp.api.users.ui.model.AlbumResponseModel;
import feign.Feign;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "albums-ws", fallbackFactory = AlbumsFallbackFactory.class)
public interface AlbumServiceClient {

    @GetMapping("/users/{id}/albums")
    public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}

/*

@Component
class AlbumsFallback implements AlbumServiceClient{
    @Override
    public List<AlbumResponseModel> getAlbums(String id) {
        return new ArrayList<>();
    }
}
*/
@Component
class AlbumsFallbackFactory implements FallbackFactory<AlbumServiceClient> {

    @Override
    public AlbumServiceClient create(Throwable cause) {
        return new AlbumServiceClientFallback(cause);
    }
}

class AlbumServiceClientFallback implements AlbumServiceClient {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Throwable cause;

    public AlbumServiceClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public List<AlbumResponseModel> getAlbums(String id) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            logger.error("404 error took place when getAlbum was called with userId: "
                    + id + ". Error Message : "
                    + cause.getLocalizedMessage());
        } else {
            logger.error("Other error took place : " + cause.getLocalizedMessage());
        }
        return new ArrayList<>();
    }
}

