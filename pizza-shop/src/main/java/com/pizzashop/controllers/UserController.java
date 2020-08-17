package com.pizzashop.controllers;

import com.pizzashop.data.models.User;
import com.pizzashop.data.repositories.implementations.UserRepository;
import com.pizzashop.data.services.IUserDataService;
import com.pizzashop.data.services.implementations.UserDataService;
import com.pizzashop.security.interfaces.Secured;
import com.pizzashop.security.modells.Role;

import javax.swing.text.html.Option;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/user")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserController {
    private final IUserDataService dataService;

    public UserController() {
        this.dataService = new UserDataService(new UserRepository());
    }

    @GET
    @Secured({Role.ADMIN})
    @Path("/get/all")
    public Response getAllUsers(){
        List<User> users = dataService.getAllUsers();
        if (users.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(users).build();
    }
    @GET
    @Secured({Role.ADMIN})
    @Path("/get/{id}")
    public Response getUserById(@PathParam("id") int id){
        User user = dataService.getUserById(id);
        if (user != null){
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createUser(User user){
        Optional<User> optionalUser = dataService.getAllUsers().stream().filter(u->u.getUserName().equals(u.getUserName())).findFirst();
        if(!optionalUser.isPresent()){
            if (dataService.createUser(user)){
                return Response.status(Response.Status.CREATED).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "User already exists").build();
    }

    @PUT
    @Secured({Role.ADMIN})
    @Path("/update")
    public Response updateUser(User user){
        if(dataService.getUserById(user.getUserId()) != null){
            if(dataService.updateUser(user)){
                return Response.status(Response.Status.NO_CONTENT).build();
            }else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Secured({Role.ADMIN})
    @Path("/delete")
    public Response deleteUser(User user){
        if(dataService.getUserById(user.getUserId()) != null){
            if(dataService.deleteUser(user)){
                return Response.status(Response.Status.NO_CONTENT).build();
            }else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Secured({Role.ADMIN})
    @Path("/delete/{id}")
    public Response deleteUserById(@PathParam("id") int id){
        if(dataService.getUserById(id) != null){
            if(dataService.deleteUser(dataService.getUserById(id))){
                return Response.status(Response.Status.NO_CONTENT).build();
            }else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
