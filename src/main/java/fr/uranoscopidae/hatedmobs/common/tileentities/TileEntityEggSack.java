package fr.uranoscopidae.hatedmobs.common.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class TileEntityEggSack extends TileEntity implements IInventory
{
    NonNullList<ItemStack> contents = NonNullList.withSize(27, ItemStack.EMPTY);

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
        return stack.getItem() == Items.SPAWN_EGG;
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
}
