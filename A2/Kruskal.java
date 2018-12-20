package A2;
import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
        
    	// Declare and initialize a WGraph which will be the minimum spanning tree 
    	WGraph MinSpanTree = new WGraph();
    	// Initialize a disjoint set
    	DisjointSets myset = new DisjointSets(g.getNbNodes());
    	
    	// Traverse through the list of edges
    	for (Edge edge : g.listOfEdgesSorted()) {
    		if (IsSafe(myset, edge)) {
    			MinSpanTree.addEdge(edge); // the edge is safe, thus add it into the minimum spanning tree
    			myset.union(edge.nodes[0], edge.nodes[1]); // add the edge into mySet so that they belong to the same tree
    		}
    	}
    	
        return MinSpanTree;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){

        /* Fill this method (The statement return 0 is here only to compile) */
    	
    	int node1Rep = p.find(e.nodes[0]); // get the root of node 1
    	int node2Rep = p.find(e.nodes[1]); // get the root of node 2
    	
    	// The first and second node in the edge already belongs in the same disjoint set (connected via other path)
    	// Therefore, it is "unsafe" to add -> discard this edge.
        if (node1Rep == node2Rep) {
        	return false;
        }
        
    	return true;
    }

    public static void main(String[] args){

//        String file = args[0];
    	String file = "/Users/Will/Documents/workspace/COMP251/src/A2/g1.txt";
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
