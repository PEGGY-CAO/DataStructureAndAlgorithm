import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Your implementation of various different graph algorithms.
 *
 * @author Yuqi Cao
 * @userid ycao344
 * @GTID 903352025
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                            Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        List<Vertex<T>> visited = new LinkedList<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjListOfGraph
                = graph.getAdjList();
        dFSHelper(start, adjListOfGraph, visited, visitedSet);
        return visited;
    }

    /**
     * Private helper method used to implement DFS recursively
     * @param curr the current vertex need to find whose neighbor
     * @param adjListOfGraph passed in adjacent list of the graph
     *                       in order to find a specific vertex's adjacent list
     * @param visited the visited list used to modify and return in the end
     * @param visitedSet the Graph's vertices set passed in
     *                   to limit contain method to O(1), originally empty
     * @param <T> the generic typing of the data
     */
    private static <T> void dFSHelper(Vertex<T> curr, Map<Vertex<T>, List
            <VertexDistance<T>>> adjListOfGraph, List<Vertex<T>> visited,
                                      Set<Vertex<T>> visitedSet) {
        if (!visitedSet.contains(curr)) {
            visited.add(curr);
            visitedSet.add(curr);
            List<VertexDistance<T>> adjListOfCurr = adjListOfGraph.get(curr);
            if (adjListOfCurr == null) {
                throw new IllegalArgumentException(
                        "the vertex passed in is not in the graph.");
            }
            for (VertexDistance<T> eachVerDis : adjListOfCurr) {
                dFSHelper(eachVerDis.getVertex(), adjListOfGraph,
                        visited, visitedSet);
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
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as it's efficient.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start index representing which vertex to start at (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every other node
     *         in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                      Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException(
                    "Input start vertex cannot be null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Input graph cannot be null");
        }
        Set<Vertex<T>> allVertices = graph.getVertices();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Map<Vertex<T>, Integer> distanceV = new HashMap<>();
        //initial distanceV
        for (Vertex<T> ver : allVertices) {
            distanceV.put(ver, Integer.MAX_VALUE);
        }
        //instantiate PQ
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        pq.add(new VertexDistance<>(start, 0));
        while (!pq.isEmpty() && visitedSet.size() < allVertices.size()) {
            VertexDistance<T> curr = pq.poll();
            //System.out.println("Vertex: " + curr.getVertex()
            //        + " dis: " + curr.getDistance());
            if (!visitedSet.contains(curr.getVertex())) {
                visitedSet.add(curr.getVertex());
                distanceV.replace(curr.getVertex(), curr.getDistance());
                List<VertexDistance<T>> adjListOfCurr = graph.getAdjList()
                        .get(curr.getVertex());
                if (adjListOfCurr == null) {
                    throw new IllegalArgumentException(
                            "the vertex passed in is not in the graph.");
                }

                //System.out.println("    " + adjListOfCurr);
                for (VertexDistance<T> vd : adjListOfCurr) {
                    if (!visitedSet.contains(vd.getVertex())) {
                        int i = curr.getDistance() + vd.getDistance();
                        pq.add(new VertexDistance<>(vd.getVertex(), i));
                    }
                }
            }
        }
        return distanceV;
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
     * to the {@code DisjointSet} and {@code DisjointSetNode} classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops into the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Input graph cannot be null.");
        }
        //initial disjoint sets
        Set<Vertex<T>> allVertices = graph.getVertices();
        DisjointSet<Vertex<T>> disjointSets = new DisjointSet<>(allVertices);
        //initial PQ and edges sets
        Set<Edge<T>> allEdges = graph.getEdges();
        Set<Edge<T>> mst = new HashSet<>();
        PriorityQueue<Edge<T>> priorityQ = new PriorityQueue<>();
        for (Edge<T> edge : allEdges) {
            priorityQ.add(edge);
        }
        while (!priorityQ.isEmpty()
                && mst.size() < 2 * (allVertices.size() - 1)) {
            Edge<T> minEdge = priorityQ.poll();
            Vertex<T> u = minEdge.getU();
            Vertex<T> v = minEdge.getV();
            Vertex<T> dataU = disjointSets.find(u);
            Vertex<T> dataV = disjointSets.find(v);
            if (!dataU.equals(dataV)) {
                mst.add(minEdge);
                mst.add(new Edge<>(v, u, minEdge.getWeight()));
                disjointSets.union(dataU, dataV);
            }
        }
        String pattern = "pattern";
        System.out.println(Character.MAX_VALUE);
        if (mst.size() == 2 * (allVertices.size() - 1)) {
            return mst;
        } else {
            return null;
        }
    }
}

