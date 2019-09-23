package fr.uranoscopidae.hatedmobs.common.entities.entityai;

import com.google.common.base.Optional;
import fr.uranoscopidae.hatedmobs.common.entities.EntityMosquito;
import fr.uranoscopidae.hatedmobs.common.entities.EntityToad;
import fr.uranoscopidae.hatedmobs.common.entities.EntityWasp;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;


public class EntityAIMlemAttack extends Goal
{
    EntityToad toad;
    int timer;

    public EntityAIMlemAttack(EntityToad toad)
    {
        this.toad = toad;
    }

    @Override
    public boolean shouldExecute()
    {
        return this.toad.getAttackTarget() != null;
    }

    @Override
    public void startExecuting()
    {
        super.startExecuting();
        timer = 0;
    }

    @Override
    public void resetTask()
    {
        toad.removeTarget();
    }

    @Override
    public void updateTask()
    {
        LivingEntity target = toad.getAttackTarget();

        if(target != null && target.isAlive() && toad.canEntityBeSeen(target))
        {
            timer++;

            toad.getLookController().setLookPosition(target.posX + 0.5f, target.posY + 0.5f, target.posZ + 0.5f, (float)toad.getHorizontalFaceSpeed(), (float)toad.getVerticalFaceSpeed());

            if(timer == 10)
            {
                toad.target(target.getPosition());
            }
            if(timer > 10 && timer <= 15)
            {
                Optional<BlockPos> pos = toad.getTongueTarget();

                if(pos.isPresent())
                {
                    BlockPos tonguePos = pos.get();
                    AxisAlignedBB box = new AxisAlignedBB(tonguePos);
                    List<LivingEntity> list = toad.world.getEntitiesWithinAABB(LivingEntity.class, box, e->e instanceof EntityWasp || e instanceof EntityMosquito);

                    if(!((List) list).isEmpty())
                    {
                        LivingEntity mlemTarget = list.get(0);
                        mlemTarget.attackEntityFrom(DamageSource.causeMobDamage(toad), 100f);
                        timer = -40;
                        toad.removeTarget();
                    }
                }
            }
            if(timer > 15)
            {
                timer = -40;
                toad.removeTarget();
            }
        }
        else if(timer > 0)
        {
            timer--;
            toad.removeTarget();
        }
    }
}
