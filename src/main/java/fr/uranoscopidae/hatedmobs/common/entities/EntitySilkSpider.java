package fr.uranoscopidae.hatedmobs.common.entities;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.blocks.BlockSpiderInfestedLeaves;
import fr.uranoscopidae.hatedmobs.common.entities.entityai.EntityAIComeHomeAtNight;
import fr.uranoscopidae.hatedmobs.common.worldwrappers.IBlockMapper;
import fr.uranoscopidae.hatedmobs.common.worldwrappers.IFalsifiedWorld;
import fr.uranoscopidae.hatedmobs.common.worldwrappers.SilkSpiderWorldWrapper;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;
import java.util.Optional;

public class EntitySilkSpider extends AnimalEntity implements IEntityAdditionalSpawnData
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

        BlockState blockState = world.getBlockState(pos);
        int spiderCount = blockState.getValue(BlockSpiderInfestedLeaves.SPIDER_COUNT);

        if(spiderCount >= 2)
        {
            pos.release();
            return Optional.empty();
        }

        EntitySilkSpider silkSpider = new EntitySilkSpider(world, pos);
        silkSpider.setPosition(x, y, z);
        world.setBlockState(pos, blockState.withProperty(BlockSpiderInfestedLeaves.SPIDER_COUNT, spiderCount + 1), 3);
        world.addEntity(silkSpider);
        pos.release();
        return Optional.of(silkSpider);
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        super.travel(strafe, vertical, forward);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        homePos.setPos(compound.getInt("homeX"), compound.getInt("homeY"), compound.getInt("homeZ"));
        if (compound.hasKey("StringShedTime"))
        {
            this.timeUntilNextString = compound.getInt("StringShedTime");
        }
    }

    @Override
    public void write(CompoundNBT compound)
    {
        super.write(compound);
        compound.putInt("homeX", homePos.getX());
        compound.putInt("homeZ", homePos.getZ());
        compound.putInt("homeY", homePos.getY());
        compound.putInt("StringShedTime", this.timeUntilNextString);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable)
    {
        return null;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isRemote)
        {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    @Override
    public CreatureAttribute getCreatureAttribute() { return CreatureAttribute.ARTHROPOD; }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new EntityAIComeHomeAtNight(this));
    }

    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
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

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn)
    {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    protected float getSoundPitch()
    {
        return 1.75f;
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if(rand.nextInt(20*60*2) == 0)
        {
            BlockState blockState = ((IFalsifiedWorld)world).getRealBlockState(this.getPosition());
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

    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos)
    {
    }

    protected PathNavigator createNavigator(World worldIn)
    {
        return new ClimberPathNavigator(this, worldIn);
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
    public void writeSpawnData(PacketBuffer packetBuffer)
    {
        packetBuffer.writeInt(homePos.getX());
        packetBuffer.writeInt(homePos.getY());
        packetBuffer.writeInt(homePos.getZ());
    }

    @Override
    public void readSpawnData(PacketBuffer packetBuffer)
    {
        int x = packetBuffer.readInt();
        int y = packetBuffer.readInt();
        int z = packetBuffer.readInt();
        homePos.setPos(x, y, z);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn)
    {
        return this.getHeight() * 0.25f;
    }

    public BlockPos getHomePos()
    {
        return homePos;
    }
}
