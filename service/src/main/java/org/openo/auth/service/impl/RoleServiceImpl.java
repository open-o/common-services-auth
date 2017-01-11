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

package org.openo.auth.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.openo.auth.common.IJsonService;
import org.openo.auth.common.JsonFactory;
import org.openo.auth.common.RoleUtil;
import org.openo.auth.common.keystone.KeyStoneServiceJson;
import org.openo.auth.constant.Constant;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.ClientResponse;
import org.openo.auth.entity.Role;
import org.openo.auth.entity.RoleResponse;
import org.openo.auth.exception.AuthException;
import org.openo.auth.rest.client.RoleServiceClient;
import org.openo.auth.service.inf.IRoleDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide the implementation for the role functionalities for the Auth Services.
 * <br/>
 * 
 * @author
 * @version
 */
public class RoleServiceImpl implements IRoleDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    /**
     * <br/>
     * 
     * @param request
     * @param response
     * @return
     * @since
     */
    public Response listRoles(HttpServletRequest request, HttpServletResponse response) {

        Response res = null;

        try {

            String authToken = request.getHeader(Constant.TOKEN_AUTH);

            LOGGER.info("authToken = " + authToken);

            ClientResponse resp = RoleServiceClient.getInstance().listAllRoles(authToken);

            int status = resp.getStatus();
            response.setStatus(status);

            String respBody = resp.getBody();

            if(status / 200 == 1) {
                respBody = getJsonService().responseForListRoles(respBody);
            }
            res = Response.status(status).entity(respBody).build();

        } catch(Exception e) {
            LOGGER.error("Exception Caught while connecting client ... " + e);
            throw new AuthException(HttpServletResponse.SC_REQUEST_TIMEOUT, ErrorCode.FAILURE_INFORMATION);
        }
        return res;
    }

    /**
     * <br/>
     * 
     * @param request
     * @param response
     * @param userId
     * @return
     * @since
     */
    public Role listRolesForUser(String authToken, String userId) {

        Role role = new Role();
        try {

            LOGGER.info("authToken = " + authToken);

            ClientResponse resp = RoleServiceClient.getInstance().listRolesForUser(authToken, userId);

            int status = resp.getStatus();

            String respBody = resp.getBody();

            if(1 == status / 200) {
                role = KeyStoneServiceJson.getInstance().roleJsonToRoleObj(respBody);
            }

        } catch(AuthException e) {
            LOGGER.error("AuthException ... " + e);
            throw e;
        } catch(Exception e) {
            LOGGER.error("Exception Caught while connecting client ... " + e);
            throw new AuthException(HttpServletResponse.SC_REQUEST_TIMEOUT, ErrorCode.FAILURE_INFORMATION);
        }
        return role;
    }

    /**
     * <br/>
     * 
     * @param request
     * @param response
     * @param userId
     * @param isAssign
     * @return
     * @since
     */
    public int updateRolesToUser(List<RoleResponse> roleInfo, String authToken, String userId, boolean isNewUser) {

        try {

            LOGGER.info("authToken = " + authToken);

            // Data Validity Check
            for(RoleResponse role : roleInfo) {
                LOGGER.info("Role Information ---> Role Name = {} , Role Id = {}", role.getName(), role.getId());
                CheckUserInfoRule.checkRoleValidity(role);
            }
            RoleUtil.getInstance().truncateValues(); 
            RoleUtil.getInstance().validateRolesForUser(authToken, userId, roleInfo, isNewUser);

            if(!isNewUser) {
                // Fetch all Roles and validate

                Map<String, String> roleToAssign = RoleUtil.getInstance().getRoleToAssign();

                Map<String, String> roleToDelete = RoleUtil.getInstance().getRoleToDelete();
                
                LOGGER.error("roleToAssign = {}, roleToDelete = {}", roleToAssign, roleToDelete);

                Map<String, ClientResponse> roleDeleteResponse =
                        RoleServiceClient.getInstance().removeRolesFromUser(authToken, userId, roleToDelete);

                Map<String, ClientResponse> roleAssignResponse =
                        RoleServiceClient.getInstance().assignRolesToUser(authToken, userId, roleToAssign);

                int removalStatusCode = RoleUtil.getInstance().roleResponseStatus(roleDeleteResponse);

                int assignStatusCode = RoleUtil.getInstance().roleResponseStatus(roleAssignResponse);

                LOGGER.error("Assign status code = {}, Removal status code = {}", assignStatusCode, removalStatusCode);

                return RoleUtil.getInstance().getStatusCode(assignStatusCode, removalStatusCode);

            } else {
                // Fetch all Roles and validate

                Map<String, String> roleToAssign = RoleUtil.getInstance().getRoleToAssign();

                Map<String, ClientResponse> roleAssignResponse =
                        RoleServiceClient.getInstance().assignRolesToUser(authToken, userId, roleToAssign);

                return RoleUtil.getInstance().roleResponseStatus(roleAssignResponse);

            }

        } catch(AuthException e) {
            LOGGER.error("Auth Exception Caught.");
            throw e;
        } catch(Exception e) {
            LOGGER.error("Exception Caught while connecting client ... " + e);
            throw new AuthException(HttpServletResponse.SC_REQUEST_TIMEOUT, ErrorCode.FAILURE_INFORMATION);
        }
    }

    /**
     * Get the Json Service instance according the service registered in the
     * <tt>auth_service.properties</tt> file.
     * <br/>
     * 
     * @return jsonService : An instance of <tt>JsonService</tt> class.
     * @since
     */
    private IJsonService getJsonService() {

        IJsonService jsonService = JsonFactory.getInstance().getJsonService();

        if(null == jsonService) {
            LOGGER.error("Exception Caught while connecting client ... ");
            throw new AuthException(HttpServletResponse.SC_REQUEST_TIMEOUT, ErrorCode.AUTH_LOAD_FAILED);
        }
        return jsonService;
    }

}
