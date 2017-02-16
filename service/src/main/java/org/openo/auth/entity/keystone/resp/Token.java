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

package org.openo.auth.entity.keystone.resp;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version
 */
public class Token {

    private List<TokenRoles> roles;

    private List<String> methods;

    @JsonProperty("expires_at")
    private String expiresAt;

    private TokenProject project;

    private List<Catalog> catalog;

    private User user;

    private Extras extras;

    @JsonProperty("audit_ids")
    private List<String> auditIds;

    @JsonProperty("issued_at")
    private String issuedAt;

    public List<TokenRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<TokenRoles> roles) {
        this.roles = roles;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public TokenProject getProject() {
        return project;
    }

    public void setProject(TokenProject project) {
        this.project = project;
    }

    public List<Catalog> getCatalog() {
        return catalog;
    }

    public void setCatalog(List<Catalog> catalog) {
        this.catalog = catalog;
    }

    public Extras getExtras() {
        return extras;
    }

    public void setExtras(Extras extras) {
        this.extras = extras;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getAuditIds() {
        return auditIds;
    }

    public void setAuditIds(List<String> auditIds) {
        this.auditIds = auditIds;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

}
