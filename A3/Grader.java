//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FilenameFilter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//public class Grader {
//
//	private static File findFilePrefix(String prefix) {
//		File[] listFiles = new File(".").listFiles( new FilenameFilter() {
//			@Override
//			public boolean accept(File dir, String name) {
//				return name.startsWith(prefix);
//			}
//		});
//		if (listFiles.length==1) {
//			return listFiles[0];
//		}
//		return null;
//	}
//	private static int flowFromFile(String filename) {
//
//		File f = findFilePrefix(filename);
//		Integer flow = null;
//		BufferedReader br;
//		try {
//			br = new BufferedReader(new FileReader(f));
//			String line = br.readLine();
//			if(line!=null){
//				flow = Integer.parseInt(line.trim());
//			}
//		} catch (FileNotFoundException ex) {
//			ex.printStackTrace();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
////		f.delete();
//		return flow;
//	}
//
//	private static List<Edge> edgesFromFile(String filename){
//		try {
//			return Files.lines(Paths.get(filename))
//					.skip(3)
//					.map(line->Arrays.stream(line.split("\\s+"))
//								.map(Integer::parseInt)
//								.limit(2)
//								.collect(Collectors
//									.collectingAndThen(Collectors.toList(),
//										lst-> new Edge(lst.get(0), lst.get(1), 0))
//									)
//						)
//					.collect(Collectors.toList());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//
//	private static List<Integer> weightsFromFile(String filename) {
//		try {
//			return Files.lines(Paths.get(filename))
//					.skip(3)
//					.map(line->Arrays.stream(line.split("\\s+"))
//								.map(Integer::parseInt)
//								.skip(2)
//								.collect(Collectors
//									.collectingAndThen(Collectors.toList(),
//										lst-> lst.get(0))
//									)
//						)
//					.collect(Collectors.toList());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	public static void main(String[] args) {
//
//		System.out.println("This is the COMP251 Assignment 3 Grader");
//
//		GraderManager.Manager make = new GraderManager.Manager();
//		System.out.println("Question 1 - Ford Fulkerson");
//		GraderManager ff = make.manager("Ford fulkerson");
//
//		ff.addGradings(
//			linker->{
//				linker.info("Testing dfs - weights");
//				WGraph g = new WGraph();
//				g.setSource(0);
//                g.setDestination(5);
//				Edge[] edges = new Edge[] {
//						new Edge(0, 1, 10),
//						new Edge(0, 2, 5),
//						new Edge(2, 4, 5),
//						new Edge(1, 3, 10),
//						new Edge(1, 6, 5),
//						new Edge(3, 5, 10),
//						new Edge(2, 5, 5)
//				};
//				Arrays.stream(edges).forEach(e->g.addEdge(e));
//				ArrayList<Integer> path = FordFulkerson.pathDFS(0, 5, g);
//				boolean isAPath = IntStream.range(0, path.size()-2).mapToObj(index->{
//					Integer s = path.get(index);
//					Integer t = path.get(index+1);
//					return g.getEdge(s, t)!=null;
//				}).reduce( true, (acc, edgeExist) -> acc && edgeExist);
//
//				boolean predicate = isAPath && path.get(0)==0 && path.get(path.size()-1)==5;
//				if(!predicate)
//					System.out.println("Not predicate for weight  " +path);
//
//				return 	predicate?
//						new GraderManager.Mark(1, 1) :
//						new GraderManager.Mark(0, 1);
//			},
//			linker->{
//				linker.info("Testing dfs - cycles");
//				WGraph g = new WGraph();
//				g.setSource(0);
//                g.setDestination(5);
//				Edge[] edges = new Edge[] {
//						new Edge(0, 1, 10),
//						new Edge(0, 2, 5),
//						new Edge(2, 4, 5),
//						new Edge(1, 3, 10),
//						new Edge(1, 6, 5),
//						new Edge(3, 0, 10),
//						new Edge(3, 5, 5)
//				};
//				Arrays.stream(edges).forEach(e->g.addEdge(e));
//				ArrayList<Integer> path = FordFulkerson.pathDFS(0, 5, g);
//				return 	path.equals(Arrays.asList(0,1,3,5)) ||
//						path.equals(Arrays.asList(0,2,5))?
//						new GraderManager.Mark(1, 1) :
//						new GraderManager.Mark(0, 1);
//			},
//			linker->{
//				linker.info("Testing ford fulkerson - dumbbell");
//				WGraph g = new WGraph();
//                g.setSource(0);
//                g.setDestination(9);
//				Edge[] edges = new Edge[] {
//						new Edge(0, 1, 10),
//						new Edge(0, 2, 5),
//						new Edge(2, 3, 5),
//						new Edge(1, 3, 10),
//						new Edge(3, 4, 5),
//						new Edge(4, 5, 10),
//						new Edge(4, 6, 5),
//						new Edge(6, 7, 5),
//						new Edge(6, 8, 10),
//						new Edge(8, 9, 10),
//				};
//				Arrays.stream(edges).forEach(e->g.addEdge(e));
//				String tempName = "1_ff_";
//				File file = findFilePrefix(tempName);
//				if (file!=null) {
//					file.delete();
//				}
//				FordFulkerson.fordfulkerson(0, 9, g, tempName);
//				List<Integer> ws = weightsFromFile(findFilePrefix(tempName).getName());
//
//				// construct residual graph
//
//
//				int flow = flowFromFile(findFilePrefix(tempName).getName());
//
//				return flow==5?
//						ws.equals(Arrays.asList(new Integer[] {5,0,0,5,5,0,5,0,5,5})) ||
//						ws.equals(Arrays.asList(new Integer[] {0,5,5,0,5,0,5,0,5,5}))?
//						new GraderManager.Mark(2, 2):
//						new GraderManager.Mark(0, 2):
//						new GraderManager.Mark(1, 2);
//			},
//			linker->{
//				linker.info("Testing ford fulkerson - cycle");
//				WGraph g = new WGraph();
//				g.setSource(0);
//                g.setDestination(5);
//				Edge[] edges = new Edge[] {
//						new Edge(0, 1, 10),
//						new Edge(0, 2, 5),
//						new Edge(2, 4, 5),
//						new Edge(1, 3, 10),
//						new Edge(1, 6, 5),
//						new Edge(3, 0, 10),
//						new Edge(3, 5, 5)
//				};
//				Arrays.stream(edges).forEach(e->g.addEdge(e));
//				String tempName = "2_ff_";
//				File file = findFilePrefix(tempName);
//				if (file!=null) {
//					file.delete();
//				}
//				FordFulkerson.fordfulkerson(0, 5, g, tempName);
//				List<Integer> ws = weightsFromFile(findFilePrefix(tempName).getName());
//				int flow = flowFromFile(tempName);
//
//				return flow==5?
//						ws.equals(Arrays.asList(new Integer[] {5,0,0,5,0,0,5}))?
//						new GraderManager.Mark(2, 2):
//						new GraderManager.Mark(0, 2):
//						new GraderManager.Mark(1, 2);
//			},
//			linker->{
//				linker.info("Testing ford fulkerson - no path");
//				WGraph g = new WGraph();
//                g.setSource(0);
//                g.setDestination(9);
//				Edge[] edges = new Edge[] {
//						new Edge(0, 1, 10),
//						new Edge(0, 2, 5),
//						new Edge(2, 3, 5),
//						new Edge(1, 3, 10),
//						new Edge(4, 5, 10),
//						new Edge(4, 6, 5),
//						new Edge(6, 7, 5),
//						new Edge(6, 8, 10),
//						new Edge(8, 9, 10),
//				};
//				Arrays.stream(edges).forEach(e->g.addEdge(e));
//				String tempName = "3_ff_";
//				File file = findFilePrefix(tempName);
//				if (file!=null) {
//					file.delete();
//				}
//				FordFulkerson.fordfulkerson(0, 9, g, tempName);
//				int flow = flowFromFile(findFilePrefix(tempName).getName());
//				return flow==0 || flow==-1?
//						new GraderManager.Mark(2, 2):
//						new GraderManager.Mark(0, 2);
//			}
//		);
//
//		make.show(ff);
//
//		GraderManager bf = make.manager("Bellmanford");
//		bf.addGradings(
//			linker->{
//				linker.info("Testing bellmanford - default case 1");
//				WGraph g = new WGraph();
//                g.setSource(0);
//                g.setDestination(8);
//				Edge[] edges = new Edge[] {
//						new Edge(0, 1, 10),
//						new Edge(0, 2, 12),
//						new Edge(1, 2, 9 ),
//						new Edge(1, 3, 8 ),
//						new Edge(2, 4, 3 ),
//						new Edge(2, 5, 1 ),
//						new Edge(3, 4, 7 ),
//						new Edge(3, 6, 11),
//						new Edge(3, 7, 5 ),
//						new Edge(4, 5, 3 ),
//						new Edge(5, 7, 6 ),
//						new Edge(6, 7, 9 ),
//						new Edge(6, 8, 2 ),
//						new Edge(7, 8, 11 ),
//				};
//				Arrays.stream(edges).forEach(e->g.addEdge(e));
//				boolean matches = false;
//				try {
//					BellmanFord bellmanFord = new BellmanFord(g, 0);
//					List<Integer> path = IntStream.of(bellmanFord.shortestPath(8)).boxed().collect(Collectors.toList());
//					matches = path.equals(Arrays.asList(new Integer[] {0,2,5,7,8}));
//				} catch (BellmanFord.BellmanFordException ex) {
//					ex.printStackTrace();
//				}
//				return matches?
//						new GraderManager.Mark(2, 2) :
//						new GraderManager.Mark(0, 2);
//			},
//			linker->{
//				linker.info("Testing bellmanford - default case 2");
//				WGraph g = new WGraph();
//                g.setSource(0);
//                g.setDestination(8);
//				Edge[] edges = new Edge[] {
//						new Edge(0,1,10),
//						new Edge(0,2,12),
//						new Edge(1,2,9),
//						new Edge(1,3,8),
//						new Edge(2,4,3),
//						new Edge(2,5,1),
//						new Edge(3,4,7),
//						new Edge(3,6,11),
//						new Edge(3,7,5),
//						new Edge(4,5,3),
//						new Edge(5,7,6),
//						new Edge(7,6,9),
//						new Edge(6,8,2),
//						new Edge(7,8,15),
//				};
//				Arrays.stream(edges).forEach(e->g.addEdge(e));
//				boolean matches = false;
//				try {
//					BellmanFord bellmanFord = new BellmanFord(g, 0);
//					List<Integer> path = IntStream.of(bellmanFord.shortestPath(8)).boxed().collect(Collectors.toList());
//					matches = path.equals(Arrays.asList(new Integer[] {0,2,5,7,6,8}));
//				} catch (BellmanFord.BellmanFordException ex) {
//					ex.printStackTrace();
//				}
//				return matches?
//						new GraderManager.Mark(2, 2) :
//						new GraderManager.Mark(0, 2);
//			},
//			linker->{
//				linker.info("Testing bellmanford - case negative loop");
//				WGraph g = new WGraph();
//                g.setSource(0);
//                g.setDestination(8);
//				Edge[] edges = new Edge[] {
//						new Edge(0, 1, 10),
//						new Edge(0, 2, 12),
//						new Edge(1, 2, 9 ),
//						new Edge(1, 3, 8 ),
//						new Edge(2, 4, 3 ),
//						new Edge(2, 5, -5 ),
//						new Edge(5, 0, -10),
//						new Edge(3, 4, 7 ),
//						new Edge(3, 6, 11),
//						new Edge(3, 7, 5 ),
//						new Edge(4, 5, 3 ),
//						new Edge(5, 7, 6 ),
//						new Edge(6, 7, 9 ),
//						new Edge(6, 8, 2 ),
//						new Edge(7, 8, 11 ),
//				};
//				Arrays.stream(edges).forEach(e->g.addEdge(e));
//				try {
//					BellmanFord bellmanFord = new BellmanFord(g, 0);
//					List<Integer> path = IntStream.of(bellmanFord.shortestPath(8)).boxed().collect(Collectors.toList());
//					path.equals(Arrays.asList(new Integer[] {0,2,5,7,8}));
//				} catch (BellmanFord.NegativeWeightException ex) {
//					return new GraderManager.Mark(2, 2);
//				} catch (BellmanFord.BellmanFordException ex) {
//					return new GraderManager.Mark(1, 2);
//				}
//				return new GraderManager.Mark(0, 2);
//			},
//			linker->{
//				linker.info("Testing bellmanford - case non-positive loop");
//				WGraph g = new WGraph();
//                g.setSource(0);
//                g.setDestination(8);
//
//				Edge[] edges = new Edge[] {
//						new Edge(0, 1, 10),
//						new Edge(0, 2, 12),
//						new Edge(1, 2, 9 ),
//						new Edge(1, 3, 8 ),
//						new Edge(2, 4, 3 ),
//						new Edge(2, 5, -5 ),
//						new Edge(5, 0, -7),
//						new Edge(3, 4, 7 ),
//						new Edge(3, 6, 11),
//						new Edge(3, 7, 5 ),
//						new Edge(4, 5, 3 ),
//						new Edge(5, 7, 6 ),
//						new Edge(6, 7, 9 ),
//						new Edge(6, 8, 2 ),
//						new Edge(7, 8, 11 ),
//				};
//				Arrays.stream(edges).forEach(e->g.addEdge(e));
//				boolean matches = false;
//				try {
//					BellmanFord bellmanFord = new BellmanFord(g, 0);
//					List<Integer> path = IntStream.of(bellmanFord.shortestPath(8)).boxed().collect(Collectors.toList());
//					matches = path.equals(Arrays.asList(new Integer[] {0,2,5,7,8}));
//				} catch (BellmanFord.BellmanFordException ex) {
//					return new GraderManager.Mark(0, 2);
//				}
//				return matches?
//						new GraderManager.Mark(2, 2) :
//						new GraderManager.Mark(0, 2);
//			},
//			linker->{
//				linker.info("Testing bellmanford - case no path");
//				WGraph g = new WGraph();
//                g.setSource(0);
//                g.setDestination(9);
//				Edge[] edges = new Edge[] {
//						new Edge(0, 1, 10),
//						new Edge(0, 2, 5),
//						new Edge(2, 3, 5),
//						new Edge(1, 3, 10),
//						new Edge(4, 5, 10),
//						new Edge(4, 6, 5),
//						new Edge(6, 7, 5),
//						new Edge(6, 8, 10),
//						new Edge(8, 9, 10),
//				};
//				Arrays.stream(edges).forEach(e->g.addEdge(e));
//				try {
//					BellmanFord bellmanFord = new BellmanFord(g, 0);
//					IntStream.of(bellmanFord.shortestPath(8)).boxed().collect(Collectors.toList());
//				} catch (BellmanFord.PathDoesNotExistException ex) {
//					return new GraderManager.Mark(2, 2);
//				} catch (BellmanFord.BellmanFordException ex) {
//					return new GraderManager.Mark(1, 2);
//				}
//				return new GraderManager.Mark(0, 2);
//			}
//		);
//
//		make.show(bf);
//
//		System.out.println("Total score: "+
//				(ff.reweightedGrade(40).combine(bf.reweightedGrade(40))).score) ;
//		System.out.println("Done");
//	}
//
//
//}
