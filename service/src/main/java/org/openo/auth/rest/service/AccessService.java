/*
 * Copyright (c) 2017, Huawei Technologies Co., Ltd.
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
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.openo.auth.service.inf.IAccessDelegate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version
 */
@Path("/auth/v1/access")
public class AccessService {

    @Autowired
    IAccessDelegate accessDelegate;

    public IAccessDelegate getAccessDelegate() {
        return accessDelegate;
    }

    public void setAccessDelegate(IAccessDelegate accessDelegate) {
        this.accessDelegate = accessDelegate;
    }

    /**
     * Validate Access Rights
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @return response for the create user operation.
     * @since
     */
    @HEAD
    @Path("/{servicename}/{accessname}")
    public Response validateRights(@Context HttpServletRequest request, @Context HttpServletResponse response,
            @PathParam("servicename") String serviceName, @PathParam("accessname") String accessName) {
        return accessDelegate.validateRights(request, response, serviceName, accessName);
    }
}
