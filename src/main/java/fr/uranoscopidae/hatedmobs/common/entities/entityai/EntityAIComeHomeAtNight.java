package fr.uranoscopidae.hatedmobs.common.entities.entityai;

import fr.uranoscopidae.hatedmobs.common.entities.EntitySilkSpider;
import net.minecraft.entity.ai.goal.Goal;

public class EntityAIComeHomeAtNight extends Goal
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
        entity.getNavigator().tryMoveToXYZ(entity.getHomePos().getX(), entity.getHomePos().getY(), entity.getHomePos().getZ(), 0.5);
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return !entity.world.isDaytime() && !entity.getNavigator().noPath();
    }
}
