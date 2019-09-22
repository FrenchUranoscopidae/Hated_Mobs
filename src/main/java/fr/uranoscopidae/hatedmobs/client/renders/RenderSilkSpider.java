package fr.uranoscopidae.hatedmobs.client.renders;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntitySilkSpider;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderSilkSpider extends RenderLiving<EntitySilkSpider>
{
    private static final ResourceLocation SILK_SPIDER_TEXTURES = new ResourceLocation(HatedMobs.MODID,"textures/entity/silk_spider.png");

    public RenderSilkSpider(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, new ModelSpider(), 1.0F/4);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySilkSpider entity)
    {
        return SILK_SPIDER_TEXTURES;
    }

    @Override
    protected void preRenderCallback(EntitySilkSpider entitylivingbaseIn, float partialTickTime)
    {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        GlStateManager.scaled(0.25, 0.25, 0.25);
    }
}
