package com.challenge.restful.app;

import com.challenge.restful.controller.GifController;
import com.challenge.restful.controller.GiphyController;
import com.challenge.restful.controller.UserController;
import com.challenge.restful.controller.DumbController;
import com.challenge.restful.security.AuthenticationController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Main resteasy app class, all resources needs to be added for the container to direct service calls.
 */

@ApplicationPath("/v1/rest")
public class ChallengeApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(UserController.class);
        resources.add(GifController.class);
        resources.add(AuthenticationController.class);
        resources.add(GiphyController.class);
        resources.add(DumbController.class);
        return resources;
    }
}