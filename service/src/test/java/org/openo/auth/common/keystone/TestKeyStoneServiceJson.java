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

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.auth.common.CommUtil;
import org.openo.auth.entity.ModifyPassword;
import org.openo.auth.entity.ModifyUser;
import org.openo.auth.entity.RoleResponse;
import org.openo.auth.entity.UserCredentialUI;
import org.openo.auth.entity.UserDetailsUI;
import org.openo.auth.entity.keystone.req.KeyStoneConfiguration;
import org.openo.auth.entity.keystone.resp.UserCreateWrapper;
import org.openo.auth.entity.keystone.resp.UserModifyWrapper;
import org.openo.auth.entity.keystone.resp.UsersWrapper;
import org.openo.auth.exception.AuthException;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version
 */
public class TestKeyStoneServiceJson {

    private KeyStoneServiceJson instance;

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since
     */
    @Before
    public void setUp() throws Exception {
        instance = KeyStoneServiceJson.getInstance();
    }

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since
     */
    @After
    public void tearDown() throws Exception {
        // Mockit.tearDownMocks(KeyStoneServiceJson.class);
    }

    /**
     * Test method for
     * {@link org.openo.auth.common.keystone.KeyStoneServiceJson#getLoginJson(org.openo.auth.entity.UserCredentialUI, org.openo.auth.entity.keystone.req.KeyStoneConfiguration)}
     * .
     */
    @Test
    public void testGetLoginJson() {

        int status = HttpServletResponse.SC_OK;

        UserCredentialUI userInfo = CommUtil.getInstance().getUserCredentialUIInstance();

        KeyStoneConfiguration keyConf = CommUtil.getInstance().getKeyStoneConfigurationInstance();

        String jsonInString = "";

        try {
            jsonInString = instance.getLoginJson(userInfo, keyConf);

        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotSame("ok", "", jsonInString);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetLoginJsonException() {

        int status = HttpServletResponse.SC_OK;

        try {
            instance.getLoginJson(null, null);

        } catch(AuthException e) {
            status = e.getResponse().getStatus();

        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    /**
     * Test method for
     * {@link org.openo.auth.common.keystone.KeyStoneServiceJson#createUserJson(org.openo.auth.entity.UserDetailsUI, org.openo.auth.entity.keystone.req.KeyStoneConfiguration)}
     * .
     */
    @Test
    public void testCreateUserJson() {

        int status = HttpServletResponse.SC_OK;

        UserDetailsUI userInfo = CommUtil.getInstance().getUserDetailsInstance();

        KeyStoneConfiguration keyConf = CommUtil.getInstance().getKeyStoneConfigurationInstance();

        String jsonInString = "";

        try {
            jsonInString = instance.createUserJson(userInfo, keyConf);

        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotSame("ok", "", jsonInString);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testCreateUserJsonException() {

        int status = HttpServletResponse.SC_OK;

        try {
            instance.createUserJson(null, null);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    /**
     * Test method for
     * {@link org.openo.auth.common.keystone.KeyStoneServiceJson#responseForCreateUser(java.lang.String)}
     * .
     */
    @Test
    public void testResponseForCreateUser() {

        int status = HttpServletResponse.SC_OK;

        UserCreateWrapper userInfo = CommUtil.getInstance().getUserCreateWrapperInstance();

        String inputJson = CommUtil.getInstance().getJsonString(userInfo);

        List<RoleResponse> list = CommUtil.getInstance().getRoleResponse();

        String jsonInString = "";

        try {
            jsonInString = instance.responseForCreateUser(inputJson, list);

        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotSame("ok", "", jsonInString);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testResponseForCreateUserException() {

        int status = HttpServletResponse.SC_OK;

        String inputJson = "Invalid JSON";

        List<RoleResponse> list = CommUtil.getInstance().getRoleResponse();

        try {
            instance.responseForCreateUser(inputJson, list);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    /**
     * Test method for
     * {@link org.openo.auth.common.keystone.KeyStoneServiceJson#keyStoneRespToCreateUserObj(java.lang.String)}
     * .
     */
    @Test
    public void testKeyStoneRespToCreateUserObj() {

        int status = HttpServletResponse.SC_OK;

        UserCreateWrapper userInfo = CommUtil.getInstance().getUserCreateWrapperInstance();

        String inputJson = CommUtil.getInstance().getJsonString(userInfo);

        UserCreateWrapper userCreateWrapper = null;

        try {
            userCreateWrapper = instance.keyStoneRespToCreateUserObj(inputJson);

        } catch(Exception e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }

        Assert.assertNotNull(userCreateWrapper);

        Assert.assertEquals("ok", "auth.service@huawei.com", userCreateWrapper.getUser().getEmail());

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    /**
     * Test method for
     * {@link org.openo.auth.common.keystone.KeyStoneServiceJson#responseForMultipleUsers(java.lang.String)}
     * .
     */
    @Test
    public void testResponseForMultipleUsers() {

        int status = HttpServletResponse.SC_OK;

        UsersWrapper userInfo = CommUtil.getInstance().getUsersWrapperInstance();

        String inputJson = CommUtil.getInstance().getJsonString(userInfo);

        String jsonInString = "";

        try {
            jsonInString = instance.responseForMultipleUsers(inputJson, null, StringUtils.EMPTY);

        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotSame("ok", "", jsonInString);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testResponseForMultipleUsersException() {

        int status = HttpServletResponse.SC_OK;

        String inputJson = "Invalid JSON";

        try {
            instance.responseForMultipleUsers(inputJson,null,StringUtils.EMPTY);

        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    /**
     * Test method for
     * {@link org.openo.auth.common.keystone.KeyStoneServiceJson#responseForModifyUser(java.lang.String)}
     * .
     */
    @Test
    public void testResponseForModifyUser() {

        int status = HttpServletResponse.SC_OK;

        UserModifyWrapper userInfo = CommUtil.getInstance().getUserModifyWrapperInstance();

        String inputJson = CommUtil.getInstance().getJsonString(userInfo);

        List<RoleResponse> list = CommUtil.getInstance().getRoleResponse();

        String jsonInString = "";

        try {
            jsonInString = instance.responseForModifyUser(inputJson, list);

        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotSame("ok", "", jsonInString);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testResponseForModifyUserException() {

        int status = HttpServletResponse.SC_OK;

        String inputJson = "Invalid JSON";

        List<RoleResponse> list = CommUtil.getInstance().getRoleResponse();

        try {
            instance.responseForModifyUser(inputJson, list);

        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    /**
     * Test method for
     * {@link org.openo.auth.common.keystone.KeyStoneServiceJson#modifyPasswordJson(org.openo.auth.entity.ModifyPassword)}
     * .
     */
    @Test
    public void testModifyPasswordJson() {

        int status = HttpServletResponse.SC_OK;

        ModifyPassword modifyPwd = CommUtil.getInstance().getModifyPasswordInstance();

        String jsonInString = "";

        try {
            jsonInString = instance.modifyPasswordJson(modifyPwd);

        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotSame("ok", "", jsonInString);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testModifyPasswordJsonException() {

        int status = HttpServletResponse.SC_OK;

        try {
            instance.modifyPasswordJson(null);

        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    /**
     * Test method for
     * {@link org.openo.auth.common.keystone.KeyStoneServiceJson#modifyUserJson(org.openo.auth.entity.ModifyUser)}
     * .
     */
    @Test
    public void testModifyUserJson() {

        int status = HttpServletResponse.SC_OK;

        ModifyUser modifyUser = CommUtil.getInstance().getModifyUserInstance();

        String jsonInString = "";

        try {
            jsonInString = instance.modifyUserJson(modifyUser);

        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotSame("ok", "", jsonInString);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testModifyUserJsonException() {

        int status = HttpServletResponse.SC_OK;

        try {
            instance.modifyUserJson(null);

        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    /**
     * Test method for {@link org.openo.auth.common.keystone.KeyStoneServiceJson#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        Assert.assertNotNull(instance);
    }

}
