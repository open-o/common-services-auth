/*
 * Copyright (c) 2017, Huawei Technologies Co., Ltd.
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

package org.openo.auth.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openo.auth.access.LoadRights;
import org.openo.auth.constant.Constant;
import org.openo.auth.entity.PolicyRights;
import org.openo.auth.entity.Rights;
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
public class UrlMapping {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlMapping.class);

    private static UrlMapping instance = new UrlMapping();

    private UrlMapping() {
    }

    public static UrlMapping getInstance() {
        return instance;
    }

    public String getServiceActions(String uriPatternValidate, String methodType) {
        /*
         * Ex : uriPatternValidate = /openoapi/auth/v1/users
         * moduleName = auth
         * serviceName = users
         */
        String moduleName = getModuleNameFromUri(uriPatternValidate);
        String serviceName = getServiceNameFromUri(uriPatternValidate);
        List<PolicyRights> list = LoadRights.loadRights();
        for(PolicyRights policies : list) {
            for(Rights right : policies.getRights()) {
                String pattern = right.getUriPattern();
                if(pattern.contains("{")) {
                    pattern = pattern.replaceAll("{", StringUtils.EMPTY);
                    pattern = pattern.replaceAll("}", StringUtils.EMPTY);
                }
                if(isUriMatching(pattern, moduleName, serviceName) && methodType.equalsIgnoreCase(right.getMethod())) {
                    return right.getAction();
                }
            }
        }
        LOGGER.info("url and method combo not matched, considering access for all");
        return StringUtils.EMPTY;
    }

   

    private String getModuleNameFromUri(String uriPattern) {
        String uri = uriPattern.split(Constant.REGEX_URI_VERSIONS)[0];
        String moduleName = StringUtils.EMPTY;
        if(!StringUtils.isEmpty(uri)) {
            LOGGER.info("getModuleNameFromUri : uri " + uri);
            moduleName = uri.split(Constant.REGEX_URI_OPENOAPI)[1];
            LOGGER.info("getModuleNameFromUri : moduleName " + moduleName);
        }
        return moduleName;

    }

    private String getServiceNameFromUri(String uriPattern) {
        return uriPattern.split(Constant.REGEX_URI_VERSIONS)[1];
    }

    private boolean isUriMatching(String inputUri, String input1, String input2) {

        LOGGER.info("getModuleNameFromUri : input2 " + input2);

        /*
         * Replacing '{' , '}' to "" (Empty String) and extracting the service url
         */
        if(input2.contains("{")) {
            input2 = input2.replace("{", StringUtils.EMPTY);
            input2 = input2.replace("}", StringUtils.EMPTY);
        }
        Pattern pattern = Pattern.compile(
                Constant.REGEX_URI_OPENOAPI + input1 + Constant.REGEX_URI_VERSIONS + input2 + Constant.REGEX_URI_LAST);
        Matcher matcher = pattern.matcher(inputUri);
        return matcher.matches();
    }

}
