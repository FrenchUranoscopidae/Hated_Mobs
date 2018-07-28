package fr.uranoscopidae.mosquito.common;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntitySilkSpider extends EntityAnimal
{
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntitySilkSpider.class, DataSerializers.BYTE);

    public EntitySilkSpider(World worldIn)
    {
        super(worldIn);
        this.setSize(1.4F, 0.9F);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return null;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
    }

    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote)
        {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }


    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAISwimming(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if(rand.nextInt(20*60*2) == 0)
        {
            if(world.isAirBlock(getPosition()))
            {
                world.setBlockState(getPosition(), Blocks.WEB.getDefaultState());
            }
        }
    }

    protected PathNavigate createNavigator(World worldIn)
    {
        return new PathNavigateClimber(this, worldIn);
    }
    public boolean isBesideClimbableBlock()
    {
        return (((Byte)this.dataManager.get(CLIMBING)).byteValue() & 1) != 0;
    }

    /**
     * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
     * false.
     */
    public void setBesideClimbableBlock(boolean climbing)
    {
        byte b0 = ((Byte)this.dataManager.get(CLIMBING)).byteValue();

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

    public void setInWeb()
    {
    }
}
