package fr.uranoscopidae.hatedmobs.common.entities;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.entityai.EntityAIMlemAttack;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class EntityToad extends AnimalEntity
{
    public static final DataParameter<Optional<BlockPos>> TONGUE_POS = EntityDataManager.createKey(EntityToad.class, DataSerializers.OPTIONAL_BLOCK_POS);
    private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(HatedMobs.DEAD_MOSQUITO, HatedMobs.DEAD_WASP);

    public EntityToad(World worldIn)
    {
        super(worldIn);
        setSize(0.25f, 0.25f);
        this.moveController = new EntityToadMoveHelper(this);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable)
    {
        return new EntityToad(this.world);
    }

    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 0.5f));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, EntityWasp.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, EntityMosquito.class, true));
        this.goalSelector.addGoal(6, new EntityAIMlemAttack(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.5D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.5D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(1, new BreedGoal(this, 0.5D));
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (!source.isMagicDamage() && source.getImmediateSource() instanceof EntityLivingBase)
        {
            LivingEntity entitylivingbase = (LivingEntity)source.getImmediateSource();

            if (!source.isExplosion())
            {
                boolean isValid = true;

                if(entitylivingbase instanceof PlayerEntity)
                {
                   isValid = entitylivingbase.getHeldItemMainhand().isEmpty();
                }

                if(isValid)
                {
                    entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), 1.0F);
                    entitylivingbase.addPotionEffect(new EffectInstance(Potion.getPotionTypeForName("poison"), 5*60, 0));
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
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6);
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
        return SoundEvent.ENTITY_SMALL_SLIME_JUMP;
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