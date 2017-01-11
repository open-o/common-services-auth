/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.auth.rest.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.PATCH;
import org.openo.auth.service.inf.IUserDelegate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * This class is the entry point for the CRUD operations for the user under the Auth Service.
 * </p>
 * <br/>
 *
 * @author
 * @version  
 */
@Path("/users")
public class UserService {

    @Autowired
    IUserDelegate userDelegate;

    public IUserDelegate getUserDelegate() {
        return userDelegate;
    }

    public void setUserDelegate(IUserDelegate userDelegate) {
        this.userDelegate = userDelegate;
    }

    /**
     * Perform Create user Operation.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @return response for the create user operation.
     * @since  
     */
    @POST
    @Produces("application/json")
    @Consumes({"application/json"})
    public Response createUser(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        return userDelegate.createUser(request, response);
    }

    /**
     * Perform Modify user Operation.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @param userId : user id for which user need to be modified.
     * @return response for the modify user operation.
     * @since  
     */
    @PATCH
    @Path("/{userid}")
    @Produces("application/json")
    @Consumes({"application/json"})
    public Response modifyUser(@Context HttpServletRequest request, @Context HttpServletResponse response,
            @PathParam("userid") String userId) {
        return userDelegate.modifyUser(request, response, userId);
    }

    /**
     * Perform the Delete User operation for Auth Service.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletRequest Object
     * @param userId : user id which needs to be deleted.
     * @return Returns the status for the following operation.
     * @since  
     */
    @DELETE
    @Path("/{userid}")
    @Produces("application/json")
    @Consumes({"application/json"})
    public int deleteUser(@Context HttpServletRequest request, @Context HttpServletResponse response,
            @PathParam("userid") String userId) {
        return userDelegate.deleteUser(request, response, userId);
    }

    /**
     * Fetches the user details of all user.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletRequest Object
     * @return response for the get user details operation
     * @since  
     */
    @GET
    @Produces("application/json")
    @Consumes({"application/json"})
    public Response getUserDetails(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        return userDelegate.getUserDetails(request, response);
    }

    /**
     * Fetch details for the specific user.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletRequest Object
     * @param userId : user id for which details needs to be fetched.
     * @return response for the get user details operation
     * @since  
     */
    @GET
    @Path("/{userid}")
    @Produces("application/json")
    @Consumes({"application/json"})
    public Response getUserDetails(@Context HttpServletRequest request, @Context HttpServletResponse response,
            @PathParam("userid") String userId) {
        return userDelegate.getUserDetails(request, response, userId);
    }

    /**
     * Modify the password for the user
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletRequest Object
     * @param userId : user id for which the password needs to be changed
     * @return Returns the status for the following operation.
     * @throws IOException 
     * @since  
     */
    @POST
    @Path("/{userid}/password")
    @Produces("application/json")
    @Consumes({"application/json"})
    public int modifyPasword(@Context HttpServletRequest request, @Context HttpServletResponse response,
            @PathParam("userid") String userId) throws IOException {
        return userDelegate.modifyPasword(request, response, userId);
    }

}
