/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
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
 * <br/>
 * <p>
 * A POJO class for project.
 * </p>
 * 
 * @author
 * @version
 */
public class Project {

    @JsonProperty("is_domain")
    private String isDomain;

    private String description;

    @JsonProperty("domain_id")
    private String domainId;

    private String enabled;

    private String id;

    private SelfLink links;

    private String name;

    @JsonProperty("parent_id")
    private String parentId;

    public String getIsDomain() {
        return isDomain;
    }

    public void setIsDomain(String isDomain) {
        this.isDomain = isDomain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SelfLink getLinks() {
        return links;
    }

    public void setLinks(SelfLink links) {
        this.links = links;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
