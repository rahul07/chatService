package com.rrahul.uacf.rest.web;

import javax.ws.rs.client.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rrahul.uacf.rest.healthcheck.AppHealthCheck;
import com.rrahul.uacf.rest.healthcheck.HealthCheckController;
import com.rrahul.uacf.rest.web.resource.ChatResource;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ChatApp extends Application<Configuration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatApp.class);

	@Override
	public void initialize(Bootstrap<Configuration> b) {
	}

	@Override
	public void run(Configuration c, Environment e) throws Exception 
	{
		LOGGER.info("Registering REST resources");
		
		e.jersey().register(new ChatResource());

		final Client client = new JerseyClientBuilder(e)
				.build("ChatClient");

		// Application health check
		e.healthChecks().register("APIHealthCheck", new AppHealthCheck(client));

		// Run multiple health checks
		e.jersey().register(new HealthCheckController(e.healthChecks()));
	}

	public static void main(String[] args) throws Exception {
		new ChatApp().run(args);
	}
}