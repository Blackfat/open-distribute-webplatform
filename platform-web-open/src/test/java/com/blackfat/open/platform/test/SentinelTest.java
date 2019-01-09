package com.blackfat.open.platform.test;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/12/29-11:24
 */
public class SentinelTest {

    @Test
    public void test1(){
        initFlowRules();
        while (true) {
            Entry entry = null;
            try {
                entry = SphU.entry("HelloWorld");
                System.out.println("hello world");
            } catch (BlockException e1) {
                System.out.println("block!");
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }

    /**
     * resource:资源名
     * count:限流阀值
     * grade：限流阈值类型，是按照 QPS 还是线程数
     * limitApp：是否根据调用者来限流
     * controlBehavior：发生拦截后的流量整形和控制策略（直接拒绝 / 排队等待 / 慢启动模式）
     * strategy:判断的根据是资源自身，还是根据其它资源 (refResource)，还是根据链路入口 (refResource)
     */
    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
