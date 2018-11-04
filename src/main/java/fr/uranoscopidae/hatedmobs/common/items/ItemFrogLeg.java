package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemFrogLeg extends ItemFood
{
    public ItemFrogLeg(int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "frog_leg"));
        setUnlocalizedName("frog_leg");
        setCreativeTab(HatedMobs.TAB);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        super.onFoodEaten(stack, worldIn, player);
        player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("poison"), 10*60, 0));
    }
}
