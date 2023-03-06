package com.example.authentication.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("amqp")
public interface NotificationFeign {
    @PostMapping(path = "/api/v1.0/amqp/producer")
    void pushNotification(String request);
}
