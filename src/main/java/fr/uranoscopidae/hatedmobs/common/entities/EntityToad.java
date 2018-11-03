package fr.uranoscopidae.hatedmobs.common.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityToad extends EntityAnimal
{
    public EntityToad(World worldIn)
    {
        super(worldIn);
        setSize(0.25f, 0.25f);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return null;
    }
}
