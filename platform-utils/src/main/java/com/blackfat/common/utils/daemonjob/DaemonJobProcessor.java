package com.blackfat.common.utils.daemonjob;

import com.blackfat.common.utils.base.Maps;
import com.blackfat.common.utils.base.Schedulers;
import com.blackfat.common.utils.spring.SpringInvokes;
import com.blackfat.common.utils.spring.Springs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:28
 * @since 1.0-SNAPSHOT
 */
@Slf4j
@Component
public class DaemonJobProcessor implements BeanPostProcessor {
    Map<String, DaemonJobBO> beanMap = new HashMap<>();

    AnnotationMethodFilter daemonJobMethodFilter = new AnnotationMethodFilter(DaemonJob.class);

    public static String key(String bean, String name){
        return bean+"."+name;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String bn) throws BeansException {
        ReflectionUtils.doWithMethods(bean.getClass(), (m)->{
            DaemonJob dj = m.getAnnotation(DaemonJob.class);
            Maps.putIfAbsent(beanMap, key(bn, m.getName()), ()->new DaemonJobBO(bn, bean, m, dj));
        }, daemonJobMethodFilter);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String bn) throws BeansException {
        return null;
    }

    /**
     *  基于Spring上下文的事件机制，在上下文初始化完成后将收集的bean及method加入调度器
     */
    public void doOnStartedEvent(){
        long defDelay = Springs.getConfig().getLongProperty("base.daemonJob.defDelay", 900L);
        long minDelay = Springs.getConfig().getLongProperty("base.daemonJob.minDelay", 60L);
        List<DaemonJobBO> list = new ArrayList<>();
        beanMap.forEach((k, bo)->{
            long delay = bo.getAnnotation().delay();
            if(delay < 1){ delay = defDelay; }
            if(delay < minDelay){ delay =minDelay; }
            if(!bo.getAnnotation().syncBoot() && delay==defDelay){
                list.add(bo);
            }else{
                long initialDelay = 0;
                if(bo.getAnnotation().syncBoot()){
                    initialDelay = delay;
                    invoke(bo);
                }
                Schedulers.get().scheduleWithFixedDelay(()->invoke(bo), initialDelay, delay, TimeUnit.SECONDS);
            }
        });
        if(list.size() > 0){
            Schedulers.get().scheduleWithFixedDelay(()->{ list.forEach(bo->invoke(bo)); }, 0, defDelay, TimeUnit.SECONDS);
        }
    }

    /**
     * 具体的执行交给SpringInvokes模块进行
     */
    void invoke(DaemonJobBO bo){
        SpringInvokes.invoke(bo.getBean(), bo.getBeanRef(), bo.getMethod(), null, "DaemonJob");
    }

}
