package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemFrogLeg extends Food
{
    public ItemFrogLeg(int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "frog_leg"));
        setUnlocalizedName("frog_leg");
        setCreativeTab(HatedMobs.TAB);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, PlayerEntity player)
    {
        super.onFoodEaten(stack, worldIn, player);
        player.addPotionEffect(new EffectInstance(Potion.getPotionTypeForName("poison"), 10*60, 0));
    }
}
