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

package org.openo.auth.entity.keystone.req;

/**
 * The base authentication model
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version     SDNO 0.5  2016年8月3日
 */
public class BaseAuth {

    private Identity identity;

    /**
     * 
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since  SDNO 0.5
     */
    public BaseAuth() {
        super();
    }

    /**
     * 
     * <br/>
     * 
     * @return
     * @since  SDNO 0.5
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * 
     * <br/>
     * 
     * @param identity
     * @since  SDNO 0.5
     */
    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

}
