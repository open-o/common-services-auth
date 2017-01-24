/*
 * Copyright (c) 2017 Huawei Technologies Co., Ltd.
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openo.auth.common.ConfigUtil;
import org.openo.auth.constant.Constant;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.Policy;
import org.openo.auth.entity.ServicePolicies;
import org.openo.auth.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version
 */
public class LoadPolicies {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadPolicies.class);

    private static List<ServicePolicies> servicePolicies;

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since
     */
    private LoadPolicies() {
    }

    /**
     * This api loads the service policies for the auth service.
     * <br/>
     * 
     * @return servicePolicies : Instance of <tt> List<ServicePolicies> </tt> class, which holds the
     *         values provided by user in the <tt> policies </tt> folder.
     * @since
     */
    public static List<ServicePolicies> loadConfigProperties() {

        LOGGER.info("Loading... " + Constant.AUTH_CONF_POLICY);

        try {

            File servicePolicyPath = new File(ConfigUtil.getPolicyPath());
            servicePolicies = listFilesForFolder(servicePolicyPath);

        } catch(Exception e) {

            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.SERVICE_LOAD_FAILED);

        }

        return servicePolicies;
    }

    private static List<ServicePolicies> listFilesForFolder(final File folder)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper policies = new ObjectMapper();
        List<ServicePolicies> policyServices = new ArrayList<ServicePolicies>();
        for(final File fileEntry : folder.listFiles()) {
            if(null != fileEntry) {
                if(fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    System.out.println(fileEntry.getName());
                    policyServices.add(policies.readValue(fileEntry, ServicePolicies.class));
                }
            }
        }
        return policyServices;
    }
}
