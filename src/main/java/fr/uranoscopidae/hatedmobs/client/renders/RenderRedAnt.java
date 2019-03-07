package fr.uranoscopidae.hatedmobs.client.renders;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntityGiantSpider;
import fr.uranoscopidae.hatedmobs.common.entities.EntityRedAnt;
import fr.uranoscopidae.hatedmobs.common.entities.EntityScorpion;
import net.ilexiconn.llibrary.client.model.tabula.ITabulaModelAnimator;
import net.ilexiconn.llibrary.client.model.tabula.TabulaModel;
import net.ilexiconn.llibrary.client.model.tabula.TabulaModelHandler;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.IOException;

public class RenderRedAnt extends RenderLiving<EntityRedAnt>
{
    private static final ResourceLocation RED_ANT_TEXTURE = new ResourceLocation(HatedMobs.MODID,"textures/entity/red_ant.png");

    public RenderRedAnt(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, loadModel(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityRedAnt entity)
    {
        return RED_ANT_TEXTURE;
    }

    public static TabulaModel loadModel()
    {
        try
        {
            return new TabulaModel(TabulaModelHandler.INSTANCE.loadTabulaModel("assets/hatedmobs/models/entity/ant"), new RenderRedAnt.Animator());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void preRenderCallback(EntityRedAnt entitylivingbaseIn, float partialTickTime)
    {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        double scale = 0.5;
        GlStateManager.scale(scale, scale, scale);
    }

    private static class Animator implements ITabulaModelAnimator
    {
        @Override
        public void setRotationAngles(TabulaModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
        {
            AdvancedModelRenderer antennaLeft1 = model.getCube("antenna_base_left");
            AdvancedModelRenderer antennaLeft2 = model.getCube("antenna_tip_left");
            AdvancedModelRenderer antennaRight1 = model.getCube("antenna_base_right");
            AdvancedModelRenderer antennaRight2 = model.getCube("antenna_tip_right");
            model.chainWave(new AdvancedModelRenderer[]{antennaLeft1, antennaLeft2}, 0.15f, (float) (Math.PI/18), 1f, ageInTicks, 0.4f);
            model.chainWave(new AdvancedModelRenderer[]{antennaRight1, antennaRight2}, 0.15f, (float) (Math.PI/18), 1f, ageInTicks, 0.4f);

            AdvancedModelRenderer abdomen = model.getCube("abdomen");
            model.chainWave(new AdvancedModelRenderer[]{abdomen}, 0.15f, (float) (Math.PI/18), 1f, ageInTicks, 0.4f);

            for (int i = 1; i <= 3; i++)
            {
                AdvancedModelRenderer legBase = model.getCube("leg_base_right" +i);
                AdvancedModelRenderer legMiddle = model.getCube("leg_middle_right" +i);
                AdvancedModelRenderer legEnd = model.getCube("leg_end_right" +i);
                model.chainFlap(new AdvancedModelRenderer[]{legBase, legMiddle, legMiddle}, 0.5f, (float) (Math.PI/4), 1f + i * 5, limbSwing, limbSwingAmount);
            }

            for (int i = 1; i <= 3; i++)
            {
                AdvancedModelRenderer legBase = model.getCube("leg_base_left" +i);
                AdvancedModelRenderer legMiddle = model.getCube("leg_middle_left" +i);
                AdvancedModelRenderer legEnd = model.getCube("leg_end_left" +i);
                model.chainFlap(new AdvancedModelRenderer[]{legBase, legMiddle, legEnd}, 0.5f, (float) (Math.PI/4), 1f + i * 5, limbSwing, limbSwingAmount);
            }
        }
    }
}
