package com.parabird.uvds.dataLake.onboarding.network;

import com.parabird.uvds.dataLake.onboarding.db.api.OnboardingDBRest;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/onboarding")
public class OnboardingJerseyConfig extends ResourceConfig {
    public OnboardingJerseyConfig() {
        register(OnboardingDBRest.class);
    }
}
