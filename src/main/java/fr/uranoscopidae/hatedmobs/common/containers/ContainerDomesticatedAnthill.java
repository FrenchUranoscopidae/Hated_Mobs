package fr.uranoscopidae.hatedmobs.common.containers;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityDomesticatedAnthill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDomesticatedAnthill extends Container
{
    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 3 * 9)
            {
                if (!this.mergeItemStack(itemstack1, 3 * 9, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 3 * 9, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    public ContainerDomesticatedAnthill(PlayerInventory inventory, TileEntityDomesticatedAnthill tileEntity)
    {
        this.addSlotToContainer(new SlotQueen(tileEntity.inventoryQueen, TileEntityDomesticatedAnthill.INDEX_QUEEN, 80, 17));

        for (int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new SlotLoot(tileEntity.inventoryLoot, 1 + i, 8 + i * 18, 53));
        }

        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlotToContainer(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 - 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(inventory, i1, 8 + i1 * 18, 161 - 18));
        }
    }

    private class SlotQueen extends Slot
    {
        public SlotQueen(IInventory inventoryIn, int index, int xPosition, int yPosition)
        {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return stack.getItem() == HatedMobs.RED_ANT_QUEEN || stack.getItem() == HatedMobs.BLACK_ANT_QUEEN;
        }
    }

    private class SlotLoot extends Slot
    {
        public SlotLoot(IInventory inventoryIn, int index, int xPosition, int yPosition)
        {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return false;
        }
    }


}
