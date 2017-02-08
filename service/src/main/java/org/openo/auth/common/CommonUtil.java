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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.ModifyPassword;
import org.openo.auth.entity.ModifyUser;
import org.openo.auth.entity.UserCredentialUI;
import org.openo.auth.entity.UserDetailsUI;
import org.openo.auth.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This class <tt>CommonUtil</tt> deals with the common utilities for the Auth Service.
 * </p>
 * 
 * @author
 * @version
 */
public class CommonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    private static CommonUtil instance = new CommonUtil();

    private CommonUtil() {

    }

    /**
     * This class persists Singleton design pattern so this method return the instance of the
     * <tt> CommonUtil<tt> class.
     * <br/>
     * 
     * @return instance : Instance of the <tt>CommonUtil</tt> class.
     * 
     * @since
     */
    public static CommonUtil getInstance() {
        return instance;
    }

    /**
     * This method will parse the JSON and maps the values to the objects.
     * <br/>
     * 
     * @param request : HttpServletRequest
     * @return userInfoCredential : Instance of <tt>UserCredentialUI</tt> contains username and
     *         password.
     * @since
     */
    public UserCredentialUI getUserInfoCredential(HttpServletRequest request) {

        try {
            LOGGER.info("getUserInfoCredential");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(request.getInputStream(), UserCredentialUI.class);
        } catch(IOException e) {
            LOGGER.info("Exception Caught " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
    }

    /**
     * This method will parse the JSON and maps the values to the objects.
     * <br/>
     * 
     * @param request : HttpServletRequest
     * @return user : Instance of <tt> UserDetailsUI </tt> class which contains all the
     *         details given by the user.
     * @since
     */
    public UserDetailsUI getUserInfo(HttpServletRequest request) {

        try {
            LOGGER.info("getUserInfo");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(request.getInputStream(), UserDetailsUI.class);
        } catch(IOException e) {
            LOGGER.info("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
    }

    /**
     * This method will parse the JSON and maps the values to the objects.
     * <br/>
     * 
     * @param request : HttpServletRequest
     * @return modifyPwd : Instance of <tt> ModifyPassword </tt> class which contains the password
     *         details.
     * @since
     */
    public ModifyPassword modifyPasswordJson(HttpServletRequest request) {

        try {
            LOGGER.info("modify password json");

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(request.getInputStream(), ModifyPassword.class);

        } catch(Exception ex) {

            LOGGER.error("Exception caught, trace = " + ex);

            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
    }

    /**
     * This method will parse the JSON and maps the values to the objects.
     * <br/>
     * 
     * @param request : HttpServletRequest
     * @return modifyUser : Instance of <tt> ModifyUser </tt> class which contains the email and
     *         description details.
     * @since
     */
    public ModifyUser modifyUserJson(HttpServletRequest request) {

        try {
            LOGGER.info("modify user json");

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(request.getInputStream(), ModifyUser.class);

        } catch(Exception ex) {
            LOGGER.error("Exception caught, trace = " + ex);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
    }

}
