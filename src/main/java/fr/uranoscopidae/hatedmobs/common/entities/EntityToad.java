package fr.uranoscopidae.hatedmobs.common.entities;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.entityai.EntityAIMlemAttack;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class EntityToad extends EntityAnimal
{
    public static final DataParameter<Optional<BlockPos>> TONGUE_POS = EntityDataManager.createKey(EntityToad.class, DataSerializers.OPTIONAL_BLOCK_POS);
    private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(HatedMobs.DEAD_MOSQUITO, HatedMobs.DEAD_WASP);

    public EntityToad(World worldIn)
    {
        super(worldIn);
        setSize(0.25f, 0.25f);
        this.moveHelper = new EntityToadMoveHelper(this);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return new EntityToad(this.world);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIWander(this, 0.5f));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(6, new EntityAINearestAttackableTarget<>(this, EntityWasp.class, true));
        this.targetTasks.addTask(6, new EntityAINearestAttackableTarget<>(this, EntityMosquito.class, true));
        this.tasks.addTask(6, new EntityAIMlemAttack(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.5D));
        this.tasks.addTask(3, new EntityAITempt(this, 0.5D, false, TEMPTATION_ITEMS));
        this.tasks.addTask(1, new EntityAIMate(this, 0.5D));
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6);
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

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return new ResourceLocation(HatedMobs.MODID, "toad");
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return TEMPTATION_ITEMS.contains(stack.getItem());
    }

    public int getJumpDelay()
    {
        return this.rand.nextInt(20) + 10;
    }

    @Override
    public float getSoundVolume()
    {
        return super.getSoundVolume();
    }

    public SoundEvent getJumpSound()
    {
        return SoundEvents.ENTITY_SMALL_SLIME_JUMP;
    }

    public boolean canBreatheUnderwater()
    {
        return true;
    }

    public boolean getCanSpawnHere()
    {
        return true;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }
}