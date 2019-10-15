package fr.uranoscopidae.hatedmobs.common.biomes;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntityGiantSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.world.biome.Biome;

public class BiomeSpiderForest extends Biome
{
    public BiomeSpiderForest()
    {
        super(new BiomeProperties("spider_forest").setBaseHeight(0.1f).setHeightVariation(0.2f).setWaterColor(7424));

        topBlock = HatedMobs.WEB_BLOCK.getDefaultState();
        fillerBlock = HatedMobs.WEB_BLOCK.getDefaultState();

        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();

        this.spawnableCreatureList.add(new SpawnListEntry(EntityGiantSpider.class, 100, 1, 2));
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySpider.class, 10, 2, 4));
    }
}
