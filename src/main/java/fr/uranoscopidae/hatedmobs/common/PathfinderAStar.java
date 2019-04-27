package fr.uranoscopidae.hatedmobs.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

/**
 * Pathfinder used for Anthills to find a path to containers
 * @author jglrxavpok
 */
public class PathfinderAStar
{
    /**
     * Single node: block and face of said block
     */
    public static class Node
    {
        BlockPos pos;
        double gScore = Double.POSITIVE_INFINITY;
        double fScore = Double.POSITIVE_INFINITY;
        Node parent;
        EnumFacing side;

        public Node(BlockPos pos, EnumFacing side)
        {
            this.pos = pos;
            this.side = side;
        }

        public BlockPos getPos() {
            return pos;
        }

        public EnumFacing getSide() {
            return side;
        }

        @Override
        public boolean equals(Object obj)
        {
            if(obj instanceof Node)
            {
                return pos.equals(((Node) obj).pos) && side == ((Node) obj).side;
            }
            return super.equals(obj);
        }
    }

    public static int compare(Node node1, Node node2)
    {
        return Double.compare(node1.fScore, node2.fScore);
    }

    public static List<Node> findPath(World world, BlockPos start, BlockPos objective, int maxDistance) {
        for(EnumFacing sideStart : EnumFacing.VALUES) {
            for(EnumFacing sideEnd : EnumFacing.VALUES) {
                List<Node> path = shortestPath(world, new Node(start.offset(sideStart), sideStart.getOpposite()), new Node(objective.offset(sideEnd), sideEnd.getOpposite()), maxDistance);
                if(path != null) {
               //     System.out.println(">> "+start+"("+sideStart+") -> "+objective+"("+sideEnd+")");
                 //   System.out.println(path.stream().map(Vec3i::toString).collect(Collectors.joining(", ")));
                    return path;
                }
            }
        }
        return null;
    }

    private static List<Node> shortestPath(World world, Node start, Node objective, int maxDistance)
    {
        if(!world.isAirBlock(start.pos))
            return null;

        // implementation based on Wikipedia article on A*
        List<Node> closedList = new LinkedList<>();
        Queue<Node> openSet = new PriorityQueue<>(PathfinderAStar::compare);
        openSet.add(start);
        double maxDistanceSq = maxDistance*maxDistance;
        int testedCount = 0;

        start.gScore = 0.0;
        start.fScore = heuristicDistance(start, objective);

        while(!openSet.isEmpty()) {
            Node current = openSet.poll();
            if(current.equals(objective)) {
                return recontructPath(current);
            }

            if(testedCount++ >= 250)
                return null;

            closedList.add(current);

            for(Node neighbor : getNeighbors(world, current)) {
                if(neighbor.pos.distanceSq(start.pos) >= maxDistanceSq)
                    continue;
                if(closedList.contains(neighbor)) {
                    continue;
                }

                double tentativeGScore = current.gScore + Math.sqrt(current.pos.distanceSq(neighbor.pos));

                if( ! openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                } else {
                    // get previous score of neighbor (need to check openSet because the one inside the set and 'neighbor' have different references)
                    Optional<Double> previousGScore = openSet.stream().filter(neighbor::equals).map(it -> it.gScore).findFirst();
                    if(previousGScore.isPresent()) {
                        if(tentativeGScore >= previousGScore.get()) {
                            continue;
                        }
                    }
                }

                neighbor.parent = current;
                neighbor.gScore = tentativeGScore;
                neighbor.fScore = neighbor.gScore + heuristicDistance(neighbor, objective);
            }
        }
        return null;
    }

    private static List<Node> recontructPath(Node current)
    {
        List<Node> posList = new LinkedList<>();
        posList.add(current);
        Node parent = current.parent;
        while(parent != null)
        {
            posList.add(parent);
            parent = parent.parent;
        }
        return posList;
    }

    private static double heuristicDistance(Node a, Node b)
    {
        return Math.abs(b.pos.getX() - a.pos.getX()) + Math.abs(b.pos.getY() - a.pos.getY()) + Math.abs(b.pos.getZ() - a.pos.getZ());
    }

    /**
     * List the valid neighbors of a given node
     * @param world
     * @param current
     * @return
     */
    private static List<Node> getNeighbors(World world, Node current)
    {
        List<Node> neighbors = new LinkedList<>();
        EnumFacing currentFace = current.side;

        // list of faces accessible to the current face (90° inside the same block)
        List<EnumFacing> accessibleFaces = new LinkedList<>();

        // directly next to this face
        for (EnumFacing facing : EnumFacing.values())
        {
            if(facing == currentFace || facing == currentFace.getOpposite())  // don't go through walls
            {
                continue;
            }
            accessibleFaces.add(facing);
            BlockPos neighborPos = current.pos.offset(facing).offset(currentFace);
            IBlockState neighborState = world.getBlockState(neighborPos);

            // N are checked neighbors, C is the current location, X is not checked: (on a 2d plane)
            //  XNX
            //  NCN
            //  XNX

            // TODO: method to check if face valid
            BlockPos potentialNextPosition = current.pos.offset(facing);
            if(world.isAirBlock(potentialNextPosition)) {
                if(!world.isAirBlock(neighborPos)/*neighborState.isSideSolid(world, neighborPos, currentFace.getOpposite())*/) { // check that there is continuity in the path (stay on the same face)
                    neighbors.add(new Node(current.pos.offset(facing), currentFace));
                } else {
                    neighbors.add(new Node(neighborPos, facing.getOpposite()));
                }
            }


            // check if accessible faces inside the same block are valid
            if(!world.isAirBlock(current.pos.offset(facing))) {
                neighbors.add(new Node(current.pos, facing));
            }
        }


        /*

        // Allow to go on another face of a given cube
        EnumFacing[] clockwiseAccessibleFaces;
        switch (currentFace)
        {
            case UP:
            case DOWN:
                clockwiseAccessibleFaces = EnumFacing.HORIZONTALS;
                break;

            case NORTH:
            case SOUTH:
                clockwiseAccessibleFaces = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST};
                break;

            case EAST:
            case WEST:
                clockwiseAccessibleFaces = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH};
                break;

            default:
                // never happens
                clockwiseAccessibleFaces = EnumFacing.HORIZONTALS;
                break;
        }
        // rotations outside a block
        for(EnumFacing clockwiseFace : clockwiseAccessibleFaces)
        {
            BlockPos supportingPos = current.pos.offset(currentFace);
            IBlockState supportingState = world.getBlockState(supportingPos);
            if(supportingState.isSideSolid(world, supportingPos, clockwiseFace))
            {
                // TODO: check for air
                if(world.isAirBlock(supportingPos.offset(clockwiseFace)))
                {
                    neighbors.add(new Node(supportingPos.offset(clockwiseFace), clockwiseFace.getOpposite()));
                }
            }
        }

        // rotations inside same block
        for(EnumFacing clockwiseFace : clockwiseAccessibleFaces)
        {
            BlockPos supportingPos = current.pos.offset(clockwiseFace);
            IBlockState supportingState = world.getBlockState(supportingPos);
            if(supportingState.isSideSolid(world, supportingPos, clockwiseFace.getOpposite()))
            {
                // TODO: check for air
                if(!world.isAirBlock(supportingPos))
                {
                    neighbors.add(new Node(current.pos, clockwiseFace));
                }
            }
        }*/
        return neighbors;
    }
}
