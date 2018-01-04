package com.blackfat.open.platform.interceptor;

import com.blackfat.common.utils.web.BaseRequest;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/1/4-11:31
 */

@Aspect
@Component
public class ReqNoDrcAspect {

    private static Logger logger = LoggerFactory.getLogger(ReqNoDrcAspect.class);


    public ReqNoDrcAspect() {
        logger.info("ReqNoDrcAspect---------------->");
    }

    // 使用map代替缓存
    private static Map<String, String> cache = Maps.newHashMap();

    @Pointcut("@annotation(com.blackfat.open.platform.interceptor.CheckReqNo)")
    public void checkRepeat() {

    }

    @Before(value = "checkRepeat()")
    public void before(JoinPoint joinPoint) throws Exception {
        BaseRequest request;
        request = getBaseRequest(joinPoint);
        if (request != null) {
            final String reqNo = request.getReqNo();
            if (StringUtils.isEmpty(reqNo)) {
                throw new RuntimeException("reqNo不能为空");
            } else {

                String tempReqNo = cache.get(reqNo);
                logger.info("tempReqNo=" + tempReqNo);
                if ((StringUtils.isEmpty(tempReqNo))) {
                    cache.put(reqNo, reqNo);
                } else {
                    throw new RuntimeException("请求号重复,reqNo=" + reqNo);
                }

            }
        }

    }

    public static BaseRequest getBaseRequest(JoinPoint joinPoint) throws Exception {
        BaseRequest returnRequest = null;
        Object[] arguments = joinPoint.getArgs();
        if (arguments != null && arguments.length > 0) {
            returnRequest = (BaseRequest) arguments[0];
        }
        return returnRequest;
    }


}
