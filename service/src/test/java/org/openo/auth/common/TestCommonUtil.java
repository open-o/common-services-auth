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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.auth.entity.ModifyPassword;
import org.openo.auth.entity.ModifyUser;
import org.openo.auth.entity.UserCredentialUI;
import org.openo.auth.entity.UserDetailsUI;
import org.openo.auth.exception.AuthException;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version
 */

public class TestCommonUtil {

    private CommonUtil instance;

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since
     */
    @Before
    public void setUp() throws Exception {
        instance = CommonUtil.getInstance();
    }

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
        // Mockit.tearDownMocks(CommonUtil.class);
        CommonMockUp.getInstance().unMockHttpServletRequest();
        CommonMockUp.getInstance().unMockHttpServletResponse();

    }

    /**
     * Test method for {@link org.openo.auth.common.CommonUtil#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        Assert.assertNotNull(instance);
    }

    @Test
    public void testGetUserInfoCredential() {

        int status = HttpServletResponse.SC_OK;

        UserCredentialUI ui = CommUtil.getInstance().getUserCredentialUIInstance();

        HttpServletRequest request =
                CommonMockUp.getInstance().mockRequestInputStream(CommUtil.getInstance().getJsonString(ui));

        try {
            ui = instance.getUserInfoCredential(request);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", "auth", ui.getUserName());

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    /**
     * Test method for
     * {@link org.openo.auth.common.CommonUtil#getUserInfoCredential(javax.servlet.http.HttpServletRequest)}
     * .
     */
    @Test
    public void testGetUserInfoCredentialAuthException() {

        int status = HttpServletResponse.SC_OK;

        HttpServletRequest request = CommonMockUp.getInstance().mockRequestInputStream("error");

        try {
            instance.getUserInfoCredential(request);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);
    }

    /**
     * Test method for
     * {@link org.openo.auth.common.CommonUtil#getUserInfo(javax.servlet.http.HttpServletRequest)}
     * .
     */
    @Test
    public void testGetUserInfo() {

        int status = HttpServletResponse.SC_OK;

        UserDetailsUI ui = CommUtil.getInstance().getUserDetailsInstance();

        HttpServletRequest request =
                CommonMockUp.getInstance().mockRequestInputStream(CommUtil.getInstance().getJsonString(ui));

        try {
            ui = instance.getUserInfo(request);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", "auth", ui.getUserName());

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    @Test
    public void testGetUserInfoAuthException() {

        int status = HttpServletResponse.SC_OK;

        HttpServletRequest request = CommonMockUp.getInstance().mockRequestInputStream("error");

        try {
            instance.getUserInfo(request);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);
    }

    /**
     * Test method for
     * {@link org.openo.auth.common.CommonUtil#modifyPasswordJson(javax.servlet.http.HttpServletRequest)}
     * .
     */
    @Test
    public void testModifyPasswordJson() {

        int status = HttpServletResponse.SC_OK;

        ModifyPassword modifyPassword = CommUtil.getInstance().getModifyPasswordInstance();

        HttpServletRequest request =
                CommonMockUp.getInstance().mockRequestInputStream(CommUtil.getInstance().getJsonString(modifyPassword));

        try {
            modifyPassword = instance.modifyPasswordJson(request);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", "password", modifyPassword.getPassword());

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    @Test
    public void testModifyPasswordJsonAuthException() {

        int status = HttpServletResponse.SC_OK;

        HttpServletRequest request = CommonMockUp.getInstance().mockRequestInputStream("error");

        try {
            instance.modifyPasswordJson(request);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);
    }

    /**
     * Test method for
     * {@link org.openo.auth.common.CommonUtil#modifyUserJson(javax.servlet.http.HttpServletRequest)}
     * .
     */
    @Test
    public void testModifyUserJson() {

        int status = HttpServletResponse.SC_OK;

        ModifyUser modifyUser = CommUtil.getInstance().getModifyUserInstance();

        HttpServletRequest request =
                CommonMockUp.getInstance().mockRequestInputStream(CommUtil.getInstance().getJsonString(modifyUser));

        try {
            modifyUser = instance.modifyUserJson(request);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", "auth.service@huawei.com", modifyUser.getEmail());

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    @Test
    public void testModifyUserJsonAuthException() {

        int status = HttpServletResponse.SC_OK;

        HttpServletRequest request = CommonMockUp.getInstance().mockRequestInputStream("error");
        try {
            instance.modifyUserJson(request);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);
    }

}
