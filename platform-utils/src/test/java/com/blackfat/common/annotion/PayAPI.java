package com.blackfat.common.annotion;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-05-11 09:33
 * @since 1.0-SNAPSHOT
 */
@BankAPI(url = "/bank/pay", desc = "支付接口")
@Data
public class PayAPI extends AbstractAPI {
    @BankAPIField(order = 1, type = "N", length = 20)
    private long userId;
    @BankAPIField(order = 2, type = "M", length = 10)
    private BigDecimal amount;
}
