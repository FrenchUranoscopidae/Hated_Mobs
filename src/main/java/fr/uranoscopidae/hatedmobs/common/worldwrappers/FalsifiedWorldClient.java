package fr.uranoscopidae.hatedmobs.common.worldwrappers;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSetMultimap;
import fr.uranoscopidae.hatedmobs.common.worldwrappers.FalsifiedChunk;
import fr.uranoscopidae.hatedmobs.common.worldwrappers.IBlockMapper;
import fr.uranoscopidae.hatedmobs.common.worldwrappers.IFalsifiedWorld;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Send (almost) everything to a World delegate. Falsifies blocks for entities
 */
public class FalsifiedWorldClient extends WorldClient implements IFalsifiedWorld
{
    private final IBlockMapper mapper;
    private World delegate;

    public FalsifiedWorldClient(WorldClient world, IBlockMapper mapper)
    {
        super(Minecraft.getMinecraft().getConnection(), new WorldSettings(world.getWorldInfo()), world.provider.getDimension(), world.getDifficulty(), world.profiler);
        chunkProvider = world.getChunkProvider();
        this.mapper = mapper;
        delegate = world;
    }

    @Override
    public void calculateInitialSkylight() { }

    @Override
    protected void calculateInitialWeather() { }

    @Override
    public void calculateInitialWeatherBody() { }

    public IBlockState getRealBlockState(BlockPos pos)
    {
        return delegate.getBlockState(pos);
    }

    @Override
    protected IChunkProvider createChunkProvider()
    {
        return null;
    }

    @Override
    protected boolean isChunkLoaded(int x, int z, boolean allowEmpty)
    {
        return delegate.isChunkGeneratedAt(x, z);
    }

