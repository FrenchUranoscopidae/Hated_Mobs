package fr.uranoscopidae.hatedmobs.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathfinderAStar
{
    private static class Node
    {
        BlockPos pos;
        double cost;
        double heuristic;
        Node parent;

        public Node(BlockPos pos)
        {
            this.pos = pos;
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
        return shortestPath(world, new Node(start), new Node(objective), maxDistance);
    }

    private static List<BlockPos> shortestPath(World world, Node start, Node objective, int maxDistance)
    {
        List<Node> closedList = new LinkedList<>();
        Queue<Node> openList = new PriorityQueue<>(PathfinderAStar::compare);
        openList.add(start);
        double maxDistanceSq = maxDistance*maxDistance;
        while (!openList.isEmpty())
        {
            Node current = openList.poll();
            if(current.equals(objective))
            {
                return recontructPath(current);
            }
            if(closedList.contains(current))
                continue;

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
                if(neighbor.pos.distanceSq(start.pos) >= maxDistanceSq) {
                    closedList.add(neighbor);
                    continue;
                }
                neighbor.cost = current.cost + current.pos.distanceSq(neighbor.pos);
                neighbor.heuristic = neighbor.cost + heuristicDistance(neighbor, objective);
                neighbor.parent = current;
                System.out.println("Add "+neighbor.pos+" / "+openList.contains(neighbor));
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
        for (EnumFacing facing : EnumFacing.values())
        {
            BlockPos neighborPos = current.pos.offset(facing);
            if(world.isBlockLoaded(neighborPos))
            {
                if(world.isAirBlock(neighborPos))
                {
                    for (EnumFacing facingAir : EnumFacing.values())
                    {
                        BlockPos airNeighbor = neighborPos.offset(facingAir);
                        IBlockState state = world.getBlockState(airNeighbor);
                        if(state.isSideSolid(world, airNeighbor, facingAir.getOpposite()))
                        {
                            neighbors.add(new Node(neighborPos));
                        }
                    }
                }
            }
        }
        return neighbors;
    }
}
