/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
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

package org.openo.auth.rest.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpException;
import org.apache.cxf.jaxrs.client.WebClient;
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
 * @version   Jul 25, 2016
 */
public class WebClientMock {

    private static WebClientMock instance = new WebClientMock();

    private WebClientMock() {
    }

    public static WebClientMock getInstance() {
        return instance;
    }

    public void mockClientServiceOne() {

        final Response response =
                getResponse(HttpServletResponse.SC_OK, Constant.TOKEN_SUBJECT, "tokenValue", "entity", false);

        new MockUp<ClientCommunicationUtil>() {

            @Mock
            public Response getResponseFromService(String url, String authToken, String input, String type) {
                return response;
            }

        };

    }

    public void mockClientServiceOneNullResponse() {

        new MockUp<ClientCommunicationUtil>() {

            @Mock
            public Response getResponseFromService(String url, String authToken, String input, String type) {
                return null;
            }

        };

    }

    public void mockClientServiceTwo() {

        final Response response =
                getResponse(HttpServletResponse.SC_OK, Constant.TOKEN_SUBJECT, "tokenValue", "entity", false);

        new MockUp<ClientCommunicationUtil>() {

            @Mock
            public Response getResponseFromService(String url, String authToken, String userId, String body,
                    String type) {
                return response;
            }

        };

    }

    public void mockClientService(boolean isLogin) {

        final Response response =
                getResponse(HttpServletResponse.SC_OK, Constant.TOKEN_SUBJECT, "tokenValue", "entity", isLogin);
        new MockUp<ClientCommunicationUtil>() {

            @Mock
            public Response getResponseFromService(String url, String input, String type) {
                return response;
            }

        };

      /*  new MockUp<ClientCommunicationUtil>() {

            @Mock
            public ClientCommunicationUtil getInstance() {
                return util;
            }

        };*/

    }

    private Response getResponse(int status, String headerName, String headerValue, String entity, boolean isLogin) {

        Response res = null;

        InputStream entityStream = new ByteArrayInputStream(entity.getBytes(StandardCharsets.UTF_8));

        try {
            if(isLogin) {
                res = Response.status(status).cookie(new NewCookie(headerName, headerValue)).entity(entityStream)
                        .build();
            } else {
                res = Response.status(status).header(headerName, headerValue).entity(entityStream).build();
            }
        } catch(Exception e) {
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);

        }
        return res;
    }

    public void mockWebClient() {

        final Response response =
                getResponse(HttpServletResponse.SC_OK, Constant.TOKEN_SUBJECT, "tokenValue", "entity", false);
        
        new MockUp<WebClient>() {

            @Mock
            public Response put(Object body) {
                return response;
            }

            @Mock
            public Response invoke(String httpMethod, Object body) {
                return response;
            }

            @Mock
            public Response delete() {
                return response;
            }

            @Mock
            public Response get() {
                return response;
            }

        };

    }
    
    public void mockClientCommunicationUtil() {

        final Response response =
                getResponse(HttpServletResponse.SC_OK, Constant.TOKEN_SUBJECT, "tokenValue", "entity", false);

        new MockUp<ClientCommunicationUtil>() {

            @Mock
            private Response getResponseFromPatchService(String url, String authToken, String body)
                    throws HttpException, IOException {
                return response;
            }
        };

    }


    public void mockWebClientNull() {

        new MockUp<WebClient>() {

            @Mock
            public WebClient create(String baseAddress, List<?> providers) {
                return null;
            }

        };

    }

    public void mockWebClientException() {

        new MockUp<WebClient>() {

            @Mock
            public Response put(Object body) {
                throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
            }

            @Mock
            public Response invoke(String httpMethod, Object body) {
                throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
            }

            @Mock
            public Response delete() {
                throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
            }

            @Mock
            public Response get() {
                throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
            }

        };

    }

}
