package fr.uranoscopidae.mosquito.common;

import fr.uranoscopidae.mosquito.ModMosquitos;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemSwatter extends Item
{
    public ItemSwatter()
    {
        super();
        setRegistryName(new ResourceLocation(ModMosquitos.MODID, "swatter"));
        setUnlocalizedName("swatter");
        setCreativeTab(ModMosquitos.MOSQUITOS);
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