    @Override
    public IBlockState getBlockState(BlockPos pos)
    {
        IBlockState blockState = delegate.getBlockState(pos);
        return mapper.map(blockState, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public Chunk getChunkFromChunkCoords(int chunkX, int chunkZ)
    {
        return new FalsifiedChunk(this, mapper, delegate.getChunkFromChunkCoords(chunkX, chunkZ));
    }

    @Override
    public Chunk getChunkFromBlockCoords(BlockPos pos)
    {
        return super.getChunkFromBlockCoords(pos);
    }

    @Override
    public Biome getBiome(BlockPos pos)
    {
        return delegate.getBiome(pos);
    }

    @Override
    public Biome getBiomeForCoordsBody(BlockPos pos)
    {
        return delegate.getBiomeForCoordsBody(pos);
    }

    @Override
    public BiomeProvider getBiomeProvider()
    {
        return delegate.getBiomeProvider();
    }

    @Nullable
    @Override
    public MinecraftServer getMinecraftServer()
    {
        return delegate.getMinecraftServer();
    }

    @Override
    public void setInitialSpawnLocation()
    {
    }

    @Override
    public IBlockState getGroundAboveSeaLevel(BlockPos pos)
    {
        return delegate.getGroundAboveSeaLevel(pos);
    }

    @Override
    public boolean isValid(BlockPos pos)
    {
        return delegate.isValid(pos);
    }

    @Override
    public boolean isOutsideBuildHeight(BlockPos pos)
    {
        return delegate.isOutsideBuildHeight(pos);
    }

    @Override
    public boolean isBlockLoaded(BlockPos pos)
    {
        return delegate.isBlockLoaded(pos);
    }

    @Override
    public boolean isBlockLoaded(BlockPos pos, boolean allowEmpty)
    {
        return delegate.isBlockLoaded(pos, allowEmpty);
    }

    @Override
    public boolean isAreaLoaded(BlockPos center, int radius)
    {
        return delegate.isAreaLoaded(center, radius);
    }

    @Override
    public boolean isAreaLoaded(BlockPos center, int radius, boolean allowEmpty)
    {
        return delegate.isAreaLoaded(center, radius, allowEmpty);
    }

    @Override
    public boolean isAreaLoaded(BlockPos from, BlockPos to)
    {
        return delegate.isAreaLoaded(from, to);
    }

    @Override
    public boolean isAreaLoaded(BlockPos from, BlockPos to, boolean allowEmpty)
    {
        return delegate.isAreaLoaded(from, to, allowEmpty);
    }

    @Override
    public boolean isAreaLoaded(StructureBoundingBox box)
    {
        return delegate.isAreaLoaded(box);
    }

    @Override
    public boolean isAreaLoaded(StructureBoundingBox box, boolean allowEmpty)
    {
        return delegate.isAreaLoaded(box, allowEmpty);
    }

    @Override
    public boolean isChunkGeneratedAt(int x, int z)
    {
        return delegate.isChunkGeneratedAt(x, z);
    }

    @Override
    public boolean setBlockState(BlockPos pos, IBlockState newState, int flags)
    {
        return delegate.setBlockState(pos, newState, flags);
    }

    @Override
    public void markAndNotifyBlock(BlockPos pos, @Nullable Chunk chunk, IBlockState iblockstate, IBlockState newState, int flags)
    {
        delegate.markAndNotifyBlock(pos, chunk, iblockstate, newState, flags);
    }

    @Override
    public boolean setBlockToAir(BlockPos pos)
    {
        return delegate.setBlockToAir(pos);
    }

    @Override
    public boolean destroyBlock(BlockPos pos, boolean dropBlock)
    {
        return delegate.destroyBlock(pos, dropBlock);
    }

    @Override
    public boolean setBlockState(BlockPos pos, IBlockState state)
    {
        return delegate.setBlockState(pos, state);
    }

    @Override
    public void notifyBlockUpdate(BlockPos pos, IBlockState oldState, IBlockState newState, int flags)
    {
        delegate.notifyBlockUpdate(pos, oldState, newState, flags);
    }

    @Override
    public void notifyNeighborsRespectDebug(BlockPos pos, Block blockType, boolean p_175722_3_)
    {
        delegate.notifyNeighborsRespectDebug(pos, blockType, p_175722_3_);
    }

    @Override
    public void markBlocksDirtyVertical(int x, int z, int y1, int y2)
    {
        delegate.markBlocksDirtyVertical(x, z, y1, y2);
    }

    @Override
    public void markBlockRangeForRenderUpdate(BlockPos rangeMin, BlockPos rangeMax)
    {
        delegate.markBlockRangeForRenderUpdate(rangeMin, rangeMax);
    }

    @Override
    public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2)
    {
        delegate.markBlockRangeForRenderUpdate(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public void updateObservingBlocksAt(BlockPos pos, Block blockType)
    {
        delegate.updateObservingBlocksAt(pos, blockType);
    }

    @Override
    public void notifyNeighborsOfStateChange(BlockPos pos, Block blockType, boolean updateObservers)
    {
        delegate.notifyNeighborsOfStateChange(pos, blockType, updateObservers);
    }

    @Override
    public void notifyNeighborsOfStateExcept(BlockPos pos, Block blockType, EnumFacing skipSide)
    {
        delegate.notifyNeighborsOfStateExcept(pos, blockType, skipSide);
    }

    @Override
    public void neighborChanged(BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        delegate.neighborChanged(pos, blockIn, fromPos);
    }

    @Override
    public void observedNeighborChanged(BlockPos pos, Block p_190529_2_, BlockPos p_190529_3_)
    {
        delegate.observedNeighborChanged(pos, p_190529_2_, p_190529_3_);
    }

    @Override
    public boolean isBlockTickPending(BlockPos pos, Block blockType)
    {
        return delegate.isBlockTickPending(pos, blockType);
    }

    @Override
    public boolean canSeeSky(BlockPos pos)
    {
        return delegate.canSeeSky(pos);
    }

    @Override
    public boolean canBlockSeeSky(BlockPos pos)
    {
        return delegate.canBlockSeeSky(pos);
    }

    @Override
    public int getLight(BlockPos pos)
    {
        return delegate.getLight(pos);
    }

    @Override
    public int getLightFromNeighbors(BlockPos pos)
    {
        return delegate.getLightFromNeighbors(pos);
    }

    @Override
    public int getLight(BlockPos pos, boolean checkNeighbors)
    {
        return delegate.getLight(pos, checkNeighbors);
    }

    @Override
    public BlockPos getHeight(BlockPos pos)
    {
        return delegate.getHeight(pos);
    }

    @Override
    public int getHeight(int x, int z)
    {
        return delegate.getHeight(x, z);
    }

    @Override
    public int getChunksLowestHorizon(int x, int z)
    {
        return delegate.getChunksLowestHorizon(x, z);
    }

    @Override
    public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos pos)
    {
        return delegate.getLightFromNeighborsFor(type, pos);
    }

    @Override
    public int getLightFor(EnumSkyBlock type, BlockPos pos)
    {
        return delegate.getLightFor(type, pos);
    }

    @Override
    public void setLightFor(EnumSkyBlock type, BlockPos pos, int lightValue)
    {
        delegate.setLightFor(type, pos, lightValue);
    }

    @Override
    public void notifyLightSet(BlockPos pos)
    {
        delegate.notifyLightSet(pos);
    }

    @Override
    public int getCombinedLight(BlockPos pos, int lightValue)
    {
        return delegate.getCombinedLight(pos, lightValue);
    }

    @Override
    public float getLightBrightness(BlockPos pos)
    {
        return delegate.getLightBrightness(pos);
    }

    @Override
    public boolean isDaytime()
    {
        return delegate.isDaytime();
    }

    @Override
    public void playSound(@Nullable EntityPlayer player, BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch)
    {
        delegate.playSound(player, pos, soundIn, category, volume, pitch);
    }

    @Override
    public void playSound(@Nullable EntityPlayer player, double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch)
    {
        delegate.playSound(player, x, y, z, soundIn, category, volume, pitch);
    }

    @Override
    public void playSound(double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch, boolean distanceDelay)
    {
        delegate.playSound(x, y, z, soundIn, category, volume, pitch, distanceDelay);
    }

    @Override
    public void playRecord(BlockPos blockPositionIn, @Nullable SoundEvent soundEventIn)
    {
        delegate.playRecord(blockPositionIn, soundEventIn);
    }

    @Override
    public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
    {
        delegate.spawnParticle(particleType, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
    }

    @Override
    public void spawnAlwaysVisibleParticle(int p_190523_1_, double p_190523_2_, double p_190523_4_, double p_190523_6_, double p_190523_8_, double p_190523_10_, double p_190523_12_, int... p_190523_14_)
    {
        delegate.spawnAlwaysVisibleParticle(p_190523_1_, p_190523_2_, p_190523_4_, p_190523_6_, p_190523_8_, p_190523_10_, p_190523_12_, p_190523_14_);
    }

    @Override
    public void spawnParticle(EnumParticleTypes particleType, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
    {
        delegate.spawnParticle(particleType, ignoreRange, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
    }

    @Override
    public boolean addWeatherEffect(Entity entityIn)
    {
        return delegate.addWeatherEffect(entityIn);
    }

    @Override
    public boolean spawnEntity(Entity entityIn)
    {
        return delegate.spawnEntity(entityIn);
    }

    @Override
    public void onEntityAdded(Entity entityIn)
    {
        delegate.onEntityAdded(entityIn);
    }

    @Override
    public void onEntityRemoved(Entity entityIn)
    {
        delegate.onEntityRemoved(entityIn);
    }

    @Override
    public void removeEntity(Entity entityIn)
    {
        delegate.removeEntity(entityIn);
    }

    @Override
    public void removeEntityDangerously(Entity entityIn)
    {
        delegate.removeEntityDangerously(entityIn);
    }

    @Override
    public void addEventListener(IWorldEventListener listener)
    {

    }

    @Override
    public void removeEventListener(IWorldEventListener listener)
    {

    }

    @Override
    public boolean isInsideWorldBorder(Entity p_191503_1_)
    {
        return delegate.isInsideWorldBorder(p_191503_1_);
    }

    @Override
    public int calculateSkylightSubtracted(float partialTicks)
    {
        return delegate.calculateSkylightSubtracted(partialTicks);
    }

    @Override
    public float getSunBrightnessFactor(float partialTicks)
    {
        return 0f;
    }

    @Override
    public float getSunBrightness(float partialTicks)
    {
        return 0f;
    }

    @Override
    public float getSunBrightnessBody(float partialTicks)
    {
        return 0f;
    }

    @Override
    public Vec3d getSkyColor(Entity entityIn, float partialTicks)
    {
        return delegate.getSkyColor(entityIn, partialTicks);
    }

    @Override
    public Vec3d getSkyColorBody(Entity entityIn, float partialTicks)
    {
        return delegate.getSkyColorBody(entityIn, partialTicks);
    }

    @Override
    public float getCelestialAngle(float partialTicks)
    {
        return delegate.getCelestialAngle(partialTicks);
    }

    @Override
    public int getMoonPhase()
    {
        return delegate.getMoonPhase();
    }

    @Override
    public float getCurrentMoonPhaseFactor()
    {
        return delegate.getCurrentMoonPhaseFactor();
    }

    @Override
    public float getCurrentMoonPhaseFactorBody()
    {
        return delegate.getCurrentMoonPhaseFactorBody();
    }

    @Override
    public float getCelestialAngleRadians(float partialTicks)
    {
        return delegate.getCelestialAngleRadians(partialTicks);
    }

    @Override
    public Vec3d getCloudColour(float partialTicks)
    {
        return delegate.getCloudColour(partialTicks);
    }

    @Override
    public Vec3d getCloudColorBody(float partialTicks)
    {
        return delegate.getCloudColorBody(partialTicks);
    }

    @Override
    public Vec3d getFogColor(float partialTicks)
    {
        return delegate.getFogColor(partialTicks);
    }

    @Override
    public BlockPos getPrecipitationHeight(BlockPos pos)
    {
        return delegate.getPrecipitationHeight(pos);
    }

    @Override
    public BlockPos getTopSolidOrLiquidBlock(BlockPos pos)
    {
        return delegate.getTopSolidOrLiquidBlock(pos);
    }

    @Override
    public float getStarBrightness(float partialTicks)
    {
        return delegate.getStarBrightness(partialTicks);
    }

    @Override
    public float getStarBrightnessBody(float partialTicks)
    {
        return delegate.getStarBrightnessBody(partialTicks);
    }

    @Override
    public boolean isUpdateScheduled(BlockPos pos, Block blk)
    {
        return delegate.isUpdateScheduled(pos, blk);
    }

    @Override
    public void scheduleUpdate(BlockPos pos, Block blockIn, int delay)
    {
        delegate.scheduleUpdate(pos, blockIn, delay);
    }

    @Override
    public void updateBlockTick(BlockPos pos, Block blockIn, int delay, int priority)
    {
        delegate.updateBlockTick(pos, blockIn, delay, priority);
    }

    @Override
    public void scheduleBlockUpdate(BlockPos pos, Block blockIn, int delay, int priority)
    {
        delegate.scheduleBlockUpdate(pos, blockIn, delay, priority);
    }

    @Override
    public void updateEntities()
    {
        delegate.updateEntities();
    }

    @Override
    protected void tickPlayers()
    {
    }

    @Override
    public boolean addTileEntity(TileEntity tile)
    {
        return delegate.addTileEntity(tile);
    }

    @Override
    public void addTileEntities(Collection<TileEntity> tileEntityCollection)
    {

    }

    @Override
    public void updateEntity(Entity ent)
    {
        delegate.updateEntity(ent);
    }

    @Override
    public void updateEntityWithOptionalForce(Entity entityIn, boolean forceUpdate)
    {
        delegate.updateEntityWithOptionalForce(entityIn, forceUpdate);
    }

    @Override
    public Explosion createExplosion(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isSmoking)
    {
        return delegate.createExplosion(entityIn, x, y, z, strength, isSmoking);
    }

    @Override
    public Explosion newExplosion(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking)
    {
        return delegate.newExplosion(entityIn, x, y, z, strength, isFlaming, isSmoking);
    }

    @Override
    public float getBlockDensity(Vec3d vec, AxisAlignedBB bb)
    {
        return delegate.getBlockDensity(vec, bb);
    }

    @Override
    public boolean extinguishFire(@Nullable EntityPlayer player, BlockPos pos, EnumFacing side)
    {
        return delegate.extinguishFire(player, pos, side);
    }

    @Override
    public String getDebugLoadedEntities()
    {
        return delegate.getDebugLoadedEntities();
    }

    @Override
    public String getProviderName()
    {
        return delegate.getProviderName();
    }

    @Nullable
    @Override
    public TileEntity getTileEntity(BlockPos pos)
    {
        return delegate.getTileEntity(pos);
    }

    @Override
    public void setTileEntity(BlockPos pos, @Nullable TileEntity tileEntityIn)
    {
        delegate.setTileEntity(pos, tileEntityIn);
    }

    @Override
    public void removeTileEntity(BlockPos pos)
    {
        delegate.removeTileEntity(pos);
    }

    @Override
    public void markTileEntityForRemoval(TileEntity tileEntityIn)
    {
        delegate.markTileEntityForRemoval(tileEntityIn);
    }

    @Override
    public void setAllowedSpawnTypes(boolean hostile, boolean peaceful)
    {
        delegate.setAllowedSpawnTypes(hostile, peaceful);
    }

    @Override
    public void tick()
    {
        delegate.tick();
    }

    @Override
    protected void updateWeather()
    {
    }

    @Override
    public void updateWeatherBody()
    {
        delegate.updateWeatherBody();
    }

    @Override
    protected void playMoodSoundAndCheckLight(int p_147467_1_, int p_147467_2_, Chunk chunkIn)
    {
    }

    @Override
    protected void updateBlocks()
    {
    }

    @Override
    public void immediateBlockTick(BlockPos pos, IBlockState state, Random random)
    {
        delegate.immediateBlockTick(pos, state, random);
    }

    @Override
    public boolean canBlockFreezeWater(BlockPos pos)
    {
        return delegate.canBlockFreezeWater(pos);
    }

    @Override
    public boolean canBlockFreezeNoWater(BlockPos pos)
    {
        return delegate.canBlockFreezeNoWater(pos);
    }

    @Override
    public boolean canBlockFreeze(BlockPos pos, boolean noWaterAdj)
    {
        return delegate.canBlockFreeze(pos, noWaterAdj);
    }

    @Override
    public boolean canBlockFreezeBody(BlockPos pos, boolean noWaterAdj)
    {
        return delegate.canBlockFreezeBody(pos, noWaterAdj);
    }

    @Override
    public boolean canSnowAt(BlockPos pos, boolean checkLight)
    {
        return delegate.canSnowAt(pos, checkLight);
    }

    @Override
    public boolean canSnowAtBody(BlockPos pos, boolean checkLight)
    {
        return delegate.canSnowAtBody(pos, checkLight);
    }

    @Override
    public boolean checkLight(BlockPos pos)
    {
        return delegate.checkLight(pos);
    }

    @Override
    public boolean checkLightFor(EnumSkyBlock lightType, BlockPos pos)
    {
        return delegate.checkLightFor(lightType, pos);
    }

    @Override
    public boolean tickUpdates(boolean runAllPending)
    {
        return delegate.tickUpdates(runAllPending);
    }

    @Nullable
    @Override
    public List<NextTickListEntry> getPendingBlockUpdates(Chunk chunkIn, boolean remove)
    {
        return delegate.getPendingBlockUpdates(chunkIn, remove);
    }

    @Nullable
    @Override
    public List<NextTickListEntry> getPendingBlockUpdates(StructureBoundingBox structureBB, boolean remove)
    {
        return delegate.getPendingBlockUpdates(structureBB, remove);
    }

    @Override
    public List<Entity> getEntitiesWithinAABBExcludingEntity(@Nullable Entity entityIn, AxisAlignedBB bb)
    {
        return delegate.getEntitiesWithinAABBExcludingEntity(entityIn, bb);
    }

    @Override
    public List<Entity> getEntitiesInAABBexcluding(@Nullable Entity entityIn, AxisAlignedBB boundingBox, @Nullable Predicate<? super Entity> predicate)
    {
        return delegate.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
    }

    @Override
    public <T extends Entity> List<T> getEntities(Class<? extends T> entityType, Predicate<? super T> filter)
    {
        return delegate.getEntities(entityType, filter);
    }

    @Override
    public <T extends Entity> List<T> getPlayers(Class<? extends T> playerType, Predicate<? super T> filter)
    {
        return delegate.getPlayers(playerType, filter);
    }

    @Override
    public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> classEntity, AxisAlignedBB bb)
    {
        return delegate.getEntitiesWithinAABB(classEntity, bb);
    }

    @Override
    public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB aabb, @Nullable Predicate<? super T> filter)
    {
        return delegate.getEntitiesWithinAABB(clazz, aabb, filter);
    }

    @Nullable
    @Override
    public <T extends Entity> T findNearestEntityWithinAABB(Class<? extends T> entityType, AxisAlignedBB aabb, T closestTo)
    {
        return delegate.findNearestEntityWithinAABB(entityType, aabb, closestTo);
    }

    @Nullable
    @Override
    public Entity getEntityByID(int id)
    {
        return delegate.getEntityByID(id);
    }

    @Override
    public List<Entity> getLoadedEntityList()
    {
        return delegate.getLoadedEntityList();
    }

    @Override
    public void markChunkDirty(BlockPos pos, TileEntity unusedTileEntity)
    {
        delegate.markChunkDirty(pos, unusedTileEntity);
    }

    @Override
    public int countEntities(Class<?> entityType)
    {
        return delegate.countEntities(entityType);
    }

    @Override
    public void loadEntities(Collection<Entity> entityCollection)
    {
        delegate.loadEntities(entityCollection);
    }

    @Override
    public void unloadEntities(Collection<Entity> entityCollection)
    {
        delegate.unloadEntities(entityCollection);
    }

    @Override
    public boolean mayPlace(Block blockIn, BlockPos pos, boolean skipCollisionCheck, EnumFacing sidePlacedOn, @Nullable Entity placer)
    {
        return delegate.mayPlace(blockIn, pos, skipCollisionCheck, sidePlacedOn, placer);
    }

    @Override
    public int getSeaLevel()
    {
        return delegate.getSeaLevel();
    }

    @Override
    public void setSeaLevel(int seaLevelIn)
    {
        delegate.setSeaLevel(seaLevelIn);
    }

    @Override
    public int getStrongPower(BlockPos pos, EnumFacing direction)
    {
        return delegate.getStrongPower(pos, direction);
    }

    @Override
    public WorldType getWorldType()
    {
        return delegate.getWorldType();
    }

    @Override
    public int getStrongPower(BlockPos pos)
    {
        return delegate.getStrongPower(pos);
    }

    @Override
    public boolean isSidePowered(BlockPos pos, EnumFacing side)
    {
        return delegate.isSidePowered(pos, side);
    }

    @Override
    public int getRedstonePower(BlockPos pos, EnumFacing facing)
    {
        return delegate.getRedstonePower(pos, facing);
    }

    @Override
    public boolean isBlockPowered(BlockPos pos)
    {
        return delegate.isBlockPowered(pos);
    }

    @Override
    public int isBlockIndirectlyGettingPowered(BlockPos pos)
    {
        return delegate.isBlockIndirectlyGettingPowered(pos);
    }

    @Nullable
    @Override
    public EntityPlayer getClosestPlayerToEntity(Entity entityIn, double distance)
    {
        return delegate.getClosestPlayerToEntity(entityIn, distance);
    }

    @Nullable
    @Override
    public EntityPlayer getNearestPlayerNotCreative(Entity entityIn, double distance)
    {
        return delegate.getNearestPlayerNotCreative(entityIn, distance);
    }

    @Nullable
    @Override
    public EntityPlayer getClosestPlayer(double posX, double posY, double posZ, double distance, boolean spectator)
    {
        return delegate.getClosestPlayer(posX, posY, posZ, distance, spectator);
    }

    @Nullable
    @Override
    public EntityPlayer getClosestPlayer(double x, double y, double z, double p_190525_7_, Predicate<Entity> p_190525_9_)
    {
        return delegate.getClosestPlayer(x, y, z, p_190525_7_, p_190525_9_);
    }

    @Override
    public boolean isAnyPlayerWithinRangeAt(double x, double y, double z, double range)
    {
        return delegate.isAnyPlayerWithinRangeAt(x, y, z, range);
    }

    @Nullable
    @Override
    public EntityPlayer getNearestAttackablePlayer(Entity entityIn, double maxXZDistance, double maxYDistance)
    {
        return delegate.getNearestAttackablePlayer(entityIn, maxXZDistance, maxYDistance);
    }

    @Nullable
    @Override
    public EntityPlayer getNearestAttackablePlayer(BlockPos pos, double maxXZDistance, double maxYDistance)
    {
        return delegate.getNearestAttackablePlayer(pos, maxXZDistance, maxYDistance);
    }

    @Nullable
    @Override
    public EntityPlayer getNearestAttackablePlayer(double posX, double posY, double posZ, double maxXZDistance, double maxYDistance, @Nullable Function<EntityPlayer, Double> playerToDouble, @Nullable Predicate<EntityPlayer> p_184150_12_)
    {
        return delegate.getNearestAttackablePlayer(posX, posY, posZ, maxXZDistance, maxYDistance, playerToDouble, p_184150_12_);
    }

    @Nullable
    @Override
    public EntityPlayer getPlayerEntityByName(String name)
    {
        return delegate.getPlayerEntityByName(name);
    }

    @Nullable
    @Override
    public EntityPlayer getPlayerEntityByUUID(UUID uuid)
    {
        return delegate.getPlayerEntityByUUID(uuid);
    }

    @Override
    public void sendQuittingDisconnectingPacket()
    {
        delegate.sendQuittingDisconnectingPacket();
    }

    @Override
    public void checkSessionLock() throws MinecraftException
    {
        delegate.checkSessionLock();
    }

    @Override
    public void setTotalWorldTime(long worldTime)
    {
        delegate.setTotalWorldTime(worldTime);
    }

    @Override
    public long getTotalWorldTime()
    {
        return delegate.getTotalWorldTime();
    }

    @Override
    public long getWorldTime()
    {
        return delegate.getWorldTime();
    }

    @Override
    public void setWorldTime(long time)
    {
        delegate.setWorldTime(time);
    }

    @Override
    public BlockPos getSpawnPoint()
    {
        return delegate.getSpawnPoint();
    }

    @Override
    public void setSpawnPoint(BlockPos pos)
    {
    }

    @Override
    public void joinEntityInSurroundings(Entity entityIn)
    {
        delegate.joinEntityInSurroundings(entityIn);
    }

    @Override
    public boolean isBlockModifiable(EntityPlayer player, BlockPos pos)
    {
        return delegate.isBlockModifiable(player, pos);
    }

    @Override
    public boolean canMineBlockBody(EntityPlayer player, BlockPos pos)
    {
        return delegate.canMineBlockBody(player, pos);
    }

    @Override
    public void setEntityState(Entity entityIn, byte state)
    {
        delegate.setEntityState(entityIn, state);
    }

    @Override
    public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam)
    {
        delegate.addBlockEvent(pos, blockIn, eventID, eventParam);
    }

    @Override
    public void updateAllPlayersSleepingFlag()
    {
        delegate.updateAllPlayersSleepingFlag();
    }

    @Override
    public float getThunderStrength(float delta)
    {
        return delegate.getThunderStrength(delta);
    }

    @Override
    public void setThunderStrength(float strength)
    {
        delegate.setThunderStrength(strength);
    }

    @Override
    public float getRainStrength(float delta)
    {
        return delegate.getRainStrength(delta);
    }

    @Override
    public void setRainStrength(float strength)
    {
        delegate.setRainStrength(strength);
    }

    @Override
    public boolean isThundering()
    {
        return delegate.isThundering();
    }

    @Override
    public boolean isRaining()
    {
        return delegate.isRaining();
    }

    @Override
    public boolean isRainingAt(BlockPos position)
    {
        return delegate.isRainingAt(position);
    }

    @Override
    public boolean isBlockinHighHumidity(BlockPos pos)
    {
        return delegate.isBlockinHighHumidity(pos);
    }

    @Nullable
    @Override
    public MapStorage getMapStorage()
    {
        return delegate.getMapStorage();
    }

    @Override
    public void setData(String dataID, WorldSavedData worldSavedDataIn)
    {
        delegate.setData(dataID, worldSavedDataIn);
    }

    @Nullable
    @Override
    public WorldSavedData loadData(Class<? extends WorldSavedData> clazz, String dataID)
    {
        return delegate.loadData(clazz, dataID);
    }

    @Override
    public int getUniqueDataId(String key)
    {
        return delegate.getUniqueDataId(key);
    }

    @Override
    public void playBroadcastSound(int id, BlockPos pos, int data)
    {
        delegate.playBroadcastSound(id, pos, data);
    }

    @Override
    public void playEvent(int type, BlockPos pos, int data)
    {
        delegate.playEvent(type, pos, data);
    }

    @Override
    public void playEvent(@Nullable EntityPlayer player, int type, BlockPos pos, int data)
    {
        delegate.playEvent(player, type, pos, data);
    }

    @Override
    public int getHeight()
    {
        return delegate.getHeight();
    }

    @Override
    public int getActualHeight()
    {
        return delegate.getActualHeight();
    }

    @Override
    public Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_)
    {
        return delegate.setRandomSeed(p_72843_1_, p_72843_2_, p_72843_3_);
    }

    @Override
    public CrashReportCategory addWorldInfoToCrashReport(CrashReport report)
    {
        return delegate.addWorldInfoToCrashReport(report);
    }

    @Override
    public double getHorizon()
    {
        return delegate.getHorizon();
    }

    @Override
    public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress)
    {
        delegate.sendBlockBreakProgress(breakerId, pos, progress);
    }

