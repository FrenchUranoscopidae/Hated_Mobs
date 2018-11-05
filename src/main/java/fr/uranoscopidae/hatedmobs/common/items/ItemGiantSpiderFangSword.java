package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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
        setMaxDamage(132);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        target.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("poison"), 5*60, 0));

        if(attacker instanceof EntityPlayer)
        {
            target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)attacker), 6);
        }
        else
        {
            target.attackEntityFrom(DamageSource.causeMobDamage(attacker), 6);
        }
        return true;
    }
}
