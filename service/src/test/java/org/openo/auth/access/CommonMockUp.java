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

package org.openo.auth.access;

import org.openo.auth.common.ConfigUtil;

import mockit.Mock;
import mockit.MockUp;
//import mockit.Mockit;

/**
 * This Class <tt>CommonMockUp</tt> deals with the common mocks that is to be handled with the unit
 * testing for the Auth Service.
 * <br/>
 * 
 * @author
 * @version
 */
public class CommonMockUp {

    private static CommonMockUp instance = new CommonMockUp();

    private CommonMockUp() {
        // Default Private Constructor
    }

    public static CommonMockUp getInstance() {
        return instance;
    }

    /**
     * <br/>
     * 
     * @param value
     * @since
     */
    public void mockConfigUtil() {

        new MockUp<ConfigUtil>() {

            @Mock
            public String getPolicyPath() {
                return "src/test/resources/policy";
            }

            @Mock
            public String getRightsPath() {
                return "src/test/resources/rights";
            }

        };

    }

}
