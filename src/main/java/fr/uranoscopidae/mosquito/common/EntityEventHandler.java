package fr.uranoscopidae.mosquito.common;

import fr.uranoscopidae.mosquito.ModMosquitos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModMosquitos.MODID)
public class EntityEventHandler
{
    @SubscribeEvent
    public static void eventDrop(LivingDropsEvent event)
    {
        Entity entity = event.getEntity();
        if(entity instanceof EntitySpider)
        {
            if(((EntitySpider) entity).getRNG().nextInt(5) == 0)
            {
                ItemStack egg = new ItemStack(Items.SPAWN_EGG);
                egg.getOrCreateSubCompound("EntityTag").setString("id", ModMosquitos.MODID + ":silk_spider");
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, egg));
            }
        }
    }
}
