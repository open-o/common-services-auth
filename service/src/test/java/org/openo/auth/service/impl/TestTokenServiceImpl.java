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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.auth.common.CommUtil;
import org.openo.auth.common.CommonMockUp;
import org.openo.auth.entity.UserCredentialUI;

//import mockit.Mockit;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version   Jul 26, 2016
 */
public class TestTokenServiceImpl {

    private TokenServiceImpl instance;

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
        instance = new TokenServiceImpl();
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
        // Mockit.tearDownMocks(TokenServiceImpl.class);
        CommonMockUp.getInstance().unMockHttpServletRequest();
        CommonMockUp.getInstance().unMockHttpServletResponse();
    }

    /**
     * Test method for
     * {@link org.openo.auth.service.impl.TokenServiceImpl#login(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
     * .
     */

    @Test
    public void testLogin() {

        UserCredentialUI ui = CommUtil.getInstance().getUserCredentialUIInstance();

        String jsonInString = CommUtil.getInstance().getJsonString(ui);

        HttpServletRequest request = CommonMockUp.getInstance().mockRequestInputStream(jsonInString);

        CommonMockUp.getInstance().mockKeystoneConfigurationName();

        CommonMockUp.getInstance().mockJsonFactory();

        CommonMockUp.getInstance().mockTokenClientDoLogin();

        Response res = instance.login(request, response);

        Assert.assertNotNull(res);

        Assert.assertEquals("ok", HttpServletResponse.SC_CREATED, res.getStatus());

    }

    /**
     * Test method for
     * {@link org.openo.auth.service.impl.TokenServiceImpl#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
     * .
     */
    @Test
    public void testLogout() {

        CommonMockUp.getInstance().mockTokenClientDoLogout();

        int res = instance.logout(request, response);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, res);

    }

    /**
     * Test method for
     * {@link org.openo.auth.service.impl.TokenServiceImpl#checkToken(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
     * .
     */
    @Test
    public void testCheckToken() {

        CommonMockUp.getInstance().mockTokenClientCheckToken();

        int res = instance.checkToken(request, response);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, res);

    }

}
