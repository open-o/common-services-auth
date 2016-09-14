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

import org.openo.auth.common.keystone.KeyStoneServiceJson;
import org.openo.auth.constant.Constant;

/**
 * This class <tt>JsonFactory<tt> is the factory for producing the JSON depending upon the service,
 * configured by the user.
 * <br/>
 * <p>
 * For Example :
 * If Service Name is configured as Keystone then JSON Factory will give the control to
 * KeystoneService JSON
 * </p>
 * 
 * @author
 * @version  
 */
public class JsonFactory {

    private static JsonFactory instance = new JsonFactory();

    private JsonFactory() {
        // Default constructor
    }

    /**
     * Singleton class, provides the instance of the <tt>JsonFactory</tt>
     * <br/>
     * 
     * @return instance : provides the instance of the <tt>JsonFactory</tt>
     * @since  
     */
    public static JsonFactory getInstance() {
        return instance;
    }

    /**
     * Depending upon the service configured, it will provide the Reference.
     * <br/>
     * 
     * @return JsonService : Returns the service instance, depending upon the configuration.
     * @since  
     */
    public IJsonService getJsonService() {

        String serviceName = ConfigUtil.getServiceName();

        if(null != serviceName && serviceName.equalsIgnoreCase(Constant.SERVICE_KEYSTONE)) {
            return KeyStoneServiceJson.getInstance();
        }
        return null;
    }

}
