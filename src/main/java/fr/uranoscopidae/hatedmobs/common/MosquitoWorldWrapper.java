package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.blocks.BlockNet;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.*;

/**
 * Send (almost) everything to a World delegate. Falsifies blocks for entities
 */
public class MosquitoWorldWrapper implements IBlockMapper
{

    @Override
    public IBlockState map(IBlockState blockState, int x, int y, int z)
    {
        if(blockState == Blocks.GLASS.getDefaultState())
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockStainedGlass)
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockPane)
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockFence)
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockFenceGate)
        {
            return Blocks.AIR.getDefaultState();
        }


        if(blockState.getBlock() != HatedMobs.NET_DOOR)
        {
            if(blockState.getBlock() instanceof BlockDoor)
            {
                return Blocks.AIR.getDefaultState();
            }
        }

        if(blockState.getBlock() instanceof BlockTrapDoor)
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockNet)
        {
            return Blocks.BEDROCK.getDefaultState();
        }

        return blockState;
    }
}
