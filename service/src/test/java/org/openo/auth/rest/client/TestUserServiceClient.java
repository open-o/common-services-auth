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

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.auth.entity.ClientResponse;
import org.openo.auth.exception.AuthException;
//import mockit.Mockit;

import mockit.Mock;
import mockit.MockUp;

public class TestUserServiceClient {

    private UserServiceClient instance;

    @Before
    public void setUp() throws Exception {
        instance = UserServiceClient.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        //Mockit.tearDownMocks(UserServiceClient.class);
    }

    @Test
    public void testGetInstance() {
        Assert.assertNotNull(instance);

    }

    @Test
    public void testCreateUser() {

        int status = HttpServletResponse.SC_OK;
        ClientResponse response = null;

        try {
            WebClientMock.getInstance().mockClientServiceOne();
            response = instance.createUser(null, null);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNotNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testCreateUserNullClient() {

        ClientResponse response = null;

        try {
            WebClientMock.getInstance().mockClientServiceOneNullResponse();
            response = instance.createUser(null, null);
        } catch(AuthException e) {
        }
        Assert.assertNotNull(response);

        Assert.assertEquals("ok", null, response.getBody());

    }

    @Test
    public void testDeleteUser() {

        int status = HttpServletResponse.SC_OK;

        try {
            WebClientMock.getInstance().mockClientServiceOne();
            status = instance.deleteUser(null, null);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetUserDetailsForOneUser() {

        int status = HttpServletResponse.SC_OK;
        ClientResponse response = null;

        try {
            WebClientMock.getInstance().mockClientServiceOne();
            response = instance.getUserDetails(null, null);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotNull(response);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetUserDetailsForOneUserNullClient() {

        ClientResponse response = null;

        try {
            //WebClientMock.getInstance().mockClientServiceOneNullResponse();
            
            new MockUp<ClientCommunicationUtil>() {

                @Mock
                public Response getResponseFromService(String url, String authToken, String input, String type) {
                    return null;
                }

            };
            
            response = UserServiceClient.getInstance().getUserDetails(null, null);
        } catch(AuthException e) {
        }

        Assert.assertNotNull(response);

        Assert.assertEquals("ok", null, response.getBody());

    }

    @Test
    public void testGetUserDetailsForAllusers() {

        int status = HttpServletResponse.SC_OK;
        ClientResponse response = null;

        try {
            WebClientMock.getInstance().mockClientServiceOne();
            response = instance.getUserDetails(null);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotNull(response);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testModifyUser() {

        int status = HttpServletResponse.SC_OK;
        ClientResponse response = null;

        try {
            WebClientMock.getInstance().mockClientServiceTwo();
            response = instance.modifyUser(null, null, null);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotNull(response);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testModifyPassword() {

        int status = HttpServletResponse.SC_OK;

        try {
            WebClientMock.getInstance().mockClientServiceTwo();
            status = instance.modifyPassword(null, null, null);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testAssignRolesToUser() {

        int status = HttpServletResponse.SC_OK;

        try {
            WebClientMock.getInstance().mockWebClient();
            status = instance.assignRolesToUser("authToken", "projectId", "userId", "roleId");
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testAssignRolesToUserNullClient() {

        int status = HttpServletResponse.SC_OK;

        try {
            WebClientMock.getInstance().mockWebClientNull();
            status = instance.assignRolesToUser("authToken", "projectId", "userId", "roleId");
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    @Test
    public void testAssignRolesToUserException() {

        int status = HttpServletResponse.SC_OK;

        try {
            WebClientMock.getInstance().mockWebClientException();
            status = instance.assignRolesToUser("authToken", "projectId", "userId", "roleId");
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

}
