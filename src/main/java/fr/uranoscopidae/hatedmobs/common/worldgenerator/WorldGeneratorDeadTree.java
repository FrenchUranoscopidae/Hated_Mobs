package fr.uranoscopidae.hatedmobs.common.worldgenerator;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class WorldGeneratorDeadTree extends WorldGenAbstractTree
{
    public WorldGeneratorDeadTree(boolean notify)
    {
        super(notify);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        WorldServer worldserver = (WorldServer)worldIn;
        MinecraftServer minecraftServer = worldserver.getMinecraftServer();
        TemplateManager templateManager = worldserver.getStructureTemplateManager();
        Template template = templateManager.getTemplate(minecraftServer, new ResourceLocation(HatedMobs.MODID, "dead_tree"));

        PlacementSettings placementSettings = new PlacementSettings().setIgnoreEntities(true).setReplacedBlock(Blocks.STONE);

        BlockPos.MutableBlockPos tmp = new BlockPos.MutableBlockPos(position);
        tmp.setPos(tmp.getX()+2, tmp.getY()+0, tmp.getZ()+2);

        boolean foundDirt = false;
        for (int y = 0; y < 4; y++)
        {
            tmp.setY(tmp.getY()-1);
            if(worldIn.getBlockState(tmp).getBlock() instanceof BlockGrass || worldIn.getBlockState(tmp).getBlock() instanceof BlockDirt)
            {
                foundDirt = true;
                break;
            }
            if(!worldIn.isAirBlock(tmp))
            {
                break;
            }
        }
        if(!foundDirt)
        {
            return false;
        }

        template.addBlocksToWorld(worldIn, tmp.add(0, 1, 0), placementSettings);
        return true;
    }
}

