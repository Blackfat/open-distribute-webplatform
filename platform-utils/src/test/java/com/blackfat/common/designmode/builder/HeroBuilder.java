package com.blackfat.common.designmode.builder;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/10-14:26
 */
public class HeroBuilder  extends  ActorBuilder{

    public  void buildType()
    {
        actor.setType("英雄");
    }
    public  void buildSex()
    {
        actor.setSex("男");
    }
    public  void buildFace()
    {
        actor.setFace("英俊");
    }
    public  void buildCostume()
    {
        actor.setCostume("盔甲");
    }
    public  void buildHairstyle()
    {
        actor.setHairstyle("飘逸");
    }
}
