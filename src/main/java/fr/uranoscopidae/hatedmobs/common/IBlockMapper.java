package fr.uranoscopidae.hatedmobs.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.HashMap;
import java.util.Map;

public interface IBlockMapper {

    static Map<World, World> falsifiedWorlds = new HashMap<>();

    IBlockState map(IBlockState blockState, int x, int y, int z);

    static World wrap(World world, IBlockMapper mapper) {
        if(!falsifiedWorlds.containsKey(world)) {
            falsifiedWorlds.put(world, wrapDirect(world, mapper));
        }
        return falsifiedWorlds.get(world);
    }

    static World wrapDirect(World world, IBlockMapper mapper) {
        if(world instanceof WorldServer) {
            return new FalsifiedWorldServer((WorldServer) world, mapper);
        } else if(world instanceof WorldClient) {
            return new FalsifiedWorldClient((WorldClient) world, mapper);
        }
        throw new IllegalArgumentException("Unknown world type: "+world.getClass());
    }
}
