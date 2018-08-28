package fr.uranoscopidae.hatedmobs.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public interface IBlockMapper {
    IBlockState map(IBlockState blockState, int x, int y, int z);

    static World wrap(World world, IBlockMapper mapper) {
        if(world instanceof WorldServer) {
            return new FalsifiedWorldServer((WorldServer) world, mapper);
        } else if(world instanceof WorldClient) {
            return new FalsifiedWorldClient((WorldClient) world, mapper);
        }
        throw new IllegalArgumentException("Unknown world type: "+world.getClass());
    }
}
