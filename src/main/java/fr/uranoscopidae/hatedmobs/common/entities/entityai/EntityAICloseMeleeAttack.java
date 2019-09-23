package fr.uranoscopidae.hatedmobs.common.entities.entityai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;

public class EntityAICloseMeleeAttack extends EntityAIAttackMelee
{
    public EntityAICloseMeleeAttack(CreatureEntity creature, double speedIn, boolean useLongMemory)
    {
        super(creature, speedIn, useLongMemory);
    }

    @Override
    protected double getAttackReachSqr(LivingEntity attackTarget)
    {
        return 4 + attackTarget.width;
    }
}
