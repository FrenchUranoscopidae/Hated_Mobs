package fr.uranoscopidae.hatedmobs.client.renders;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntityGiantSpider;
import fr.uranoscopidae.hatedmobs.common.entities.EntityTamedGiantSpider;
import net.ilexiconn.llibrary.client.model.tabula.ITabulaModelAnimator;
import net.ilexiconn.llibrary.client.model.tabula.TabulaModel;
import net.ilexiconn.llibrary.client.model.tabula.TabulaModelHandler;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.IOException;

public class RenderTamedGiantSpider extends RenderLiving<EntityTamedGiantSpider>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(HatedMobs.MODID, "textures/entity/tamed_giant_spider.png");
    public static final ResourceLocation TEXTURE_SADDLE = new ResourceLocation(HatedMobs.MODID, "textures/entity/tamed_giant_spider_saddle.png");

    public RenderTamedGiantSpider(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, loadModel(), 2);
    }

    public static TabulaModel loadModel()
    {
        try
        {
            return new TabulaModel(TabulaModelHandler.INSTANCE.loadTabulaModel("assets/hatedmobs/models/entity/giant_spider"), new RenderTamedGiantSpider.Animator());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void preRenderCallback(EntityTamedGiantSpider entitylivingbaseIn, float partialTickTime)
    {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        GlStateManager.translate(0, 0.65, 1);
        double scale = 1.75;
        GlStateManager.scale(scale, scale, scale);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityTamedGiantSpider entity)
    {
        if(entity.getSaddled())
        {
            return TEXTURE_SADDLE;
        }
        else
        {
            return TEXTURE;
        }
    }

    private static class Animator implements ITabulaModelAnimator
    {
        @Override
        public void setRotationAngles(TabulaModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
        {
            EntityTamedGiantSpider spider = (EntityTamedGiantSpider)entity;
            limbSwing = limbSwing/2;
            limbSwingAmount = limbSwingAmount/2;
            for (int i = 1; i <= 8; i++)
            {
                AdvancedModelRenderer legOrigin = model.getCube("leg_origin" +i);
                AdvancedModelRenderer lowerLeg = model.getCube("lower_leg" +i);
                AdvancedModelRenderer upperLeg = model.getCube("upper_leg" +i);
                model.chainFlap(new AdvancedModelRenderer[]{legOrigin, lowerLeg, upperLeg}, 0.5f, (float) (Math.PI/8), 1f + i * 5, limbSwing, limbSwingAmount);
            }

            AdvancedModelRenderer abdomen1 = model.getCube("abdomen1");
            AdvancedModelRenderer abdomen2 = model.getCube("abdomen2");
            AdvancedModelRenderer abdomen3 = model.getCube("abdomen3");
            model.chainWave(new AdvancedModelRenderer[]{abdomen1, abdomen2, abdomen3}, 0.15f, (float) (Math.PI/18), 1f, ageInTicks, 0.4f);

            AdvancedModelRenderer leftMandibula = model.getCube("mandible_left");
            AdvancedModelRenderer rightMandibula = model.getCube("mandible_right");
            model.flap(leftMandibula, 0.15f, (float) (Math.PI/8), false, 1f, 0.5f, ageInTicks, 0.1f);
            model.flap(rightMandibula, 0.15f, (float) (Math.PI/8), false, 1f, -0.5f, ageInTicks, 0.1f);
        }
    }
}
