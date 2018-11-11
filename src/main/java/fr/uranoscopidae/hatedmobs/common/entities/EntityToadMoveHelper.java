package fr.uranoscopidae.hatedmobs.common.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;

public class EntityToadMoveHelper extends EntityMoveHelper
{
    private float yRot;
    private int jumpDelay;
    private final EntityToad toad;
    private boolean isAggressive;

    public EntityToadMoveHelper(EntityToad toadIn)
    {
        super(toadIn);
        this.toad = toadIn;
        this.yRot = 180.0F * toadIn.rotationYaw / (float)Math.PI;
    }

    public void setDirection(float p_179920_1_, boolean p_179920_2_)
    {
        this.yRot = p_179920_1_;
        this.isAggressive = p_179920_2_;
    }

    public void setSpeed(double speedIn)
    {
        this.speed = speedIn;
        this.action = EntityMoveHelper.Action.MOVE_TO;
    }

    public void onUpdateMoveHelper()
    {
        this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.yRot, 90.0F);
        this.entity.rotationYawHead = this.entity.rotationYaw;
        this.entity.renderYawOffset = this.entity.rotationYaw;

        if (this.action != EntityMoveHelper.Action.MOVE_TO)
        {
            this.entity.setMoveForward(0.0F);
        }
        else
        {
            this.action = EntityMoveHelper.Action.WAIT;

            if (this.entity.onGround)
            {
                this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

                if (this.jumpDelay-- <= 0)
                {
                    this.jumpDelay = this.toad.getJumpDelay();

                    this.toad.getJumpHelper().setJumping();

                    this.toad.playSound(this.toad.getJumpSound(), this.toad.getSoundVolume(), ((this.toad.getRNG().nextFloat() - this.toad.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                }
                else
                {
                    this.toad.moveStrafing = 0.0F;
                    this.toad.moveForward = 0.0F;
                    this.entity.setAIMoveSpeed(0.0F);
                }
            }
            else
            {
                this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
            }
        }
    }

    @Override
    public void setMoveTo(double x, double y, double z, double speedIn)
    {
        double dx = x - toad.posX;
        double dz = z - toad.posZ;
        setSpeed(speedIn);
        setDirection((float) Math.toDegrees(Math.atan2(dz, dx)) - 90, false);
        super.setMoveTo(x, y, z, speedIn);
    }
}
