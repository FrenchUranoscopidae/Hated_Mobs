package fr.uranoscopidae.hatedmobs.common.entities.entityai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class EntityAICloseMeleeAttack extends EntityAIAttackMelee
{
    public EntityAICloseMeleeAttack(EntityCreature creature, double speedIn, boolean useLongMemory)
    {
        super(creature, speedIn, useLongMemory);
    }

    @Override
    protected double getAttackReachSqr(EntityLivingBase attackTarget)
    {
        return 4 + attackTarget.width;
    }
}
