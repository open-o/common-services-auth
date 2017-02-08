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

package org.openo.auth.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.openo.auth.common.ConfigUtil;
import org.openo.auth.common.keystone.KeyStoneConfigInitializer;
import org.openo.auth.constant.Constant;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.ClientResponse;
import org.openo.auth.entity.keystone.req.KeyStoneConfiguration;
import org.openo.auth.entity.keystone.resp.Project;
import org.openo.auth.entity.keystone.resp.ProjectWrapper;
import org.openo.auth.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version
 */
public class RoleServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceClient.class);

    private static RoleServiceClient instance = new RoleServiceClient();

    private RoleServiceClient() {
        // Default Private Constructor
    }

    /**
     * Singleton Class, returns the instance of <tt>UserServiceClient</tt>
     * <br/>
     * 
     * @return instance : Returns the instance of <tt>UserServiceClient</tt>
     * @since
     */
    public static RoleServiceClient getInstance() {
        return instance;
    }

    /**
     * Perform the Create User operation for Auth Service.
     * <br/>
     * 
     * @param json : Request Body Input, to perform the operation.
     * @param authToken : Auth Token, representing the current session.
     * @return response : An Object which has status header and body, for which the value is set
     *         according to the response given by the Service Client.
     * @since
     */
    public ClientResponse listAllRoles(String authToken) {

        Response userResponse = ClientCommunicationUtil.getInstance().getResponseFromService(
                Constant.KEYSTONE_IDENTITY_ROLES, authToken, StringUtils.EMPTY, Constant.TYPE_GET);
        return makeResponse(userResponse);
    }

    /**
     * Perform the Create User operation for Auth Service.
     * <br/>
     * 
     * @param json : Request Body Input, to perform the operation.
     * @param authToken : Auth Token, representing the current session.
     * @return response : An Object which has status header and body, for which the value is set
     *         according to the response given by the Service Client.
     * @since
     */
    public ClientResponse listRolesForUser(String authToken, String userId) {

        // Sending UserId as EmptyString as url is already formed from the
        // getUrlForRoleOperations(userId, "") api.

        Response userResponse = ClientCommunicationUtil.getInstance().getResponseFromService(
                getUrlForRoleOperations(authToken, userId, StringUtils.EMPTY), authToken, StringUtils.EMPTY,
                Constant.TYPE_GET);

        return makeResponse(userResponse);
    }

    /**
     * Assigning Default Role and Default Project to the users created.
     * <br/>
     * 
     * @param authToken : Auth Token, representing the current session.
     * @param projectId : Default project id for the user has to be associated with.
     * @param userId : User Id for which operation need to be carried out.
     * @param roleId : Default Role Id for which user has to be associated with.
     * @return Returns the status for the following operation.
     * @since
     */
    public int updateRolesToUser(String authToken, String projectId, String userId, String roleId, String type) {

        Response userResponse = null;

        final List<Object> providerList = new ArrayList<Object>();

        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();

        providerList.add(jacksonJsonProvider);

        LOGGER.info("Connecting The Client.");

        String clientURL = ConfigUtil.getBaseURL();

        WebClient webClient = WebClient.create(clientURL, providerList);

        if(null != webClient) {

            webClient.type(Constant.MEDIA_TYPE_JSON);
            webClient.header(Constant.TOKEN_AUTH, authToken);
            webClient.accept(Constant.MEDIA_TYPE_JSON);

            webClient.path(Constant.KEYSTONE_IDENTITY_PROJECTS);
            webClient.path(Constant.PROJECTID, projectId);
            webClient.path(Constant.USERS);
            webClient.path(Constant.USERID, userId);
            webClient.path(Constant.ROLES);
            webClient.path(Constant.ROLEID, roleId);

            try {
                if(type.equalsIgnoreCase(Constant.TYPE_PUT)) {
                    LOGGER.info("The URL is : " + webClient.getCurrentURI());
                    userResponse = webClient.put(null);
                }
                if(type.equalsIgnoreCase(Constant.TYPE_DELETE)) {
                    LOGGER.info("The URL is : " + webClient.getCurrentURI());
                    userResponse = webClient.delete();
                }
                return null != userResponse ? userResponse.getStatus() : null;
            } catch(Exception e) {
                LOGGER.error("Exceptions " + e);
                throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.COMMUNICATION_ERROR);
            }
        } else {
            LOGGER.error("Client returned null exception.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.COMMUNICATION_ERROR);
        }
    }

    /**
     * Provides the token header.
     * <br/>
     * 
     * @param userResponse : A <tt>Response</tt> object.
     * @return tokenHeader : A token header information.
     * @since
     */
    private String getTokenHeader(Response userResponse) {
        return userResponse.getHeaderString(Constant.TOKEN_SUBJECT);
    }

    /**
     * <br/>
     * 
     * @param userResponse
     * @return
     * @since
     */
    private ClientResponse makeResponse(Response userResponse) {

        String body = "";
        ClientResponse response = new ClientResponse();
        LOGGER.info("Response = " + userResponse);

        if(null != userResponse && userResponse.hasEntity()) {

            String tokenHeader = getTokenHeader(userResponse);
            response.setHeader(tokenHeader);
            LOGGER.info("token Header = " + tokenHeader);

            InputStream responseBody = (InputStream)userResponse.getEntity();

            try {
                body = IOUtils.toString(responseBody);
            } catch(IOException e) {
                LOGGER.error("Exception is caught : " + e);
            }
            response.setBody(body);
            LOGGER.info("Response : " + body);
            response.setStatus(userResponse.getStatus());
        }

        return response;
    }

    private String getUrlForRoleOperations(String authToken, String userId, String roleId) {

        KeyStoneConfiguration keyConf = KeyStoneConfigInitializer.getKeystoneConfiguration();

        String projectId = keyConf.getProjectId();

        if(StringUtils.isEmpty(projectId)) {
            projectId = getProjectIdFromName(authToken, keyConf.getProjectName());
            keyConf.setProjectId(projectId);
            writeToKeyConfProperties(projectId);
        }

        if(StringUtils.isEmpty(roleId)) {
            return Constant.KEYSTONE_IDENTITY_PROJECTS + Constant.FORWARD_SLASH + projectId + Constant.USERS
                    + Constant.FORWARD_SLASH + userId + Constant.ROLES;
        } else {
            return Constant.KEYSTONE_IDENTITY_PROJECTS + Constant.FORWARD_SLASH + projectId + Constant.USERS
                    + Constant.FORWARD_SLASH + userId + Constant.ROLES + Constant.FORWARD_SLASH + roleId;
        }

    }

    public Map<String, ClientResponse> removeRolesFromUser(String authToken, String userId,
            Map<String, String> rolesToRemove) {

        // Sending UserId as EmptyString as url is already formed from the
        // getUrlForRoleAssignmentOnUser(userId) api.

        Map<String, ClientResponse> removedRoleResp = new HashMap<String, ClientResponse>();

        for(Map.Entry<String, String> entry : rolesToRemove.entrySet()) {
            String url = getUrlForRoleOperations(authToken, userId, entry.getKey());

            Response userResponse = ClientCommunicationUtil.getInstance().getResponseFromService(url, authToken,
                    StringUtils.EMPTY, Constant.TYPE_DELETE);
            ClientResponse response = makeResponse(userResponse);
            removedRoleResp.put(entry.getKey(), response);
        }
        return removedRoleResp;
    }

    public Map<String, ClientResponse> assignRolesToUser(String authToken, String userId,
            Map<String, String> rolesToAssign) {

        // Sending UserId as EmptyString as url is already formed from the
        // getUrlForRoleAssignmentOnUser(userId) api.

        Map<String, ClientResponse> assignedRoleResp = new HashMap<String, ClientResponse>();

        for(Map.Entry<String, String> entry : rolesToAssign.entrySet()) {
            String url = getUrlForRoleOperations(authToken, userId, entry.getKey());

            Response userResponse = ClientCommunicationUtil.getInstance().getResponseFromService(url, authToken,
                    StringUtils.EMPTY, Constant.TYPE_PUT);
            ClientResponse response = makeResponse(userResponse);
            assignedRoleResp.put(entry.getKey(), response);
        }
        return assignedRoleResp;
    }

    private String getProjectIdFromName(String authToken, String projectName) {
        ClientResponse clientResponse = getProjectDetails(authToken);
        String projectJson = clientResponse.getBody();
        LOGGER.info("Project Json = {}", projectJson);
        ProjectWrapper proj = getObjFromJson(projectJson);
        if(null != proj) {
            List<Project> projectList = proj.getProjects();
            if(null != projectList && !projectList.isEmpty()) {
                for(Project project : projectList) {
                    if(project.getName().equalsIgnoreCase(projectName)) {
                        return project.getId();
                    }
                }
            }
        }
        return StringUtils.EMPTY;
    }

    private ProjectWrapper getObjFromJson(String inputJson) {

        ObjectMapper mapperRead = new ObjectMapper();

        ProjectWrapper project = new ProjectWrapper();

        try {
            project = mapperRead.readValue(inputJson, ProjectWrapper.class);
        } catch(Exception e) {

            LOGGER.error("Exception Caught " + e);

            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
        return project;
    }

    private void writeToKeyConfProperties(String projectId) {

        LOGGER.info("Loading...{}, for writhing project id ", Constant.KEYSTONE_CONF_PROPERTIES);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Properties properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream(Constant.KEYSTONE_CONF_PROPERTIES));
        } catch(IOException e) {

            LOGGER.error("Exception Caught " + e);

            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

        properties.setProperty(Constant.KEYSTONE_CONF_PROJECT_ID, projectId);
        LOGGER.info("Setting the project id in the keystone config file, project id = {} = ", projectId);
    }

    /**
     * Fetches the user details of all users.
     * <br/>
     * 
     * @param userId : user id for which user details need to be fetched.
     * @param authToken : Auth Token, representing the current session.
     * @return response : An Object which has status header and body, for which the value is set
     *         according to the response given by the Service Client.
     * @since
     */
    public ClientResponse getProjectDetails(String authToken) {

        Response userResponse = ClientCommunicationUtil.getInstance().getResponseFromService(
                Constant.KEYSTONE_IDENTITY_PROJECTS, authToken, StringUtils.EMPTY, Constant.TYPE_GET);

        return makeResponse(userResponse);
    }

}
