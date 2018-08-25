package com.parabird.data_lake.onboarding.network;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/onboarding")
public class OnboardingJerseyConfig extends ResourceConfig {
    public OnboardingJerseyConfig() {
        register(com.parabird.data_lake.onboarding.db.api.OnboardingDBRest.class);
    }
}
