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

package org.openo.auth.rest.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.openo.auth.constant.Constant;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.exception.AuthException;

import mockit.Mock;
import mockit.MockUp;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version
 */
public class ClientCommonMock {

    private static ClientCommonMock instance = new ClientCommonMock();

    private ClientCommonMock() {
        // Default Constructor for singleton
    }

    public static ClientCommonMock getInstance() {
        return instance;
    }

    public void mockClientCommunicationUtil() {

        final Response response =
                getResponse(HttpServletResponse.SC_OK, Constant.TOKEN_SUBJECT, "tokenValue", "entity");

        new MockUp<ClientCommunicationUtil>() {

            @Mock
            public Response getResponseFromService(String url, String authToken, String userId, String body,
                    String type) {
                return response;
            }

            @Mock
            public Response getResponseFromService(String url, String authToken, String input, String type) {
                return response;
            }

        };
    }
    
    public void mockRoleServiceClient() {

        final Response response =
                getResponse(HttpServletResponse.SC_OK, Constant.TOKEN_SUBJECT, "tokenValue", "entity");

        new MockUp<RoleServiceClient>() {

            @Mock
            private String getUrlForRoleOperations(String authToken, String userId, String roleId) {
                return "url";
            }
            
        };
    }

    private Response getResponse(int status, String headerName, String headerValue, String entity) {

        Response res = null;

        InputStream entityStream = new ByteArrayInputStream(entity.getBytes(StandardCharsets.UTF_8));

        try {
            res = Response.status(status).header(headerName, headerValue).entity(entityStream).build();
        } catch(Exception e) {
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);

        }
        return res;
    }
}
