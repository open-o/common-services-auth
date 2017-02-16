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

package org.openo.auth.access;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.openo.auth.constant.Constant;
import org.openo.auth.entity.PolicyRights;
import org.openo.auth.entity.Rights;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version
 */
public class TestLoadRights {

    @Test
    public void testLoadConfigProperties() {
        CommonMockUp.getInstance().mockConfigUtil();
        List<PolicyRights> list = LoadRights.loadRights();
        for(PolicyRights p1 : list) {
            for(Rights right : p1.getRights()) {
                System.out.println("Action = " + right.getAction() + ", Method = " + right.getMethod()
                        + ", UriPattern = " + right.getUriPattern() + "");
            }
        }
    }


    public static void testLoadConfigPropertiess() {
        CommonMockUp.getInstance().mockConfigUtil();
        List<PolicyRights> list = LoadRights.loadRights();
        for(PolicyRights p1 : list) {
            for(Rights right : p1.getRights()) {
                System.out.println(right.getUriPattern() + "     " + right.getMethod());
                if(check(right.getUriPattern(), "auth", "users/create") && "POST".equalsIgnoreCase(right.getMethod())) {
                    System.out.print(right.getAction() + "====");
                }
            }
        }
    }

    public static boolean check(String inputUri, String input1, String input2) {
        Pattern pattern = Pattern.compile(
                Constant.REGEX_URI_OPENOAPI + input1 + Constant.REGEX_URI_VERSIONS + input2 + Constant.REGEX_URI_LAST);
        Matcher matcher = pattern.matcher(inputUri);
        return matcher.matches();
    }
}
