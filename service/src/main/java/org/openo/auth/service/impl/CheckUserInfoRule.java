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

package org.openo.auth.service.impl;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.RoleResponse;
import org.openo.auth.entity.UserDetailsUI;
import org.openo.auth.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Check the user name and password rule.
 * <br/>
 * 
 * @author
 * @version
 */
public class CheckUserInfoRule {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckUserInfoRule.class);

    /**
     * Constructor<br/>
     * 
     * @since
     */
    private CheckUserInfoRule() {
        super();
    }

    /**
     * Check all the user info rule.
     * <br/>
     * 
     * @param userInfo
     * @since
     */
    public static void checkInfo(UserDetailsUI userInfo) {
        checkUserNameRule(userInfo.getUserName());
        checkPassword(userInfo.getPassword(), userInfo.getUserName());
        if(null == userInfo.getRoles() || userInfo.getRoles().isEmpty()) {
            LOGGER.error("no role info provided.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
        for(RoleResponse role : userInfo.getRoles()) {
            checkRoleValidity(role);
        }

    }

    /**
     * <br/>
     * 
     * @param role
     * @since
     */
    public static void checkRoleValidity(RoleResponse role) {
        if(null != role) {
            checkRoleNameRule(role.getName());
        } else {
            LOGGER.error("role name is empty.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
    }

    /**
     * Check the user name rule:
     * 1. Length should between 5 to 30;
     * 2. Only contain A-Z, a-z, 0-9, and "_";
     * 3. "_" must between the words
     * 4. can not contain space.
     * <br/>
     * 
     * @param userName
     * @since
     */
    private static void checkUserNameRule(String userName) {

        if(!checkLength(5, 30, userName)) {
            LOGGER.error("User name length is invali.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

        if(!checkNameCharacters(userName)) {
            LOGGER.error("User name have illegal characters.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

        if(!checkUnderScore(userName)) {
            LOGGER.error("'_' must between the words.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

        if(!checkNoSpace(userName)) {
            LOGGER.error("User name should not have space.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

    }

    /**
     * <br/>
     * 
     * @param roleName
     * @since
     */
    private static void checkRoleNameRule(String roleName) {

        if(StringUtils.isEmpty(roleName)) {
            LOGGER.error("role name is empty.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

        if(!checkLength(5, 20, roleName)) {
            LOGGER.error("role name length is invali.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

        if(!checkName(roleName)) {
            LOGGER.error("role name have illegal characters.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

        if(!checkNoSpace(roleName)) {
            LOGGER.error("role name should not have space.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

    }

    /**
     * Check for the password's rule:
     * 1. Length should between 8 to 32;
     * 2. At least contains: one lower case letter(a-z), and one digit(0-9), one special character:
     * ~`@#$%^&*-_=+|\?/()<>[]{}",.;'!
     * 3. Can not contain any the user name or user name in reverse order;
     * 4. Can not contain space.
     * <br/>
     * 
     * @param password
     * @param userName
     * @since
     */
    public static void checkPassword(String password, String userName) {
        if(!checkLength(8, 32, password)) {
            LOGGER.error("password length should between 8 to 32.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

        if(!checkPasswordCharacter(password)) {
            LOGGER.error(
                    "At least contains: one lowercase letter(a-z), and one digit(0-9), and one special character.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

        if(!checkNoNameOrReverse(password, userName)) {
            LOGGER.error("Password should not contain user name or reverse.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }

        if(!checkNoSpace(password)) {
            LOGGER.error("Password should not have space.");
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
    }

    /**
     * check the string's length
     * <br/>
     * 
     * @param min
     * @param max
     * @param string
     * @return true: only the string's length is equal or between the min and max.
     * @since
     */
    private static boolean checkLength(int min, int max, String string) {
        return string.length() >= min && string.length() <= max;
    }

    /**
     * check the user name 's character
     * <br/>
     * 
     * @param string
     * @return true: only contain a-z, A-Z, 0-9, and "_"
     * @since
     */
    private static boolean checkNameCharacters(String string) {
        return Pattern.matches("^[a-zA-Z0-9_]+", string);
    }

    /**
     * <br/>
     * 
     * @param string
     * @return
     * @since
     */
    private static boolean checkName(String string) {
        return Pattern.matches("^[a-zA-Z]+", string);
    }

    /**
     * check the under score;
     * <br/>
     * 
     * @param string
     * @return false: the "_" is in the first or in the end.
     * @since
     */
    private static boolean checkUnderScore(String string) {
        return !(Pattern.matches("^[a-zA-Z0-9_]+_$", string) || Pattern.matches("^_[a-zA-Z1-9_]+", string));
    }

    /**
     * check string have no space .
     * <br/>
     * 
     * @param userName
     * @return true: no space in the string.
     * @since
     */
    private static boolean checkNoSpace(String userName) {
        return !userName.contains(" ");
    }

    /**
     * check the password's special character.
     * <br/>
     * 
     * @param password
     * @return true: contain at least: one lower case letter(a-z), and one digit(0-9), one special
     *         character: ~`@#$%^&*-_=+|\?/()<>[]{}",.;'!
     * @since
     */
    private static boolean checkPasswordCharacter(String string) {
        return Pattern.matches(".*[A-Z]+.*$", string) && Pattern.matches(".*[a-z]+.*$", string)
                && Pattern.matches(".*[0-9]+.*$", string)
                && Pattern.matches(".*[~`@#$%^&\\*\\-_=\\+|\\?/\\(\\)<>\\[\\]{}\",\\.;'!]+.*$", string);
    }

    /**
     * check password not contain the user name and user name reverse.
     * <br/>
     * 
     * @param password
     * @param userName
     * @return true: password do not contain the user name and user name reverse.
     * @since
     */
    private static boolean checkNoNameOrReverse(String password, String userName) {
        return !password.contains(userName) && !password.contains(reverse(userName));
    }

    /**
     * reverse the string.
     * <br/>
     * 
     * @param password
     * @return
     * @since
     */
    private static String reverse(String password) {
        int length = password.length();
        char temp;
        char[] array = password.toCharArray();
        for(int i = 0; i < length / 2; i++) {
            temp = array[i];
            array[i] = array[length - 1 - i];
            array[length - 1 - i] = temp;
        }
        return String.valueOf(array);
    }

}
