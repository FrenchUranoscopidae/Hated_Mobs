package fr.uranoscopidae.hatedmobs.common.items;

import com.google.common.collect.Multimap;
import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class ItemSilkBoots extends ArmorItem
{
    public ItemSilkBoots()
    {
        super(HatedMobs.silkBootsMaterial, 0, EquipmentSlotType.FEET);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "silk_boots"));
        setUnlocalizedName("silk_boots");
        getCreativeTabs(HatedMobs.TAB);
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
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);

        if(equipmentSlot == EquipmentSlotType.FEET)
        {
            multimap.put(HatedMobs.modifier.getName(), HatedMobs.modifier);
        }
        return multimap;
    }
}
