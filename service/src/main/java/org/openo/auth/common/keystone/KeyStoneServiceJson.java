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

package org.openo.auth.common.keystone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.openo.auth.common.IJsonService;
import org.openo.auth.constant.Constant;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.ModifyPassword;
import org.openo.auth.entity.ModifyUser;
import org.openo.auth.entity.Role;
import org.openo.auth.entity.RoleResponse;
import org.openo.auth.entity.UserCredentialUI;
import org.openo.auth.entity.UserDetailsUI;
import org.openo.auth.entity.UserResponse;
import org.openo.auth.entity.keystone.req.Auth;
import org.openo.auth.entity.keystone.req.AuthWrapper;
import org.openo.auth.entity.keystone.req.BaseAuth;
import org.openo.auth.entity.keystone.req.Domain;
import org.openo.auth.entity.keystone.req.Identity;
import org.openo.auth.entity.keystone.req.KeyStoneConfiguration;
import org.openo.auth.entity.keystone.req.ModifyPwdWrapper;
import org.openo.auth.entity.keystone.req.ModifyUserWrapper;
import org.openo.auth.entity.keystone.req.Password;
import org.openo.auth.entity.keystone.req.Project;
import org.openo.auth.entity.keystone.req.Scope;
import org.openo.auth.entity.keystone.req.User;
import org.openo.auth.entity.keystone.req.UserLoginInfo;
import org.openo.auth.entity.keystone.req.UserWrapper;
import org.openo.auth.entity.keystone.resp.Roles;
import org.openo.auth.entity.keystone.resp.RolesWrapper;
import org.openo.auth.entity.keystone.resp.UserCreate;
import org.openo.auth.entity.keystone.resp.UserCreateWrapper;
import org.openo.auth.entity.keystone.resp.UserModifyWrapper;
import org.openo.auth.entity.keystone.resp.UsersWrapper;
import org.openo.auth.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This class <tt>KeyStoneServiceJson</tt> creates and provides the JSON for the Keystone Service to
 * access Keystone-Identity Api's.
 * </p>
 * <br/>
 * 
 * @author
 * @version
 */
