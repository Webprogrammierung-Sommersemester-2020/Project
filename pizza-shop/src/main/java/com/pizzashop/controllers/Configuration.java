package com.pizzashop.controllers;


import com.pizzashop.security.AuthorizationFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class Configuration extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(AuthController.class);
        classes.add(OrderController.class);
        classes.add(AuthorizationFilter.class);
        return classes;
    }
}
