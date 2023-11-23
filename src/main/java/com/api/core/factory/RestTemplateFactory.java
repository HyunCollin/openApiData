package com.api.core.factory;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory {
    private static SimpleClientHttpRequestFactory factory;
    public static RestTemplate getRestTemplate() {
        factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(30000);
        factory.setReadTimeout(30000);
        return new RestTemplate(factory);
    }
}
