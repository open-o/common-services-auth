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

package org.openo.auth.entity.keystone.resp;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openo.auth.entity.RoleResponse;

/**
 * A POJO class
 * <br/>
 * 
 * @author
 * @version
 */
public class UserCreate {

    private SelfLink links;

    @JsonProperty("default_project_id")
    private String defaultProjectId;

    private String name;

    @JsonProperty("domain_id")
    private String domainId;

    private String id;

    private String enabled;

    private String description;

    private String email;

    private List<RoleResponse> roles;

    public List<RoleResponse> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResponse> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public String getDomainId() {
        return domainId;
    }

    public String getEnabled() {
        return enabled;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public SelfLink getLinks() {
        return links;
    }

    public String getName() {
        return name;
    }

    public String getDefaultProjectId() {
        return defaultProjectId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDefaultProjectId(String defaultProjectId) {
        this.defaultProjectId = defaultProjectId;
    }

    public void setLinks(SelfLink links) {
        this.links = links;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

}
