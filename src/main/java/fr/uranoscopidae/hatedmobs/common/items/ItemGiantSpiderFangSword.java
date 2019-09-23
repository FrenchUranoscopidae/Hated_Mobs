package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class ItemGiantSpiderFangSword extends Item
{
    public ItemGiantSpiderFangSword()
    {
        super();
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "giant_spider_fang_sword"));
        setUnlocalizedName("giant_spider_fang_sword");
        setCreativeTab(HatedMobs.TAB);
        setMaxStackSize(1);
        setDamage(132);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        stack.damageItem(1, attacker);
        target.addPotionEffect(new EffectInstance(Potion.getPotionTypeForName("poison"), 5*60, 0));

        if(attacker instanceof PlayerEntity)
        {
            target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) attacker), 6);
        }
        else
        {
            target.attackEntityFrom(DamageSource.causeMobDamage(attacker), 6);
        }
        return true;
    }
}
