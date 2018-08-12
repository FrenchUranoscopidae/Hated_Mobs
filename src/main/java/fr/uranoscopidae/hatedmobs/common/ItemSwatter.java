package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemSwatter extends Item
{
    public ItemSwatter()
    {
        super();
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "swatter"));
        setUnlocalizedName("swatter");
        setCreativeTab(HatedMobs.TAB);
        setMaxStackSize(1);
        setMaxDamage(50);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        return true;
    }
}
