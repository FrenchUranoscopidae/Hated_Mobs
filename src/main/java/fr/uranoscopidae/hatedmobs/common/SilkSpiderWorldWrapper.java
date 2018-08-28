package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SilkSpiderWorldWrapper implements IBlockMapper
{

    @Override
    public IBlockState map(IBlockState blockState, int x, int y, int z)
    {
        if(blockState.getBlock() == HatedMobs.SPIDER_INFESTED_LEAVES_BLOCK)
        {
            return Blocks.AIR.getDefaultState();
        }

        return blockState;
    }
}
