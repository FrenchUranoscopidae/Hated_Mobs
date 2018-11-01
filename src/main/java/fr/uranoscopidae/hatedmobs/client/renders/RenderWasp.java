package fr.uranoscopidae.hatedmobs.client.renders;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntityGiantSpider;
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

public class RenderWasp extends RenderLiving<EntityWasp>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(HatedMobs.MODID, "textures/entity/wasp.png");

    public RenderWasp(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, loadModel(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture (EntityWasp entity)
    {
        return TEXTURE;
    }

    public static TabulaModel loadModel ()
    {
        try
        {
            return new TabulaModel(TabulaModelHandler.INSTANCE.loadTabulaModel("assets/hatedmobs/models/entity/wasp"), new Animator());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void preRenderCallback (EntityWasp entitylivingbaseIn,float partialTickTime)
    {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        GlStateManager.translate(0, 0.05, 0);
        double scale = 0.45/2;
        GlStateManager.scale(scale, scale, scale);
    }

    public static class Animator implements ITabulaModelAnimator
    {
        @Override
        public void setRotationAngles(TabulaModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
        {
            model.getCube("wing_left").scaleX = 0.001f;
            model.getCube("wing_right").scaleX = 0.001f;

            AdvancedModelRenderer abdomen1 = model.getCube("abdomen1");
            AdvancedModelRenderer abdomen2 = model.getCube("abdomen2");
            AdvancedModelRenderer abdomen3 = model.getCube("abdomen3");
            model.chainWave(new AdvancedModelRenderer[]{abdomen1, abdomen2, abdomen3}, 0.15f, (float) (Math.PI/18), 1f, ageInTicks, 0.4f);

            if(!entity.onGround)
            {
                AdvancedModelRenderer wingLeft = model.getCube("wing_left");
                AdvancedModelRenderer wingRight = model.getCube("wing_right");
                wingLeft.rotateAngleX = wingLeft.rotateAngleX + (-(float)Math.PI/2 - wingLeft.rotateAngleX) * 0.7f;
                wingRight.rotateAngleX = wingRight.rotateAngleX + (-(float)Math.PI/2 - wingRight.rotateAngleX) * 0.7f;
                model.swing(model.getCube("wing_left"), 2f ,(float)Math.PI, false, 0, 1, ageInTicks, limbSwingAmount);
                model.swing(model.getCube("wing_right"), 2f ,(float)Math.PI, true, 0, 1, ageInTicks, limbSwingAmount);
            }
            else
            {
                for (int i = 1; i <= 6; i++)
                {
                    AdvancedModelRenderer legOrigin = model.getCube("leg_origin" +i);
                    AdvancedModelRenderer lowerLeg = model.getCube("lower_leg" +i);
                    AdvancedModelRenderer upperLeg = model.getCube("upper_leg" +i);
                    model.chainFlap(new AdvancedModelRenderer[]{legOrigin, lowerLeg, upperLeg}, 0.5f, (float) (Math.PI/8), 1f + i * 5, limbSwing, limbSwingAmount);
                }
            }
        }
    }
}
