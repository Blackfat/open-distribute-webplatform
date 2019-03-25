package com.blackfat.common.distributed.limit;

import com.blackfat.common.distributed.handler.aspect.CommonAspect;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;

/**
 * @author wangfeiyang
 * @desc
 * @create 2019/3/25-10:20
 */
public class RedisLimitTest {

    /*
   * @InjectMocks
   * 创建一个实例，其余用@Mock（或@Spy）注解创建的mock将被注入到用该实例中
   * */
    @InjectMocks
    CommonAspect commonAspect;


    @Mock
    // 创建一个Mock
    private RedisLimit redisLimit;

    @Before
    // 注解生效初始化
    public void setBefore(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void limitTest(){
        Mockito.when(redisLimit.limit()).thenReturn(true);
        boolean limit = redisLimit.limit();
        assertTrue(limit);
        Mockito.verify(redisLimit,Mockito.times(1)).limit();
    }

    @Test
    public void commonLimitTest(){
        Mockito.when(redisLimit.limit()).thenReturn(false);
        commonAspect.doBefore(null);
        Mockito.verify(redisLimit,Mockito.times(1)).limit();
    }

}
