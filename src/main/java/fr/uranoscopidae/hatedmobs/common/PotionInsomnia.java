package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionInsomnia extends Potion
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(HatedMobs.MODID, "textures/insomnia.png");

    public PotionInsomnia()
    {
        super(true, 0);
        setPotionName("effect.insomnia");
        setRegistryName(HatedMobs.MODID, "insomnia");
    }

    @Override
    public boolean isInstant()
    {
        return false;
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return duration > 0;
    }

    @Override
    public boolean shouldRender(PotionEffect effect)
    {
        return true;
    }

    @Override
    public boolean shouldRenderInvText(PotionEffect effect)
    {
        return true;
    }

    @Override
    public boolean shouldRenderHUD(PotionEffect effect)
    {
        return true;
    }

    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha)
    {
        mc.getTextureManager().bindTexture(TEXTURE);
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 4, 0, 0, 18, 18, 18, 18);
    }

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        if(mc.currentScreen != null)
        {
            mc.getTextureManager().bindTexture(TEXTURE);
            Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 8, 0, 0, 18, 18, 18, 18);
        }
    }
}
