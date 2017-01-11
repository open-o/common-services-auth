/*
 * Copyright (c) 2016-2017 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.openo.auth.service.inf.IRoleDelegate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * <p>
 * This class is the entry point for the CRUD operations for the roles under the Auth Service.
 * </p>
 * 
 * @author
 * @version
 */
@Path("/roles")
public class RoleService {

    @Autowired
    IRoleDelegate roleDelegate;

    /**
     * @return Returns the roleDelegate.
     */
    public IRoleDelegate getRoleDelegate() {
        return roleDelegate;
    }

    /**
     * @param roleDelegate The roleDelegate to set.
     */
    public void setRoleDelegate(IRoleDelegate roleDelegate) {
        this.roleDelegate = roleDelegate;
    }

    /**
     * List all the roles present.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @return response for the create user operation.
     * @since
     */
    @GET
    @Produces("application/json")
    @Consumes({"application/json"})
    public Response listRoles(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        return roleDelegate.listRoles(request, response);
    }

}
