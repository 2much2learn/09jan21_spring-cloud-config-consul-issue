package com.toomuch2learn.microservices.servicea.client;

import com.toomuch2learn.microservices.servicea.model.Greeting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "serviceb", url = "${serviceb.url}")
public interface ServiceBClient {

    @RequestMapping(method = RequestMethod.GET, value = "/greeting")
    Greeting getGreetingMessage();
}