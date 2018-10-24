package com.blackfat.open.platform.controller;

import com.blackfat.api.activity.service.OrderServiceApi;
import com.blackfat.api.activity.service.StockServiceApi;
import com.crossoverjie.distributed.annotation.CommonLimit;
import com.crossoverjie.distributed.annotation.ControllerLimit;
import com.crossoverjie.distributed.annotation.SpringControllerLimit;
import com.wordnik.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/23-16:41
 */
@Controller
@RequestMapping(value = "/activity")
@Api(value = "/activity", description = "活动")
public class ActivityController
{

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private StockServiceApi stockService;

    @Autowired
    private OrderServiceApi orderService;

    /**
     * 乐观锁更新库存
     * @param sid
     * @return
     */
    @RequestMapping(value = "/createOptimisticOrder/{sid}",method = RequestMethod.GET)
    @ResponseBody
    public String createOptimisticOrder(@PathVariable int sid) {
        logger.info("sid=[{}]", sid);
        int id = 0;
        try {
            id = orderService.createOptimisticOrder(sid);
        } catch (Exception e) {
            logger.error("Exception",e);
        }
        return String.valueOf(id);
    }

    /**
     * 乐观锁更新库存 限流
     * @param sid
     * @return
     */
    @SpringControllerLimit
    @RequestMapping(value = "/createOptimisticLimitOrder/{sid}",method = RequestMethod.GET)
    @ResponseBody
    public String createOptimisticLimitOrder(@PathVariable int sid) {
        logger.info("sid=[{}]", sid);
        int id = 0;
        try {
            id = orderService.createOptimisticOrder(sid);
        } catch (Exception e) {
            logger.error("Exception",e);
        }
        return String.valueOf(id);
    }

}
