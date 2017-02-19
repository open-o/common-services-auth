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

package org.openo.auth.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openo.auth.common.keystone.KeyStoneConfigInitializer;
import org.openo.auth.common.keystone.KeyStoneServiceJson;
import org.openo.auth.constant.Constant;
import org.openo.auth.entity.ClientResponse;
import org.openo.auth.entity.Configuration;
import org.openo.auth.entity.ModifyPassword;
import org.openo.auth.entity.ModifyUser;
import org.openo.auth.entity.Role;
import org.openo.auth.entity.RoleResponse;
import org.openo.auth.entity.UserDetailsUI;
import org.openo.auth.entity.keystone.req.KeyStoneConfiguration;
import org.openo.auth.entity.keystone.resp.Domain;
import org.openo.auth.entity.keystone.resp.ProjectWrapper;
import org.openo.auth.entity.keystone.resp.Token;
import org.openo.auth.entity.keystone.resp.TokenWrapper;
import org.openo.auth.entity.keystone.resp.User;
import org.openo.auth.entity.keystone.resp.UserCreate;
import org.openo.auth.entity.keystone.resp.UserCreateWrapper;
import org.openo.auth.rest.client.ClientCommunicationUtil;
import org.openo.auth.rest.client.RoleServiceClient;
import org.openo.auth.rest.client.TokenServiceClient;
import org.openo.auth.rest.client.UserServiceClient;
import org.openo.auth.service.impl.TokenServiceImpl;
import org.openo.auth.service.inf.IRoleDelegate;

import mockit.Mock;
import mockit.MockUp;
//import mockit.Mockit;

/**
 * This Class <tt>CommonMockUp</tt> deals with the common mocks that is to be handled with the unit
 * testing for the Auth Service.
 * <br/>
 * 
 * @author
 * @version
 */
public class CommonMockUp {

    private static CommonMockUp instance = new CommonMockUp();

    private CommonMockUp() {
        // Default Private Constructor
    }

    public static CommonMockUp getInstance() {
        return instance;
    }

    public HttpServletRequest mockHttpServletRequest() {
        return new MockUp<HttpServletRequest>() {}.getMockInstance();
    }

    public void unMockHttpServletRequest() {
        // Mockit.tearDownMocks(HttpServletRequest.class);
    }

    public HttpServletResponse mockHttpServletResponse() {
        return new MockUp<HttpServletResponse>() {}.getMockInstance();
    }

    public void unMockHttpServletResponse() {
        // Mockit.tearDownMocks(HttpServletResponse.class);
    }

    /**
     * <br/>
     * 
     * @param value
     * @since
     */
    public HttpServletRequest mockRequestInputStream(final String value) {
        HttpServletRequest request = new MockUp<HttpServletRequest>() {

            @Mock
            public ServletInputStream getInputStream() throws IOException {
                InputStream inputStream = new ByteArrayInputStream(value.getBytes());
                DelegateServletStream output = new DelegateServletStream(inputStream);
                return output;
            }

        }.getMockInstance();
        return request;
    }

    /**
     * <br/>
     * 
     * @param value
     * @since
     */
    public KeyStoneConfiguration mockKeystoneConfiguration() {

        final KeyStoneConfiguration keyConf = new KeyStoneConfiguration();

        new MockUp<KeyStoneConfigInitializer>() {

            @Mock
            public KeyStoneConfiguration getKeystoneConfiguration() {

                keyConf.setDomainId("default");
                keyConf.setDomainName("Default");
                keyConf.setProjectId("openo");
                keyConf.setProjectName("openo");
                keyConf.setRoleId("admin");
                return keyConf;
            }

        };
        return keyConf;
    }

    /**
     * <br/>
     * 
     * @param value
     * @since
     */
    public void mockJsonFactory() {

        new MockUp<JsonFactory>() {

            @Mock
            public IJsonService getJsonService() {
                return KeyStoneServiceJson.getInstance();
            }

        };

    }

