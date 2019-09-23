package fr.uranoscopidae.hatedmobs.client.renders;

import com.google.common.base.Optional;
import com.mojang.blaze3d.platform.GlStateManager;
import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntityToad;
import net.ilexiconn.llibrary.client.model.tabula.ITabulaModelAnimator;
import net.ilexiconn.llibrary.client.model.tabula.TabulaModel;
import net.ilexiconn.llibrary.client.model.tabula.TabulaModelHandler;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderLivingEvent;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.io.IOException;

public class RenderToad  extends RenderLivingEvent<EntityToad>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(HatedMobs.MODID, "textures/entity/toad.png");

    public RenderToad(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, loadModel(), 0.5f);
    }

    public static TabulaModel loadModel ()
    {
        try
        {
            return new TabulaModel(TabulaModelHandler.INSTANCE.loadTabulaModel("assets/hatedmobs/models/entity/toad"), new RenderToad.Animator());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityToad entity)
    {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback (EntityToad entitylivingbaseIn,float partialTickTime)
    {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        GlStateManager.translated(0, 0.05, 0);
        double scale = 0.45;

        if(entitylivingbaseIn.isChild())
        {
            scale *= 0.5;
        }

        GlStateManager.scaled(scale, scale, scale);
    }

    @Override
    public void doRender(EntityToad entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        Optional<BlockPos> pos = entity.getTongueTarget();

        if(pos.isPresent())
        {
            BlockPos tonguePos = pos.get();
            double dx = tonguePos.getX() + 0.5 - entity.posX;
            double dy = tonguePos.getY() + 0.5 - entity.posY - 0.25;
            double dz = tonguePos.getZ() + 0.5 - entity.posZ;

            GlStateManager.pushMatrix();
            GlStateManager.translated(x, y, z);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
            buffer.pos(0, 0.25, 0).color(0xff, 0xb8, 0xca, 0xff).endVertex();
            buffer.pos(dx, dy, dz).color(0xff, 0xb8, 0xca, 0xff).endVertex();
            GlStateManager.disableTexture2D();
            GlStateManager.glLineWidth(5);
            tessellator.draw();
            GlStateManager.enableTexture2D();
            GlStateManager.popMatrix();
        }
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    public static class Animator implements ITabulaModelAnimator
    {
        @Override
        public void setRotationAngles(TabulaModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
        {
            EntityToad toad = (EntityToad)entity;
            float yawOffset = interpolateRotation(toad.prevRenderYawOffset, toad.renderYawOffset, 1);
            float yawHead = interpolateRotation(toad.prevRotationYawHead, toad.rotationYawHead, 1);
            float modelHeadYaw = yawHead - yawOffset;
            model.getCube("head").rotateAngleY = modelHeadYaw * (float)Math.PI/180;
            model.getCube("head").rotateAngleX = toad.rotationPitch * (float)Math.PI/180;

            if(!entity.onGround)
            {
                model.getCube("upper_leg_right").rotateAngleX = 95;
                model.getCube("lower_leg_right").rotateAngleX = 80;
                model.getCube("foot_right").rotateAngleY = 85;
                model.getCube("foot_right").rotateAngleX = 85;
                model.getCube("foot_right").rotateAngleZ = 0;

                model.getCube("upper_leg_left").rotateAngleX = 95 + 96;
                model.getCube("lower_leg_left").rotateAngleX = -80;
                model.getCube("foot_left").rotateAngleY = -85;
                model.getCube("foot_left").rotateAngleX = -85;
                model.getCube("foot_left").rotateAngleZ = 0;
            }
        }

        protected float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks)
        {
            float f;

            for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F)
            {
                ;
            }

            while (f >= 180.0F)
            {
                f -= 360.0F;
            }

            return prevYawOffset + partialTicks * f;
        }
    }


}
