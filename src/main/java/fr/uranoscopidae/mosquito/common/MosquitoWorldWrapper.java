package fr.uranoscopidae.mosquito.common;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class MosquitoWorldWrapper extends World
{
    private World delegate;

    protected MosquitoWorldWrapper(World world)
    {
        super(world.getSaveHandler(), world.getWorldInfo(), world.provider, world.profiler, world.isRemote);
        chunkProvider = world.getChunkProvider();
        delegate = world;
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

        if(blockState == Blocks.GLASS.getDefaultState())
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockStainedGlass)
        {
            return  Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockPane)
        {
            return  Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockFence)
        {
            return  Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockFenceGate)
        {
            return  Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockDoor)
        {
            return  Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockTrapDoor)
        {
            return  Blocks.AIR.getDefaultState();
        }

        return blockState;
    }
}
