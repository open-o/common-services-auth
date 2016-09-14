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

import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.auth.common.keystone.KeyStoneServiceJson;
import org.openo.auth.exception.AuthException;

import mockit.Mock;
import mockit.MockUp;
//import mockit.Mockit;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version  
 */
public class TestJsonFactory {

    private JsonFactory jsonFactory;

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since  
     */
    @Before
    public void setUp() throws Exception {
        jsonFactory = JsonFactory.getInstance();
    }

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since  
     */
    @After
    public void tearDown() throws Exception {
        //Mockit.tearDownMocks(JsonFactory.class);
    }

    /**
     * Test method for {@link org.openo.auth.common.JsonFactory#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        Assert.assertNotNull(jsonFactory);
    }

    /**
     * Test method for {@link org.openo.auth.common.JsonFactory#getJsonService()}.
     */
    @Test
    public void testGetJsonService() {

        int status = HttpServletResponse.SC_OK;

        IJsonService jsonService = null;

        mockConfigUtilServiceName("Keystone");

        try {
            jsonService = jsonFactory.getJsonService();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotNull(jsonService);

        boolean check = false;

        if(jsonService instanceof KeyStoneServiceJson) {
            check = true;
        }

        Assert.assertTrue(check);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    @Test
    public void testGetJsonServiceNull() {

        int status = HttpServletResponse.SC_OK;

        IJsonService jsonService = null;

        mockConfigUtilServiceName(null);

        try {
            jsonService = jsonFactory.getJsonService();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNull(jsonService);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    @Test
    public void testGetJsonServiceEmpty() {

        int status = HttpServletResponse.SC_OK;

        IJsonService jsonService = null;

        mockConfigUtilServiceName("");

        try {
            jsonService = jsonFactory.getJsonService();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNull(jsonService);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    @Test
    public void testGetJsonServiceNotRegistered() {

        int status = HttpServletResponse.SC_OK;

        IJsonService jsonService = null;

        mockConfigUtilServiceName("RBAC");

        try {
            jsonService = jsonFactory.getJsonService();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNull(jsonService);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    /**
     * <br/>
     * 
     * @since  
     */
    private void mockConfigUtilServiceName(final String serviceName) {

        new MockUp<ConfigUtil>() {

            @Mock
            public String getServiceName() {
                return serviceName;

            }
        };
    }

}
