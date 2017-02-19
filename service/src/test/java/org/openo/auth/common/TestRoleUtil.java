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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.auth.entity.ClientResponse;
import org.openo.auth.entity.Role;
import org.openo.auth.entity.RoleResponse;
import org.openo.auth.exception.AuthException;

import mockit.Deencapsulation;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version
 */
public class TestRoleUtil {

    private RoleUtil instance;

    private HttpServletRequest request;

    private Map<String, String> roleUpdate;

    private Map<String, String> roleExisting;

    private Map<String, String> roleToAssign;

    private Map<String, String> roleToDelete;

    /**
     * <br/>
     * 
     * @throws java.lang.Exception
     * @since
     */
    @Before
    public void setUp() throws Exception {
        instance = RoleUtil.getInstance();
        request = CommonMockUp.getInstance().mockHttpServletRequest();
        setReferenceVariable();
        Deencapsulation.setField(instance, "roleUpdate", roleUpdate);
        Deencapsulation.setField(instance, "roleExisting", roleExisting);
        Deencapsulation.setField(instance, "roleToAssign", roleToAssign);
        Deencapsulation.setField(instance, "roleToDelete", roleToDelete);

    }

    @Test
    public void testGetInstance() {
        Assert.assertNotNull(instance);
    }

    private void setReferenceVariable() {

        roleUpdate = new HashMap<String, String>();
        roleUpdate.put("roleUpdateId", "roleUpdateVal");

        roleExisting = new HashMap<String, String>();
        roleExisting.put("roleExistingId", "roleExistingValue");

        roleToAssign = new HashMap<String, String>();
        roleToAssign.put("roleAssignId", "roleAssignValue");

        roleToDelete = new HashMap<String, String>();
        roleToDelete.put("roleDeleteId", "roleDeleteValue");
    }

    @Test
    public void testGetRoleInfo() {
        boolean status = true;
        try {
            Role role = new Role();

            HttpServletRequest request =
                    CommonMockUp.getInstance().mockRequestInputStream(CommUtil.getInstance().getJsonString(role));
            instance.getRoleInfo(request);
        } catch(AuthException ex) {
            status = false;
        }
        Assert.assertTrue(status);
    }

    @Test
    public void testGetRoleInfoExcepton() {
        boolean status = false;
        try {
            instance.getRoleInfo(request);
        } catch(AuthException ex) {
            status = true;
        }
        Assert.assertTrue(status);
    }

    @Test
    public void testValidateRolesForUser() {

        boolean status = true;
        try {
            CommonMockUp.getInstance().mockRoleServiceClient();

            CommonMockUp.getInstance().mockRoleUtil();

            CommonMockUp.getInstance().mockKeyStoneServiceJson();

            List<RoleResponse> roleToUpdate = new ArrayList<RoleResponse>();
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setId("roleId");
            roleResponse.setName("roleName");
            roleToUpdate.add(roleResponse);
            instance.validateRolesForUser("authToken", "userId", roleToUpdate, false);
            instance.validateRolesForUser("authToken", "userId", roleToUpdate, true);
        } catch(AuthException ex) {
            status = false;
        }
        Assert.assertTrue(status);
    }

    @Test
    public void testValidateRolesForUserExceptionExistingUser() {

        boolean status = false;
        try {
            CommonMockUp.getInstance().mockRoleServiceClientException();

            CommonMockUp.getInstance().mockRoleUtil();

            CommonMockUp.getInstance().mockKeyStoneServiceJson();

            List<RoleResponse> roleToUpdate = new ArrayList<RoleResponse>();
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setId("roleId");
            roleResponse.setName("roleName");
            roleToUpdate.add(roleResponse);
            instance.validateRolesForUser("authToken", "userId", roleToUpdate, false);
            instance.validateRolesForUser("authToken", "userId", roleToUpdate, true);
        } catch(AuthException ex) {
            status = true;
        }
        Assert.assertTrue(status);
    }

