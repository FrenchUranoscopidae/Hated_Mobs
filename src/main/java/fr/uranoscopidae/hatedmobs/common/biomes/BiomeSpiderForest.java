package fr.uranoscopidae.hatedmobs.common.biomes;

import fr.uranoscopidae.hatedmobs.common.entities.EntityGiantSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeSpiderForest extends Biome
{
    public BiomeSpiderForest()
    {
        super(new BiomeProperties("spider_forest").setBaseHeight(0.1f).setHeightVariation(0.2f).setWaterColor(11747072));

        topBlock = Blocks.GRASS.getDefaultState();

        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();

        this.spawnableCreatureList.add(new SpawnListEntry(EntityGiantSpider.class, 100, 1, 2));
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySpider.class, 10, 2, 4));
    }

    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        return 15856113;
    }
}
