package com.toomuch2learn.microservices.servicea;

import java.util.concurrent.atomic.AtomicLong;

import com.toomuch2learn.microservices.servicea.client.ServiceBClient;

import com.toomuch2learn.microservices.servicea.client.ServiceCClient;
import com.toomuch2learn.microservices.servicea.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Service-A - Hello, %s! - %s - %s";
	private final AtomicLong counter = new AtomicLong();

	@Value("${common.message}")
	private String commonMessage;

	@Autowired
	private ServiceBClient serviceBClient;

	@Autowired
	private ServiceCClient serviceCClient;

	@GetMapping("/greeting")
	public Greeting greeting() {
		System.out.println(String.format("===> %s %s", commonMessage, serviceName));

		return new Greeting(
			counter.incrementAndGet(),
			String.format(template, name, getGreetingsFromFromServiceB(), getGreetingsFromFromServiceC()));
	}

	private String getGreetingsFromFromServiceB() {
		Greeting greeting = serviceBClient.getGreetingMessage();

		return greeting != null ? greeting.getContent() + " - " + greeting.getId() : "Service B Not Available";
	}

	private String getGreetingsFromFromServiceC() {
		Greeting greeting = serviceCClient.getGreetingMessage();

		return greeting != null ? greeting.getContent() + " - " + greeting.getId() : "Service C Not Available";
	}
}