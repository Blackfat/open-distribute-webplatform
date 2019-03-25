package com.blackfat.common.distributed.lock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;

/**
 * @author wangfeiyang
 * @desc
 * @create 2019/3/25-10:26
 */
public class RedisLockTest {

    @Before
    public void setBefore(){
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private RedisLock redisLock;


    @Test
    public void tryLockTest(){
        Mockito.when(redisLock.tryLock(Mockito.anyString(),Mockito.anyString(),Mockito.anyInt())).thenReturn(true);
        boolean result = redisLock.tryLock("test", "test", 1);
        assertTrue(result);
        Mockito.verify(redisLock,Mockito.times(1))
                .tryLock(Mockito.anyString(),Mockito.anyString(),Mockito.anyInt());
    }
}
