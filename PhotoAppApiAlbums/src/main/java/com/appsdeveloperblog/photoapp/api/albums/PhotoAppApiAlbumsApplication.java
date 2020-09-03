package com.appsdeveloperblog.photoapp.api.albums;

import com.appsdeveloperblog.photoapp.api.albums.shared.FeignErrorDecoder;
import feign.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PhotoAppApiAlbumsApplication {
    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(PhotoAppApiAlbumsApplication.class, args);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }


}

