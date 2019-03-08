package fr.uranoscopidae.hatedmobs.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityRedAnt extends EntityAnimal
{
    public EntityRedAnt(World worldIn)
    {
        super(worldIn);
        setSize(0.75f, 0.5f);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    public void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(2, new EntityAILookIdle(this));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1f, false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityMob.class, true));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag)
        {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

}
