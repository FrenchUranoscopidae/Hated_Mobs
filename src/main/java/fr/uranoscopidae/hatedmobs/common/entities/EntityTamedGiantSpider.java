package fr.uranoscopidae.hatedmobs.common.entities;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityTamedGiantSpider extends EntityTameable
{
    private static final DataParameter<Boolean> SADDLED = EntityDataManager.<Boolean>createKey(EntityTamedGiantSpider.class, DataSerializers.BOOLEAN);

    public EntityTamedGiantSpider(World worldIn)
    {
        super(worldIn);
        setSize(2, 1.25f);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(SADDLED, Boolean.valueOf(false));
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return null;
    }

    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
    }

    public void setInWeb()
    {
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    protected float getSoundPitch()
    {
        return 0.5f;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        if (!super.processInteract(player, hand))
        {
            ItemStack itemstack = player.getHeldItem(hand);

            if (itemstack.getItem() == Items.NAME_TAG)
            {
                itemstack.interactWithEntity(player, this, hand);
                return true;
            }
            else if (this.getSaddled() && !this.isBeingRidden())
            {
                if (!this.world.isRemote)
                {
                    player.startRiding(this);
                }

                return true;
            }
            else if (itemstack.getItem() == Items.SADDLE)
            {
                if (this instanceof EntityTamedGiantSpider)
                {
                    EntityTamedGiantSpider entityTamedGiantSpider = this;

                    if (!entityTamedGiantSpider.getSaddled() && !entityTamedGiantSpider.isChild())
                    {
                        entityTamedGiantSpider.setSaddled(true);
                        entityTamedGiantSpider.world.playSound(player, entityTamedGiantSpider.posX, entityTamedGiantSpider.posY, entityTamedGiantSpider.posZ, SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                        itemstack.shrink(1);
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    public boolean getSaddled()
    {
        return ((Boolean)this.dataManager.get(SADDLED)).booleanValue();
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Saddle", this.getSaddled());
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setSaddled(compound.getBoolean("Saddle"));
    }

    public void setSaddled(boolean saddled)
    {
        if (saddled)
        {
            this.dataManager.set(SADDLED, Boolean.valueOf(true));
        }
        else
        {
            this.dataManager.set(SADDLED, Boolean.valueOf(false));
        }
    }

    @Nullable
    public Entity getControllingPassenger()
    {
        return this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return new ResourceLocation(HatedMobs.MODID, "giant_spider");
    }
}