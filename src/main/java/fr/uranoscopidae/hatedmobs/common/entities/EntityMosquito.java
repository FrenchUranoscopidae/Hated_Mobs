package fr.uranoscopidae.hatedmobs.common.entities;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.worldwrappers.IBlockMapper;
import fr.uranoscopidae.hatedmobs.common.items.ItemSwatter;
import fr.uranoscopidae.hatedmobs.common.worldwrappers.MosquitoWorldWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityMosquito extends MobEntity
{
    public EntityMosquito(World world)
    {
        super(IBlockMapper.wrap(world, MosquitoWorldWrapper.INSTANCE));
        setSize(2f/16f, 2f/16f);
        this.experienceValue = 1;
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

    public void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.4D);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        Entity attacker = source.getTrueSource();

        if(attacker instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) attacker;
            ItemStack item = player.getHeldItemMainhand();

            return !(item.getItem() instanceof ItemSwatter);
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new WaterAvoidingRandomFlyingGoal(this, 0.30));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1, false));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public boolean isPreventingPlayerRest(PlayerEntity playerIn)
    {
        return false;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        return false;
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos)
    {

    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return new ResourceLocation(HatedMobs.MODID, "mosquito");
    }
}
