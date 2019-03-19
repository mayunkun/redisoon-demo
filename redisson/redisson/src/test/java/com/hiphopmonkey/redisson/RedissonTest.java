package com.hiphopmonkey.redisson;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedissonApplication.class)
public class RedissonTest {
	
    @Autowired
    private RedissonClient redissonClient;
    
    @Test
    public void set() throws InterruptedException {
    	for(int i=0;i<10;i++) {
    		Thread.sleep(100);
        	new Thread(()->{
        		RLock lock = redissonClient.getLock("anyLock");
        		try{
        			// 1. 最常见的使用方法
        			//lock.lock();
        			// 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
        			//lock.lock(10, TimeUnit.SECONDS);
        			// 3. 尝试加锁，最多等待3秒，上锁以后10秒自动解锁
        			boolean res = lock.tryLock(3, 10, TimeUnit.SECONDS);
        			if(res){ //成功
        			// do your business
        				System.out.println(">>>>>>>>>>>>"+Thread.currentThread().getName());
        			}else {
        				System.out.println("》》》》》》》》》》》》未持有锁" +Thread.currentThread().getName());
        			}
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		} finally {
        			if(lock.isLocked()) {
        				lock.unlock();
        			}
        		}
        		
        	}).start();
    	}
    }

}
