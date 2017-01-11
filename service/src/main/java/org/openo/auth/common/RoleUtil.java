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

package org.openo.auth.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.openo.auth.common.keystone.KeyStoneServiceJson;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.ClientResponse;
import org.openo.auth.entity.Role;
import org.openo.auth.entity.RoleResponse;
import org.openo.auth.exception.AuthException;
import org.openo.auth.rest.client.RoleServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This class <tt>RoleUtil</tt> deals with the role utilities for the Auth Service.
 * </p>
 * 
 * @author
 * @version
 */
public class RoleUtil {

    private static RoleUtil instance = new RoleUtil();

    private Map<String, String> roleUpdate = new HashMap<String, String>();

    private Map<String, String> roleExisting = new HashMap<String, String>();

    private Map<String, String> roleToAssign = new HashMap<String, String>();

    private Map<String, String> roleToDelete = new HashMap<String, String>();

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleUtil.class);

    private RoleUtil() {
        // Default Constructor
    }

    public static RoleUtil getInstance() {
        return instance;
    }

    /**
     * <br/>
     * 
     * @param request
     * @param response
     * @return
     * @since
     */
    public Role getRoleInfo(HttpServletRequest request, HttpServletResponse response) {

        try {
            LOGGER.info("getRoleInfo");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(request.getInputStream(), Role.class);
        } catch(IOException e) {
            LOGGER.info("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
    }

    /**
     * <br/>
     * 
     * @param authToken
     * @param userId
     * @param roleToUpdate
     * @param isNewUser
     * @since
     */
    public void validateRolesForUser(String authToken, String userId, List<RoleResponse> roleToUpdate,
            boolean isNewUser) {

        LOGGER.info("authToken = " + authToken);

        ClientResponse allRolesResp = RoleServiceClient.getInstance().listAllRoles(authToken);
        int allRoleStatus = allRolesResp.getStatus();

        if(!isNewUser) {
            ClientResponse userRolesResp = RoleServiceClient.getInstance().listRolesForUser(authToken, userId);
            int userRolesStatus = userRolesResp.getStatus();
            LOGGER.error("userRolesStatus = {}", userRolesStatus);
            if((allRoleStatus / 200 == 1) && (userRolesStatus / 200 == 1)) {

                String allRolesJson = allRolesResp.getBody();
                String userRolesJson = userRolesResp.getBody();
                LOGGER.error("userRolesJson = {}", userRolesJson);
                Role allRoles = KeyStoneServiceJson.getInstance().roleJsonToRoleObj(allRolesJson);
                Role userRoles = KeyStoneServiceJson.getInstance().roleJsonToRoleObj(userRolesJson);

                validateRolesForUser(allRoles, userRoles, roleToUpdate, isNewUser);
                LOGGER.error("Roles fetched from db, validating with the roles to update ... ");

            } else {
                LOGGER.error("Not Allowed ... ");
                throw new AuthException(HttpServletResponse.SC_METHOD_NOT_ALLOWED, ErrorCode.FAILURE_INFORMATION);
            }
        } else {
            if(allRoleStatus / 200 == 1) {

                String allRolesJson = allRolesResp.getBody();

                Role allRoles = KeyStoneServiceJson.getInstance().roleJsonToRoleObj(allRolesJson);

                validateRolesForUser(allRoles, null, roleToUpdate, isNewUser);
                LOGGER.error("Roles fetched from db, validating with the roles to update ... ");

            } else {
                LOGGER.error("Not Allowed ... ");
                throw new AuthException(HttpServletResponse.SC_METHOD_NOT_ALLOWED, ErrorCode.FAILURE_INFORMATION);
            }
        }
    }

    /**
     * Updates the role to assigned.
     * <br/>
     * 
     * @return
     * @since
     */
    public Map<String, String> getRoleToAssign() {
        if(roleExisting.size() == 0) {
            return roleUpdate;
        }
        for(Map.Entry<String, String> entry : roleUpdate.entrySet()) {
            String roleId = entry.getKey();
            if(!roleExisting.containsKey(roleId)) {
                roleToAssign.put(roleId, entry.getValue());
            }
        }
        return roleToAssign;
    }

    /**
     * Updates the roles to be deleted.
     * <br/>
     * 
     * @return
     * @since
     */
    public Map<String, String> getRoleToDelete() {

        for(Map.Entry<String, String> entry : roleExisting.entrySet()) {
            String roleId = entry.getKey();
            if(!roleUpdate.containsKey(roleId)) {
                roleToDelete.put(roleId, entry.getValue());
            }
        }

        return roleToDelete;
    }

    /**
     * Returns the response status.
     * <br/>
     * 
     * @param roleResponse
     * @return
     * @since
     */
    public int roleResponseStatus(Map<String, ClientResponse> roleResponse) {
        boolean status = true;
        int count = 0;
        for(Map.Entry<String, ClientResponse> entry : roleResponse.entrySet()) {
            int statusCode = entry.getValue().getStatus();
            if(!(statusCode / 200 == 1)) {
                status = false;
                count++;
            }
        }
        if(status) {
            return HttpServletResponse.SC_OK;
        } else {
            if(count == roleResponse.size()) {
                return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            } else {
                return HttpServletResponse.SC_PARTIAL_CONTENT;
            }
        }
    }

    /**
     * Provide the status code.
     * <br/>
     * 
     * @param assignCode
     * @param removalCode
     * @return
     * @since
     */
    public int getStatusCode(int assignCode, int removalCode) {
        if((HttpServletResponse.SC_OK == assignCode) && (HttpServletResponse.SC_OK == removalCode)) {
            return HttpServletResponse.SC_OK;
        } else if((HttpServletResponse.SC_INTERNAL_SERVER_ERROR == assignCode)
                && (HttpServletResponse.SC_INTERNAL_SERVER_ERROR == removalCode)) {

            LOGGER.error("completely failed ... ");
            throw new AuthException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ErrorCode.FAILURE_INFORMATION);

        } else {
            LOGGER.error("partial Success ... ");
            throw new AuthException(HttpServletResponse.SC_PARTIAL_CONTENT, ErrorCode.PARTIAL_SUCCESS);
        }
    }

    /**
     * Truncate the values.
     * <br/>
     * 
     * @since SDNO 0.5
     */
    public void truncateValues() {
        roleUpdate.clear();

        roleExisting.clear();

        roleToAssign.clear();

        roleToDelete.clear();
    }

    private void validateRolesForUser(Role allRoles, Role existingRoles, List<RoleResponse> rolesToUpdate,
            boolean isNewUser) {

        Map<String, String> allRolesMap = roleListToMap(allRoles);
        for(RoleResponse role : rolesToUpdate) {
            roleUpdate.put(role.getId(), role.getName());
        }
        LOGGER.info("Roles to update = " + roleUpdate);
        if(!isNewUser) {
            roleExisting = roleListToMap(existingRoles);
            validateForDuplicacyRoles(roleExisting, roleUpdate);
        }
        LOGGER.info("Roles existing = " + roleExisting);
        validateForValidRoles(allRolesMap, roleUpdate);

    }

    private Map<String, String> roleListToMap(Role listRoles) {
        Map<String, String> roleMap = new HashMap<String, String>();
        if(null != listRoles) {
            for(RoleResponse role : listRoles.getRoles()) {
                roleMap.put(role.getId(), role.getName());
            }
        }
        return roleMap;
    }

    private void validateForDuplicacyRoles(Map<String, String> existingRoles, Map<String, String> rolesToUpdate) {
        boolean flag = true;
        LOGGER.error("existingRoles = {}, rolesToUpdate = {}", existingRoles, rolesToUpdate);
        if(existingRoles.size() == rolesToUpdate.size()) {
            for(Map.Entry<String, String> entry : rolesToUpdate.entrySet()) {
                if(!existingRoles.containsKey(entry.getKey())) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                LOGGER.error("No Change in Roles ... ");
                throw new AuthException(HttpServletResponse.SC_FORBIDDEN, ErrorCode.DUPLICATE_VALUES);

            }
        }

    }

    private void validateForValidRoles(Map<String, String> allRoles, Map<String, String> rolesToUpdate) {
        boolean flag = true;
        LOGGER.error("allRoles = {}, rolesToUpdate = {}", allRoles, rolesToUpdate);
        for(Map.Entry<String, String> entry : rolesToUpdate.entrySet()) {
            String dbName = allRoles.get(entry.getKey());
            if(null == dbName || !dbName.equals(entry.getValue())) {
                flag = false;
                break;
            }
        }
        if(!flag) {
            LOGGER.error("Role Information not correct ... ");
            throw new AuthException(HttpServletResponse.SC_FORBIDDEN, ErrorCode.FAILURE_INFORMATION);
        }
    }
}
