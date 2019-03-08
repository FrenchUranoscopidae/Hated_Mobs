package fr.uranoscopidae.hatedmobs.common.worldwrappers;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class SilkSpiderWorldWrapper implements IBlockMapper
{

    public static final SilkSpiderWorldWrapper INSTANCE = new SilkSpiderWorldWrapper();

    private SilkSpiderWorldWrapper() {}

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