    @Override
    public Calendar getCurrentDate()
    {
        return delegate.getCurrentDate();
    }

    @Override
    public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, @Nullable NBTTagCompound compound)
    {
        delegate.makeFireworks(x, y, z, motionX, motionY, motionZ, compound);
    }

    @Override
    public Scoreboard getScoreboard()
    {
        return delegate.getScoreboard();
    }

    @Override
    public void updateComparatorOutputLevel(BlockPos pos, Block blockIn)
    {
        delegate.updateComparatorOutputLevel(pos, blockIn);
    }

    @Override
    public DifficultyInstance getDifficultyForLocation(BlockPos pos)
    {
        return delegate.getDifficultyForLocation(pos);
    }

    @Override
    public EnumDifficulty getDifficulty()
    {
        return delegate.getDifficulty();
    }

    @Override
    public int getSkylightSubtracted()
    {
        return delegate.getSkylightSubtracted();
    }

    @Override
    public void setSkylightSubtracted(int newSkylightSubtracted)
    {
        delegate.setSkylightSubtracted(newSkylightSubtracted);
    }

    @Override
    public int getLastLightningBolt()
    {
        return delegate.getLastLightningBolt();
    }

    @Override
    public void setLastLightningBolt(int lastLightningBoltIn)
    {
        delegate.setLastLightningBolt(lastLightningBoltIn);
    }

    @Override
    public VillageCollection getVillageCollection()
    {
        return delegate.getVillageCollection();
    }

    @Override
    public boolean isSpawnChunk(int x, int z)
    {
        return delegate.isSpawnChunk(x, z);
    }

    @Override
    public ImmutableSetMultimap<ChunkPos, ForgeChunkManager.Ticket> getPersistentChunks()
    {
        return delegate.getPersistentChunks();
    }

    @Override
    public Iterator<Chunk> getPersistentChunkIterable(Iterator<Chunk> chunkIterator)
    {
        return delegate.getPersistentChunkIterable(chunkIterator);
    }

    @Override
    public int getBlockLightOpacity(BlockPos pos)
    {
        return delegate.getBlockLightOpacity(pos);
    }

    @Override
    public int countEntities(EnumCreatureType type, boolean forSpawnCount)
    {
        return delegate.countEntities(type, forSpawnCount);
    }

    @Override
    public void markTileEntitiesInChunkForRemoval(Chunk chunk)
    {
        delegate.markTileEntitiesInChunkForRemoval(chunk);
    }

    @Override
    protected void initCapabilities()
    {
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return delegate.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return delegate.getCapability(capability, facing);
    }

    @Override
    public MapStorage getPerWorldStorage()
    {
        return delegate.getPerWorldStorage();
    }

    @Override
    public void sendPacketToServer(Packet<?> packetIn)
    {
        delegate.sendPacketToServer(packetIn);
    }

    @Override
    public LootTableManager getLootTableManager()
    {
        return delegate.getLootTableManager();
    }

    @Nullable
    @Override
    public BlockPos findNearestStructure(String p_190528_1_, BlockPos p_190528_2_, boolean p_190528_3_)
    {
        return delegate.findNearestStructure(p_190528_1_, p_190528_2_, p_190528_3_);
    }
}
