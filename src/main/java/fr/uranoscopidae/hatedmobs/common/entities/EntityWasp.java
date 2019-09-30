package fr.uranoscopidae.hatedmobs.common.entities;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.entityai.EntityAICloseMeleeAttack;
import fr.uranoscopidae.hatedmobs.common.items.ItemSwatter;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityWasp extends MobEntity
{
    public EntityWasp(World worldIn)
    {
        super(worldIn);
        setSize(0.25f, 0.25f);
        this.moveController = new FlyingMovementController(this);
    }

    @Override
    protected PathNavigator createNavigator(World worldIn)
    {
        FlyingPathNavigator fly = new FlyingPathNavigator(this, worldIn);
        fly.setCanEnterDoors(true);
        fly.setCanSwim(true);
        return fly;
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new WaterAvoidingRandomFlyingGoal(this, 0.30));
        this.goalSelector.addGoal(5, new EntityAICloseMeleeAttack(this, 1.3f, false));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, true));
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource source)
    {
        Entity attacker = source.getTrueSource();

        if(attacker instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity)attacker;
            ItemStack item = player.getHeldItemMainhand();

            return !(item.getItem() instanceof ItemSwatter);
        }
        return super.isInvulnerable(source);
    }

    public void registerAttributes()
    {
        super.registerAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(1D);
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos)
    {

    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn)
    {
        return return 0.25f * getHeight();;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return new ResourceLocation(HatedMobs.MODID, "wasp");
    }
}
