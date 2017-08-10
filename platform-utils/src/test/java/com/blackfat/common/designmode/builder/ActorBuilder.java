package com.blackfat.common.designmode.builder;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/10-14:25
 */
public abstract  class ActorBuilder {


    protected  Actor actor = new Actor();

    public  abstract void buildType();
    public  abstract void buildSex();
    public  abstract void buildFace();
    public  abstract void buildCostume();
    public  abstract void buildHairstyle();

    //工厂方法，返回一个完整的游戏角色对象
    public Actor createActor()
    {
        return actor;
    }
}
