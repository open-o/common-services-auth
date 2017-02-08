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

package org.openo.auth.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.cxf.common.util.StringUtils;
import org.openo.auth.access.LoadPolicies;
import org.openo.auth.constant.Constant;
import org.openo.auth.entity.Policy;
import org.openo.auth.entity.RoleResponse;
import org.openo.auth.entity.ServicePolicies;
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
public class AccessUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessUtil.class);

    private static AccessUtil instance = new AccessUtil();

    private AccessUtil() {
        // Default private constructor for singleton.
    }

    public static AccessUtil getInstance() {
        return instance;
    }

    public Map<String, List<String>> getRoleWithRules(String role) {

        Map<String, List<String>> ruleRoleMap = new HashMap<String, List<String>>();

        if(StringUtils.isEmpty(role)) {
            ruleRoleMap.put(Constant.ALL, null);
        } else if(null != role && 1 == role.length() && role.equalsIgnoreCase(Constant.NONE)) {
            ruleRoleMap.put(Constant.NONE, null);
        } else if(null != role && role.length() > 1) {
            String[] roles = getFromRegex(role, Constant.REGEX_ROLE_FROM_RULE);
            List<String> listOr = new ArrayList<String>();
            List<String> listNot = new ArrayList<String>();
            for(String value : roles) {
                String[] roleValue = getFromRegex(value, Constant.EXTRACT_ROLE);
                if(roleValue[1].startsWith(Constant.NONE)) {
                    listNot.add(roleValue[1]);
                } else {
                    listOr.add(roleValue[1]);
                }
            }
            ruleRoleMap.put(Constant.OR, listOr);
            ruleRoleMap.put(Constant.NOT, listNot);
        }

        return ruleRoleMap;
    }

    private String[] getFromRegex(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        String[] roles = pattern.split(input);
        for(int i = 0; i < roles.length; i++) {
            LOGGER.info(roles[i]);
        }
        return roles;
    }

    public String[] getServiceAccessName(String serviceAccessName) {
        String[] array = new String[2];
        if(!StringUtils.isEmpty(serviceAccessName) && serviceAccessName.contains(Constant.COLON)) {
            array = StringUtils.split(serviceAccessName, Constant.COLON);
        }
        return array;
    }

    public String getRuleFromServiceAccessCache(String serviceName, String accessName) {
        String rule = null;
        List<ServicePolicies> list = LoadPolicies.loadConfigProperties();
        for(ServicePolicies p1 : list) {
            for(Policy p : p1.getPolicies()) {
                LOGGER.info("Service = {}, Rule = {} ", p.getService(), p.getRule());
                String[] serviceAccessArray = getServiceAccessName(p.getService());
                rule = getRule(serviceName, accessName, rule, p, serviceAccessArray);
            }
        }
        return rule;
    }

    /**
     * <br/>
     * 
     * @param serviceName
     * @param accessName
     * @param rule
     * @param p
     * @param serviceAccessArray
     * @return
     * @since
     */
    private String getRule(String serviceName, String accessName, String rule, Policy p, String[] serviceAccessArray) {
        if(null != serviceAccessArray && serviceAccessArray.length > 0) {
            String serviceNameDb = serviceAccessArray[0];
            String accessNameDb = serviceAccessArray[1];
            if(serviceName.equalsIgnoreCase(serviceNameDb) && accessName.equalsIgnoreCase(accessNameDb)) {
                return p.getRule();
            }
        }
        return rule;
    }

    /**
     * <br/>
     * 
     * @param roleListUser
     * @param ruleRoleMap
     * @since
     */
    public boolean actionHasRole(List<RoleResponse> roleListUser, Map<String, List<String>> ruleRoleMap) {
        boolean status = true;
        for(Map.Entry<String, List<String>> entry : ruleRoleMap.entrySet()) {
            if(Constant.ALL.equalsIgnoreCase(entry.getKey())) {
                return status;
            } else if(Constant.NONE.equalsIgnoreCase(entry.getKey())) {
                return !status;
            } else if(Constant.OR.equalsIgnoreCase(entry.getKey())) {
                if(hasActionOrRule(roleListUser, entry.getValue())) {
                    return status;
                }
            } else if(Constant.NOT.equalsIgnoreCase(entry.getKey())) {
                if(hasActionNoneRule(roleListUser, entry.getValue())) {
                    return !status;
                }
            }
        }
        return !status;
    }

    /**
     * <br/>
     * 
     * @param roleListUser
     * @param entry
     * @since
     */
    private boolean hasActionNoneRule(List<RoleResponse> roleListUser, List<String> listRoles) {
        for(RoleResponse roles : roleListUser) {
            if(listRoles.contains(Constant.NONE + roles.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br/>
     * 
     * @param roleListUser
     * @param status
     * @param entry
     * @since
     */
    private boolean hasActionOrRule(List<RoleResponse> roleListUser, List<String> listRoles) {
        for(RoleResponse roles : roleListUser) {
            if(listRoles.contains(roles.getName())) {
                return true;
            }
        }
        return false;
    }
}
