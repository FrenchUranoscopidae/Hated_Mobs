package fr.uranoscopidae.hatedmobs.common.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityGiantSpider extends EntityMob implements IRangedAttackMob
{
    public EntityGiantSpider(World worldIn)
    {
        super(worldIn);
        setSize(2, 1.25f);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.5D));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 0.5, false));
        this.targetTasks.addTask(6, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.tasks.addTask(5, new EntityAIAttackRanged(this, 0.5, 40, 10f));
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

    public void setInWeb()
    {
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
        double accX = target.posX - posX;
        double accY = (target.posY + getEyeHeight()) - (posY + getEyeHeight());
        double accZ = target.posZ - posZ;

        EntityWitherSkull skull = new EntityWitherSkull(world, this, accX, accY, accZ);
        skull.setPosition(posX, posY + getEyeHeight(), posZ);
        world.spawnEntity(skull);
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {

    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    protected float getSoundPitch()
    {
        return 0.5f;
    }
}
