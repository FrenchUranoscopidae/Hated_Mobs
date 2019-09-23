package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.entity.LivingEntity;
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
        getCreativeTabs(HatedMobs.TAB);
        setMaxStackSize(1);
        setDamage(50);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        stack.damageItem(1, attacker);
        return true;
    }
}
