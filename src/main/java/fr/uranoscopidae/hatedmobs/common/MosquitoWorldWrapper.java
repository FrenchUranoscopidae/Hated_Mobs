package fr.uranoscopidae.hatedmobs.common;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSetMultimap;
import fr.uranoscopidae.hatedmobs.common.blocks.BlockNet;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Send (almost) everything to a World delegate. Falsifies blocks for entities
 */
public class MosquitoWorldWrapper extends FalsifiedWorld
{

    public MosquitoWorldWrapper(World world)
    {
        super(world);
    }

    @Override
    public IBlockState map(IBlockState blockState, int x, int y, int z)
    {
        if(blockState == Blocks.GLASS.getDefaultState())
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockStainedGlass)
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockPane)
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockFence)
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockFenceGate)
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockDoor)
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockTrapDoor)
        {
            return Blocks.AIR.getDefaultState();
        }

        if(blockState.getBlock() instanceof BlockNet)
        {
            return Blocks.BEDROCK.getDefaultState();
        }

        return blockState;
    }
}
