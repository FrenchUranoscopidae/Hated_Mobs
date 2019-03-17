package fr.uranoscopidae.hatedmobs.common.entities.entityai;

import fr.uranoscopidae.hatedmobs.common.entities.EntitySlug;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAINomNomCultures extends EntityAIBase
{
    EntitySlug slug;

    public EntityAINomNomCultures(EntitySlug slug)
    {
        this.slug = slug;
    }

    @Override
    public boolean shouldExecute()
    {
        return false;
    }
}
