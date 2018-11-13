package fr.uranoscopidae.hatedmobs.client.renders;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntityGiantSpider;
import fr.uranoscopidae.hatedmobs.common.entities.EntityScorpion;
import fr.uranoscopidae.hatedmobs.common.entities.EntityToad;
import fr.uranoscopidae.hatedmobs.common.entities.EntityWasp;
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

public class RenderScorpion extends RenderLiving<EntityScorpion> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(HatedMobs.MODID, "textures/entity/scorpion.png");

    public RenderScorpion(RenderManager rendermanagerIn) {
        super(rendermanagerIn, loadModel(), 0.5f);
    }

    public static TabulaModel loadModel() {
        try {
            return new TabulaModel(TabulaModelHandler.INSTANCE.loadTabulaModel("assets/hatedmobs/models/entity/scorpion"), new RenderScorpion.Animator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityScorpion entity)
    {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback (EntityScorpion entitylivingbaseIn, float partialTickTime)
    {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        GlStateManager.translate(0, 1, 0);
        //double scale = 0.45/2;
        //GlStateManager.scale(scale, scale, scale);
    }

    public static class Animator implements ITabulaModelAnimator
    {
        @Override
        public void setRotationAngles(TabulaModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
        {
            EntityScorpion scorpion = (EntityScorpion)entity;

            for (int i = 1; i <= 4; i++)
            {
                AdvancedModelRenderer legOrigin = model.getCube("leg_origin_right" +i);
                AdvancedModelRenderer lowerLeg = model.getCube("lower_leg_right" +i);
                AdvancedModelRenderer upperLeg = model.getCube("upper_leg_right" +i);
                model.chainFlap(new AdvancedModelRenderer[]{legOrigin, lowerLeg, upperLeg}, 0.5f, (float) (Math.PI/8), 1f + i * 5, limbSwing, limbSwingAmount);
            }

            for (int i = 1; i <= 4; i++)
            {
                AdvancedModelRenderer legOrigin = model.getCube("leg_origin_left" +i);
                AdvancedModelRenderer lowerLeg = model.getCube("lower_leg_left" +i);
                AdvancedModelRenderer upperLeg = model.getCube("upper_leg_left" +i);
                model.chainFlap(new AdvancedModelRenderer[]{legOrigin, lowerLeg, upperLeg}, 0.5f, (float) (Math.PI/8), 1f + i * 5, limbSwing, limbSwingAmount);
            }

            AdvancedModelRenderer tail1 = model.getCube("tail1");
            AdvancedModelRenderer tail2 = model.getCube("tail2");
            AdvancedModelRenderer tail3 = model.getCube("tail3");
            AdvancedModelRenderer tail4 = model.getCube("tail4");
            AdvancedModelRenderer tail5 = model.getCube("tail5");
            AdvancedModelRenderer tail6 = model.getCube("tail6");
            model.chainWave(new AdvancedModelRenderer[]{tail1, tail2, tail3, tail4, tail5, tail6}, 0.15f, (float) (Math.PI/18), 1f, ageInTicks, 0.4f);
        }

    }
}
