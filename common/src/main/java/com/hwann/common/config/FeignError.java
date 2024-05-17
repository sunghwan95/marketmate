package com.hwann.common.config;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignError implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return FeignException.errorStatus(methodKey, response);
    }
}