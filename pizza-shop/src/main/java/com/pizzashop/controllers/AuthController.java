package com.pizzashop.controllers;

import com.pizzashop.data.repositories.implementations.UserRepository;
import com.pizzashop.data.services.IUserDataService;
import com.pizzashop.data.services.implementations.UserDataService;
import com.pizzashop.security.services.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthController {

    private final IUserDataService userDataService;

    public AuthController() {
        this.userDataService = new UserDataService(new UserRepository());
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response authenticateUser(@Context HttpServletRequest request) {

        TokenService tokenService = new TokenService();
        String token = tokenService.create(request);
        if (token.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(token).build();
    }


}
