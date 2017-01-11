/*
 * Copyright (c) 2016-2017 Huawei Technologies Co., Ltd.
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

package org.openo.auth.service.inf;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.openo.auth.entity.Role;
import org.openo.auth.entity.RoleResponse;

/**
 * <br/>
 * <p>
 * A Role Service Delegate.
 * </p>
 * 
 * @author
 * @version
 */
public interface IRoleDelegate {

    /**
     * <br/>
     * 
     * @param request
     * @param response
     * @return
     * @since
     */
    Response listRoles(HttpServletRequest request, HttpServletResponse response);

    /**
     * <br/>
     * 
     * @param request
     * @param response
     * @param userId
     * @return
     * @since
     */
    Role listRolesForUser(String authToken, String userId);

    /**
     * <br/>
     * 
     * @param request
     * @param response
     * @param userId
     * @return
     * @since
     */
    int updateRolesToUser(List<RoleResponse> roleInfo, String authToken, String userId, boolean isNewUser);

}
