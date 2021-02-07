package com.toomuch2learn.microservices.serviceb;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class GreetingController {

	@Value("${common.message}")
	private String commonMessage;

	@Value("${service.name}")
	private String serviceName;

	@Value("${service.secret}")
	private String secretMessage;

	private static final String template = "Service-B - Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting() {
		System.out.println(String.format("===> %s %s", commonMessage, serviceName));

		return new Greeting(counter.incrementAndGet(), String.format(template, String.format("%s - %s", serviceName, secretMessage)));
	}
}

class Greeting {

	private final long id;
	private final String content;

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}