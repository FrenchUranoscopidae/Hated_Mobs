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
        super(rendermanagerIn, loadModel(), 1);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture (EntityWasp entity)
    {
        return TEXTURE;
    }

    public static TabulaModel loadModel ()
    {
        try {
            return new TabulaModel(TabulaModelHandler.INSTANCE.loadTabulaModel("assets/hatedmobs/models/entity/wasp"), new Animator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void preRenderCallback (EntityWasp entitylivingbaseIn,float partialTickTime)
    {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        GlStateManager.translate(0, 0.65, 1);
        double scale = 1.75;
        GlStateManager.scale(scale, scale, scale);
    }

    public static class Animator implements ITabulaModelAnimator
    {
        @Override
        public void setRotationAngles(TabulaModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
        {

        }
    }
}
