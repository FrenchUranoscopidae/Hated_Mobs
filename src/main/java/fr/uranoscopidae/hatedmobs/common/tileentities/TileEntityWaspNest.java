package fr.uranoscopidae.hatedmobs.common.tileentities;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntityWasp;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityWaspNest extends TileEntity implements ITickable
{
    public static final int DISTANCE = 20;
    private int cooldown;

    @Override
    public void update()
    {
        if(world.isRemote)
        {
           return;
        }

        if(cooldown-- >= 0)
        {
            return;
        }

        cooldown = 20*60;

        double xoffset = (Math.random() * 2 - 1) * DISTANCE;
        double zoffset = (Math.random() * 2 - 1) * DISTANCE;
        double yoffset = (Math.random() * 2 - 1) * 4;

        BlockPos.PooledMutableBlockPos spawnPos = BlockPos.PooledMutableBlockPos.retain(this.pos.getX() + xoffset, this.pos.getY() + yoffset, this.pos.getZ() + zoffset);

        if(!world.isAirBlock(spawnPos))
        {
            spawnPos.release();
            return;
        }

        EntityWasp wasp = new EntityWasp(world);
        wasp.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
        world.spawnEntity(wasp);
        spawnPos.release();
    }
}
