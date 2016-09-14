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

package org.openo.auth.rest.client;

import javax.ws.rs.core.Response;

import org.openo.auth.constant.Constant;
import org.openo.auth.entity.ClientResponse;
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
public class TokenServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceClient.class);

    private static TokenServiceClient instance = new TokenServiceClient();

    private TokenServiceClient() {
    }

    /**
     * Singleton Class, returns the instance of <tt>TokenServiceClient</tt>
     * <br/>
     * 
     * @return instance : Returns the instance of <tt>TokenServiceClient</tt>
     * @since  
     */
    public static TokenServiceClient getInstance() {
        return instance;
    }

    /**
     * Perform the Login operation for Auth Service.
     * <br/>
     * 
     * @param json : Request Body Input, to perform the operation.
     * @return response : An Object which has status header and body, for which the value is set
     *         according to the response given by the Service Client.
     * @since  
     */
    public ClientResponse doLogin(String json) {

        LOGGER.info("Input Json from UI = " + json);

        Response userResponse = ClientCommunicationUtil.getInstance()
                .getResponseFromService(Constant.KEYSTONE_IDENTITY_TOKEN, json, Constant.TYPE_POST);
        LOGGER.info("Response status = " + userResponse.getStatus());

        ClientResponse response = new ClientResponse();

        String tokenHeader = userResponse.getHeaderString(Constant.TOKEN_SUBJECT);
        LOGGER.info("token Header = " + tokenHeader);

        response.setHeader(tokenHeader);

        response.setStatus(userResponse.getStatus());

        return response;
    }

    /**
     * Performs the Logout operation
     * <br/>
     * 
     * @param token : Auth token, which represents the current session.
     * @return int : returns the status for the following operation.
     * @since  
     */
    public int doLogout(String token) {

        LOGGER.info("Input from UI = " + token);

        Response userResponse = ClientCommunicationUtil.getInstance()
                .getResponseFromService(Constant.KEYSTONE_IDENTITY_TOKEN, token, Constant.TYPE_DELETE);

        LOGGER.info("Response = " + userResponse);

        LOGGER.info("Response Staus= " + userResponse.getStatus());

        return userResponse.getStatus();
    }

    /**
     * Performs the validity of the token.
     * <br/>
     * 
     * @param token : Auth token, which represents the current session.
     * @return int : returns the status for the following operation.
     * @since  
     */
    public int checkToken(String token) {

        LOGGER.info("Input from UI = " + token);

        Response userResponse = ClientCommunicationUtil.getInstance()
                .getResponseFromService(Constant.KEYSTONE_IDENTITY_TOKEN, token, Constant.TYPE_HEAD);

        LOGGER.info("Response = " + userResponse);

        LOGGER.info("Response Staus= " + userResponse.getStatus());

        return userResponse.getStatus();
    }

}
