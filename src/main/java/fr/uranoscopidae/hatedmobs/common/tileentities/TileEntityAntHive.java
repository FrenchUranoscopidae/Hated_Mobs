package fr.uranoscopidae.hatedmobs.common.tileentities;

import fr.uranoscopidae.hatedmobs.common.PathfinderAStar;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TileEntityAntHive extends TileEntity implements ITickable
{
    public static final int RADIUS = 20; // every ~6min (1 block/tick & 20^3 blocks)
    private int xoffset = -RADIUS;
    private int yoffset = -RADIUS;
    private int zoffset = -RADIUS;

    public Set<BlockPos> posSet = new HashSet<>();

    @Override
    public void update()
    {
        stepScan();
        stealChests();
    }

    public void stepScan()
    {
        for (int i = 0; i < 100; i++)
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
                if(!posSet.contains(blockPos) && PathfinderAStar.findPath(world, pos, blockPos, RADIUS) != null)
                {
                    posSet.add(new BlockPos(blockPos));
                }
            }
        }
    }

    public void stealChests()
    {
        if (world.getTotalWorldTime()%(20 * 2 * 60) != 0)
        {
            return;
        }

        Iterator<BlockPos> iterator = posSet.iterator();
        tileEntityLoop: while (iterator.hasNext())
        {
            BlockPos chestPos = iterator.next();
            TileEntity chestTileEntity = world.getTileEntity(chestPos);

            if (chestTileEntity instanceof TileEntityChest)
            {
                TileEntityChest tileEntityChest = (TileEntityChest)world.getTileEntity(chestPos);
                int inventorySize = tileEntityChest.getSizeInventory();

                for (int i = 0; i < inventorySize; i++)
                {
                    ItemStack stack = tileEntityChest.getStackInSlot(i);
                    Item item = stack.getItem();

                    if(item instanceof ItemFood)
                    {
                        stack.shrink(1);
                        continue tileEntityLoop;
                    }
                }
            }
            else
            {
                iterator.remove();
            }
        }
    }
}
