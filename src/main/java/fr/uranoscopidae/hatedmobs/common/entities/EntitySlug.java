package fr.uranoscopidae.hatedmobs.common.entities;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.entityai.EntityAINomNomCultures;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntitySlug extends MobEntity
{
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntitySilkSpider.class, DataSerializers.BYTE);

    public EntitySlug(World worldIn)
    {
        super(worldIn);
        setSize(0.75f, 0.5f);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.2D));
        this.goalSelector.addGoal(6, new EntityAINomNomCultures(this, 0.2D));
    }

    @Override
    public void tick()
    {
        super.tick();
        if (!this.world.isRemote)
        {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    public boolean wantsMoreFood()
    {
        return true;
    }

    protected PathNavigator createNavigator(World worldIn)
    {
        return new ClimberPathNavigator(this, worldIn);
    }
    public boolean isBesideClimbableBlock()
    {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean climbing)
    {
        byte b0 = this.dataManager.get(CLIMBING);

        if (climbing)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 = (byte)(b0 & -2);
        }

        this.dataManager.set(CLIMBING, Byte.valueOf(b0));
    }
    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return new ResourceLocation(HatedMobs.MODID, "slug");
    }
}
