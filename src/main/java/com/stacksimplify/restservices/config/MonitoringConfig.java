package com.stacksimplify.restservices.config;

import org.springframework.context.annotation.Configuration;

import io.micrometer.appoptics.AppOpticsConfig;
import io.micrometer.appoptics.AppOpticsMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.lang.Nullable;

@Configuration
public class MonitoringConfig {
	
	AppOpticsConfig appopticsConfig = new AppOpticsConfig() {
	    @Override
	    public String apiToken() {
	        return "mxlKqyCfZZJHO4KI6HBrnBGJhxv5TXIzcI72JCb5cB_4tKzrJ_orqPsweEp3CZby9x3KyK8";
	    }

	    @Override
	    @Nullable
	    public String get(String k) {
	        return null;
	    }
	};
	MeterRegistry registry = new AppOpticsMeterRegistry(appopticsConfig, Clock.SYSTEM);
}
