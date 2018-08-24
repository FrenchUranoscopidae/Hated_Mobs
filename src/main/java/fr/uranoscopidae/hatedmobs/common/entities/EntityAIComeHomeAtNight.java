package fr.uranoscopidae.hatedmobs.common.entities;

import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIComeHomeAtNight extends EntityAIBase
{
    private EntitySilkSpider entity;

    public EntityAIComeHomeAtNight(EntitySilkSpider  entity)
    {
        this.entity = entity;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        return !entity.world.isDaytime();
    }

    @Override
    public void startExecuting()
    {
        entity.getNavigator().tryMoveToXYZ(entity.getHomePos().getX()+0.5, entity.getHomePos().getY(), entity.getHomePos().getZ()+0.5, 0.5);
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return !entity.world.isDaytime() && !entity.getNavigator().noPath();
    }
}
