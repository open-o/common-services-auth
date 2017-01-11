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

package org.openo.auth.common.keystone;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.openo.auth.constant.Constant;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.keystone.req.KeyStoneConfiguration;
import org.openo.auth.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This class <tt>KeyStoneConfigInitializer</tt> initializes the configurations for the KeyStone.
 * </p>
 * <br/>
 *
 * @author
 * @version  
 */
public class KeyStoneConfigInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyStoneConfigInitializer.class);

    private static String domainId;

    private static String domainName;

    private static String projectName;

    private static String projectId;

    private static String roleId;
    
    private static String adminToken;
    
    private static String adminName;

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since  
     */
    private KeyStoneConfigInitializer() {
    }

    /**
     * This api loads the KeyStone configurations.
     * <br/>
     * 
     * @since  
     */
    private static void loadConfigProperties() {

        LOGGER.info("Loading... " + Constant.KEYSTONE_CONF_PROPERTIES);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Properties properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream(Constant.KEYSTONE_CONF_PROPERTIES));
        } catch(IOException e) {

            LOGGER.error("Exception Caught " + e);

            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
        domainId = properties.getProperty(Constant.KEYSTONE_CONF_DOMAIN_ID);

        domainName = properties.getProperty(Constant.KEYSTONE_CONF_DOMAIN_NAME);

        projectName = properties.getProperty(Constant.KEYSTONE_CONF_PROJECT_NAME);

        projectId = properties.getProperty(Constant.KEYSTONE_CONF_PROJECT_ID);

        roleId = properties.getProperty(Constant.KEYSTONE_CONF_ROLE_ID);
        
        adminToken = properties.getProperty(Constant.ADMIN_TOKEN);
        
        adminName = properties.getProperty(Constant.ADMIN_NAME);

        LOGGER.info("roleId = " + roleId);
    }

    /**
     * Provides the basic configuration values for the KeyStone.
     * <br/>
     * 
     * @return keyConf : A <tt>KeyStoneConfiguration</tt> object which has the basic properties
     *         which KeyStone needs for doing any operation.
     * @since  
     */
    public static KeyStoneConfiguration getKeystoneConfiguration() {

        loadConfigProperties();

        KeyStoneConfiguration keyConf = new KeyStoneConfiguration();

        keyConf.setDomainId(domainId);

        keyConf.setDomainName(domainName);

        keyConf.setProjectName(projectName);

        keyConf.setProjectId(projectId);

        keyConf.setRoleId(roleId);

        keyConf.setAdminToken(adminToken);
        
        keyConf.setAdminName(adminName);

        return keyConf;

    }

}
