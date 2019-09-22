package fr.uranoscopidae.hatedmobs.common.entities;

import fr.uranoscopidae.hatedmobs.common.entities.entityai.EntityAICloseMeleeAttack;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EntityScorpion extends MobEntity
{
    public EntityScorpion(World worldIn)
    {
        super(worldIn);
        setSize(2f, 1f);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(5, new EntityAICloseMeleeAttack(this, 0.4, false));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, RabbitEntity.class, true));
    }

    public CreatureAttribute getCreatureAttribute() { return CreatureAttribute.ARTHROPOD; }
}