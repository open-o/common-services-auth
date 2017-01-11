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

package org.openo.auth.service.inf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

/**
 * A Token Service Delegate.
 * <br/>
 * 
 * @author
 * @version  
 */
public interface ITokenDelegate {

    /**
     * Perform Login Operation.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @return response for the login operation.
     * @since  
     */
    @POST
    Response login(HttpServletRequest request, HttpServletResponse response);

    /**
     * Perform Logout Operation.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @return response status for the operation.
     * @since  
     */
    int logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * Perform Validate token Operation.
     * <br/>
     * 
     * @param request : HttpServletRequest Object
     * @param response : HttpServletResponse Object
     * @return response status for the operation.
     * @since  
     */
    int checkToken(HttpServletRequest request, HttpServletResponse response);
}
