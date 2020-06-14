package utils;

import java.util.List;
import java.util.Set;

import datastructures.Tuple;
import pathfinding.AStarSearch;
import pathfinding.IGraph;
import pathfinding.ITraversable;
import pathfinding.UnionFind;

public class PathFinder<N,U> {
	
	IGraph<N> graph;
	ITraversable<N,U> traversable;
	UnionFind unionfind;
	
	public PathFinder(IGraph<N> graph, UnionFind unionfind) {
		this.graph = graph;
		this.unionfind = unionfind;
	}
	
	
	public List<N> getPath(N start, N end) {
		return AStarSearch.getPath(graph, start, end);
	}
	
	public List<N> getPath(N start, N end, U unit) {
		return AStarSearch.getPath(traversable, start, end, unit);
	}
	
	
	public Set<N> getReachableNodes(N start, float budget) {
		return AStarSearch.getReachableNodes(graph, start, budget);
	}
	
	public List<Tuple<N, Float>> getReachableNodes(N start, float budget, U unit) {
		return AStarSearch.getReachableNodes(traversable, start, budget, unit);
	}
	
	public float getPathCosts(List<N> path) {
		return AStarSearch.getPathCosts(graph, path);
	}
}
