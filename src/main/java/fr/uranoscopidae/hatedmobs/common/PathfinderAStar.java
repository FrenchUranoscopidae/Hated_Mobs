package fr.uranoscopidae.hatedmobs.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class PathfinderAStar
{
    private static class Node
    {
        BlockPos pos;
        double cost;
        double heuristic;
        Node parent;
        EnumFacing side;

        public Node(BlockPos pos, EnumFacing side)
        {
            this.pos = pos;
            this.side = side;
        }

        @Override
        public boolean equals(Object obj)
        {
            if(obj instanceof Node)
            {
                return pos.equals(((Node) obj).pos);
            }
            return super.equals(obj);
        }
    }

    public static int compare(Node node1, Node node2)
    {
        return Double.compare(node1.heuristic, node2.heuristic);
    }

    public static List<BlockPos> findPath(World world, BlockPos start, BlockPos objective, int maxDistance)
    {
        for(EnumFacing sideStart : EnumFacing.VALUES) {
            for(EnumFacing sideEnd : EnumFacing.VALUES) {
                List<BlockPos> path = shortestPath(world, new Node(start.offset(sideStart), sideStart.getOpposite()), new Node(objective.offset(sideEnd), sideEnd.getOpposite()), maxDistance);
                if(path != null)
                    return path;
            }
        }
        return null;
    }

    private static List<BlockPos> shortestPath(World world, Node start, Node objective, int maxDistance)
    {
        List<Node> closedList = new LinkedList<>();
        Queue<Node> openList = new PriorityQueue<>(PathfinderAStar::compare);
        openList.add(start);
        double maxDistanceSq = maxDistance*maxDistance;
        int testedCount = 0;
        while (!openList.isEmpty())
        {
            testedCount++;
            Node current = openList.poll();
            if(current.equals(objective))
            {
                return recontructPath(current);
            }
            if(testedCount > 250)
                break;
         /*   if(!world.isAirBlock(current.pos))
            {
                closedList.add(current);
                continue;
            }*/
            if(current.pos.distanceSq(start.pos) >= maxDistanceSq) {
                closedList.add(current);
                continue;
            }
            neighborLoop : for (Node neighbor : getNeighbors(world, current))
            {
                if(closedList.contains(neighbor))
                {
                    continue;
                }
                for (Node neighbor2 : openList)
                {
                    if(neighbor.equals(neighbor2))
                    {
                        if(neighbor2.cost <= neighbor.cost)
                        {
                            continue neighborLoop;
                        }
                    }
                }

                neighbor.cost = current.cost + current.pos.distanceSq(neighbor.pos);
                neighbor.heuristic = neighbor.cost + heuristicDistance(neighbor, objective);
                neighbor.parent = current;
          //      System.out.println("Add "+neighbor.pos+" / "+openList.contains(neighbor));
                openList.add(neighbor);
            }
            closedList.add(current);
        }
        return null;
    }

    private static List<BlockPos> recontructPath(Node current)
    {
        List<BlockPos> posList = new LinkedList<>();
        posList.add(current.pos);
        Node parent = current.parent;
        while(parent != null)
        {
            posList.add(parent.pos);
            current = parent;
            parent = current.parent;
        }
        return posList;
    }

    private static double heuristicDistance(Node a, Node b)
    {
        return Math.abs(b.pos.getX() - a.pos.getX()) + Math.abs(b.pos.getY() - a.pos.getY()) + Math.abs(b.pos.getZ() - a.pos.getZ());
    }

    private static List<Node> getNeighbors(World world, Node current)
    {
        List<Node> neighbors = new LinkedList<>();
        EnumFacing currentFace = current.side;
        for (EnumFacing facing : EnumFacing.values())
        {
            if(facing == currentFace || facing == currentFace.getOpposite())  // don't go through walls
            {
                continue;
            }
            BlockPos neighborPos = current.pos.offset(facing).offset(currentFace.getOpposite());
            IBlockState neighborState = world.getBlockState(neighborPos);
            if(neighborState.isSideSolid(world, neighborPos, currentFace.getOpposite())) { // check that there is continuity in the path (stay on the same face)
                neighbors.add(new Node(current.pos.offset(facing), currentFace));
            }
        }

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
        }
        return neighbors;
    }
}
