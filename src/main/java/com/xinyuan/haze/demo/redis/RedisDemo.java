package com.xinyuan.haze.demo.redis;

import com.xinyuan.haze.system.entity.User;
import com.xinyuan.haze.system.utils.Sex;
import com.xinyuan.haze.system.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class RedisDemo {

    @Autowired
    protected RedisTemplate redisTemplate;

    public void saveUser(final User user) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    redisTemplate.execute(new RedisCallback<Object>() {
                        @Override
                        public Object doInRedis(final RedisConnection connection) throws DataAccessException {
                            System.out.println("开始执行" + Thread.currentThread().getName());
                            for (int j = index * 200000; j < (index + 1) * 200000; j++) {
                                User u = new User();
                                u.setId("zhangsan" + j);
                                u.setName("张三" + j);
                                u.setEmail("张三" + j + "@163.com");
                                u.setLoginName("zhangsan" + j);
                                u.setSex(Sex.F);
                                u.setStatus(Status.ENABLE);
                                connection.set(redisTemplate.getStringSerializer().serialize("user.uid." + u.getId()),
                                        redisTemplate.getStringSerializer().serialize(u.toString()));
                            }
                            System.out.println("线程" + Thread.currentThread().getName() + "执行完毕,执行时间点" + System.currentTimeMillis());
                            return null;
                        }
                    });

                }
            });
        }

    }

    public Object getUser(final String id) {
        return redisTemplate.execute(new RedisCallback<User>() {
            @Override
            public User doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize("user.uid." + id);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String name = (String) redisTemplate.getStringSerializer().deserialize(value);
                    User user = new User();
                    user.setName(name);
                    //user.setId(id);
                    return user;
                }
                return null;
            }
        });
    }

}
