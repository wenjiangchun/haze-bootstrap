package com.xinyuan.haze.soa.jms;

import com.xinyuan.haze.system.entity.User;
import com.xinyuan.haze.system.service.UserService;
import com.xinyuan.haze.system.utils.Sex;
import com.xinyuan.haze.system.utils.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

/**
 * Created by sofar on 15-12-4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext-test.xml"})
@ActiveProfiles("test")
public class MessageSenderTest {

    @Autowired
    private MessageSender messageSender;
    @Autowired
    private UserService userService;
    @Test
    public void testSendMessage() throws Exception {
/*JmsMessage message = new JmsMessage();
        message.setKey("testKey");
        message.setContent("JMS内容");
        messageSender.sendMessage(message);*/
        //messageSender.receiveMessage();

        User user = new User();
        user.setLoginName("zhangsan");
        user.setPassword("666666");
        user.setSex(Sex.M);
        user.setStatus(Status.E);
        userService.saveOrUpdate(user);

        String jql = "from User where name=:name";
        userService.findByJql(jql, new HashMap<String, Object>());
    }
}