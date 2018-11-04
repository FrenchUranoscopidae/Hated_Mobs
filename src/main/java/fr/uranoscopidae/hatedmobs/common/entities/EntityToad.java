package fr.uranoscopidae.hatedmobs.common.entities;

import com.google.common.base.Optional;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityToad extends EntityAnimal
{
    public static final DataParameter<Optional<BlockPos>> TONGUE_POS = EntityDataManager.createKey(EntityToad.class, DataSerializers.OPTIONAL_BLOCK_POS);

    public EntityToad(World worldIn)
    {
        super(worldIn);
        setSize(0.25f, 0.25f);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return null;
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWander(this, 0.5f));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8));
        this.tasks.addTask(1, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(6, new EntityAINearestAttackableTarget<>(this, EntityWasp.class, true));
        this.targetTasks.addTask(6, new EntityAINearestAttackableTarget<>(this, EntityMosquito.class, true));
        this.tasks.addTask(6, new EntityAIMlemAttack(this));
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (!source.isMagicDamage() && source.getImmediateSource() instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)source.getImmediateSource();

            if (!source.isExplosion())
            {
                boolean isValid = true;

                if(entitylivingbase instanceof EntityPlayer)
                {
                   isValid = entitylivingbase.getHeldItemMainhand().isEmpty();
                }

                if(isValid)
                {
                    entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), 1.0F);
                    entitylivingbase.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("poison"), 5*60, 0));
                }
            }
        }

        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(TONGUE_POS, Optional.absent());
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
    }

    public void target(BlockPos pos)
    {
        dataManager.set(TONGUE_POS, Optional.of(pos));
    }

    public void removeTarget()
    {
        dataManager.set(TONGUE_POS, Optional.absent());
    }

    public Optional<BlockPos> getTongueTarget()
    {
        return dataManager.get(TONGUE_POS);
    }
}