public class KeyStoneServiceJson implements IJsonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyStoneServiceJson.class);

    private static KeyStoneServiceJson instance = new KeyStoneServiceJson();

    private KeyStoneServiceJson() {
        super();
        // Private Default Constructor
    }

    /**
     * Singleton class, provides the instance of <tt>KeyStoneServiceJson</tt> class.
     * <br/>
     * 
     * @return instance of <tt>KeyStoneServiceJson</tt> class.
     * @since
     */
    public static KeyStoneServiceJson getInstance() {
        return instance;
    }

    /**
     * Provides the JSON for KeyStone to perform Login operation.
     * <br/>
     * 
     * @param userInfo : Contains the values input from the user.
     * @param keyConf : Default values/configuration set for the KeyStone Service.
     * @return jsonInString : Provides the JSON for KeyStone to perform Login operation.
     * @since
     */
    public String getLoginJson(UserCredentialUI userInfo, KeyStoneConfiguration keyConf) {

        AuthWrapper obj = new AuthWrapper();

        BaseAuth auth;

        Identity identity = new Identity();
        Scope scope = new Scope();
        Password password = new Password();
        Project project = new Project();
        UserLoginInfo user = new UserLoginInfo();
        Domain domain = new Domain();
        String jsonInString = "";
        ObjectMapper mapper = new ObjectMapper();

        try {

            if(null == userInfo || null == keyConf) {
                throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
            }
            identity.setPassword(password);
            Set<String> set = new HashSet<String>();
            set.add(Constant.KEYSTONE_METHOD_PASSWORD);
            identity.setMethods(set);
            password.setUser(user);

            domain.setId(keyConf.getDomainId());
            domain.setName(keyConf.getDomainName());
            project.setDomain(domain);
            project.setName(keyConf.getProjectName());

            user.setDomain(domain);
            user.setName(userInfo.getUserName());
            user.setPassword(userInfo.getPassword());

            if(userInfo.getUserName().equals(keyConf.getAdminName())) {
                auth = new Auth();
                scope.setProject(project);
                ((Auth)auth).setScope(scope);
                LOGGER.info("set the Auth with scope");
            } else {
                auth = new BaseAuth();
                LOGGER.info("set the Auth without scope");
            }

            auth.setIdentity(identity);

            obj.setAuth(auth);

            jsonInString = mapper.writeValueAsString(obj);
            LOGGER.info("jsonInString --> " + jsonInString);

        } catch(Exception e) {

            LOGGER.error("Exception Caught : " + e);

            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);

        }
        return jsonInString;
    }

    /**
     * Provides the JSON for KeyStone to perform create user operation.
     * <br/>
     * 
     * @param inputDetails : Contains the values input from the user
     * @param keyConf : Default values/configuration set for the KeyStone Service.
     * @return jsonInString : Provides the JSON for KeyStone to perform create user operation.
     * @since
     */
    public String createUserJson(UserDetailsUI inputDetails, KeyStoneConfiguration keyConf) {

        UserWrapper userInfo = new UserWrapper();
        User user = new User();
        String jsonInString = "";
        try {

            if(null == inputDetails || null == keyConf) {
                throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
            }

            user.setEmail(inputDetails.getEmail());
            user.setDescription(inputDetails.getDescription());
            user.setName(inputDetails.getUserName());
            user.setPassword(inputDetails.getPassword());
            user.setDomainId(keyConf.getDomainId());
            user.setDefaultProjectId(keyConf.getProjectId());

            userInfo.setUser(user);
            ObjectMapper mapper = new ObjectMapper();

            jsonInString = mapper.writeValueAsString(userInfo);
            LOGGER.info("jsonInString : " + jsonInString);
        } catch(Exception e) {
            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
        return jsonInString;
    }

    /**
     * Provides the filtered response to the user.
     * <br/>
     * 
     * @param userJson : Contains the response provided by the KeyStone
     * @return jsonInString : It provides the filtered response to the user.
     * @since
     */
    public String responseForCreateUser(String userJson, List<RoleResponse> roles) {
        String jsonInString = "";
        UserResponse userResp = new UserResponse();
        try {
            UserCreateWrapper userWrapper = keyStoneRespToCreateUserObj(userJson);
            userResp.setId(userWrapper.getUser().getId());
            userResp.setDescription(userWrapper.getUser().getDescription());
            userResp.setName(userWrapper.getUser().getName());
            userResp.setEmail(userWrapper.getUser().getEmail());
            userResp.setRoles(roles);

            ObjectMapper mapperWrite = new ObjectMapper();
            jsonInString = mapperWrite.writeValueAsString(userResp);
            LOGGER.info("jsonInString : " + jsonInString);

        } catch(Exception e) {
            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
        return jsonInString;
    }

    /**
     * Converts KeyStone Response to User Create Object
     * <br/>
     * 
     * @param inputJson : Contains the response provided by the KeyStone
     * @return UserCreateWrapper : An Object containing the JSON values provided by KeyStone
     * @throws IOException, JsonParseException, JsonMappingException
     * @since
     */
    public UserCreateWrapper keyStoneRespToCreateUserObj(String inputJson) throws IOException {
        LOGGER.info("keystone resp create/modify user obj, json {}", inputJson);
        ObjectMapper mapperRead = new ObjectMapper();
        return mapperRead.readValue(inputJson, UserCreateWrapper.class);
    }

    /**
     * Converting user response to json
     * <br/>
     * 
     * @param userResp : List of user response
     * @return returning user response to json
     * @since
     */
    private String userResponseToJson(List<UserResponse> userResp) {

        String jsonInString = "";

        ObjectMapper mapperWrite = new ObjectMapper();

        try {

            jsonInString = mapperWrite.writeValueAsString(userResp);

            LOGGER.info("jsonInString : " + jsonInString);

        } catch(Exception e) {
            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
        return jsonInString;
    }

    /**
     * Gets and Create the JSON provided by KeyStone and given to the user.
     * <br/>
     * 
     * @param userJson : Contains the response provided by the KeyStone.
     * @return jsonInString : It provides the filtered response to the user.
     * @since
     */
    public String responseForModifyUser(String userJson, List<RoleResponse> roles) {

        UserModifyWrapper userWrapper = null;
        UserResponse userResp = new UserResponse();

        try {
            LOGGER.info("response for modify user ---> {}", userJson);

            ObjectMapper mapperRead = new ObjectMapper();

            userWrapper = mapperRead.readValue(userJson, UserModifyWrapper.class);

            LOGGER.info("Message Body is parsed not null");

            userResp.setEmail(userWrapper.getUser().getExtra().getEmail());
            userResp.setId(userWrapper.getUser().getId());
            userResp.setDescription(userWrapper.getUser().getExtra().getDescription());
            userResp.setName(userWrapper.getUser().getName());
            userResp.setRoles(roles);

            return userRespToJson(userResp);

        } catch(Exception e) {
            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

    }

    /**
     * Converting user response to json
     * <br/>
     * 
     * @param userResp : user response
     * @return returning user response to json
     * @since
     */
    private String userRespToJson(UserResponse userResp) {

        String jsonInString = "";

        ObjectMapper mapperWrite = new ObjectMapper();

        try {
            jsonInString = mapperWrite.writeValueAsString(userResp);
            LOGGER.info("jsonInString : " + jsonInString);
        } catch(Exception e) {
            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
        return jsonInString;
    }

    /**
     * Provides the JSON for KeyStone to perform Modify password operation.
     * <br/>
     * 
     * @param modifyPwd
     * @return jsonInString : Provides the JSON for KeyStone to perform Modify password operation.
     * @since
     */
    public String modifyPasswordJson(ModifyPassword modifyPwd) {

        String jsonInString = "";

        ModifyPwdWrapper modifyPwdWrapper = new ModifyPwdWrapper();

        ObjectMapper mapperWrite = new ObjectMapper();

        try {

            if(null == modifyPwd) {
                throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
            }

            modifyPwdWrapper.setUser(modifyPwd);

            jsonInString = mapperWrite.writeValueAsString(modifyPwdWrapper);

            LOGGER.info("jsonInString : " + jsonInString);

        } catch(Exception e) {
            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
        return jsonInString;

    }

    /**
     * Provides the JSON for KeyStone to perform Modify User operation.
     * <br/>
     * 
     * @param modifyUser : Provides the JSON for KeyStone to perform Modify User operation.
     * @return
     * @since
     */

    public String modifyUserJson(ModifyUser modifyUser) {

        String jsonInString = "";

        ModifyUserWrapper modifyUserWrapper = new ModifyUserWrapper();

        ObjectMapper mapperWrite = new ObjectMapper();

        try {

            if(null == modifyUser) {
                throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
            }

            modifyUserWrapper.setUser(modifyUser);

            jsonInString = mapperWrite.writeValueAsString(modifyUserWrapper);

            LOGGER.info("jsonInString : " + jsonInString);

        } catch(Exception e) {
            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
        return jsonInString;

    }

    /**
     * Provide the response for listing the roles.
     * <br/>
     * 
     * @param inputJson
     * @return
     * @since
     */
    public String responseForListRoles(String inputJson) {

        String jsonInString = "";
        Role roles = roleJsonToRoleObj(inputJson);
        ObjectMapper mapperWrite = new ObjectMapper();
        try {
            jsonInString = mapperWrite.writeValueAsString(roles);
        } catch(Exception e) {
            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
        LOGGER.info("jsonInString : " + jsonInString);
        return jsonInString;

    }

    /**
     * Convert role json to role object.
     * <br/>
     * 
     * @param inputJson
     * @since
     */
    public Role roleJsonToRoleObj(String inputJson) {

        List<RoleResponse> roleResponse = new ArrayList<RoleResponse>();
        try {
            RolesWrapper rolesWrapper = keyStoneRespToListRoles(inputJson);

            for(Roles roles : rolesWrapper.getRoles()) {
                RoleResponse roleResp = new RoleResponse();
                roleResp.setId(roles.getId());
                roleResp.setName(roles.getName());
                roleResponse.add(roleResp);
            }

            Role roles = new Role();
            roles.setRoles(roleResponse);
            return roles;

        } catch(Exception e) {
            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
    }

    /**
     * Provide the response to list user roles.
     * <br/>
     * 
     * @param inputJson
     * @return
     * @since
     */
    public String responseForListUserRoles(String inputJson) {

        String jsonInString = "";
        List<RoleResponse> roleResponse = new ArrayList<RoleResponse>();

        try {
            RolesWrapper rolesWrapper = keyStoneRespToListRoles(inputJson);

            for(Roles roles : rolesWrapper.getRoles()) {
                RoleResponse roleResp = new RoleResponse();
                roleResp.setId(roles.getId());
                roleResp.setName(roles.getName());
                roleResponse.add(roleResp);
            }

            Role roles = new Role();
            roles.setRoles(roleResponse);

            ObjectMapper mapperWrite = new ObjectMapper();
            jsonInString = mapperWrite.writeValueAsString(roles);
            LOGGER.info("jsonInString : " + jsonInString);

        } catch(Exception e) {
            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
        return jsonInString;
    }

    /**
     * Gets and Create the JSON provided by KeyStone and given to the user.
     * <br/>
     * 
     * @param inputJson : Contains the response provided by the KeyStone.
     * @return jsonInString : It provides the filtered response to the user.
     * @since
     */
    public String responseForMultipleUsers(String inputJson) {

        List<UserCreate> userInfo = new ArrayList<UserCreate>();

        UsersWrapper userWrapper = new UsersWrapper();

        List<UserResponse> userResp = new ArrayList<UserResponse>();

        try {

            LOGGER.info("response for multiple users");

            ObjectMapper mapperRead = new ObjectMapper();

            userWrapper = mapperRead.readValue(inputJson, UsersWrapper.class);

            userInfo = userWrapper.getUsers();

            for(UserCreate user : userInfo) {
                UserResponse res = new UserResponse();
                res.setId(user.getId());
                res.setDescription(user.getDescription());
                res.setName(user.getName());
                res.setEmail(user.getEmail());
                userResp.add(res);
            }

            return userResponseToJson(userResp);

        } catch(Exception e) {
            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);

        }

    }

    /**
     * Converts KeyStone Response to User Create Object
     * <br/>
     * 
     * @param inputJson : Contains the response provided by the KeyStone
     * @return UserCreateWrapper : An Object containing the JSON values provided by KeyStone
     * @throws IOException, JsonParseException, JsonMappingException
     * @since
     */
    public RolesWrapper keyStoneRespToListRoles(String inputJson) throws IOException {
        LOGGER.info("get Roles Information");
        ObjectMapper mapperRead = new ObjectMapper();
        return mapperRead.readValue(inputJson, RolesWrapper.class);
    }

}
