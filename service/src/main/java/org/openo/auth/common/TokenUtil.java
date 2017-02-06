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

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.openo.auth.constant.ErrorCode;
import org.openo.auth.entity.keystone.resp.Token;
import org.openo.auth.exception.AuthException;
import org.openo.auth.rest.client.TokenServiceClient;
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
public class TokenUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenUtil.class);

    private static TokenUtil instance = new TokenUtil();

    private TokenUtil() {
        // Private default constructor
    }

    public static TokenUtil getInstance() {
        return instance;
    }

    public String getUserIdFromToken(String authToken) {
        String tokenInfoJson = TokenServiceClient.getInstance().getTokenInfo(authToken);
        Token tokenInfo = getUserId(tokenInfoJson);
        return tokenInfo.getUser().getId();
    }

    private Token getUserId(String json) {

        try {
            LOGGER.info("getUserInfo");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Token.class);
        } catch(IOException e) {
            LOGGER.info("Exception Caught : " + e);
            throw new AuthException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.FAILURE_INFORMATION);
        }
    }

}
