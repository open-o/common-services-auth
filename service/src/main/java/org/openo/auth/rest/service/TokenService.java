/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.openo.auth.service.inf.ITokenDelegate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * This class is the entry point for the login logout and validate token operations.
 * </p>
 * <br/>
 * 
 * @author
 * @version  
 */
@Path("/tokens")
public class TokenService {

    @Autowired
    ITokenDelegate tokenDelegate;

    public ITokenDelegate getTokenDelegate() {
        return tokenDelegate;
    }

    public void setTokenDelegate(ITokenDelegate tokenDelegate) {
        this.tokenDelegate = tokenDelegate;
    }

    /**
     * Perform Login Operation.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @return response for the login operation.
     * @since  
     */
    @POST
    @Produces("application/json")
    @Consumes({"application/json"})
    public Response login(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        return tokenDelegate.login(request, response);
    }

    /**
     * Perform Logout Operation.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @return response status for the operation.
     * @since  
     */
    @DELETE
    public int logout(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        return tokenDelegate.logout(request, response);
    }

    /**
     * Perform Validate token Operation.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @return response status for the operation.
     * @since  
     */
    @HEAD
    public int checkToken(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        return tokenDelegate.checkToken(request, response);
    }
}
