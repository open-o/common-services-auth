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

package org.openo.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.auth.common.AccessUtil;
import org.openo.auth.common.TokenUtil;
import org.openo.auth.constant.Constant;
import org.openo.auth.entity.Role;
import org.openo.auth.entity.RoleResponse;
import org.openo.auth.service.inf.IAccessDelegate;
import org.openo.auth.service.inf.IRoleDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * 
 * @author
 * @version
 */
public class AccessServiceImpl implements IAccessDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessServiceImpl.class);

    @Autowired
    IRoleDelegate roleDelegate;

    /**
     * @return Returns the roleDelegate.
     */
    public IRoleDelegate getRoleDelegate() {
        return roleDelegate;
    }

    /**
     * @param roleDelegate The roleDelegate to set.
     */
    public void setRoleDelegate(IRoleDelegate roleDelegate) {
        this.roleDelegate = roleDelegate;
    }

    /**
     * <br/>
     * 
     * @param request
     * @param response
     * @param serviceAccessName
     * @return
     * @since
     */
    public boolean validateRights(HttpServletRequest request, HttpServletResponse response, String serviceAccessName) {

        String authToken = request.getHeader(Constant.TOKEN_AUTH);

        String userId = TokenUtil.getInstance().getUserIdFromToken(authToken);

        LOGGER.info("authToken = " + authToken);

        Role role = roleDelegate.listRolesForUser(authToken, userId);

        List<RoleResponse> roleListUser = new ArrayList<RoleResponse>();

        Map<String, List<String>> ruleRoleMap = new HashMap<String, List<String>>();

        if(null != role && null != role.getRoles() && 0 < role.getRoles().size()) {
            roleListUser = role.getRoles();
        }

        String[] serviceAccessArray = AccessUtil.getInstance().getServiceAccessName(serviceAccessName);

        if(null != serviceAccessArray && serviceAccessArray.length > 0) {

            String serviceName = serviceAccessArray[0];
            String accessName = serviceAccessArray[1];

            String rule = AccessUtil.getInstance().getRuleFromServiceAccessCache(serviceName, accessName);

            ruleRoleMap = AccessUtil.getInstance().getRoleWithRules(rule);
        }

        return AccessUtil.getInstance().actionHasRole(roleListUser, ruleRoleMap);

    }

}
