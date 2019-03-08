package fr.uranoscopidae.hatedmobs.common.tileentities;

import fr.uranoscopidae.hatedmobs.common.entities.EntityRedAnt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

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

    @Override
    public void update()
    {
        if(world.isRemote)
        {
            return;
        }
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

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        return super.writeToNBT(compound);
    }
}
