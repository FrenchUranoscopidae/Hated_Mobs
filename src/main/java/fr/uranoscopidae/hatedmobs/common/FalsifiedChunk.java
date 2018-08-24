package fr.uranoscopidae.hatedmobs.common;

import com.google.common.base.Predicate;
import fr.uranoscopidae.hatedmobs.common.blocks.BlockNet;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FalsifiedChunk extends Chunk
{
    private final Chunk delegate;
    private final FalsifiedWorld falseWorld;

    public FalsifiedChunk(FalsifiedWorld falseWorld, Chunk delegate)
    {
        super(falseWorld, delegate.x, delegate.z);
        this.delegate = delegate;
        this.falseWorld = falseWorld;
    }

    @Override
    public ExtendedBlockStorage[] getBlockStorageArray()
    {
        return delegate.getBlockStorageArray();
    }

    @Override
    public IBlockState getBlockState(int x, int y, int z)
    {
        IBlockState blockState = delegate.getBlockState(x, y, z);
        return falseWorld.map(blockState, x, y, z);
    }

    @Override
    public boolean isAtLocation(int x, int z)
    {
        return delegate.isAtLocation(x, z);
    }

    @Override
    public int getHeight(BlockPos pos)
    {
        return delegate.getHeight(pos);
    }

    @Override
    public int getHeightValue(int x, int z)
    {
        return delegate.getHeightValue(x, z);
    }

    @Override
    public int getTopFilledSegment()
    {
        return delegate.getTopFilledSegment();
    }

    @Override
    public void generateSkylightMap()
    {
        delegate.generateSkylightMap();
    }

    @Override
    public int getBlockLightOpacity(BlockPos pos)
    {
        return delegate.getBlockLightOpacity(pos);
    }

    @Nullable
    @Override
    public IBlockState setBlockState(BlockPos pos, IBlockState state)
    {
        return delegate.setBlockState(pos, state);
    }

    @Override
    public int getLightFor(EnumSkyBlock type, BlockPos pos)
    {
        return delegate.getLightFor(type, pos);
    }

    @Override
    public void setLightFor(EnumSkyBlock type, BlockPos pos, int value)
    {
        delegate.setLightFor(type, pos, value);
    }

    @Override
    public int getLightSubtracted(BlockPos pos, int amount)
    {
        return delegate.getLightSubtracted(pos, amount);
    }

    @Override
    public void addEntity(Entity entityIn)
    {
        delegate.addEntity(entityIn);
    }

    @Override
    public void removeEntity(Entity entityIn)
    {
        delegate.removeEntity(entityIn);
    }

    @Override
    public void removeEntityAtIndex(Entity entityIn, int index)
    {
        delegate.removeEntityAtIndex(entityIn, index);
    }

    @Override
    public boolean canSeeSky(BlockPos pos)
    {
        return delegate.canSeeSky(pos);
    }

    @Nullable
    @Override
    public TileEntity getTileEntity(BlockPos pos, EnumCreateEntityType p_177424_2_)
    {
        return delegate.getTileEntity(pos, p_177424_2_);
    }

    @Override
    public void addTileEntity(TileEntity tileEntityIn)
    {
        delegate.addTileEntity(tileEntityIn);
    }

    @Override
    public void addTileEntity(BlockPos pos, TileEntity tileEntityIn)
    {
        delegate.addTileEntity(pos, tileEntityIn);
    }

    @Override
    public void removeTileEntity(BlockPos pos)
    {
        delegate.removeTileEntity(pos);
    }

    @Override
    public void onLoad()
    {
        delegate.onLoad();
    }

    @Override
    public void onUnload()
    {
        delegate.onUnload();
    }

    @Override
    public void markDirty()
    {
        delegate.markDirty();
    }

    @Override
    public void getEntitiesWithinAABBForEntity(@Nullable Entity entityIn, AxisAlignedBB aabb, List<Entity> listToFill, Predicate<? super Entity> filter)
    {
        delegate.getEntitiesWithinAABBForEntity(entityIn, aabb, listToFill, filter);
    }

    @Override
    public <T extends Entity> void getEntitiesOfTypeWithinAABB(Class<? extends T> entityClass, AxisAlignedBB aabb, List<T> listToFill, Predicate<? super T> filter)
    {
        delegate.getEntitiesOfTypeWithinAABB(entityClass, aabb, listToFill, filter);
    }

    @Override
    public boolean needsSaving(boolean p_76601_1_)
    {
        return delegate.needsSaving(p_76601_1_);
    }

    @Override
    public Random getRandomWithSeed(long seed)
    {
        return delegate.getRandomWithSeed(seed);
    }

    @Override
    public boolean isEmpty()
    {
        return delegate.isEmpty();
    }

    @Override
    public void populate(IChunkProvider chunkProvider, IChunkGenerator chunkGenrator)
    {
        delegate.populate(chunkProvider, chunkGenrator);
    }

    @Override
    public BlockPos getPrecipitationHeight(BlockPos pos)
    {
        return delegate.getPrecipitationHeight(pos);
    }

    @Override
    public void onTick(boolean skipRecheckGaps)
    {
        delegate.onTick(skipRecheckGaps);
    }

    @Override
    public boolean isPopulated()
    {
        return delegate.isPopulated();
    }

    @Override
    public boolean wasTicked()
    {
        return delegate.wasTicked();
    }

    @Override
    public ChunkPos getPos()
    {
        return delegate.getPos();
    }

    @Override
    public boolean isEmptyBetween(int startY, int endY)
    {
        return delegate.isEmptyBetween(startY, endY);
    }

    @Override
    public void setStorageArrays(ExtendedBlockStorage[] newStorageArrays)
    {
        delegate.setStorageArrays(newStorageArrays);
    }

    @Override
    public void read(PacketBuffer buf, int availableSections, boolean groundUpContinuous)
    {
        delegate.read(buf, availableSections, groundUpContinuous);
    }

    @Override
    public Biome getBiome(BlockPos pos, BiomeProvider provider)
    {
        return delegate.getBiome(pos, provider);
    }

    @Override
    public byte[] getBiomeArray()
    {
        return delegate.getBiomeArray();
    }

    @Override
    public void setBiomeArray(byte[] biomeArray)
    {
        delegate.setBiomeArray(biomeArray);
    }

    @Override
    public void resetRelightChecks()
    {
        delegate.resetRelightChecks();
    }

    @Override
    public void enqueueRelightChecks()
    {
        delegate.enqueueRelightChecks();
    }

    @Override
    public void checkLight()
    {
        delegate.checkLight();
    }

    @Override
    public boolean isLoaded()
    {
        return delegate.isLoaded();
    }

    @Override
    public void markLoaded(boolean loaded)
    {
        delegate.markLoaded(loaded);
    }

    @Override
    public World getWorld()
    {
        return falseWorld;
    }

    @Override
    public int[] getHeightMap()
    {
        return delegate.getHeightMap();
    }

    @Override
    public void setHeightMap(int[] newHeightMap)
    {
        delegate.setHeightMap(newHeightMap);
    }

    @Override
    public Map<BlockPos, TileEntity> getTileEntityMap()
    {
        return delegate.getTileEntityMap();
    }

    @Override
    public ClassInheritanceMultiMap<Entity>[] getEntityLists()
    {
        return delegate.getEntityLists();
    }

    @Override
    public boolean isTerrainPopulated()
    {
        return delegate.isTerrainPopulated();
    }

    @Override
    public void setTerrainPopulated(boolean terrainPopulated)
    {
        delegate.setTerrainPopulated(terrainPopulated);
    }

    @Override
    public boolean isLightPopulated()
    {
        return delegate.isLightPopulated();
    }

    @Override
    public void setLightPopulated(boolean lightPopulated)
    {
        delegate.setLightPopulated(lightPopulated);
    }

    @Override
    public void setModified(boolean modified)
    {
        delegate.setModified(modified);
    }

    @Override
    public void setHasEntities(boolean hasEntitiesIn)
    {
        delegate.setHasEntities(hasEntitiesIn);
    }

    @Override
    public void setLastSaveTime(long saveTime)
    {
        delegate.setLastSaveTime(saveTime);
    }

    @Override
    public int getLowestHeight()
    {
        return delegate.getLowestHeight();
    }

    @Override
    public long getInhabitedTime()
    {
        return delegate.getInhabitedTime();
    }

    @Override
    public void setInhabitedTime(long newInhabitedTime)
    {
        delegate.setInhabitedTime(newInhabitedTime);
    }

    @Override
    public void removeInvalidTileEntity(BlockPos pos)
    {
        delegate.removeInvalidTileEntity(pos);
    }

    @Nullable
    @Override
    public CapabilityDispatcher getCapabilities()
    {
        return delegate.getCapabilities();
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
}
