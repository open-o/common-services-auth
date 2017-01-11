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

package org.openo.auth.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.openo.auth.common.ConfigUtil;
import org.openo.auth.constant.Constant;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.ClientResponse;
import org.openo.auth.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br/>
 * <p>
 * Client for communicating to the services which is configured by the user in the
 * <tt>auth_service.properties</tt> file
 * <b>Services be like KeyStone or Openo-RBAC</b>
 * <blockquote><pre>
 * Example :
 * IP=1.1.1.1 - IP Address where Service is installed.
 * PORT=123 - Open port to access the installed service.
 * SERVICE=KeyStone - Installed Service
 * </blockquote></pre>
 * </p>
 * <b>Currently, Auth Service supports only KeyStone Service</b>
 * 
 * @author
 * @version  
 */
public class UserServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceClient.class);

    private static UserServiceClient instance = new UserServiceClient();

    private UserServiceClient() {
        // Default Private Constructor
    }

    /**
     * Singleton Class, returns the instance of <tt>UserServiceClient</tt>
     * <br/>
     * 
     * @return instance : Returns the instance of <tt>UserServiceClient</tt>
     * @since  
     */
    public static UserServiceClient getInstance() {
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
    public ClientResponse createUser(String json, String authToken) {

        LOGGER.info("Input Json from UI = " + json);

        Response userResponse = ClientCommunicationUtil.getInstance()
                .getResponseFromService(Constant.KEYSTONE_IDENTITY_USER, authToken, json, Constant.TYPE_POST);
        
        return makeResponse(userResponse);
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
     * Perform the Delete User operation for Auth Service.
     * <br/>
     * 
     * @param userId : user id which needs to be deleted.
     * @param authToken : Auth Token, representing the current session.
     * @return Returns the status for the following operation.
     * @since  
     */
    public int deleteUser(String userId, String authToken) {

        Response userResponse = ClientCommunicationUtil.getInstance()
                .getResponseFromService(Constant.KEYSTONE_IDENTITY_USER, authToken, userId, Constant.TYPE_DELETE);

        LOGGER.info("Response = " + userResponse);

        return userResponse.getStatus();
    }

    /**
     * Fetches the user details of specific user.
     * <br/>
     * 
     * @param userId : user id for which user details need to be fetched.
     * @param authToken : Auth Token, representing the current session.
     * @return response : An Object which has status header and body, for which the value is set
     *         according to the response given by the Service Client.
     * @since  
     */
    public ClientResponse getUserDetails(String userId, String authToken) {

        Response userResponse = ClientCommunicationUtil.getInstance()
                .getResponseFromService(Constant.KEYSTONE_IDENTITY_USER, authToken, userId, Constant.TYPE_GET);
        
        return makeResponse(userResponse);
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
    public ClientResponse getUserDetails(String authToken) {


        Response userResponse = ClientCommunicationUtil.getInstance()
                .getResponseFromService(Constant.KEYSTONE_IDENTITY_USER, authToken, StringUtils.EMPTY, Constant.TYPE_GET);

        return makeResponse(userResponse);
    }

    /**
     * Perform the Modify User operation for Auth Service.
     * <br/>
     * 
     * @param userId : user id for which user need to be modified.
     * @param json : Requested input body in the form of JSON.
     * @param authToken : Auth Token, representing the current session.
     * @return response : An Object which has status header and body, for which the value is set
     *         according to the response given by the Service Client.
     * @since  
     */
    public ClientResponse modifyUser(String userId, String json, String authToken) {

        LOGGER.info("Input Json from UI = " + json);

        Response userResponse = ClientCommunicationUtil.getInstance()
                .getResponseFromService(Constant.KEYSTONE_IDENTITY_USER, authToken, userId, json, Constant.TYPE_PATCH);
        
        return makeResponse(userResponse);
    }

    /**
     * Perform the modify password operation for the user.
     * <br/>
     * 
     * @param userId : User Id for which operation need to be carried out.
     * @param json :Requested input body.
     * @param authToken : Auth Token, representing the current session.
     * @return Returns the status for the following operation.
     * @since  
     */
    public int modifyPassword(String userId, String json, String authToken) {

        LOGGER.info("Input Json from UI = " + json);

        Response userResponse = ClientCommunicationUtil.getInstance()
                .getResponseFromService(Constant.KEYSTONE_IDENTITY_USER, authToken, userId, json, Constant.TYPE_POST);
        LOGGER.info("Response = " + userResponse);

        return userResponse.getStatus();
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
    public int assignRolesToUser(String authToken, String projectId, String userId, String roleId) {

        String clientURL = ConfigUtil.getBaseURL();

        final List<Object> providerList = new ArrayList<Object>();

        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();

        providerList.add(jacksonJsonProvider);

        LOGGER.info("Connecting The Client.");

        WebClient webClient = WebClient.create(clientURL, providerList);

        Response userResponse = null;

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
                LOGGER.info("The URL is : " + webClient.getCurrentURI());
                userResponse = webClient.put(null);
                return userResponse.getStatus();
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
     * 
     * <br/>
     * 
     * @param userResponse
     * @return
     * @since   
     */
    private ClientResponse makeResponse(Response userResponse) {
      
      LOGGER.info("Response = " + userResponse);
      
      String body = "";
      ClientResponse response = new ClientResponse();

      if(null != userResponse && userResponse.hasEntity()) {

          String tokenHeader = getTokenHeader(userResponse);
          LOGGER.info("token Header = " + tokenHeader);

          response.setHeader(tokenHeader);

          InputStream responseBody = (InputStream)userResponse.getEntity();

          try {
              body = IOUtils.toString(responseBody);
          } catch(IOException e) {
              LOGGER.error("Exception caught : " + e);
          }
          response.setBody(body);
          LOGGER.info("Response = " + body);
          response.setStatus(userResponse.getStatus());
      }

      return response;
    }

}
