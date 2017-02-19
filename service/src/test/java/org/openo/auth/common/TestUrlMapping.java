/*
 * Copyright 2017, Huawei Technologies Co., Ltd.
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.auth.access.CommonMockUp;

public class TestUrlMapping {

    private UrlMapping instance;

    @Before
    public void setUp() throws Exception {
        instance = UrlMapping.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    @Test
    public void testGetInstance() {
        Assert.assertNotNull(instance);

    }

    @Test
    public void testGetServiceActions() {
        CommonMockUp.getInstance().mockConfigUtil();
        String action = instance.getServiceActions("/openoapi/auth/v1/users", "POST");
        Assert.assertEquals("ok","cs-userservice:createuser",action);

    }

}
