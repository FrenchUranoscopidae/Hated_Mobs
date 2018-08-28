package fr.uranoscopidae.hatedmobs.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public interface IFalsifiedWorld {

    IBlockState getRealBlockState(BlockPos pos);
}
