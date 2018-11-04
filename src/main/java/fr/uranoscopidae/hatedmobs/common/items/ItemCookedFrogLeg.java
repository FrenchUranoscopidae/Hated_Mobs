package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;

public class ItemCookedFrogLeg extends ItemFood
{
    public ItemCookedFrogLeg(int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "cooked_frog_leg"));
        setUnlocalizedName("cooked_frog_leg");
        setCreativeTab(HatedMobs.TAB);
    }
}
