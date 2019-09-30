package fr.uranoscopidae.hatedmobs.common.entities;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.entityai.EntityAICloseMeleeAttack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.swing.*;

public class EntityTamedGiantSpider extends TameableEntity implements IRangedAttackMob
{
    private boolean boosting;
    private int boostTime;
    private int totalBoostTime;
    private static final DataParameter<Integer> BOOST_TIME = EntityDataManager.<Integer>createKey(EntityTamedGiantSpider.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SADDLED = EntityDataManager.<Boolean>createKey(EntityTamedGiantSpider.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityTamedGiantSpider.class, DataSerializers.BYTE);

    public EntityTamedGiantSpider(World worldIn)
    {
        super(worldIn);
        setSize(2, 1.25f);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(SADDLED, Boolean.valueOf(false));
        this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
    }

    public void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
    }

    protected void registerGoals()
    {
        this.sitGoal = new SitGoal(this);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, this.sitGoal);
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, false, new Class[0]));
        this.goalSelector.addGoal(5, new EntityAICloseMeleeAttack(this, 0.4, false));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        //this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, true, new Class[0]);
        this.goalSelector.addGoal(5, new EntityAIAttackRanged(this, 0.5, 40, 10f));
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        float f = (float)this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
        int i = 0;

        if (entityIn instanceof LivingEntity)
        {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((LivingEntity)entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0 && entityIn instanceof LivingEntity)
            {
                ((LivingEntity)entityIn).knockBack(this, (float)i * 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                entityIn.setFire(j * 4);
            }

            if (entityIn instanceof PlayerEntity)
            {
                PlayerEntity entityplayer = (PlayerEntity)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer))
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable)
    {
        return null;
    }

    @Override
    public void updatePassenger(Entity passenger)
    {
        if(this.isPassenger(passenger))
        {
            float distance = -0.4f;
            double angle = Math.toRadians(180 - passenger.rotationYaw);
            double x = this.posX + Math.sin(angle) * distance;
            double z = this.posZ + Math.cos(angle) * distance;
            passenger.setPosition(x, this.posY + this.getMountedYOffset() + passenger.getYOffset(), z);
        }
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

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn)
    {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);

    }

    public void setInWeb()
    {
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer)
    {
        return false;
    }

    @Override
    protected float getSoundPitch()
    {
        return 0.5f;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand)
    {
        if (!super.processInteract(player, hand))
        {
            ItemStack itemstack = player.getHeldItem(hand);

            if (itemstack.getItem() == Items.NAME_TAG)
            {
                itemstack.interactWithEntity(player, this, hand);
                return true;
            }
            else if (this.getSaddled() && !this.isBeingRidden() && !player.isSneaking())
            {
                if (!this.world.isRemote)
                {
                    player.startRiding(this);
                }

                return true;
            }
            else if (itemstack.getItem() == Items.SADDLE)
            {
                if (this instanceof EntityTamedGiantSpider)
                {
                    EntityTamedGiantSpider entityTamedGiantSpider = this;

                    if (!entityTamedGiantSpider.getSaddled() && !entityTamedGiantSpider.isChild())
                    {
                        entityTamedGiantSpider.setSaddled(true);
                        entityTamedGiantSpider.world.playSound(player, entityTamedGiantSpider.posX, entityTamedGiantSpider.posY, entityTamedGiantSpider.posZ, SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                        itemstack.shrink(1);
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                if(world.isRemote)
                    return true;
                if(player.isSneaking())
                {
                    this.sitGoal.setSitting(!this.isSitting());
                    this.isJumping = false;
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity) null);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return true;
        }
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

    @Override
    public void tick()
    {
        super.tick();
        this.setBesideClimbableBlock(this.collidedHorizontally);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }

    public boolean getSaddled()
    {
        return ((Boolean)this.dataManager.get(SADDLED)).booleanValue();
    }

    public void write(CompoundNBT compound)
    {
        super.write(compound);
        compound.putBoolean("Saddle", this.getSaddled());
    }

    public void read(CompoundNBT compound)
    {
        super.read(compound);
        this.setSaddled(compound.getBoolean("Saddle"));
    }

    public void setSaddled(boolean saddled)
    {
        if (saddled)
        {
            this.dataManager.set(SADDLED, Boolean.valueOf(true));
        }
        else
        {
            this.dataManager.set(SADDLED, Boolean.valueOf(false));
        }
    }

    @Nullable
    public Entity getControllingPassenger()
    {
        return this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return new ResourceLocation(HatedMobs.MODID, "giant_spider");
    }

    public boolean canBeSteered()
    {
        return this.getControllingPassenger() instanceof EntityLivingBase;
    }

    public void travel(float strafe, float vertical, float forward)
    {
        Entity entity = this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);

        if (this.isBeingRidden() && this.canBeSteered() && dataManager.get(SADDLED))
        {
            this.rotationYaw = entity.rotationYaw;
            this.prevRotationYaw = this.rotationYaw;
            this.rotationPitch = entity.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.rotationYaw;
            this.stepHeight = 1.0F;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

            if (this.boosting && this.boostTime++ > this.totalBoostTime)
            {
                this.boosting = false;
            }

            if (this.canPassengerSteer() && entity instanceof LivingEntity)
            {
                float maxSpeed = 0.50f;
                float passengerForward = ((LivingEntity)entity).moveForward * maxSpeed;
                float passengerStrafe = ((LivingEntity)entity).moveStrafing * maxSpeed;


                double speed = Math.sqrt(passengerForward * passengerForward + passengerStrafe * passengerStrafe);

                this.setAIMoveSpeed((float)speed);
                super.travel(passengerStrafe, vertical, passengerForward);
            }
            else
            {
                this.motionX = 0.0D;
                this.motionY = 0.0D;
                this.motionZ = 0.0D;
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }

            this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        }
        else
        {
            this.stepHeight = 0.5F;
            this.jumpMovementFactor = 0.02F;
            super.travel(strafe, vertical, forward);
        }
    }

    public boolean boost()
    {
        if (this.boosting)
        {
            return false;
        }
        else
        {
            this.boosting = true;
            this.boostTime = 0;
            this.totalBoostTime = this.getRNG().nextInt(841) + 140;
            this.getDataManager().set(BOOST_TIME, Integer.valueOf(this.totalBoostTime));
            return true;
        }
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
    public void setSwingingArms(boolean swingingArms)
    {

    }
}