    public void mockKeyStoneServiceJson() {

        new MockUp<KeyStoneServiceJson>() {

            @Mock
            public String responseForCreateUser(String inputJson, List<RoleResponse> roles) {
                return "response";
            }

            @Mock
            public UserCreateWrapper keyStoneRespToCreateUserObj(String inputJson) throws IOException {
                UserCreateWrapper user = new UserCreateWrapper();
                UserCreate us = new UserCreate();
                us.setName("test123");
                us.setId("id-1");
                user.setUser(us);
                return user;

            }

            @Mock
            public String modifyUserJson(ModifyUser modifyUser) {
                return "json-response";
            }

            @Mock
            public String responseForModifyUser(String inputJson, List<RoleResponse> roles) {
                return "json-response";
            }

            @Mock
            public String responseForMultipleUsers(String inputJson, IRoleDelegate roleDelegate, String authToken) {
                return "json-response";
            }

            @Mock
            public Role roleJsonToRoleObj(String inputJson) {
                return new Role();
            }

        };
    }

    /**
     * <br/>
     * 
     * @param value
     * @since
     */
    public ClientResponse mockTokenClientDoLogin() {

        final ClientResponse resp = new ClientResponse();

        new MockUp<TokenServiceClient>() {

            @Mock
            public ClientResponse doLogin(String json) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setBody("body");
                resp.setHeader("header");
                return resp;
            }

        };

