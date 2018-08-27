package fr.uranoscopidae.hatedmobs.common.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPoisonBall extends EntityThrowable
{
    public EntityPoisonBall(World world, EntityLivingBase entity)
    {
        super(world, entity);
    }

    public EntityPoisonBall(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityPoisonBall(World worldIn)
    {
        super(worldIn);
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if(result.entityHit != null)
        {
            if(result.entityHit instanceof EntityGiantSpider)
            {
                return;
            }
            if(result.entityHit instanceof EntityLivingBase)
            {
                EntityLivingBase entity = (EntityLivingBase)result.entityHit;
                entity.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("poison"), 20*60, 0));
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.5f);
            }
        }

        if(result.typeOfHit != RayTraceResult.Type.MISS)
        {
            setDead();
        }
    }
}
