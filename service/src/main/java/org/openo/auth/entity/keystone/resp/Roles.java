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

package org.openo.auth.entity.keystone.resp;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * A POJO class
 * <br/>
 * 
 * @author
 * @version
 */
public class Roles {

    @JsonProperty("domain_id")
    private String domainId;

    private SelfLink links;

    private String id;

    private String name;

    /**
     * @return Returns the domainId.
     */
    public String getDomainId() {
        return domainId;
    }

    /**
     * @param domainId The domainId to set.
     */
    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    /**
     * @return Returns the links.
     */
    public SelfLink getLinks() {
        return links;
    }

    /**
     * @param links The links to set.
     */
    public void setLinks(SelfLink links) {
        this.links = links;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

}
