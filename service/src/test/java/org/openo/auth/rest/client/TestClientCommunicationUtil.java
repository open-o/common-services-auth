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
import org.openo.auth.constant.Constant;
import org.openo.auth.exception.AuthException;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version   Jul 25, 2016
 */
public class TestClientCommunicationUtil {

    private ClientCommunicationUtil instance = null;

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since  
     */
    @Before
    public void setUp() throws Exception {
        instance = ClientCommunicationUtil.getInstance();
       // CommonMockUp.getInstance().mockConfiguration();
    }

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since  
     */
    @After
    public void tearDown() throws Exception {
        //Mockit.tearDownMocks(ClientCommunicationUtil.class);
    }

    /**
     * Test method for {@link org.openo.auth.rest.client.ClientCommunicationUtil#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        Assert.assertNotNull(instance);

    }

    /**
     * Test method for
     * {@link org.openo.auth.rest.client.ClientCommunicationUtil#getResponseFromService(java.lang.String, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testGetRespFromServicePost() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClient();
            response = instance.getResponseFromService("testDemoVal", "testDemoVal", Constant.TYPE_POST);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNotNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetRespFromServiceDelete() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClient();
            response = instance.getResponseFromService("testDemoVal", "testDemoVal", Constant.TYPE_DELETE);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNotNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetRespFromServiceHead() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClient();
            response = instance.getResponseFromService("testDemoVal", "testDemoVal", Constant.TYPE_HEAD);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNotNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetRespFromServiceNullClient() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClientNull();
            response = instance.getResponseFromService("testDemoVal", "testDemoVal", Constant.TYPE_POST);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    @Test
    public void testGetRespFromServiceException() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClientException();
            response = instance.getResponseFromService("testDemoVal", "testDemoVal", Constant.TYPE_POST);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    /**
     * Test method for
     * {@link org.openo.auth.rest.client.ClientCommunicationUtil#getResponseFromService(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testGetResponseServiceDelete() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClient();
            response =
                    instance.getResponseFromService("testDemoVal", "testDemoVal", "testDemoVal", Constant.TYPE_DELETE);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNotNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetResponseServiceGet() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClient();
            response = instance.getResponseFromService("testDemoVal", "testDemoVal", "testDemoVal", Constant.TYPE_GET);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNotNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetResponseServicePatch() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClient();
            response =
                    instance.getResponseFromService("testDemoVal", "testDemoVal", "testDemoVal", Constant.TYPE_PATCH);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNotNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetResponseServicePost() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClient();
            response =
                    instance.getResponseFromService("testDemoVal", "testDemoVal", "testDemoVal", Constant.TYPE_POST);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNotNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetResponseServiceNullClient() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClientNull();
            response =
                    instance.getResponseFromService("testDemoVal", "testDemoVal", "testDemoVal", Constant.TYPE_POST);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    @Test
    public void testGetResponseServiceException() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClientException();
            response =
                    instance.getResponseFromService("testDemoVal", "testDemoVal", "testDemoVal", Constant.TYPE_POST);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    /**
     * Test method for
     * {@link org.openo.auth.rest.client.ClientCommunicationUtil#getResponseFromService(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testGetResponseFromServicePatch() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClient();
            response = instance.getResponseFromService("testDemoVal", "testDemoVal", "testDemoVal", "testDemoVal",
                    Constant.TYPE_PATCH);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNotNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetResponseFromServicePost() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClient();
            response = instance.getResponseFromService("testDemoVal", "testDemoVal", "testDemoVal", "testDemoVal",
                    Constant.TYPE_POST);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNotNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetResponseFromServiceNullClient() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClientNull();
            response = instance.getResponseFromService("testDemoVal", "testDemoVal", "testDemoVal", "testDemoVal",
                    Constant.TYPE_POST);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

    @Test
    public void testGetResponseFromServiceException() {

        int status = HttpServletResponse.SC_OK;
        Response response = null;

        try {
            WebClientMock.getInstance().mockWebClientException();
            response = instance.getResponseFromService("testDemoVal", "testDemoVal", "testDemoVal", "testDemoVal",
                    Constant.TYPE_POST);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_BAD_REQUEST, status);

    }

}
