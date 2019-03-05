package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraftforge.common.config.Config;

@Config(modid=HatedMobs.MODID)
public class ConfigurationHandler
{
    @Config.Name("Mob Toggle")
    @Config.RequiresMcRestart
    public static final MobToggle MOB_TOGGLE = new MobToggle();

    public static class MobToggle
    {
        @Config.Comment("Set to false to disable mosquitos")
        public boolean mosquito = true;
        @Config.Comment("Set to false to disable silk spiders")
        public boolean silkSpider = true;
        @Config.Comment("Set to false to disable giant spiders")
        public boolean giantSpider = true;
        @Config.Comment("Set to false to disable wasps")
        public boolean wasp = true;
        @Config.Comment("Set to false to disable toads")
        public boolean toad = true;
        @Config.Comment("Set to false to disable scorpions")
        public boolean scorpion = true;
        @Config.Comment("Set to false to disable ants")
        public boolean ant = true;
    }
}
