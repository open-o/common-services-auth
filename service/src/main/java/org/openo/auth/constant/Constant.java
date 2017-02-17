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

package org.openo.auth.constant;

/**
 * <p>
 * An class which holds the all the constant values for the Auth Service.
 * </p>
 * <br/>
 * 
 * @author
 * @version
 */
public final class Constant {

    public static final String AUTH_CONF_PROPERTIES = "auth_service.properties";

    public static final String AUTH_CONF_IP = "IP";

    public static final String AUTH_CONF_PORT = "PORT";

    public static final String AUTH_CONF_SERVICE = "SERVICE";

    public static final String AUTH_CONF_POLICY = "POLICY";

    public static final String AUTH_CONF_RIGHTS = "RIGHTS";

    public static final String TYPE_POST = "POST";

    public static final String TYPE_DELETE = "DELETE";

    public static final String TYPE_HEAD = "HEAD";

    public static final String TYPE_GET = "GET";

    public static final String TYPE_PATCH = "PATCH";

    public static final String TYPE_PUT = "PUT";

    public static final String HEADER_X_TOKEN_ID = "X-Token-ID";

    public static final String TOKEN_AUTH = "X-Auth-Token";

    public static final String TOKEN_SUBJECT = "X-Subject-Token";

    public static final String URI_PATTERN = "Uri-Pattern";

    public static final String METHOD = "Method";

    public static final String MEDIA_TYPE_JSON = "application/json";

    public static final String SERVICE_KEYSTONE = "Keystone";

    public static final String KEYSTONE_CONF_PROPERTIES = "keystone_config.properties";

    public static final String KEYSTONE_CONF_DOMAIN_ID = "domain_id";

    public static final String KEYSTONE_CONF_DOMAIN_NAME = "domain_name";

    public static final String KEYSTONE_CONF_PROJECT_NAME = "project_name";

    public static final String KEYSTONE_CONF_PROJECT_ID = "project_id";

    public static final String KEYSTONE_CONF_ROLE_ID = "role_id";

    public static final String ADMIN_TOKEN = "admin_token";

    public static final String ADMIN_NAME = "admin_name";

    public static final String KEYSTONE_METHOD_PASSWORD = "password";

    public static final String KEYSTONE_IDENTITY_TOKEN = "/v3/auth/tokens";

    public static final String KEYSTONE_IDENTITY_USER = "/v3/users";

    public static final String KEYSTONE_IDENTITY_ROLES = "/v3/roles";

    public static final String KEYSTONE_IDENTITY_PROJECTS = "/v3/projects";

    public static final String USERS = "/users";

    public static final String ROLES = "/roles";

    public static final String USERID = "{user_id}";

    public static final String PROJECTID = "{project_id}";

    public static final String ROLEID = "{role_id}";

    public static final String FORWARD_SLASH = "/";

    public static final String COLON = ":";

    public static final String OR = "OR";

    public static final String NOT = "NOT";

    public static final String ALL = "";

    public static final String NONE = "!";

    public static final String EXTRACT_ROLE = "role:";

    public static final String REGEX_ROLE_FROM_RULE = "\\s+" + Constant.OR + "\\s+";

    public static final String LEFT_SMALL_BRACES = "(";

    public static final String RIGHT_SMALL_BRACES = ")";

    public static final String REGEX_URI_VERSIONS = "/v[\\d]/";

    public static final String REGEX_URI_OPENOAPI = "/openoapi/";

    public static final String REGEX_URI_LAST = "[[/\\w]*[{\\w}]*]*";

    public static final String CS_USERSERVICE = "cs-userservice";

    public static final String OPERATION_CREATEUSER = "createuser";

    public static final String OPERATION_MODIFYUSER = "modifyuser";

    public static final String OPERATION_LISTUSER = "listuser";

    public static final String OPERATION_DELETEUSER = "deleteuser";

    public static final String OPERATION_LISTROLES = "listroles";

    public static final String OPERATION_MODIFYPASSWORD = "modifypassword";

    private Constant() {
    }
}
