package fr.uranoscopidae.hatedmobs.common.entities;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntitySlug extends EntityMob
{
    public EntitySlug(World worldIn)
    {
        super(worldIn);
        setSize(0.75f, 0.5f);
    }
}
