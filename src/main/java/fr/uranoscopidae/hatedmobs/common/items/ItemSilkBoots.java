package fr.uranoscopidae.hatedmobs.common.items;

import com.google.common.collect.Multimap;
import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemSilkBoots extends ItemArmor
{
    public ItemSilkBoots()
    {
        super(HatedMobs.silkBootsMaterial, 0, EntityEquipmentSlot.FEET);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "silk_boots"));
        setUnlocalizedName("silk_boots");
        setCreativeTab(HatedMobs.TAB);
        setMaxStackSize(1);
    }

    public boolean getIsRepairable(ItemStack input, ItemStack repair)
    {
        if(repair.getItem() == Items.STRING)
        {
            return true;
        }
        return false;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if(equipmentSlot == EntityEquipmentSlot.FEET)
        {
            multimap.put(HatedMobs.modifier.getName(), HatedMobs.modifier);
        }
        return multimap;
    }
}
