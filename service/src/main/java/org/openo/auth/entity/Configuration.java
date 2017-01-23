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

package org.openo.auth.entity;

/**
 * A POJO class
 * <br/>
 * 
 * @author
 * @version
 */
public class Configuration {

    private String ipAddr;

    private String portNo;

    private String service;

    private String policy;

    private String rights;

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getPortNo() {
        return portNo;
    }

    public void setPortNo(String portNo) {
        this.portNo = portNo;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

}
