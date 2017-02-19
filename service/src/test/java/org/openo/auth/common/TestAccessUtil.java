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

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openo.auth.access.CommonMockUp;
import org.openo.auth.entity.RoleResponse;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version
 */
public class TestAccessUtil {

    private AccessUtil instance;

    @Before
    public void setUp() throws Exception {
        instance = AccessUtil.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    @Test
    public void testGetInstance() {
        Assert.assertNotNull(instance);

    }

    @Test
    public void testGetRoleWithRulesAll() {
        Map<String, List<String>> map = instance.getRoleWithRules("");
        Assert.assertEquals("ok", 1, map.size());
    }

    @Test
    public void testGetRoleWithRulesNone() {
        Map<String, List<String>> map = instance.getRoleWithRules("!");
        Assert.assertEquals("ok", 1, map.size());
    }

    @Test
    public void testGetRoleWithRulesAdmin() {
        Map<String, List<String>> map = instance.getRoleWithRules("role:admin");
        Assert.assertEquals("ok", 2, map.size());
    }

    @Test
    public void testGetRoleWithRulesNotAdmin() {
        Map<String, List<String>> map = instance.getRoleWithRules("role:!admin");
        Assert.assertEquals("ok", 2, map.size());
    }

    @Test
    public void testGetServiceName() {
        String[] array = instance.getServiceName("role:admin");
        Assert.assertEquals("ok", "admin", array[1]);

    }

    @Test
    public void testGetRuleFromCache() {
        CommonMockUp.getInstance().mockConfigUtil();
        String empty = instance.getRuleFromCache("", "");
        Assert.assertEquals("ok", "", empty);

    }

    @Test
    public void testActionHasRoleAll() {

        List<RoleResponse> roleListUser = new ArrayList<RoleResponse>();
        RoleResponse resp = new RoleResponse();
        resp.setId("id");
        resp.setName("name");
        roleListUser.add(resp);
        Map<String, List<String>> ruleRoleMap = new HashMap<String, List<String>>();
        List<String> rules = new ArrayList<String>();
        rules.add(StringUtils.EMPTY);
        ruleRoleMap.put(StringUtils.EMPTY, rules);
        ruleRoleMap.put("!", rules);
        ruleRoleMap.put("admin", rules);
        ruleRoleMap.put("!admin", rules);
        boolean status = instance.actionHasRole(roleListUser, ruleRoleMap);
        Assert.assertTrue(status);

    }

    @Test
    public void testActionHasRoleNone() {

        List<RoleResponse> roleListUser = new ArrayList<RoleResponse>();
        RoleResponse resp = new RoleResponse();
        resp.setId("id");
        resp.setName("name");
        roleListUser.add(resp);
        Map<String, List<String>> ruleRoleMap = new HashMap<String, List<String>>();
        List<String> rules = new ArrayList<String>();
        rules.add(StringUtils.EMPTY);
        rules.add("!");
        ruleRoleMap.put("!", rules);
        boolean status = instance.actionHasRole(roleListUser, ruleRoleMap);
        Assert.assertFalse(status);

    }

    @Test
    public void testActionHasRoleAdmin() {

        List<RoleResponse> roleListUser = new ArrayList<RoleResponse>();
        RoleResponse resp = new RoleResponse();
        resp.setId("id");
        resp.setName("name");
        roleListUser.add(resp);
        Map<String, List<String>> ruleRoleMap = new HashMap<String, List<String>>();
        List<String> rules = new ArrayList<String>();
        rules.add("admin");
        ruleRoleMap.put("OR", rules);
        boolean status = instance.actionHasRole(roleListUser, ruleRoleMap);
        Assert.assertFalse(status);
    }

    @Test
    public void testActionHasRoleNotAdmin() {

        List<RoleResponse> roleListUser = new ArrayList<RoleResponse>();
        RoleResponse resp = new RoleResponse();
        resp.setId("id");
        resp.setName("name");
        roleListUser.add(resp);
        Map<String, List<String>> ruleRoleMap = new HashMap<String, List<String>>();
        List<String> rules = new ArrayList<String>();
        rules.add(StringUtils.EMPTY);
        rules.add("!admin");
        ruleRoleMap.put("NOT", rules);
        boolean status = instance.actionHasRole(roleListUser, ruleRoleMap);
        Assert.assertFalse(status);
    }

}
