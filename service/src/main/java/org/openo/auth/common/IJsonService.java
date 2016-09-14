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

package org.openo.auth.common;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openo.auth.entity.ModifyPassword;
import org.openo.auth.entity.ModifyUser;
import org.openo.auth.entity.UserCredentialUI;
import org.openo.auth.entity.UserDetailsUI;
import org.openo.auth.entity.keystone.req.KeyStoneConfiguration;
import org.openo.auth.entity.keystone.resp.UserCreateWrapper;

/**
 * <p>
 * This class <tt>JsonService</tt> is an abstract class which is extended by the different Services
 * that auth service supports. It plays the important role for implementing the Factory Design
 * Pattern for <tt>JsonFactory</tt>.
 * </p>
 * <br/>
 * 
 * @author
 * @version  
 */
public interface IJsonService {
    
    /**
     * 
     * <br/>
     * 
     * @param userInfo
     * @param keyConf
     * @return
     * @since   
     */
    String getLoginJson(UserCredentialUI userInfo, KeyStoneConfiguration keyConf);
    
    /**
     * 
     * <br/>
     * 
     * @param inputDetails
     * @param keyConf
     * @return
     * @since   
     */
    String createUserJson(UserDetailsUI inputDetails, KeyStoneConfiguration keyConf);
    
    /**
     * 
     * <br/>
     * 
     * @param inputJson
     * @return
     * @since   
     */
    String responseForCreateUser(String inputJson);

    /**
     * 
     * <br/>
     * 
     * @param inputJson
     * @return
     * @throws IOException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @since   
     */
    UserCreateWrapper keyStoneRespToCreateUserObj(String inputJson)
            throws IOException;

    /**
     * 
     * <br/>
     * 
     * @param inputJson
     * @return
     * @since   
     */
    String responseForMultipleUsers(String inputJson);

    /**
     * 
     * <br/>
     * 
     * @param inputJson
     * @return
     * @since   
     */
    String responseForModifyUser(String inputJson);

    /**
     * 
     * <br/>
     * 
     * @param modifyPwd
     * @return
     * @since   
     */
    String modifyPasswordJson(ModifyPassword modifyPwd);

    /**
     * 
     * <br/>
     * 
     * @param modifyUser
     * @return
     * @since   
     */
    String modifyUserJson(ModifyUser modifyUser);

}
