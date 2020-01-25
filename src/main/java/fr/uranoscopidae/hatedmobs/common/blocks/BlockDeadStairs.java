package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockDeadStairs extends BlockStairs
{
    public BlockDeadStairs(IBlockState modelState)
    {
        super(modelState);
        this.setCreativeTab(HatedMobs.TAB);
    }
}
