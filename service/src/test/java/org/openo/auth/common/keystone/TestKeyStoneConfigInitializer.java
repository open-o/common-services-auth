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

package org.openo.auth.common.keystone;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.openo.auth.entity.keystone.req.KeyStoneConfiguration;
import org.openo.auth.exception.AuthException;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version  
 */
public class TestKeyStoneConfigInitializer {

    /**
     * Test method for
     * {@link org.openo.auth.common.keystone.KeyStoneConfigInitializer#getKeystoneConfiguration()}.
     */
    @Test
    public void testGetKeystoneConfiguration() {

        KeyStoneConfiguration keyConf = null;

        int status = HttpServletResponse.SC_OK;

        try {
            keyConf = KeyStoneConfigInitializer.getKeystoneConfiguration();
        } catch(AuthException e) {
            status = e.getResponse().getStatus();
        }

        Assert.assertNotNull(keyConf);

        Assert.assertEquals("ok", "Default", keyConf.getDomainName());

        Assert.assertEquals("ok", HttpServletResponse.SC_OK, status);

    }

}