        return resp;
    }

    public void mockTokenClientDoLogout() {

        new MockUp<TokenServiceClient>() {

            @Mock
            public int doLogout(String token) {
                return HttpServletResponse.SC_OK;
            }
        };

    }

    public void mockTokenClientCheckToken() {

        new MockUp<TokenServiceClient>() {

            @Mock
            public int checkToken(String token) {
                return HttpServletResponse.SC_OK;
            }
        };

    }

    public void mockTokenServiceImpl() {

        new MockUp<TokenServiceImpl>() {

            @Mock
            public Response hasAccess(HttpServletRequest request, HttpServletResponse response, String uriPattern,
                    String method) {
                return Response.status(HttpServletResponse.SC_OK).entity(Boolean.TRUE).build();
            }
        };

    }

    public void mockCommonUtilApi() {

        new MockUp<CommonUtil>() {

            @Mock
            public UserDetailsUI getUserInfo(HttpServletRequest request) {
                UserDetailsUI ui = new UserDetailsUI();
                RoleResponse role = new RoleResponse();
                role.setId("role-id");
                role.setName("admin");
                List<RoleResponse> listRoles = new ArrayList<RoleResponse>();
                listRoles.add(role);
                ui.setEmail("auth.service@huawei.com");
                ui.setUserName("Shubham");
                ui.setPassword("Test1234_");
                ui.setDescription("service_description");
                ui.setRoles(listRoles);
                return ui;
            }

            @Mock
            public ModifyUser modifyUserJson(HttpServletRequest request) {
                ModifyUser mu = new ModifyUser();
                mu.setEmail("auth.service@huawei.com");
                mu.setDescription("service_description");

                return mu;

            }

            @Mock
            public ModifyPassword modifyPasswordJson(HttpServletRequest request) {
                ModifyPassword mp = new ModifyPassword();
                mp.setPassword("Changeme_123");
                mp.setOriginalPassword("Test12345_");
                return mp;
            }
        };

    }

    public void mockKeyStoneConfiguration() {

        final KeyStoneConfiguration keyConf = CommUtil.getInstance().getKeyStoneConfigurationInstance();
        new MockUp<KeyStoneConfigInitializer>() {

            @Mock
            public KeyStoneConfiguration getKeystoneConfiguration() {
                return keyConf;
            }
        };

    }

    public void mockConfiguration() {

        new MockUp<ConfigUtil>() {

            @Mock
            public Configuration loadConfigProperties() {
                return new Configuration();
            }

            @Mock
            public String getBaseURL() {
                return "";
            }

            @Mock
            public String getServiceName() {
                return "";
            }
        };

    }

    public void mockUserClient() {

        new MockUp<UserServiceClient>() {

            @Mock
            public ClientResponse createUser(String json, String authToken) {
                ClientResponse resp = new ClientResponse();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setBody("body");
                resp.setHeader("header");
                return resp;
            }

            @Mock
            public int assignRolesToUser(String authToken, String projectId, String userId, String roleId) {
                return HttpServletResponse.SC_OK;
            }

            @Mock
            public ClientResponse modifyUser(String userId, String json, String authToken) {
                ClientResponse resp = new ClientResponse();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setBody("body");
                resp.setHeader("header");
                return resp;
            }

            @Mock
            public int deleteUser(String userId, String authToken) {
                return HttpServletResponse.SC_OK;
            }

            @Mock
            public ClientResponse getUserDetails(String authToken) {
                ClientResponse resp = new ClientResponse();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setBody("body");
                resp.setHeader("header");
                return resp;
            }

            @Mock
            public ClientResponse getUserDetails(String userId, String authToken) {
                ClientResponse resp = new ClientResponse();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setBody("body");
                resp.setHeader("header");
                return resp;
            }

            @Mock
            public int modifyPassword(String userId, String json, String authToken) {
                return HttpServletResponse.SC_OK;
            }
        };

    }

    public void mockUserClientException() {

        new MockUp<UserServiceClient>() {

            @Mock
            public ClientResponse createUser(String json, String authToken) {
                ClientResponse resp = new ClientResponse();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setBody("body");
                resp.setHeader("header");
                return resp;
            }

            @Mock
            public int assignRolesToUser(String authToken, String projectId, String userId, String roleId) {
                return HttpServletResponse.SC_OK;
            }

            @Mock
            public ClientResponse modifyUser(String userId, String json, String authToken) {
                ClientResponse resp = new ClientResponse();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setBody("body");
                resp.setHeader("header");
                return resp;
            }

            @Mock
            public int deleteUser(String userId, String authToken) {
                return HttpServletResponse.SC_BAD_REQUEST;
            }

            @Mock
            public ClientResponse getUserDetails(String authToken) {
                ClientResponse resp = new ClientResponse();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setBody("body");
                resp.setHeader("header");
                return resp;
            }

            @Mock
            public ClientResponse getUserDetails(String userId, String authToken) {
                ClientResponse resp = new ClientResponse();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setBody("body");
                resp.setHeader("header");
                return resp;
            }

            @Mock
            public int modifyPassword(String userId, String json, String authToken) {
                return HttpServletResponse.SC_OK;
            }
        };

    }

    public KeyStoneConfiguration mockKeystoneConfigurationName() {

        final KeyStoneConfiguration keyConf = new KeyStoneConfiguration();

        new MockUp<KeyStoneConfigInitializer>() {

            @Mock
            public KeyStoneConfiguration getKeystoneConfiguration() {

                keyConf.setDomainId("default");
                keyConf.setDomainName("Default");
                keyConf.setProjectId("openo");
                keyConf.setProjectName("openo");
                keyConf.setRoleId("admin");
                keyConf.setAdminName("auth");
                return keyConf;
            }

        };
        return keyConf;
    }

    public void mockRoleServiceClient() {
        new MockUp<RoleServiceClient>() {

            @Mock
            public ClientResponse listAllRoles(String authToken) {
                ClientResponse client = new ClientResponse();
                client.setStatus(HttpServletResponse.SC_OK);
                client.setBody("");
                return client;
            }

            @Mock
            public ClientResponse listRolesForUser(String authToken, String userId) {

                ClientResponse client = new ClientResponse();
                client.setStatus(HttpServletResponse.SC_OK);
                client.setBody("");
                return client;

            }

            @Mock
            public int updateRolesToUser(String authToken, String projectId, String userId, String roleId,
                    String type) {
                return 0;
            }

            @Mock
            private String getTokenHeader(Response userResponse) {
                return "";
            }

            @Mock
            private ClientResponse makeResponse(Response userResponse) {

                ClientResponse client = new ClientResponse();
                client.setStatus(HttpServletResponse.SC_OK);
                client.setBody("");
                return client;

            }

            @Mock
            private String getUrlForRoleOperations(String authToken, String userId, String roleId) {
                return "";
            }

            @Mock
            public Map<String, ClientResponse> removeRolesFromUser(String authToken, String userId,
                    Map<String, String> rolesToRemove) {
                return null;
            }

            @Mock
            public Map<String, ClientResponse> assignRolesToUser(String authToken, String userId,
                    Map<String, String> rolesToAssign) {
                return null;
            }

            @Mock
            private String getProjectIdFromName(String authToken, String projectName) {
                return "";
            }

            @Mock
            private ProjectWrapper getObjFromJson(String inputJson) {
                return null;
            }

            @Mock
            private void writeToKeyConfProperties(String projectId) {
                return;
            }

            @Mock
            public ClientResponse getProjectDetails(String authToken) {

                ClientResponse client = new ClientResponse();
                client.setStatus(HttpServletResponse.SC_OK);
                client.setBody("");
                return client;

            }

        };
    }

    public void mockRoleServiceClientException() {
        new MockUp<RoleServiceClient>() {

            @Mock
            public ClientResponse listAllRoles(String authToken) {
                ClientResponse client = new ClientResponse();
                client.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                client.setBody("");
                return client;
            }

            @Mock
            public ClientResponse listRolesForUser(String authToken, String userId) {

                ClientResponse client = new ClientResponse();
                client.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                client.setBody("");
                return client;

            }

            @Mock
            public int updateRolesToUser(String authToken, String projectId, String userId, String roleId,
                    String type) {
                return 0;
            }

            @Mock
            private String getTokenHeader(Response userResponse) {
                return "";
            }

            @Mock
            private ClientResponse makeResponse(Response userResponse) {

                ClientResponse client = new ClientResponse();
                client.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                client.setBody("");
                return client;

            }

            @Mock
            private String getUrlForRoleOperations(String authToken, String userId, String roleId) {
                return "";
            }

            @Mock
            public Map<String, ClientResponse> removeRolesFromUser(String authToken, String userId,
                    Map<String, String> rolesToRemove) {
                return null;
            }

            @Mock
            public Map<String, ClientResponse> assignRolesToUser(String authToken, String userId,
                    Map<String, String> rolesToAssign) {
                return null;
            }

            @Mock
            private String getProjectIdFromName(String authToken, String projectName) {
                return "";
            }

            @Mock
            private ProjectWrapper getObjFromJson(String inputJson) {
                return null;
            }

            @Mock
            private void writeToKeyConfProperties(String projectId) {
                return;
            }

            @Mock
            public ClientResponse getProjectDetails(String authToken) {

                ClientResponse client = new ClientResponse();
                client.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                client.setBody("");
                return client;

            }

        };
    }

    public void mockRoleUtil() {
        new MockUp<RoleUtil>() {

            @Mock
            private void validateRolesForUser(Role allRoles, Role existingRoles, List<RoleResponse> rolesToUpdate,
                    boolean isNewUser) {
            }

        };

    }

    public void mockTokenServiceClient() throws Exception {

        /*
         * new MockUp<TokenServiceClient>() {
         * @Mock
         * public String getTokenInfo(String token) {
         * TokenWrapper tokenWrapper = new TokenWrapper();
         * Token tokens = new Token();
         * tokens.setAuditIds(null);
         * tokens.setCatalog(null);
         * tokens.setExpiresAt(StringUtils.EMPTY);
         * tokens.setExtras(null);
         * tokens.setIssuedAt(null);
         * tokens.setMethods(null);
         * tokens.setProject(null);
         * tokens.setRoles(null);
         * User user = new User();
         * Domain domain = new Domain();
         * user.setDomain(domain);
         * user.setId("user-id");
         * user.setName("user-name");
         * tokens.setUser(user);
         * tokenWrapper.setToken(tokens);
         * String tokenjson = StringUtils.EMPTY;
         * ObjectMapper mapper = new ObjectMapper();
         * try {
         * tokenjson = mapper.writeValueAsString(tokenWrapper);
         * } catch(Exception e) {
         * }
         * return tokenjson;
         * }
         * };
         */

        new MockUp<ClientCommunicationUtil>() {

            @Mock
            public Response getResponseFromService(String url, String input, String type) {
                TokenWrapper tokenWrapper = new TokenWrapper();
                Token tokens = new Token();
                tokens.setAuditIds(null);
                tokens.setCatalog(null);
                tokens.setExpiresAt(StringUtils.EMPTY);
                tokens.setExtras(null);
                tokens.setIssuedAt(null);
                tokens.setMethods(null);
                tokens.setProject(null);
                tokens.setRoles(null);
                User user = new User();
                Domain domain = new Domain();
                user.setDomain(domain);
                user.setId("user-id");
                user.setName("user-name");
                tokens.setUser(user);
                tokenWrapper.setToken(tokens);
                String tokenjson = StringUtils.EMPTY;
                ObjectMapper mapper = new ObjectMapper();
                try {
                    tokenjson = mapper.writeValueAsString(tokenWrapper);
                } catch(Exception e) {
                }
                InputStream inp = new ByteArrayInputStream(tokenjson.getBytes());
                Response response =
                        Response.status(200).entity(inp).header(Constant.TOKEN_AUTH, "auth-Token").build();
                return response;
            }

        };

    }

}
