package fr.uranoscopidae.hatedmobs.common.entities;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.entityai.EntityAICloseMeleeAttack;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityGiantSpider extends MobEntity implements IRangedAttackMob
{
    public EntityGiantSpider(World worldIn)
    {
        super(worldIn);
        setSize(2, 1.25f);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(5, new EntityAICloseMeleeAttack(this, 0.4, false));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.addGoal(5, new EntityAIAttackRanged(this, 0.5, 40, 10f));
    }

    public CreatureAttribute getCreatureAttribute() { return CreatureAttribute.ARTHROPOD; }

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
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor)
    {
        double accX = target.posX - posX;
        double accY = (target.posY + getEyeHeight()) - (posY + getEyeHeight());
        double accZ = target.posZ - posZ;

        EntityPoisonBall ball = new EntityPoisonBall(world, this);
        ball.setPosition(posX, posY + getEyeHeight(), posZ);
        ball.shoot(accX, accY, accZ, 1.2f, 10f);
        world.addEntity(ball);
    }

    @Override
    protected boolean processInteract(PlayerEntity player, Hand hand)
    {
        if(player.getHeldItemMainhand().getItem() == HatedMobs.SPIDER_CANDY)
        {
            ItemStack itemstack = player.getHeldItem(hand);

            if(isAlive() && !world.isRemote)
            {
                if (!player.abilities.isCreativeMode)
                {
                    itemstack.shrink(1);
                }

                EntityTamedGiantSpider spider = new EntityTamedGiantSpider(world);
                spider.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
                spider.setTamedBy(player);
                world.addEntity(spider);
                setDead();
                return true;
            }
        }
        return super.processInteract(player, hand);
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {

    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected float getSoundPitch()
    {
        return 0.5f;
    }

    public void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return new ResourceLocation(HatedMobs.MODID, "giant_spider");
    }
}
