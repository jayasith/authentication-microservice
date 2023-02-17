package com.example.authentication.feign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AmqpRequest {
    private Object payload;
    private String exchange;
    private String routingKey;
}
