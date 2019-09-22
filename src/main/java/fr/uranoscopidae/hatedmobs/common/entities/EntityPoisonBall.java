package fr.uranoscopidae.hatedmobs.common.entities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPoisonBall extends ThrowableEntity
{
    public EntityPoisonBall(World world, LivingEntity entity)
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
        if(result.hitInfo != null)
        {
            if(result.hitInfo instanceof EntityGiantSpider)
            {
                return;
            }
            if(result.hitInfo instanceof LivingEntity)
            {
                LivingEntity entity = (LivingEntity) result.hitInfo;
                entity.addPotionEffect(new Effect(Potion.getPotionFromResourceLocation("poison"), 20*60, 0));
                ((LivingEntity) result.hitInfo).attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.5f);
            }
        }

        if(result.typeOfHit != RayTraceResult.Type.MISS)
        {
            setDead();
        }
    }
}
