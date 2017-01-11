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
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.openo.auth.constant.Constant;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.Configuration;
import org.openo.auth.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class <tt>ConfigUtil<tt> deals with the functionality of loading the configuration for the
 * service for Auth
 * <br/>
 * 
 * <b>Services be like KeyStone or Openo-RBAC</b>
 * 
 * <blockquote><pre>
 * Example :
 * IP=1.1.1.1 - IP Address where Service is installed.
 * PORT=123 - Open port to access the installed service.
 * SERVICE=KeyStone - Installed Service
 * </blockquote></pre>
 * <b>Currently, Auth Service supports only KeyStone Service</b>
 * 
 * @author
 * @version  
 */
public class ConfigUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);

    private static Configuration config;

    static {
        loadConfigProperties();
    }

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since  
     */
    private ConfigUtil() {
    }

    /**
     * This api loads the service configuration for the proxy service.
     * <br/>
     * 
     * @return conf : Instance of <tt> Configuration </tt> class, which holds the configuration
     *         values provided by user in the <tt> auth_service.properties </tt>/
     * @since  
     */
    public static Configuration loadConfigProperties() {

        Configuration conf = new Configuration();

        LOGGER.info("Loading... " + Constant.AUTH_CONF_PROPERTIES);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Properties properties = new Properties();

        try {

            properties.load(classLoader.getResourceAsStream(Constant.AUTH_CONF_PROPERTIES));

        } catch(IOException e) {

            LOGGER.error("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.AUTH_LOAD_FAILED);

        }

        conf.setIpAddr(properties.getProperty(Constant.AUTH_CONF_IP));

        conf.setPortNo(properties.getProperty(Constant.AUTH_CONF_PORT));

        conf.setService(properties.getProperty(Constant.AUTH_CONF_SERVICE));

        LOGGER.info("Service Name = " + conf.getService());

        return conf;
    }

    /**
     * Provides the base url for the service which is to be communicate for the
     * Authentication.
     * <br/>
     * 
     * @return conf: Return the base url for the service which is to be communicate for the
     *         Authentication.
     * @since  
     */
    public static String getBaseURL() {

        if(null == config) {
            config = loadConfigProperties();
        }

        return createBaseURL(config);
    }

    /**
     * Provides the Service which is to be used for Auth Service.
     * <br/>
     * 
     * @return Service Name : It will return either the service name or it will return null
     * @since  
     */
    public static String getServiceName() {

        if(null == config) {

            config = loadConfigProperties();

            LOGGER.info("Service Name = " + config.getService());
            
        }

        return config.getService();

    }

    private static String createBaseURL(Configuration conf) {

        String ip = conf.getIpAddr();

        String port = conf.getPortNo();

        return "http://" + ip + ":" + port;
    }

}
