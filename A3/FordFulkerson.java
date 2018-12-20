package A3;
import java.io.*;
import java.util.*;




public class FordFulkerson {

	public static boolean[] checked;
	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		// YOUR CODE GOES HERE
		
		// Get the edges of the graph
		ArrayList<Edge> edges = graph.getEdges(); 
		
		// Initialize the array which will be used to mark all nodes checked 
		checked = new boolean[graph.getNbNodes()];
				
		// Initialize a stack to take advantage of pop/push function
		Stack<Integer> path = new Stack<Integer>();
		
		// Place the starting node inside the path and mark as checked
		path.push(source);
		checked[source] = true;
		
		// Convert the result of recursive algorithm into required type: ArrayList<Integer>
		ArrayList<Integer> result = new ArrayList<Integer>(recurDPS(path, source, destination, edges));
		
		// If the returned Stack size from recurDPS is equal to 1, it indicates that no path is found as the path contains only the starting node.
		return result.size() > 1 ? result : new ArrayList<Integer>();
	}
	
	/** A recursive helper method to determine the path from source node to destination node using Depth First Search.
	 * 
	 * @param stack - contains the resulted path
	 * @param src - the starting node
	 * @param dest - the end node
	 * @param graphEdges - contains all edges in the given graph
	 * @return
	 */
	public static Stack<Integer> recurDPS(Stack<Integer> stack, Integer src, Integer dest, ArrayList<Edge> graphEdges) {
		int latestNode = stack.lastElement();
		
		// Traverse through the edges within the graphs
		for (int i = 0; i < graphEdges.size(); i++) {
			Edge edge = graphEdges.get(i);
			
			// If the current edge does not start with the node-to-check, skip
			if (edge.nodes[0] != latestNode) {
				continue;
			}
			
			// Place the end node of this edge into the stack (this node becomes part of the path)
			stack.push(edge.nodes[1]);
			
			// If the latest added node is equal to our destination, return the stack.
			if (edge.nodes[1] == dest) {
				return stack;
				
			} else if (!checked[edge.nodes[1]]) {
				// This node has not yet been checked; thus, mark it
				checked[edge.nodes[1]] = true;
				
				// Recursively calls the function until the last element within the stack is our destination
				stack = recurDPS(stack, src, dest, graphEdges);
				if (stack.lastElement() == dest) {
					return stack;
				}
			}
			// The end node of the edge has already been checked, pop it. 
			// Then we'll check the next edge until no edges starts "latestNode"
			stack.pop();
		}
		return stack;
	}

	
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260606721"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		// YOUR CODE GOES HERE
		
		WGraph resGraph = new WGraph(graph);
		
		// Acquire a path from source to destination using our recursive pathDPS method.
		ArrayList<Integer> path = pathDFS(source, destination, resGraph);
		
		// Base case where source is equal to destination. 
		if (source == destination) {
			maxFlow = 0;
		}
		
		// If there are no path found between the node, return -1
		if (path.size() == 0) {
			maxFlow = -1;
		}
		
		// While there is still a path from source to destination.
		while(path.size() > 0) {
			
			// Pre-define a bottleneck flow
			int bottleFlow = Integer.MAX_VALUE;
			int currentFlow;
			
			// Determine the minimum capacity throughout the path and set the value to "bottleFlow"
			for (int i = 0; i < path.size() - 1; i++) {
				currentFlow = resGraph.getEdge(path.get(i), path.get(i + 1)).weight;
				bottleFlow = Math.min(bottleFlow, currentFlow);
			}

			for (int i = 0; i < path.size() - 1; i++) {	
				// Deduct all capacity of the flow by the bottleFlow on the path found
				Edge forwardEdge = resGraph.getEdge(path.get(i), path.get(i + 1));
				forwardEdge.weight -= bottleFlow;
				
				// Remove the edge from the residual graph as the capacity is now full
				if (forwardEdge.weight == 0) {
					resGraph.getEdges().remove(forwardEdge);
				}
				
				// Add a backward edge into the residual graph if the flow after deduction is not 0
				if (resGraph.getEdge(path.get(i + 1), path.get(i)) != null) {
					resGraph.getEdge(path.get(i + 1), path.get(i)).weight += bottleFlow;
				} else {
					Edge backwardEdge = new Edge(path.get(i + 1), path.get(i), bottleFlow);
					resGraph.addEdge(backwardEdge);
				}
			}
			
			// Determine a new path given the newly modified residual graph
			path = pathDFS(source, destination, resGraph);
			maxFlow += bottleFlow;
		}
		
		// Final resGraph in this example is the same as the one illustrated in Figure 26.6 (f)
		// of Course Textbook p.748. Therefore, we need to deduct some edges in this graph from 
		// from the original grpah.
		for (Edge e : graph.getEdges()) {
			if (resGraph.getEdge(e.nodes[0], e.nodes[1]) != null) {
				e.weight -= resGraph.getEdge(e.nodes[0], e.nodes[1]).weight;
				if (e.weight < 0) {
					e.weight = 0;
				}
			} 
		}	
		
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesn't exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
//		 String file = args[0];
		 String file = "/Users/Will/Documents/workspace/COMP251/src/A3/ff2.txt";
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
