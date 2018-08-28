package fr.uranoscopidae.hatedmobs.common.tileentities;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntitySilkSpider;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEntityEggSack extends TileEntity implements IInventory, ITickable
{
    NonNullList<ItemStack> contents = NonNullList.withSize(27, ItemStack.EMPTY);
    private int cooldown;

    @Override
    public int getSizeInventory()
    {
        return 27;
    }

    @Override
    public boolean isEmpty()
    {
        for (ItemStack stack : contents)
        {
            if(!stack.isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        return contents.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack stack = ItemStackHelper.getAndSplit(contents, index, count);
        if(!stack.isEmpty())
        {
            markDirty();
        }
        return stack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(contents, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        contents.set(index, stack);
        markDirty();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {

    }

    @Override
    public void closeInventory(EntityPlayer player)
    {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return stack.getItem() == Items.SPAWN_EGG || stack.getItem() == HatedMobs.SPIDER_EGG;
    }

    @Override
    public int getField(int id)
    {
        return 0;
    }

    @Override
    public void setField(int id, int value)
    {

    }

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    @Override
    public void clear()
    {
        contents.clear();
    }

    @Override
    public String getName()
    {
        return "container.egg_sack";
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public void update()
    {
        if(world.isRemote)
            return;
        cooldown --;
        if(cooldown <= 0)
        {
            int spiderCount = 0;
            cooldown = 5 * 20 * 60;

            for (EnumFacing facing : EnumFacing.VALUES)
            {
                BlockPos blockPos = pos.offset(facing);

                if(world.getBlockState(blockPos).getBlock() == HatedMobs.SPIDER_INFESTED_LEAVES_BLOCK)
                {
                    spiderCount += world.getEntitiesWithinAABB(EntitySilkSpider.class, new AxisAlignedBB(blockPos)).size();
                }
            }
            for (int i = 0; i < spiderCount; i++)
            {
                int slot = this.storeItemStack(new ItemStack(HatedMobs.SPIDER_EGG));
                if(slot >= 0)
                {
                    ItemStack stack = getStackInSlot(slot);
                    if(stack.isEmpty())
                    {
                        setInventorySlotContents(slot, new ItemStack(HatedMobs.SPIDER_EGG));
                    }
                    else
                    {
                        stack.grow(1);
                        markDirty();
                    }
                }
            }
        }
    }

    public int storeItemStack(ItemStack itemStackIn)
    {
        for (int i = 0; i < this.getSizeInventory(); ++i)
        {
            if (getStackInSlot(i).isEmpty() || this.canMergeStacks(getStackInSlot(i), itemStackIn))
            {
                return i;
            }
        }
        return -1;
    }

    private boolean canMergeStacks(ItemStack stack1, ItemStack stack2)
    {
        return !stack1.isEmpty() && this.stackEqualExact(stack1, stack2) && stack1.isStackable() && stack1.getCount() < stack1.getMaxStackSize() && stack1.getCount() < this.getInventoryStackLimit();
    }

    private boolean stackEqualExact(ItemStack stack1, ItemStack stack2)
    {
        return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }
}
