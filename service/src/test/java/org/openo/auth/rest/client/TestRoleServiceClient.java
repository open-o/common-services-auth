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

package org.openo.auth.rest.client;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.openo.auth.constant.Constant;
import org.openo.auth.entity.ClientResponse;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version
 */
public class TestRoleServiceClient {

    RoleServiceClient client = RoleServiceClient.getInstance();

    /**
     * Test method for {@link org.openo.auth.rest.client.RoleServiceClient#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        Assert.assertNotNull(client);
    }

    /**
     * Test method for
     * {@link org.openo.auth.rest.client.RoleServiceClient#listAllRoles(java.lang.String)}.
     */
    @Test
    public void testListAllRoles() {
        WebClientMock.getInstance().mockWebClient();
        ClientCommonMock.getInstance().mockClientCommunicationUtil();
        ClientResponse resp = client.listAllRoles("authToken");
        Assert.assertNotNull(resp);
    }

    /**
     * Test method for
     * {@link org.openo.auth.rest.client.RoleServiceClient#listRolesForUser(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testListRolesForUser() {
        WebClientMock.getInstance().mockWebClient();
        ClientCommonMock.getInstance().mockClientCommunicationUtil();
        ClientCommonMock.getInstance().mockRoleServiceClient();
        ClientResponse resp = client.listRolesForUser("authToken", "userId");
        Assert.assertNotNull(resp);
    }

    /**
     * Test method for
     * {@link org.openo.auth.rest.client.RoleServiceClient#updateRolesToUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testUpdateRolesToUserPut() {
        WebClientMock.getInstance().mockWebClient();
        int resp = client.updateRolesToUser("authToken", "projectId", "userId", "roleId", Constant.TYPE_PUT);
        Assert.assertNotNull(resp);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, resp);
    }

    @Test
    public void testUpdateRolesToUserDelete() {
        WebClientMock.getInstance().mockWebClient();
        int resp = client.updateRolesToUser("authToken", "projectId", "userId", "roleId", Constant.TYPE_DELETE);
        Assert.assertNotNull(resp);
        Assert.assertEquals("ok", HttpServletResponse.SC_OK, resp);
    }
    /**
     * Test method for
     * {@link org.openo.auth.rest.client.RoleServiceClient#removeRolesFromUser(java.lang.String, java.lang.String, java.util.Map)}.
     */
    @Test
    public void testRemoveRolesFromUser() {
        WebClientMock.getInstance().mockWebClient();
        ClientCommonMock.getInstance().mockClientCommunicationUtil();
        ClientCommonMock.getInstance().mockRoleServiceClient();

        Map<String, String> map = new HashMap<String, String>();
        map.put("dummy-1", "dummy-2");
        Map<String, ClientResponse> resp = client.removeRolesFromUser("authToken", "userId", map);

        Assert.assertNotNull(resp);
        Assert.assertNotNull(resp.get("dummy-1"));
    }

    /**
     * Test method for
     * {@link org.openo.auth.rest.client.RoleServiceClient#assignRolesToUser(java.lang.String, java.lang.String, java.util.Map)}.
     */
    @Test
    public void testAssignRolesToUser() {
        WebClientMock.getInstance().mockWebClient();
        ClientCommonMock.getInstance().mockClientCommunicationUtil();
        ClientCommonMock.getInstance().mockRoleServiceClient();

        Map<String, String> map = new HashMap<String, String>();
        map.put("dummy-1", "dummy-2");
        Map<String, ClientResponse> resp = client.assignRolesToUser("authToken", "userId", map);

        Assert.assertNotNull(resp);
        Assert.assertNotNull(resp.get("dummy-1"));
    }

    /**
     * Test method for
     * {@link org.openo.auth.rest.client.RoleServiceClient#getProjectDetails(java.lang.String)}.
     */
    @Test
    public void testGetProjectDetails() {
        WebClientMock.getInstance().mockWebClient();
        ClientCommonMock.getInstance().mockClientCommunicationUtil();
        ClientCommonMock.getInstance().mockRoleServiceClient();

        ClientResponse resp = client.getProjectDetails("authToken");

        Assert.assertNotNull(resp);
    }

}
