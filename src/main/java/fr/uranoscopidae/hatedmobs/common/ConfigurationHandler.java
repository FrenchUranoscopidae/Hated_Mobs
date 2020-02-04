package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.Map;

@Config(modid=HatedMobs.MODID)
public class ConfigurationHandler
{
    @Config.LangKey("hatedmobs.mob_toggle")
    @Config.RequiresMcRestart
    public static final MobToggle MOB_TOGGLE = new MobToggle();

    @Config.LangKey("hatedmobs.mob_spawnrate")
    @Config.RequiresMcRestart
    public static final MobFrequency MOB_FREQUENCY = new MobFrequency();

    public static class MobToggle
    {
        @Config.LangKey("entity.hatedmobs.mosquito.name")
        @Config.Comment("Set to false to disable mosquitos")
        public boolean mosquito = true;
        @Config.LangKey("entity.hatedmobs.silk_spider.name")
        @Config.Comment("Set to false to disable silk spiders")
        public boolean silkSpider = true;
        @Config.LangKey("entity.hatedmobs.giant_spider.name")
        @Config.Comment("Set to false to disable giant spiders")
        public boolean giantSpider = true;
        @Config.LangKey("entity.hatedmobs.wasp.name")
        @Config.Comment("Set to false to disable wasps")
        public boolean wasp = true;
        @Config.LangKey("entity.hatedmobs.toad.name")
        @Config.Comment("Set to false to disable toads")
        public boolean toad = true;
        @Config.LangKey("entity.hatedmobs.scorpion.name")
        @Config.Comment("Set to false to disable scorpions")
        public boolean scorpion = true;
        @Config.LangKey("hatedmobs.ant_toggle")
        @Config.Comment("Set to false to disable ants")
        public boolean ant = true;
        @Config.LangKey("entity.hatedmobs.slug.name")
        @Config.Comment("Set to false to disable slugs")
        public boolean slug = true;
    }

    public static class MobFrequency
    {
        @Config.LangKey("hatedmobs.mosquito_biomes")
        @Config.Comment("Biome to mosquito spawnrate mapping, more is higher frequency. Keys are biome IDs")
        public Map<String, Integer> mosquito = new HashMap<String, Integer>()
        {
            {
                for(Biome biome : Biome.REGISTRY)
                {
                    put(biome.getRegistryName().toString(), 75);
                }
                put(Biomes.DESERT.getRegistryName().toString(), 10);
                put(Biomes.DESERT_HILLS.getRegistryName().toString(), 10);
                put(Biomes.MUTATED_DESERT.getRegistryName().toString(), 10);
                put(Biomes.JUNGLE.getRegistryName().toString(), 100);
                put(Biomes.JUNGLE_EDGE.getRegistryName().toString(), 100);
                put(Biomes.JUNGLE_HILLS.getRegistryName().toString(), 100);
                put(Biomes.MUTATED_JUNGLE.getRegistryName().toString(), 100);
                put(Biomes.MUTATED_JUNGLE_EDGE.getRegistryName().toString(), 100);
            }
        };

        @Config.LangKey("hatedmobs.mosquito_default_name")
        @Config.Comment("Default mosquito spawnrate in all biomes that are not defined in the biome list")
        public int mosquitoDefault = 75;

        @Config.LangKey("hatedmobs.giant_spider_biomes")
        @Config.Comment("Biome to giant spider spawnrate mapping, more is higher frequency. Keys are biome IDs")
        public Map<String, Integer> giantSpider = new HashMap<String, Integer>()
        {
            {
                for(Biome biome : Biome.REGISTRY)
                {
                    if(biome != Biomes.HELL && biome != Biomes.SKY)
                    {
                        put(biome.getRegistryName().toString(), 1);
                    }
                }
            }
        };

        @Config.LangKey("hatedmobs.giant_spider_default_name")
        @Config.Comment("Default giant spider spawnrate in all biomes that are not defined in the biome list")
        public int giantSpiderDefault = 1;


        @Config.LangKey("hatedmobs.toad_biomes")
        @Config.Comment("Biome to toad spawnrate mapping, more is higher frequency. Keys are biome IDs")
        public Map<String, Integer> toad = new HashMap<String, Integer>()
        {
            {
                for(Biome biome : Biome.REGISTRY)
                {
                    if(biome != Biomes.HELL && biome != Biomes.SKY)
                    {
                        put(biome.getRegistryName().toString(), 100);
                    }
                }
            }
        };

        @Config.LangKey("hatedmobs.toad_default_name")
        @Config.Comment("Default toad spawnrate in all biomes that are not defined in the biome list")
        public int toadDefault = 100;

        @Config.LangKey("hatedmobs.scorpion_biomes")
        @Config.Comment("Biome to scorpion spawnrate mapping, more is higher frequency. Keys are biome IDs")
        public Map<String, Integer> scorpion = new HashMap<String, Integer>()
        {
            {
                for(Biome biome : Biome.REGISTRY)
                {
                    put(biome.getRegistryName().toString(), 0);
                }
                put(Biomes.DESERT.getRegistryName().toString(), 50);
                put(Biomes.DESERT_HILLS.getRegistryName().toString(), 50);
                put(Biomes.MUTATED_DESERT.getRegistryName().toString(), 50);
            }
        };

        @Config.LangKey("hatedmobs.scorpion_default_name")
        @Config.Comment("Default scorpion spawnrate in all biomes that are not defined in the biome list")
        public int scorpionDefault = 0;

        @Config.LangKey("hatedmobs.slug_biomes")
        @Config.Comment("Biome to slug spawnrate mapping, more is higher frequency. Keys are biome IDs")
        public Map<String, Integer> slug = new HashMap<String, Integer>()
        {
            {
                for(Biome biome : Biome.REGISTRY)
                {
                    if(biome != Biomes.HELL && biome != Biomes.SKY)
                    {
                        put(biome.getRegistryName().toString(), 100);
                    }
                }
                put(Biomes.DESERT.getRegistryName().toString(), 0);
                put(Biomes.DESERT_HILLS.getRegistryName().toString(), 0);
                put(Biomes.MUTATED_DESERT.getRegistryName().toString(), 0);
            }
        };

        @Config.LangKey("hatedmobs.slug_default_name")
        @Config.Comment("Default slug spawnrate in all biomes that are not defined in the biome list")
        public int slugDefault = 0;

        @Config.LangKey("hatedmobs.anthill_spawner_biomes")
        public Map<String, Integer> anthillSpawner = new HashMap<String, Integer>()
        {
            {
                for(Biome biome : Biome.REGISTRY)
                {
                    if(biome != Biomes.HELL && biome != Biomes.SKY)
                    {
                        put(biome.getRegistryName().toString(), 30);
                    }
                }
            }
        };

        @Config.LangKey("hatedmobs.anthill_spawner_default_name")
        public int anthillSpawnerDefault = 30;

        @Config.LangKey("hatedmobs.waspnest_spawner_default_name")
        @Config.Comment("The smaller the value is the more nests will spawn. Can't  be lower than 0.")
        public int waspnestSpawnerDefault = 10;
    }
}
