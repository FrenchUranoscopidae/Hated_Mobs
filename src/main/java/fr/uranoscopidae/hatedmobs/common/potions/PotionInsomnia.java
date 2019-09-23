package fr.uranoscopidae.hatedmobs.common.potions;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
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
    public boolean shouldRender(EffectInstance effect)
    {
        return true;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect)
    {
        return true;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect)
    {
        return true;
    }

    @Override
    public void renderHUDEffect(int x, int y, EffectInstance effect, Minecraft mc, float alpha)
    {
        mc.getTextureManager().bindTexture(TEXTURE);
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 4, 0, 0, 18, 18, 18, 18);
    }

    @Override
    public void renderInventoryEffect(int x, int y, EffectInstance effect, Minecraft mc)
    {
        if(mc.currentScreen != null)
        {
            mc.getTextureManager().bindTexture(TEXTURE);
            Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 8, 0, 0, 18, 18, 18, 18);
        }
    }
}
