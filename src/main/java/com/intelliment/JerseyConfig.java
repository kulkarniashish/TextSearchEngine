package com.intelliment;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.intelliment.service.impl.SearchServiceImpl;

@Configuration
@ApplicationPath("/counter-api")
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		register(SearchServiceImpl.class);
	}
}
