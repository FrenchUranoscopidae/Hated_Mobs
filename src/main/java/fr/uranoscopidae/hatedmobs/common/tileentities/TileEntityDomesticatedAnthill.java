package fr.uranoscopidae.hatedmobs.common.tileentities;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.uranoscopidae.hatedmobs.common.entities.EntityRedAnt;
import fr.uranoscopidae.hatedmobs.common.items.ItemBlackAntQueen;
import fr.uranoscopidae.hatedmobs.common.items.ItemRedAntQueen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileEntityDomesticatedAnthill extends TileEntity implements ITickable
{
    private Map<EntityMob, List<EntityRedAnt>> map = new HashMap<>();

    public static final int INDEX_QUEEN = 0;
    public InventoryBasic inventory = new InventoryBasic("hatedmobs.inventory.domesticatedanthill", false, 10);

    public static final int RADIUS = 20;
    private int xoffset = -RADIUS;
    private int yoffset = -RADIUS;
    private int zoffset = -RADIUS;

    private Item currentItem;

    public int progressTick;
    public static final int DURATION_IN_TICKS = /*16 * 60*/3 * 20;
    static List<LootItem> loot = new ArrayList<>();

    public static void loadLoot(File file)
    {
        Gson gson = new Gson();
        try {
            JsonArray objectList = gson.fromJson(new FileReader(file), JsonArray.class);
            for (int i = 0; i < objectList.size(); i++)
            {
                JsonObject itemEntry = objectList.get(i).getAsJsonObject();
                ResourceLocation itemName = new ResourceLocation(itemEntry.getAsJsonPrimitive("name").getAsString());
                int itemWeight = itemEntry.getAsJsonPrimitive("weight").getAsInt();
                loot.add(new LootItem(itemName, itemWeight));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update()
    {
        ItemStack stack = inventory.getStackInSlot(INDEX_QUEEN);

        if(progressTick == 0)
        {
            if(!stack.isEmpty())
            {
                progressTick = DURATION_IN_TICKS;
                currentItem = stack.getItem();
                stack.shrink(1);
            }
        }

        if(progressTick > 0)
        {
            progressTick--;
            if(currentItem instanceof ItemBlackAntQueen)
            {
                if (progressTick % (/*60 **/ 20) == 0)
                {
                    if(!world.isRemote) {
                        if(world.rand.nextBoolean())
                        {
                            ResourceLocation id = WeightedRandom.getRandomItem(world.rand, loot).itemName;
                            Item item = Item.REGISTRY.getObject(id);
                            inventory.addItem(new ItemStack(item));
                        }
                    }
                }
            }
        }

        if(world.isRemote || progressTick <= 0)
        {
            return;
        }

        if(currentItem instanceof ItemRedAntQueen)
        {
            for (int i = 0; i < 100; i++)
            {
                zoffset++;
                if (zoffset >= RADIUS)
                {
                    yoffset++;
                    zoffset = -RADIUS;
                }

                if (yoffset >= RADIUS)
                {
                    xoffset++;
                    yoffset = -RADIUS;
                }

                if (xoffset >= RADIUS)
                {
                    xoffset = -RADIUS;
                }

                BlockPos blockPos = pos.add(xoffset, yoffset, zoffset);
                AxisAlignedBB aabb = new AxisAlignedBB(blockPos);

                List<Entity> entityList = world.getEntitiesWithinAABB(Entity.class, aabb);

                for (int j = 0; j < entityList.size(); j++)
                {
                    Entity entity = entityList.get(j);

                    if (entity instanceof EntityMob)
                    {
                        List<EntityRedAnt> redAntList = map.get(entity);
                        boolean canSpawn = redAntList == null
                                || redAntList.stream().noneMatch(Entity::isEntityAlive);

                        if(canSpawn)
                        {
                            if(redAntList == null)
                            {
                                redAntList = new ArrayList<>();
                            }

                            redAntList.clear();

                            for (int k = 0; k < Math.random() * 3 + 3; k++)
                            {
                                EntityRedAnt redAnt = new EntityRedAnt(world);
                                redAnt.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                                world.spawnEntity(redAnt);
                                redAnt.spawnExplosionParticle();
                                redAntList.add(redAnt);
                            }
                            map.put((EntityMob)entity, redAntList);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        for (int i = 0; i < 9; i++)
        {
            ItemStack itemStack = new ItemStack(compound.getCompoundTag("Stack" + i));
            inventory.setInventorySlotContents(i, itemStack);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        for (int i = 0; i < 9; i++)
        {
            ItemStack itemStack = inventory.getStackInSlot(i);
            compound.setTag("Stack" + i, itemStack.writeToNBT(new NBTTagCompound()));
        }

        return super.writeToNBT(compound);
    }

    private static class LootItem extends WeightedRandom.Item
    {
        ResourceLocation itemName;

        public LootItem(ResourceLocation itemName, int itemWeight)
        {
            super(itemWeight);
            this.itemName = itemName;
        }
    }
}
