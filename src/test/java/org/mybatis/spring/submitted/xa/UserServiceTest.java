/*
 *    Copyright 2010-2013 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.spring.submitted.xa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:org/mybatis/spring/submitted/xa/applicationContext.xml")
public class UserServiceTest {
  
  @Autowired
  private UserService userService;
  
  @Test
  public void testCommit() {
    User user = new User(1, "Pocoyo");
    userService.saveWithNoFailure(user);
    Assert.assertTrue(userService.checkUserExists(user.getId()));
  }
  
  @Test
  public void testRollback() {
    User user = new User(2, "Pocoyo");
    try {
      userService.saveWithFailure(user);
    } catch (RuntimeException ignore) {
      // ignored
    }
    Assert.assertFalse(userService.checkUserExists(user.getId()));
  }

}
