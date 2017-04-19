package com.xinyuan.haze.demo.redis;

import com.xinyuan.haze.system.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext-test.xml"})
@ActiveProfiles("test")
public class RedisDemoTest {

    @Autowired
    private RedisDemo redisDemo;

    @Test
    public void saveUser() throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("开始执行saveUser。。。当前时间点：" + System.currentTimeMillis());
        User u = new User();
        u.setId(UUID.randomUUID().toString());
        u.setName("张三" );
        redisDemo.saveUser(u);
        try {
            Thread.sleep(1000000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("共执行" + (endTime-startTime)/1000 + "秒");
    }

    public void getUser() throws Exception {
        redisDemo.getUser("aaa");
    }

}