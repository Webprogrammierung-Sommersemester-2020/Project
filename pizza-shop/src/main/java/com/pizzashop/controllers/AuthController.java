package com.pizzashop.controllers;

import com.pizzashop.data.repositories.implementations.UserRepository;
import com.pizzashop.data.services.IUserDataService;
import com.pizzashop.data.services.implementations.UserDataService;
import com.pizzashop.security.services.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Path("/token")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createTokenForAuthenticatedUser(@Context HttpServletRequest request) {
        String token = TokenService.create(request);
        if (token.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "User or username does not exists...").build();
        }
        return Response.ok(token).build();
    }

    @POST
    @Path("/logout")
    @Produces({MediaType.APPLICATION_JSON})
    public Response logout(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("username") != null){
            session.removeAttribute("username");
            //session.invalidate();
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "Some thing went wrong during logout...").build();
    }
}
