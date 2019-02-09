package fr.uranoscopidae.hatedmobs.common.tileentities;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityAntHive extends TileEntity implements ITickable
{
    public static final int RADIUS = 20; // every ~6min (1 block/tick & 20^3 blocks)
    private int xoffset = -RADIUS;
    private int yoffset = -RADIUS;
    private int zoffset = -RADIUS;

    @Override
    public void update()
    {
        zoffset ++;
        if(zoffset >= RADIUS)
        {
            yoffset ++;
            zoffset = -RADIUS;
        }

        if(yoffset >= RADIUS)
        {
            xoffset ++;
            yoffset = -RADIUS;
        }

        if(xoffset >= RADIUS)
        {
            xoffset = -RADIUS;
        }

        BlockPos blockPos = pos.add(xoffset, yoffset, zoffset);

        IBlockState blockState = world.getBlockState(blockPos);

        if(blockState.getBlock() instanceof BlockChest)
        {
            TileEntityChest tileEntityChest = (TileEntityChest)world.getTileEntity(blockPos);
            int inventorySize = tileEntityChest.getSizeInventory();

            for (int i = 0; i < inventorySize; i++)
            {
                ItemStack stack = tileEntityChest.getStackInSlot(i);
                Item item = stack.getItem();

                if(item instanceof ItemFood)
                {
                    stack.shrink(1);
                }
            }
        }
    }
}
