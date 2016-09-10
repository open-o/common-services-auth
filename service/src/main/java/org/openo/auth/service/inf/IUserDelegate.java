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

package org.openo.auth.service.inf;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

/**
 * A User Service Delegate
 * <br/>
 * 
 * @author
 * @version     SDNO 0.5
 */
public interface IUserDelegate {

    /**
     * Perform Create user Operation.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @return response for the create user operation.
     * @since SDNO 0.5
     */
    Response createUser(HttpServletRequest request, HttpServletResponse response);

    /**
     * Perform Modify user Operation.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @param userId : user id for which user need to be modified.
     * @return response for the modify user operation.
     * @since SDNO 0.5
     */
    Response modifyUser(HttpServletRequest request, HttpServletResponse response, String userId);

    /**
     * Perform the Delete User operation for Auth Service.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletRequest Object
     * @param userId : user id which needs to be deleted.
     * @return Returns the status for the following operation.
     * @since SDNO 0.5
     */
    int deleteUser(HttpServletRequest request, HttpServletResponse response, String userId);

    /**
     * Fetch details for the specific user.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletRequest Object
     * @param userId : user id for which details needs to be fetched.
     * @return response for the get user details operation
     * @since SDNO 0.5
     */
    Response getUserDetails(HttpServletRequest request, HttpServletResponse response, String userId);
    
    /**
     * Fetches the user details of all user.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletRequest Object
     * @return response for the get user details operation
     * @since SDNO 0.5
     */
    Response getUserDetails(HttpServletRequest request, HttpServletResponse response);

    /**
     * Modify the password for the user
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletRequest Object
     * @param userId : user id for which the password needs to be changed
     * @return Returns the status for the following operation.
     * @throws IOException 
     * @since SDNO 0.5
     */
    int modifyPasword(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException;

}
