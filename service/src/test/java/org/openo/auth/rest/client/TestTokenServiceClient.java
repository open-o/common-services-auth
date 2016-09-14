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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.auth.entity.ClientResponse;
import org.openo.auth.exception.AuthException;

//import mockit.Mockit;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version   Jul 25, 2016
 */
public class TestTokenServiceClient {

    private TokenServiceClient instance = null;

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since  
     */
    @Before
    public void setUp() throws Exception {
        instance = TokenServiceClient.getInstance();
    }

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since  
     */
    @After
    public void tearDown() throws Exception {
        //Mockit.tearDownMocks(TokenServiceClient.class);
    }

    /**
     * Test method for {@link org.openo.auth.rest.client.TokenServiceClient#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        Assert.assertNotNull(instance);

    }

    /**
     * Test method for
     * {@link org.openo.auth.rest.client.TokenServiceClient#doLogin(java.lang.String)}.
     */
    @Test
    public void testDoLogin() {

        int status = HttpServletResponse.SC_OK;
        ClientResponse response = null;

        try {
            WebClientMock.getInstance().mockClientService(true);
            response = instance.doLogin(null);
            status = response.getStatus();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertNotNull(response);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    /**
     * Test method for
     * {@link org.openo.auth.rest.client.TokenServiceClient#doLogout(java.lang.String)}.
     */
    @Test
    public void testDoLogout() {

        int status = HttpServletResponse.SC_OK;

        try {
            WebClientMock.getInstance().mockClientService(true);
            status = instance.doLogout(null);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    /**
     * Test method for
     * {@link org.openo.auth.rest.client.TokenServiceClient#checkToken(java.lang.String)}.
     */
    @Test
    public void testCheckToken() {

        int status = HttpServletResponse.SC_OK;

        try {
            WebClientMock.getInstance().mockClientService(true);
            status = instance.checkToken(null);
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

}
