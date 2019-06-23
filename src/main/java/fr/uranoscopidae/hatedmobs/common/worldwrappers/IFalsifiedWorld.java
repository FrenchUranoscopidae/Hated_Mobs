package fr.uranoscopidae.hatedmobs.common.worldwrappers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public interface IFalsifiedWorld {

    IBlockState getRealBlockState(BlockPos pos);
}
