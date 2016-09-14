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

package org.openo.auth.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.openo.auth.entity.ModifyPassword;
import org.openo.auth.entity.ModifyUser;
import org.openo.auth.entity.UserCredentialUI;
import org.openo.auth.entity.UserDetailsUI;
import org.openo.auth.entity.keystone.req.KeyStoneConfiguration;
import org.openo.auth.entity.keystone.resp.Links;
import org.openo.auth.entity.keystone.resp.SelfLink;
import org.openo.auth.entity.keystone.resp.UserCreate;
import org.openo.auth.entity.keystone.resp.UserCreateWrapper;
import org.openo.auth.entity.keystone.resp.UserExtra;
import org.openo.auth.entity.keystone.resp.UserModify;
import org.openo.auth.entity.keystone.resp.UserModifyWrapper;
import org.openo.auth.entity.keystone.resp.UsersWrapper;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version  
 */
public class CommUtil {

    private static CommUtil instance = new CommUtil();

    private CommUtil() {
    }

    public static CommUtil getInstance() {
        return instance;
    }

    public String getJsonString(Object obj) {
        String jsonInString = "";
        try {
            jsonInString = new ObjectMapper().writeValueAsString(obj);
        } catch(IOException e) {

        }
        return jsonInString;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public UserCredentialUI getUserCredentialUIInstance() {
        UserCredentialUI ui = new UserCredentialUI();

        ui.setUserName("auth");

        ui.setPassword("service");

        return ui;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public UserDetailsUI getUserDetailsInstance() {
        UserDetailsUI ui = new UserDetailsUI();

        ui.setUserName("auth");

        ui.setPassword("service");

        ui.setDescription("hi i am auth service");

        ui.setEmail("auth.service@huawei.com");

        return ui;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public ModifyPassword getModifyPasswordInstance() {
        ModifyPassword modifyPassword = new ModifyPassword();

        modifyPassword.setPassword("password");

        modifyPassword.setOriginalPassword("original_password");

        return modifyPassword;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public ModifyUser getModifyUserInstance() {
        ModifyUser modifyUser = new ModifyUser();

        modifyUser.setDescription("hi i am auth service");

        modifyUser.setEmail("auth.service@huawei.com");

        return modifyUser;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public KeyStoneConfiguration getKeyStoneConfigurationInstance() {

        KeyStoneConfiguration keyConf = new KeyStoneConfiguration();

        keyConf.setDomainId("default");

        keyConf.setDomainName("Default");

        keyConf.setProjectId("admin");

        keyConf.setProjectName("admin");

        keyConf.setRoleId("default_role");

        return keyConf;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public SelfLink getSelfLink() {

        SelfLink selfLink = new SelfLink();

        selfLink.setSelf("self");

        return selfLink;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public UserCreate getUserCreateInstance() {

        UserCreate userCreate = new UserCreate();

        userCreate.setDefaultProjectId("default_project_id");

        userCreate.setDescription("dummy_values_for_test");

        userCreate.setDomainId("default");

        userCreate.setEmail("auth.service@huawei.com");

        userCreate.setEnabled("true");

        userCreate.setId("id_1");

        userCreate.setLinks(getSelfLink());

        userCreate.setName("Shubham");

        return userCreate;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public UserCreateWrapper getUserCreateWrapperInstance() {

        UserCreateWrapper userCreateWrapper = new UserCreateWrapper();

        userCreateWrapper.setUser(getUserCreateInstance());

        return userCreateWrapper;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public Links getLinksInstance() {

        Links links = new Links();

        links.setNext("next");

        links.setPrevious("previous");

        links.setSelf("self");

        return links;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public UsersWrapper getUsersWrapperInstance() {

        UsersWrapper usersWrapper = new UsersWrapper();

        usersWrapper.setLinks(getLinksInstance());

        List<UserCreate> listUsers = new ArrayList<UserCreate>();

        listUsers.add(getUserCreateInstance());

        usersWrapper.setUsers(listUsers);

        return usersWrapper;
    }

    public UserExtra getUserExtraInstance() {

        UserExtra userCreate = new UserExtra();

        userCreate.setDescription("dummy_values_for_test");

        userCreate.setEmail("auth.service@huawei.com");

        return userCreate;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public UserModify getUserModifyInstance() {

        UserModify userModify = new UserModify();

        userModify.setDefaultProjectId("default_project_id");

        userModify.setDescription("dummy_values_for_test");

        userModify.setDomainId("default");

        userModify.setEmail("auth.service@huawei.com");

        userModify.setEnabled("true");

        userModify.setId("id_1");

        userModify.setLinks(getSelfLink());

        userModify.setName("Shubham");

        userModify.setExtra(getUserExtraInstance());

        return userModify;
    }

    /**
     * <br/>
     * 
     * @return
     * @since  
     */
    public UserModifyWrapper getUserModifyWrapperInstance() {

        UserModifyWrapper userModifyWrapper = new UserModifyWrapper();

        userModifyWrapper.setUser(getUserModifyInstance());

        return userModifyWrapper;
    }

}
