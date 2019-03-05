package fr.uranoscopidae.hatedmobs.client.renders;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntityRedAnt;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderRedAnt extends RenderLiving<EntityRedAnt>
{
    private static final ResourceLocation SILK_SPIDER_TEXTURES = new ResourceLocation(HatedMobs.MODID,"textures/entity/silk_spider");

    public RenderRedAnt(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, new ModelCow(), 1f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityRedAnt entity)
    {
        return SILK_SPIDER_TEXTURES;
    }
}