    @Test
    public void testValidateRolesForUserExceptionNewUser() {

        boolean status = false;
        try {
            CommonMockUp.getInstance().mockRoleServiceClientException();

            CommonMockUp.getInstance().mockRoleUtil();

            CommonMockUp.getInstance().mockKeyStoneServiceJson();

            List<RoleResponse> roleToUpdate = new ArrayList<RoleResponse>();
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setId("roleId");
            roleResponse.setName("roleName");
            roleToUpdate.add(roleResponse);
            instance.validateRolesForUser("authToken", "userId", roleToUpdate, true);
        } catch(AuthException ex) {
            status = true;
        }
        Assert.assertTrue(status);
    }

    @Test
    public void testGetRoleToAssignUpdate() {
        // Map<String, String> map = instance.getRoleToAssign();
        // Assert.assertSame("Ok", 0, map.size());

    }

    @Test
    public void testGetRoleToAssign() {
        Map<String, String> map = instance.getRoleToAssign();
        Assert.assertSame("Ok", 2, map.size());
    }

    @Test
    public void testGetRoleToDelete() {

        Map<String, String> map = instance.getRoleToDelete();
        Assert.assertSame("Ok", 2, map.size());

    }

    @Test
    public void testRoleResponseStatusOk() {

        Map<String, ClientResponse> roleResponse = new HashMap<String, ClientResponse>();
        ClientResponse client = new ClientResponse();
        client.setStatus(HttpServletResponse.SC_OK);
        instance.roleResponseStatus(roleResponse);
        // ssert.assertSame("Ok", 0, map.size());

    }

    @Test
    public void testRoleResponseStatus() {

        Map<String, ClientResponse> roleResponse = new HashMap<String, ClientResponse>();
        ClientResponse client = new ClientResponse();
        client.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        roleResponse.put("key", client);
        instance.roleResponseStatus(roleResponse);
        // ssert.assertSame("Ok", 0, map.size());

    }

    @Test
    public void testGetStatusCodeOk() {
        boolean status = true;
        try {
            instance.getStatusCode(HttpServletResponse.SC_OK, HttpServletResponse.SC_OK);
        } catch(AuthException e) {
            status = false;
        }
        Assert.assertTrue(status);
    }

    @Test
    public void testGetStatusCodeError() {
        boolean status = false;
        try {
            instance.getStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch(AuthException e) {
            status = true;
        }
        Assert.assertTrue(status);
    }

    @Test
    public void testGetStatusCodePartial() {
        boolean status = false;
        try {
            instance.getStatusCode(0, 0);
        } catch(AuthException e) {
            status = true;
        }
        Assert.assertTrue(status);
    }

    @Test
    public void testTruncateValues() {
        boolean status = true;
        try {
            instance.truncateValues();
        } catch(AuthException e) {
            status = false;
        }
        Assert.assertTrue(status);
    }

    @Test
    public void testRoleListToMap() {
        boolean status = true;
        try {
            Role listRoles = new Role();
            List<RoleResponse> roles = new ArrayList<RoleResponse>();
            RoleResponse resp = new RoleResponse();
            resp.setId("id");
            resp.setName("name");
            roles.add(resp);
            listRoles.setRoles(roles);

            Deencapsulation.invoke(instance, "roleListToMap", listRoles);
        } catch(AuthException ex) {
            status = false;
        }
        Assert.assertTrue(status);

    }

    @Test
    public void testValidateRolesForUserPrivate() {
        boolean status = true;

        try {
            Role role = new Role();
            List<RoleResponse> roles = new ArrayList<RoleResponse>();
            RoleResponse resp = new RoleResponse();
            resp.setId("id");
            resp.setName("name");
            roles.add(resp);
            role.setRoles(roles);

            Deencapsulation.invoke(instance, "validateRolesForUser", role, role, roles, true);
            Deencapsulation.invoke(instance, "validateRolesForUser", role, role, roles, false);
            Deencapsulation.invoke(instance, "validateRolesForUser", role, role, roleToAssign);
        } catch(AuthException ex) {
            status = false;
        }
        Assert.assertFalse(status);
    }

    @Test
    public void testValidateForDuplicacyRoles() {
        boolean status = true;

        try {
            Deencapsulation.invoke(instance, "validateForDuplicacyRoles", roleToAssign, roleToAssign);
        } catch(AuthException ex) {
            status = false;
        }
        Assert.assertFalse(status);
    }
}
