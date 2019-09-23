package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.item.Food;
import net.minecraft.util.ResourceLocation;

public class ItemCookedFrogLeg extends Food
{
    public ItemCookedFrogLeg(int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "cooked_frog_leg"));
        setUnlocalizedName("cooked_frog_leg");
        setCreativeTab(HatedMobs.TAB);
    }
}
