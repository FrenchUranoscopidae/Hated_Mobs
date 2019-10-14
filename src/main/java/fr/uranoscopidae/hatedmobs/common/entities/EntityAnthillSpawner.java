package fr.uranoscopidae.hatedmobs.common.entities;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityAntHive;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Biomes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityAnthillSpawner extends EntityMob
{
    public EntityAnthillSpawner(World worldIn)
    {
        super(worldIn);
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        IBlockState blockState = world.getBlockState(getPosition());
        if(blockState.getBlock().isAir(blockState, world, getPosition()))
        {
            if(!world.isSideSolid(getPosition().down(), EnumFacing.UP))
            {
                return;
            }

            if(world.getBiome(getPosition()) == Biomes.SKY || world.getBiome(getPosition()) == Biomes.HELL)
            {
                return;
            }

            world.setBlockState(getPosition(), HatedMobs.ANT_HIVE.getDefaultState());

            TileEntityAntHive antHive = (TileEntityAntHive) world.getTileEntity(getPosition());
        }
        System.out.println("anthill spawned");

        attackEntityFrom(DamageSource.MAGIC, 10000);
    }
}
