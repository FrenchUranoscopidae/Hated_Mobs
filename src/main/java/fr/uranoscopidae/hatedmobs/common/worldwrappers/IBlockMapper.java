package fr.uranoscopidae.hatedmobs.common.worldwrappers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public interface IBlockMapper {

    static Map<World, Map<IBlockMapper, World>> falsifiedWorlds = new HashMap<>();

    IBlockState map(IBlockState blockState, int x, int y, int z);

    static World wrap(World world, IBlockMapper mapper) {
        if(!falsifiedWorlds.containsKey(world)) {
            Map<IBlockMapper, World> map = new HashMap<>();
            map.put(mapper, wrapDirect(world, mapper));
            falsifiedWorlds.put(world, map);
        } else {
            Map<IBlockMapper, World> map = falsifiedWorlds.get(world);
            if(!map.containsKey(mapper)) {
                map.put(mapper, wrapDirect(world, mapper));
            }
        }
        return falsifiedWorlds.get(world).get(mapper);
    }

    static World wrapDirect(World world, IBlockMapper mapper) {
        boolean foundClientClass = false;
        try {
            Class<?> worldClientClassDeobf = Class.forName("net.minecraft.client.multiplayer.WorldClient");
            foundClientClass = true;
        } catch (ClassNotFoundException e) {
        }
        try {
            Class<?> worldClientClassObf = Class.forName("bsb");
            foundClientClass = true;
        } catch (ClassNotFoundException e) {
        }
        if(world instanceof WorldServer) {
            return new FalsifiedWorldServer((WorldServer) world, mapper);
        } else {
            if(foundClientClass) {
                return attemptWrapClient(world, mapper);
            }
        }
        throw new IllegalArgumentException("Unknown world type: "+world.getClass());
    }

    @SideOnly(Side.CLIENT)
    static World attemptWrapClient(World world, IBlockMapper mapper) {
        if(world instanceof WorldClient) {
            return new FalsifiedWorldClient((WorldClient) world, mapper);
        }
        throw new IllegalArgumentException("Unknown world type: "+world.getClass());
    }
}
