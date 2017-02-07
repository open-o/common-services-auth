/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.auth.access;

import java.util.List;

import org.junit.Test;
import org.openo.auth.entity.Policy;
import org.openo.auth.entity.ServicePolicies;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version
 */
public class TestLoadPolicies {

    /**
     * Test method for {@link org.openo.auth.access.LoadPolicies#loadConfigProperties()}.
     */
    @Test
    public void testLoadConfigProperties() {
        CommonMockUp.getInstance().mockConfigUtil();
        List<ServicePolicies> list = LoadPolicies.loadConfigProperties();
        for(ServicePolicies p1 : list) {
            for(Policy p : p1.getPolicies()) {
                System.out.println(p.getService() + "     " + p.getRule());
            }
        }
    }

}
