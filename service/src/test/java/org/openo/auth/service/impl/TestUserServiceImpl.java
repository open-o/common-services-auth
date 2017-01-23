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

package org.openo.auth.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.auth.common.CommonMockUp;
import org.openo.auth.exception.AuthException;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version Jul 26, 2016
 */

public class TestUserServiceImpl {

    private UserServiceImpl instance;

    private HttpServletRequest request;

    private HttpServletResponse response;

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since
     */
    @Before
    public void setUp() throws Exception {
        instance = new UserServiceImpl();
        response = CommonMockUp.getInstance().mockHttpServletResponse();
        request = CommonMockUp.getInstance().mockHttpServletRequest();
    }

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since
     */
    @After
    public void tearDown() throws Exception {
        // Mockit.tearDownMocks(UserServiceImpl.class);
        CommonMockUp.getInstance().unMockHttpServletRequest();
        CommonMockUp.getInstance().unMockHttpServletResponse();
    }

    /**
     * Test method for
     * {@link org.openo.auth.service.impl.UserServiceImpl#createUser(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
     * .
     */
    @Test
    public void testCreateUser() {

        CommonMockUp.getInstance().mockCommonUtilApi();

        CommonMockUp.getInstance().mockKeyStoneConfiguration();

        CommonMockUp.getInstance().mockJsonFactory();

        CommonMockUp.getInstance().mockUserClient();

        CommonMockUp.getInstance().mockKeyStoneServiceJson();

        int status;
        try {
            Response res = instance.createUser(request, response);
            status = res.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_CREATED, status);
    }

    @Test
    public void testCreateUserException() {

        int status;
        try {
            Response res = instance.createUser(request, response);
            status = res.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    /**
     * Test method for
     * {@link org.openo.auth.service.impl.UserServiceImpl#modifyUser(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)}
     * .
     */
    @Test
    public void testModifyUser() {

        CommonMockUp.getInstance().mockCommonUtilApi();

        CommonMockUp.getInstance().mockKeyStoneConfiguration();

        CommonMockUp.getInstance().mockJsonFactory();

        CommonMockUp.getInstance().mockUserClient();

        CommonMockUp.getInstance().mockKeyStoneServiceJson();

        int status;
        try {
            Response res = instance.modifyUser(request, response, "user-id-1");
            status = res.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    @Test
    public void testModifyUserException() {

        int status;
        try {
            Response res = instance.modifyUser(request, response, "user-id-1");
            status = res.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);
    }

    /**
     * Test method for
     * {@link org.openo.auth.service.impl.UserServiceImpl#deleteUser(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)}
     * .
     */
    @Test
    public void testDeleteUser() {

        CommonMockUp.getInstance().mockUserClient();

        int status;
        try {
            status = instance.deleteUser(request, response, "id-1");
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    @Test
    public void testDeleteUserException() {

        CommonMockUp.getInstance().mockUserClientException();

        int status;
        try {
            status = instance.deleteUser(request, response, "id-1");
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);
    }

    /**
     * Test method for
     * {@link org.openo.auth.service.impl.UserServiceImpl#getUserDetails(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)}
     * .
     */
    @Test
    public void testGetUserDetails() {

        CommonMockUp.getInstance().mockUserClient();

        CommonMockUp.getInstance().mockKeyStoneServiceJson();

        int status;
        try {
            Response res = instance.getUserDetails(request, response, "id");
            status = res.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    @Test
    public void testGetUserDetailsException() {

        CommonMockUp.getInstance().mockUserClient();

        int status;
        try {
            Response res = instance.getUserDetails(request, response, "user-id");
            status = res.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    /**
     * Test method for
     * {@link org.openo.auth.service.impl.UserServiceImpl#getUserDetails(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
     * .
     */
    @Test
    public void testGetUserDetailsAll() {

        CommonMockUp.getInstance().mockCommonUtilApi();

        CommonMockUp.getInstance().mockKeyStoneConfiguration();

        CommonMockUp.getInstance().mockJsonFactory();

        CommonMockUp.getInstance().mockUserClient();

        CommonMockUp.getInstance().mockKeyStoneServiceJson();

        int status;
        try {
            Response res = instance.getUserDetails(request, response);
            status = res.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    @Test
    public void testGetUserDetailsAllException() {

        CommonMockUp.getInstance().mockUserClient();
        int status;
        try {
            Response res = instance.getUserDetails(request, response);
            status = res.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);
    }

    /**
     * Test method for
     * {@link org.openo.auth.service.impl.UserServiceImpl#modifyPasword(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)}
     * .
     * 
     * @throws IOException
     */
    @Test
    public void testModifyPasword() throws IOException {

        CommonMockUp.getInstance().mockCommonUtilApi();

        CommonMockUp.getInstance().mockKeyStoneConfiguration();

        CommonMockUp.getInstance().mockJsonFactory();

        CommonMockUp.getInstance().mockUserClient();

        CommonMockUp.getInstance().mockKeyStoneServiceJson();

        int status;
        try {
            status = instance.modifyPasword(request, response, "user-id-1");
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    @Test
    public void testModifyPaswordException() throws IOException {

        int status;
        try {
            status = instance.modifyPasword(request, response, "user-id-1");
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);
    }

}
