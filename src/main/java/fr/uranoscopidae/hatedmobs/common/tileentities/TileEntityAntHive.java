package fr.uranoscopidae.hatedmobs.common.tileentities;

import fr.uranoscopidae.hatedmobs.common.PathfinderAStar;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import java.util.*;

public class TileEntityAntHive extends TileEntity implements ITickable
{
    public static final int RADIUS = 20; // every ~6min (1 block/tick & 20^3 blocks)
    private static final int TIME_TO_CHECK_PATH = 20; // in ticks (by default every second)
    private int xoffset = -RADIUS;
    private int yoffset = -RADIUS;
    private int zoffset = -RADIUS;

    public Set<BlockPos> posSet = new HashSet<>();

    private Map<BlockPos, List<PathfinderAStar.Node>> paths = new HashMap<>();
    private int tick;

    @Override
    public void update()
    {
        ensureAllStillExist();
        for (int i = 0; i < 100; i++) {
            stepScan();
        }
        stealChests();
        tick++;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        posSet.clear();
        paths.clear();
        xoffset = compound.getInteger("ScanXOff");
        yoffset = compound.getInteger("ScanYOff");
        zoffset = compound.getInteger("ScanZOff");

        NBTTagList targets = compound.getTagList("Targets", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < targets.tagCount(); i++) {
            NBTTagCompound position = targets.getCompoundTagAt(i);
            BlockPos blockPos = new BlockPos(position.getInteger("x"), position.getInteger("y"), position.getInteger("z"));
            posSet.add(blockPos);
            // paths will be computed at a later time
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("ScanXOff", xoffset);
        compound.setInteger("ScanYOff", yoffset);
        compound.setInteger("ScanZOff", zoffset);

        NBTTagList targets = new NBTTagList();
        for (BlockPos pos : posSet) {
            NBTTagCompound posNBT = new NBTTagCompound();
            posNBT.setInteger("x", pos.getX());
            posNBT.setInteger("y", pos.getY());
            posNBT.setInteger("z", pos.getZ());

            targets.appendTag(posNBT);
        }
        compound.setTag("Targets", targets);
        return super.writeToNBT(compound);
    }

    /**
     * Verify that the positions in {@link #posSet} are still valid.
     */
    private void ensureAllStillExist() {
        posSet.removeIf(pos -> {
            boolean valid = isValid(pos);
            boolean noPath = false;
            if(valid && tick % TIME_TO_CHECK_PATH == 0) {
                List<PathfinderAStar.Node> path = PathfinderAStar.findPath(world, TileEntityAntHive.this.pos, pos, RADIUS);
                noPath = path == null;

                if(path != null) {
                    paths.put(pos,path);
                }
            }
            return !valid || noPath;
        });
    }

    /**
     * Checks the validity of a given BlockPos. For the moment, a position is valid if it holds a BlockChest block
     * @param pos
     * @return
     */
    private boolean isValid(BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        return blockState.getBlock() instanceof BlockChest;
    }

    public void stepScan()
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


        if(isValid(blockPos))
        {
            List<PathfinderAStar.Node> path = PathfinderAStar.findPath(world, pos, blockPos, RADIUS);
            if(!posSet.contains(blockPos) && path != null)
            {
                BlockPos targetPosition = new BlockPos(blockPos);
                posSet.add(targetPosition);
                paths.put(targetPosition, path);
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

    public List<PathfinderAStar.Node> getPath(BlockPos target) {
        return paths.get(target);
    }
}
