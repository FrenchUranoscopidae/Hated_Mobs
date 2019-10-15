package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.common.biomes.BiomeSpiderForest;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeInit
{
    public static final Biome SPIDER_FOREST = new BiomeSpiderForest();

    public static void registerBiomes()
    {
        initBiome(SPIDER_FOREST, "spider_forest", BiomeManager.BiomeType.COOL, BiomeDictionary.Type.FOREST);
    }

    private static Biome initBiome(Biome biome, String name, BiomeManager.BiomeType biomeType, BiomeDictionary.Type types)
    {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        System.out.println("spider biome loaded");
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, 1000));
        BiomeManager.addSpawnBiome(biome);
        System.out.println("biome added");
        return biome;
    }
}
