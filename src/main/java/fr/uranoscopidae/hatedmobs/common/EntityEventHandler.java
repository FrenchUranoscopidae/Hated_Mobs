package fr.uranoscopidae.hatedmobs.common;

import com.sun.java.accessibility.util.java.awt.TextComponentTranslator;
import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntityMosquito;
import fr.uranoscopidae.hatedmobs.common.entities.EntityWasp;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = HatedMobs.MODID)
public class EntityEventHandler
{
    public static final TextComponentTranslator WAKE_UP_BY_MOSQUITO = new TextComponentTranslator(HatedMobs.MODID + ".wake_up_by_mosquito");
    public static final TextComponentTranslator CANT_SLEEP_INSOMNIA = new TextComponentTranslator(HatedMobs.MODID + ".cant_sleep_insomnia");

    @SubscribeEvent
    public static void eventDrop(LivingDropsEvent event)
    {
        Entity entity = event.getEntity();
        if(entity instanceof SpiderEntity)
        {
            if(((SpiderEntity) entity).getRNG().nextInt(5) == 0)
            {
                ItemStack egg = new ItemStack(HatedMobs.SPIDER_EGG);
                event.getDrops().add(new ItemEntity(entity.world, entity.posX, entity.posY, entity.posZ, egg));
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

        List<PlayerEntity> players = world.getPlayers(PlayerEntity.class, a->true);
        int count = 0;

        for (PlayerEntity player:players)
        {
            if(player.isPlayerFullyAsleep())
            {
                List<EntityMosquito> mosquitoList = world.getEntities(EntityMosquito.class, m->
                {
                    if(m.getDistanceSq(player)<20*20)
                    {
                        Path path = m.getNavigator().getPathToEntityLiving(player);
                        if(path == null)
                        {
                            return false;
                        }

                        PathPoint point = path.getFinalPathPoint();
                        double dist = player.getDistance(point.x, point.y, point.z);
                        if(dist > 1.1f)
                        {
                            return false;
                        }

                        return true;
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
            long timeBeforeMorning = 24000L - world.getGameTime();
            long timeStep = timeBeforeMorning/(count + 1);
            wakeAllPlayers(players, timeBeforeMorning - timeStep);
            long newTime = world.getGameTime() + timeStep;
            world.setGameTime(newTime);
        }
    }

    private static void wakeAllPlayers(List<PlayerEntity> playerList, long timeBeforeMorning)
    {
        for (PlayerEntity entityplayer : playerList.stream().filter(PlayerEntity::isPlayerFullyAsleep).collect(Collectors.toList()))
        {
            entityplayer.wakeUpPlayer(false, false, true);
            entityplayer.sendStatusMessage(WAKE_UP_BY_MOSQUITO, true);
            entityplayer.addPotionEffect(new EffectInstance(HatedMobs.INSOMNIA, (int)timeBeforeMorning));
        }
    }

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent event)
    {
        if(event.player.inventory.armorInventory.get(0).getItem() == HatedMobs.SILK_BOOTS)
        {
            ObfuscationReflectionHelper.setPrivateValue(Entity.class, event.player, false, "isInWeb", "field_70134_J");
        }
    }

    @SubscribeEvent
    public static void playerSleepingBedEvent(PlayerSleepInBedEvent event)
    {
        if(event.getEntityPlayer().isPotionActive(HatedMobs.INSOMNIA))
        {
            event.setResult(PlayerEntity.SleepResult.OTHER_PROBLEM);
            event.getEntityPlayer().sendStatusMessage(CANT_SLEEP_INSOMNIA, true);
        }
    }

    @SubscribeEvent
    public static void entityFleesWasp(EntityJoinWorldEvent event)
    {
        if(event.getEntity() instanceof VillagerEntity)
        {
            VillagerEntity villager = (VillagerEntity)event.getEntity();
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, EntityWasp.class, 8.0F, 0.6D, 0.6D));
        }

        if(event.getEntity() instanceof CreeperEntity)
        {
            CreeperEntity creeper = (CreeperEntity)event.getEntity();
            creeper.goalSelector.addGoal(1, new AvoidEntityGoal<>(creeper, EntityWasp.class, 8.0f, 1.2D, 1.2D));
        }
    }
}
