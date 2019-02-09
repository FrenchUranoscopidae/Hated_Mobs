package fr.uranoscopidae.hatedmobs.common.entities;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.*;
import fr.uranoscopidae.hatedmobs.common.blocks.BlockSpiderInfestedLeaves;
import fr.uranoscopidae.hatedmobs.common.entities.entityai.EntityAIComeHomeAtNight;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;
import java.util.Optional;

public class EntitySilkSpider extends EntityAnimal implements IEntityAdditionalSpawnData
{
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntitySilkSpider.class, DataSerializers.BYTE);
    private BlockPos.MutableBlockPos homePos = new BlockPos.MutableBlockPos();
    public int timeUntilNextString;

    public EntitySilkSpider(World worldIn)
    {
        super(IBlockMapper.wrap(worldIn, SilkSpiderWorldWrapper.INSTANCE));
        this.setSize(1.4F/4, 0.9F/4);
        this.timeUntilNextString = this.rand.nextInt(6000) + 6000;
    }

    public EntitySilkSpider(World worldIn, BlockPos home)
    {
        this(worldIn);
        homePos.setPos(home);
    }

    public static Optional<EntitySilkSpider> trySpawn(World world, double x, double y, double z)
    {
        BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain(x, y, z);
        if(world.getBlockState(pos).getBlock() != HatedMobs.SPIDER_INFESTED_LEAVES_BLOCK)
        {
            pos.release();
            return Optional.empty();
        }

        IBlockState blockState = world.getBlockState(pos);
        int spiderCount = blockState.getValue(BlockSpiderInfestedLeaves.SPIDER_COUNT);

        if(spiderCount >= 2)
        {
            pos.release();
            return Optional.empty();
        }

        EntitySilkSpider silkSpider = new EntitySilkSpider(world, pos);
        silkSpider.setPosition(x, y, z);
        world.setBlockState(pos, blockState.withProperty(BlockSpiderInfestedLeaves.SPIDER_COUNT, spiderCount + 1), 3);
        world.spawnEntity(silkSpider);
        pos.release();
        return Optional.of(silkSpider);
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        super.travel(strafe, vertical, forward);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        homePos.setPos(compound.getInteger("homeX"), compound.getInteger("homeY"), compound.getInteger("homeZ"));
        if (compound.hasKey("StringShedTime"))
        {
            this.timeUntilNextString = compound.getInteger("StringShedTime");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("homeX", homePos.getX());
        compound.setInteger("homeZ", homePos.getZ());
        compound.setInteger("homeY", homePos.getY());
        compound.setInteger("StringShedTime", this.timeUntilNextString);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return null;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
    }

    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote)
        {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(2, new EntityAILookIdle(this));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIComeHomeAtNight(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
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

    @Override
    protected float getSoundPitch()
    {
        return 1.75f;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if(rand.nextInt(20*60*2) == 0)
        {
            IBlockState blockState = ((IFalsifiedWorld)world).getRealBlockState(this.getPosition());
            if(blockState.getBlock().isAir(blockState, world, getPosition()))
            {
                world.setBlockState(getPosition(), Blocks.WEB.getDefaultState());
            }
        }

        if(world.isBlockLoaded(homePos))
        {
            if(((IFalsifiedWorld)world).getRealBlockState(homePos).getBlock() != HatedMobs.SPIDER_INFESTED_LEAVES_BLOCK)
            {
                attackEntityFrom(DamageSource.MAGIC, 10000);
            }
        }

        if (!this.world.isRemote && !this.isChild() && --this.timeUntilNextString <= 0)
        {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(Items.STRING, 1);
            this.timeUntilNextString = this.rand.nextInt(6000) + 6000;
        }
    }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }

    protected PathNavigate createNavigator(World worldIn)
    {
        return new PathNavigateClimber(this, worldIn);
    }
    public boolean isBesideClimbableBlock()
    {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }


    public void setBesideClimbableBlock(boolean climbing)
    {
        byte b0 = this.dataManager.get(CLIMBING);

        if (climbing)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 = (byte)(b0 & -2);
        }

        this.dataManager.set(CLIMBING, Byte.valueOf(b0));
    }
    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }

    public void setInWeb()
    {
    }

    @Override
    public void writeSpawnData(ByteBuf buffer)
    {
        buffer.writeInt(homePos.getX());
        buffer.writeInt(homePos.getY());
        buffer.writeInt(homePos.getZ());
    }

    @Override
    public void readSpawnData(ByteBuf additionalData)
    {
        int x = additionalData.readInt();
        int y = additionalData.readInt();
        int z = additionalData.readInt();
        homePos.setPos(x, y, z);
    }

    @Override
    public float getEyeHeight()
    {
        return this.height * 0.25f;
    }

    public BlockPos getHomePos()
    {
        return homePos;
    }
}
