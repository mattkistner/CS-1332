import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author MATTHEW KISTNER
 * @version 1.0
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null || start == null) {
            throw new IllegalArgumentException("Parameters cannot be null.");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex must be in the graph.");
        } else {
            HashSet<Vertex<T>> vertexSet = new HashSet<>();
            List<Vertex<T>> vertexList = new LinkedList<>();
            Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
            Queue<Vertex<T>> vqueue = new LinkedList<>();
            vertexSet.add(start);
            vqueue.add(start);
            while (!vqueue.isEmpty()) {
                Vertex<T> vertex = vqueue.remove();
                vertexList.add(vertex);
                for (VertexDistance<T> vd : adjList.get(vertex)) {
                    if (!vertexSet.contains(vd.getVertex())){
                        vertexSet.add(vd.getVertex());
                        vqueue.add(vd.getVertex());
                    }
                }
            }
            return vertexList;
        }
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null || start == null) {
            throw new IllegalArgumentException("Parameters cannot be null.");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start vertex must be in the graph.");
        } else {
            Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
            Set<Vertex<T>> vertexSet = new HashSet<>();
            List<Vertex<T>> vertexList = new ArrayList<>();
            dfsH(start, vertexSet, vertexList, adjList);
            return vertexList;
        }
    }

    /**
     * Helper method for dfs.
     *
     * @param vertex the current vertex.
     * @param vertexSet the set of vertices.
     * @param vertexList the current list of vertices.
     * @param adjList existent vertices list.
     * @param <T> return type T.
     */
    private static <T> void dfsH(Vertex<T> vertex, Set<Vertex<T>> vertexSet,
                                 List<Vertex<T>> vertexList, Map<Vertex<T>, List<VertexDistance<T>>> adjList) {
        vertexList.add(vertex);
        vertexSet.add(vertex);
        for (VertexDistance<T> distance : adjList.get(vertex)) {
            if (!vertexSet.contains(distance.getVertex())) {
                dfsH(distance.getVertex(), vertexSet, vertexList, adjList);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (graph == null || start == null) {
            throw new IllegalArgumentException("Parameters cannot be null.");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex must be in graph.");
        } else {
            Map<Vertex<T>, Integer> mapToReturn = new HashMap<>();
            PriorityQueue<VertexDistance<T>> queue = new PriorityQueue<>();
            Set<Vertex<T>> vertexSet = new HashSet<>();
            Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
            for (Vertex<T> vertex : adjList.keySet()) {
                if (vertex.equals(start)) {
                    mapToReturn.put(vertex, 0);
                } else {
                    mapToReturn.put(vertex, Integer.MAX_VALUE);
                }
            }
            queue.add(new VertexDistance<>(start, 0));
            while (!queue.isEmpty() && vertexSet.size() <= mapToReturn.size()) {
                VertexDistance<T> tempDist = queue.remove();
                if (!vertexSet.contains(tempDist.getVertex())) {
                    vertexSet.add(tempDist.getVertex());
                }
                for (VertexDistance<T> vertexDist : adjList.get(tempDist.getVertex())) {
                    int curr = vertexDist.getDistance() + tempDist.getDistance();
                    if (mapToReturn.get(vertexDist.getVertex()).compareTo(curr) > 0) {
                        mapToReturn.put(vertexDist.getVertex(), curr);
                        queue.add(new VertexDistance<>(vertexDist.getVertex(), curr));
                        if (!vertexSet.contains(vertexDist.getVertex())) {
                            vertexSet.add(vertexDist.getVertex());
                        }
                    }
                }
            }
            return mapToReturn;
        }
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        } else {
            Set<Edge<T>> edgeSet = graph.getEdges();
            DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();
            Set<Edge<T>> minSpan = new HashSet<>();
            PriorityQueue<Edge<T>> priorityQ = new PriorityQueue<>(edgeSet);
            while (minSpan.size() < (2 * (graph.getVertices().size() - 1)) && !priorityQ.isEmpty()) {
                Edge<T> curr = (Edge<T>) priorityQ.poll();
                if (!(disjointSet.find(curr.getU()).equals(disjointSet.find(curr.getV())))) {
                    disjointSet.union(curr.getU(), curr.getV());
                    minSpan.add(curr);
                    minSpan.add(new Edge<>(curr.getV(), curr.getU(), curr.getWeight()));
                }
            }
            if (minSpan.size() != 2 * (graph.getVertices().size() - 1)) {
                return null;
            }
            return minSpan;
        }
    }
}
