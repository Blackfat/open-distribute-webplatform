package com.blackfat.common.designmode.builder;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/10-14:29
 */
public class ActorController {

    //逐步构建复杂产品对象
    public Actor construct(ActorBuilder ab)
    {
        Actor actor;
        ab.buildType();
        ab.buildSex();
        ab.buildFace();
        ab.buildCostume();
        ab.buildHairstyle();
        actor=ab.createActor();
        return actor;
    }
}
