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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.auth.entity.Configuration;
import org.openo.auth.exception.AuthException;

import mockit.Deencapsulation;
import mockit.Mock;
import mockit.MockUp;
//import mockit.Mockit;

public class TestConfigUtil {

    @Before
    public void setUp() throws Exception {
        mockPropertiesFileValues();
    }

    @After
    public void tearDown() throws Exception {
        //Mockit.tearDownMocks(Properties.class);
    }

    @Test
    public void testLoadConfigProperties() {

        Configuration config = null;

        int status = HttpServletResponse.SC_OK;

        try {
            config = ConfigUtil.loadConfigProperties();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotNull(config);

        Assert.assertEquals("ok", "Keystone", config.getService());

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);
    }

    @Test
    public void testGetBaseURL() {

        String url = "";

        int status = HttpServletResponse.SC_OK;

        try {
            url = ConfigUtil.getBaseURL();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotSame("Success", "", url);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    @Test
    public void testGetServiceName() {

        String serviceName = "";

        int status = HttpServletResponse.SC_OK;

        try {
            serviceName = ConfigUtil.getServiceName();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", "Keystone", serviceName);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }
    
    @Test
    public void testGetPolicyPath() {

        String policyPath = "";

        int status = HttpServletResponse.SC_OK;

        try {
            policyPath = ConfigUtil.getPolicyPath();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", "etc/policy", policyPath);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }
    
    
    @Test
    public void testGetRightsPath() {
        
        String rightPath = "";

        int status = HttpServletResponse.SC_OK;

        try {
            rightPath = ConfigUtil.getRightsPath();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertEquals("ok", "etc/rights", rightPath);

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

    /**
     * <br/>
     * 
     * @since  
     */
    private void mockPropertiesFileValues() {
        new MockUp<Properties>() {

            @Mock
            public synchronized void load(InputStream inStream) throws IOException {
            }

            @Mock
            public String getProperty(String key) {
                return "Keystone";
            }
        };
    }

}
