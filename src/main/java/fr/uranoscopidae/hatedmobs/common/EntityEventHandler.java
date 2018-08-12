package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = HatedMobs.MODID)
public class EntityEventHandler
{
    public static final TextComponentTranslation WAKE_UP_BY_MOSQUITO = new TextComponentTranslation(HatedMobs.MODID + ".wake_up_by_mosquito");
    @SubscribeEvent
    public static void eventDrop(LivingDropsEvent event)
    {
        Entity entity = event.getEntity();
        if(entity instanceof EntitySpider)
        {
            if(((EntitySpider) entity).getRNG().nextInt(5) == 0)
            {
                ItemStack egg = new ItemStack(Items.SPAWN_EGG);
                egg.getOrCreateSubCompound("EntityTag").setString("id", HatedMobs.MODID + ":silk_spider");
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, egg));
            }
        }
    }

    @SubscribeEvent
    public static void worldTickEvent(TickEvent.WorldTickEvent event)
    {
        World world = event.world;

        if(world.isRemote)
        {
            return;
        }

        List<EntityPlayer> players = world.getPlayers(EntityPlayer.class, a->true);
        int count = 0;

        for (EntityPlayer player:players)
        {
            if(player.isPlayerSleeping())
            {
                List<EntityMosquito> mosquitoList = world.getEntities(EntityMosquito.class, m->
                {
                    if(m.getDistanceSq(player)<20*20)
                    {
                        return m.getNavigator().getPathToEntityLiving(player) != null;
                    }
                    else return false;
                });
                if(!mosquitoList.isEmpty())
                {
                    count++;
                }
            }
        }

        if(count > 0)
        {
            long timeBeforeMorning = 24000L - world.getWorldTime();
            long timeStep = timeBeforeMorning/(count + 1);
            wakeAllPlayers(players);
            long newTime = world.getWorldTime() + timeStep;
            world.setWorldTime(newTime);
        }
    }

    private static void wakeAllPlayers(List<EntityPlayer> playerList)
    {
        for (EntityPlayer entityplayer : playerList.stream().filter(EntityPlayer::isPlayerSleeping).collect(Collectors.toList()))
        {
            entityplayer.wakeUpPlayer(false, false, true);
            entityplayer.sendStatusMessage(WAKE_UP_BY_MOSQUITO, true);
        }
    }
}
