package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntityMosquito;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
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
                ItemStack egg = new ItemStack(HatedMobs.SPIDER_EGG);
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

                        /*for (int i = 0; i < path.getCurrentPathLength(); i++)
                        {
                            Vec3d vector = path.getVectorFromIndex(m, i);
                            blockPos.setPos(vector.x, vector.y, vector.z);
                            AxisAlignedBB aabb = new AxisAlignedBB(blockPos);
                            List<AxisAlignedBB> list = m.world.getCollisionBoxes(null, aabb);
                            System.out.println(m.world.getBlockState(blockPos)+" (real: "+world.getBlockState(blockPos)+") /"+vector);

                            if(!list.isEmpty())
                            {
                                return false;
                            }
                        }*/
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

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent event)
    {
        if(event.player.inventory.armorInventory.get(0).getItem() == HatedMobs.SILK_BOOTS)
        {
            ObfuscationReflectionHelper.setPrivateValue(Entity.class, event.player, false, "isInWeb");
        }
    }
}
