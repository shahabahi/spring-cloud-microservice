package com.art.photoapp.api.users;

import com.art.photoapp.api.users.shared.FeignErrorDecoder;
import feign.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class PhotoappapiusersApplication {
    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(PhotoappapiusersApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Profile(value = "production")
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
    @Bean
    @Profile(value = "!production")
    Logger.Level feignDefaultLoggerLevel() {
        return Logger.Level.FULL;
    }

    /*  @Bean
      public FeignErrorDecoder getFeignErrorDecoder(){
          return new FeignErrorDecoder();
      }*/
    @Bean
    @Profile(value = "production")
    public String createProductionBean() {
        System.out.println("Production Bean Created. myapplication.environment= " + environment.getProperty("myapplication.environment"));
        return "Production Bean";
    }

    @Bean
    @Profile(value = "!production")
    public String createNotProductionBean() {
        System.out.println("Not Production Bean Created. myapplication.environment= " + environment.getProperty("myapplication.environment"));
        return "Not Production Bean";
    }

    @Bean
    @Profile(value = "default")
    public String createDevelopmentBean() {
        System.out.println("Development Bean Created. myapplication.environment= " + environment.getProperty("myapplication.environment"));
        return "Development Bean";
    }
}
