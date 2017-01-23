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

package org.openo.auth.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.openo.auth.common.CommonUtil;
import org.openo.auth.common.IJsonService;
import org.openo.auth.common.JsonFactory;
import org.openo.auth.common.keystone.KeyStoneConfigInitializer;
import org.openo.auth.common.keystone.KeyStoneServiceJson;
import org.openo.auth.constant.Constant;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.ClientResponse;
import org.openo.auth.entity.ModifyPassword;
import org.openo.auth.entity.ModifyUser;
import org.openo.auth.entity.Role;
import org.openo.auth.entity.UserDetailsUI;
import org.openo.auth.entity.keystone.req.KeyStoneConfiguration;
import org.openo.auth.entity.keystone.resp.UserCreateWrapper;
import org.openo.auth.exception.AuthException;
import org.openo.auth.rest.client.UserServiceClient;
import org.openo.auth.service.inf.IRoleDelegate;
import org.openo.auth.service.inf.IUserDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * An Implementation class for user service delegate.
 * <br/>
 * 
 * @author
 * @version
 */
public class UserServiceImpl implements IUserDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

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
     * Perform Create user Operation.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @return response for the create user operation.
     * @since
     */
    public Response createUser(HttpServletRequest request, HttpServletResponse response) {

        Response res = null;

        try {

            String authToken = request.getHeader(Constant.TOKEN_AUTH);

            LOGGER.info("authToken = " + authToken);

            UserDetailsUI userInfo = CommonUtil.getInstance().getUserInfo(request, response);

            LOGGER.info("userInfo UserName= " + userInfo.getUserName());

            CheckUserInfoRule.checkInfo(userInfo);

            KeyStoneConfiguration keyConf = KeyStoneConfigInitializer.getKeystoneConfiguration();

            String json = getJsonService().createUserJson(userInfo, keyConf);

            LOGGER.info("User json = " + json);

            ClientResponse resp = UserServiceClient.getInstance().createUser(json, authToken);

            int status = resp.getStatus();

            String respBody = resp.getBody();

            /* assign the role to the user */
            if(1 == status / 200 && null != roleDelegate) {

                LOGGER.info("User Created, assigning roles to user now.");

                status = roleDelegate.updateRolesToUser(userInfo.getRoles(), authToken, getUserIdFromNewUser(respBody),
                        Boolean.TRUE);
                respBody = getJsonService().responseForCreateUser(resp.getBody(), userInfo.getRoles());

            }
            response.setStatus(status);

            res = Response.status(status).entity(respBody).build();

        } catch(AuthException e) {
            LOGGER.error("Auth Exception ... " + e);
            throw e;
        } catch(Exception e) {
            LOGGER.error("Exception Caught while connecting client ... " + e);
            throw new AuthException(HttpServletResponse.SC_REQUEST_TIMEOUT, ErrorCode.FAILURE_INFORMATION);
        }
        return res;
    }

    /**
     * <br/>
     * 
     * @param respBody
     * @since
     */
    private String getUserIdFromNewUser(String respBody) {
        String userId = StringUtils.EMPTY;
        try {
            UserCreateWrapper userWrapper = KeyStoneServiceJson.getInstance().keyStoneRespToCreateUserObj(respBody);
            userId = userWrapper.getUser().getId();
            LOGGER.info("New user created, User ID : " + userId);
        } catch(Exception ex) {

        }
        return userId;
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
    public Response modifyUser(final HttpServletRequest request, HttpServletResponse response, String userId) {

        String authToken = request.getHeader(Constant.TOKEN_AUTH);

        LOGGER.info("authToken = " + authToken);

        ModifyUser modifyUser = CommonUtil.getInstance().modifyUserJson(request, response);
        String json = getJsonService().modifyUserJson(modifyUser);

        LOGGER.info("json = " + json);

        ClientResponse resp = UserServiceClient.getInstance().modifyUser(userId, json, authToken);

        int status = resp.getStatus();

        response.setStatus(status);

        String respBody = resp.getBody();

        if(status / 200 == 1 && null != roleDelegate) {

            LOGGER.info("User modified, modifying the roles info.");

            status = roleDelegate.updateRolesToUser(modifyUser.getRoles(), authToken, userId, Boolean.FALSE);

            Role role = roleDelegate.listRolesForUser(authToken, userId);

            respBody = getJsonService().responseForModifyUser(resp.getBody(), role.getRoles());

        }

        Response res = null;
        try {
            res = Response.status(status).entity(respBody).build();
        } catch(Exception e) {
            LOGGER.error("Exception Caught " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.COMMUNICATION_ERROR);
        }

        return res;

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
    public int deleteUser(HttpServletRequest request, HttpServletResponse response, String userId) {

        String authToken = request.getHeader(Constant.TOKEN_AUTH);

        LOGGER.info("authToken" + authToken);

        int status = UserServiceClient.getInstance().deleteUser(userId, authToken);

        response.setStatus(status);

        return status;

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
    public Response getUserDetails(HttpServletRequest request, HttpServletResponse response, String userId) {

        String authToken = request.getHeader(Constant.TOKEN_AUTH);

        LOGGER.info("authToken = " + authToken);

        ClientResponse resp = UserServiceClient.getInstance().getUserDetails(userId, authToken);

        int status = resp.getStatus();

        response.setStatus(status);

        String respBody = resp.getBody();

        if(status / 200 == 1 && null != roleDelegate) {
            respBody = getJsonService().responseForCreateUser(resp.getBody(),
                    roleDelegate.listRolesForUser(authToken, userId).getRoles());
        }

        Response res = null;
        try {
            res = Response.status(status).entity(respBody).build();
        } catch(Exception e) {
            LOGGER.error("Exception Caught " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.COMMUNICATION_ERROR);
        }

        return res;

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
    public Response getUserDetails(HttpServletRequest request, HttpServletResponse response) {

        String authToken = request.getHeader(Constant.TOKEN_AUTH);

        LOGGER.info("authToken = " + authToken);

        ClientResponse resp = UserServiceClient.getInstance().getUserDetails(authToken);

        int status = resp.getStatus();

        response.setStatus(status);

        String respBody = resp.getBody();

        if(status / 200 == 1) {
            respBody = getJsonService().responseForMultipleUsers(resp.getBody());
        }

        Response res = null;
        try {
            res = Response.status(status).entity(respBody).build();
        } catch(Exception e) {
            LOGGER.error("Exception Caught " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.COMMUNICATION_ERROR);
        }

        return res;

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
    public int modifyPasword(HttpServletRequest request, HttpServletResponse response, String userId)
            throws IOException {

        String authToken = request.getHeader(Constant.TOKEN_AUTH);

        UserCreateWrapper user = null;

        LOGGER.info("authToken = " + authToken);

        ModifyPassword modifyPwd = CommonUtil.getInstance().modifyPasswordJson(request, response);

        ClientResponse resp = UserServiceClient.getInstance().getUserDetails(userId, authToken);

        if(resp.getStatus() / 200 == 1) {
            user = getJsonService().keyStoneRespToCreateUserObj(resp.getBody());
        }

        if(user != null && user.getUser() != null && StringUtils.isNotEmpty(user.getUser().getName())) {
            CheckUserInfoRule.checkPassword(modifyPwd.getPassword(), user.getUser().getName());
        }

        String json = getJsonService().modifyPasswordJson(modifyPwd);

        int status = UserServiceClient.getInstance().modifyPassword(userId, json, authToken);

        response.setStatus(status);

        return status;

    }

}
