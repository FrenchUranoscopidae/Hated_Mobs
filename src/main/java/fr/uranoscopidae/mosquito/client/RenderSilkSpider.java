package fr.uranoscopidae.mosquito.client;

import fr.uranoscopidae.mosquito.ModMosquitos;
import fr.uranoscopidae.mosquito.common.EntitySilkSpider;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RenderSilkSpider extends RenderLiving<EntitySilkSpider>
{
    private static final ResourceLocation SILK_SPIDER_TEXTURES = new ResourceLocation(ModMosquitos.MODID,"textures/entity/silk_spider.png");

    public RenderSilkSpider(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, new ModelSpider(), 1.0F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySilkSpider entity)
    {
        return SILK_SPIDER_TEXTURES;
    }
}
