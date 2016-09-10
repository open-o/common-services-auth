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

package org.openo.auth.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * <p>
 * Runtime exception for applications.
 * <p>
 * This exception may be thrown by a resource method, provider or
 * {@link javax.ws.rs.core.StreamingOutput} implementation if a specific
 * HTTP error response needs to be produced. Only effective if thrown prior to
 * the response being committed.
 * </p>
 * 
 * @author
 * @version SDNO 0.5
 */
public class AuthException extends WebApplicationException {

    private static final long serialVersionUID = -7781121388414483107L;

    /**
     * <p>
     * Construct a new instance with the supplied message and a HTTP status.
     * </p>
     * 
     * @since SDNO 0.5
     * @param httpCode : HTTP status code that will be returned to the client
     * @param message : the detail message.
     */
    public AuthException(int httpCode, String message) {
        super(Response.status(httpCode).entity(message).type(MediaType.APPLICATION_JSON).build());
    }
}
