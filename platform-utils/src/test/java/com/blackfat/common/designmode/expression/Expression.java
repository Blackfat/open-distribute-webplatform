package com.blackfat.common.designmode.expression;

import java.util.Map;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-22 13:10
 * @since 1.0-SNAPSHOT
 */
public interface Expression {

    boolean interpret(Map<String, Long> stats);
}
