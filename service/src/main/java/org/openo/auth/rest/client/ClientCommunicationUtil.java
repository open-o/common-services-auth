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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.openo.auth.common.ConfigUtil;
import org.openo.auth.common.keystone.KeyStoneConfigInitializer;
import org.openo.auth.constant.Constant;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.keystone.req.KeyStoneConfiguration;
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
public class ClientCommunicationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCommunicationUtil.class);

    private static ClientCommunicationUtil instance = new ClientCommunicationUtil();

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since  
     */
    private ClientCommunicationUtil() {

    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public static ClientCommunicationUtil getInstance() {
        return instance;
    }

    /**
     * API for connecting the client and providing the result for the requested api service.
     * <br/>
     * 
     * @param url : Rest API URI
     * @param input : Input for the requested operation.
     * @param type : The type of operation like PUT/GET/HEAD , etc.
     * @return userResponse : A <tt> Response </tt> object, which is populated by the result of the
     *         requested api service.
     * @since  
     */
    public Response getResponseFromService(String url, String input, String type) {

        WebClient client = initializeClient();

        KeyStoneConfiguration keyConf = KeyStoneConfigInitializer.getKeystoneConfiguration();

        Response userResponse = null;

        if(null != client) {

            client.type(Constant.MEDIA_TYPE_JSON);
            client.accept(Constant.MEDIA_TYPE_JSON);
            client.path(url);

            LOGGER.info("Current URI -> " + client.getCurrentURI());

            try {
                if(type.equals(Constant.TYPE_POST)) {

                    userResponse = client.invoke(Constant.TYPE_POST, input);

                } else if(type.equals(Constant.TYPE_DELETE)) {

                    client.header(Constant.TOKEN_AUTH, keyConf.getAdminToken());
                    client.header(Constant.TOKEN_SUBJECT, input);
                    userResponse = client.delete();

                } else if(type.equals(Constant.TYPE_HEAD)) {

                    client.header(Constant.TOKEN_AUTH, keyConf.getAdminToken());
                    client.header(Constant.TOKEN_SUBJECT, input);
                    userResponse = client.get();

                }
            } catch(Exception e) {

                LOGGER.error("Exception Caught while connecting client ... " + e);
                throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.COMMUNICATION_ERROR);

            }
        } else {
            LOGGER.error("Exception Caught while connecting client as client returned null ... ");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.COMMUNICATION_ERROR);
        }

        return userResponse;
    }

    /**
     * API for connecting the client and providing the result for the requested api service.
     * <br/>
     * 
     * @param url : Rest API URI.
     * @param authToken : Auth Token, representing the current session.
     * @param userId : User Id for which operation need to be carried out.
     * @param body : Requested input body.
     * @param type : The type of operation like PUT/GET/HEAD , etc.
     * @return userResponse : A <tt> Response </tt> object, which is populated by the result of the
     *         requested api service.
     * @since  
     */
    public Response getResponseFromService(String url, String authToken, String userId, String body, String type) {

        WebClient client = initializeClient();

        Response userResponse = null;

        if(null != client) {

            client.type(Constant.MEDIA_TYPE_JSON);
            client.accept(Constant.MEDIA_TYPE_JSON);

            try {

                if(type.equals(Constant.TYPE_PATCH)) {
                    client.path(url);
                    client.header(Constant.TOKEN_AUTH, authToken);
                    client.path(Constant.USERID, userId);
                    userResponse = client.invoke(Constant.TYPE_PATCH, body);
                } else if(type.equals(Constant.TYPE_POST)) {
                    String urlPassword = url + "/" + userId + "/password";
                    client.path(urlPassword);
                    LOGGER.info("Current URI -> " + client.getCurrentURI());
                    client.header(Constant.TOKEN_AUTH, authToken);
                    userResponse = client.invoke(Constant.TYPE_POST, body);

                }
            } catch(Exception e) {

                LOGGER.error("Exception Caught while connecting client ... " + e);
                throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.COMMUNICATION_ERROR);

            }
        } else {
            LOGGER.error("Exception Caught while connecting client as client returned null ... ");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.COMMUNICATION_ERROR);
        }

        return userResponse;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    private WebClient initializeClient() {
        String baseURL = ConfigUtil.getBaseURL();

        final List<Object> providers = new ArrayList<Object>();

        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();

        providers.add(jacksonJsonProvider);

        LOGGER.info("Connecting Client ...");

        return WebClient.create(baseURL, providers);
    }

    /**
     * API for connecting the client and providing the result for the requested api service.
     * <br/>
     * 
     * @param url : Rest API URI
     * @param authToken : Auth Token, representing the current session.
     * @param input : Input for the requested operation.
     * @param type : The type of operation like PUT/GET/HEAD , etc.
     * @return userResponse : A <tt> Response </tt> object, which is populated by the result of the
     *         requested api service.
     * @since  
     */
    public Response getResponseFromService(String url, String authToken, String input, String type) {

        WebClient client = initializeClient();

        Response userResponse = null;

        if(null != client) {

            client.type(Constant.MEDIA_TYPE_JSON);
            client.accept(Constant.MEDIA_TYPE_JSON);
            client.path(url);
            client.header(Constant.TOKEN_AUTH, authToken);

            LOGGER.info("Current URI -> " + client.getCurrentURI());

            try {
                if(type.equals(Constant.TYPE_POST)) {

                    userResponse = client.invoke(Constant.TYPE_POST, input);

                } else if(type.equals(Constant.TYPE_DELETE)) {

                    client.path(Constant.USERID, input);
                    userResponse = client.delete();

                } else if(type.equals(Constant.TYPE_GET)) {

                    if(StringUtils.isNotEmpty(input)) {
                        client.path(Constant.USERID, input);
                    }
                    userResponse = client.get();

                } else if(type.equals(Constant.TYPE_PATCH)) {

                    client.path(Constant.USERID, input);
                    userResponse = client.invoke(Constant.TYPE_PATCH, input);

                }
            } catch(Exception e) {

                LOGGER.error("Exception Caught while connecting client ... " + e);
                throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.COMMUNICATION_ERROR);

            }
        } else {
            LOGGER.error("Exception Caught while connecting client as client returned null ... ");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.COMMUNICATION_ERROR);
        }

        return userResponse;
    }

}
