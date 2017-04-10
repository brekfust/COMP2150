import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {
//This should include a list of Vertexobjects and a list of Edge
//objects as instance variables.  You can use java.util.ArrayListor
//java.util.LinkedListfor these lists.  This class should also include
//the following methods
//o method that loads information from a data file
//o method that runs Dijkstra’s algorithm usinga specified start and end vertex
//o toString method that includes information about the graph’s vertices and edges
	
	final double inf = Double.POSITIVE_INFINITY;
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;

	public Graph() {
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}
	
	public void fileImport(File file) throws FileNotFoundException, ParseException {
		//TODO would it be better to read in with array list, then sort?
		Scanner reader = new Scanner(file);
		while (reader.hasNext()) {
			//if input file is good, 
			String line[] = reader.nextLine().split(" ");
			if (line.length != 3) {
				throw new ParseException("Line read does not have 3 strings.", edges.size() + 1);
			}
			
			//create vertices, see if it's already on the list
			Vertex v1 = new Vertex(line[0]);
			Vertex v2 = new Vertex(line[1]);
			int v1Index = vertices.indexOf(v1);
			int v2Index = vertices.indexOf(v2);
			//indexOf returns -1 if it doesn't exist, so only add this if it's not already on the list
			//and get the already existing vertex if it does
			if (v1Index == -1) 
				vertices.add(v1);
			else 
				v1 = vertices.get(v1Index);
			
			if (v2Index == -1) 
				vertices.add(v2);
			else 
				v2 = vertices.get(v2Index);
			
			//get weight
			double weight = Double.parseDouble(line[2]);
			
			//create edge and give to first vertex
			Edge curEdge = new Edge(v1, v2, weight);
			edges.add(curEdge);
			v1.addNeighbor(curEdge);
		}
	}
	
	//didn't really use the edge list.. might be losing efficiency
	public LinkedList<Vertex> findPath(Vertex v1, Vertex v2) {
		//set up
		for (Vertex vert : vertices) {
			vert.setDistance(inf);
			vert.setVisited(false);
			vert.setPrevious(null);
		}
		v1.setDistance(0f);
		
		//main loop
		boolean areWeDone = false;
		while (!areWeDone) {
			//find smallest unvisited distance
			double small = inf;
			int smallIndex = -1;
			for (int j = 0; j < vertices.size(); j++) {
				if (vertices.get(j).getDistance() < small && vertices.get(j).isVisited() == false) {
					smallIndex = j; 
					small = vertices.get(j).getDistance();
				}
			}

			//nothing less than infinity was found, there is no path from v1 to v2, or there is an unconnected vertex
			if (small == inf)
				return null;
			
			//Assign new possible distance for neighbors and give it current vertex as its shortest path
			Vertex currentVisit = vertices.get(smallIndex);
			Vertex neighbor;
			double travelCost;
			for (int i = 0; i < currentVisit.neighborSize(); i++){
				neighbor = currentVisit.getNeighbor(i).getEnd();
				if (neighbor.isVisited())
					continue;
				travelCost = currentVisit.getDistance() + currentVisit.getNeighbor(i).getWeight();
				if (travelCost < neighbor.getDistance()){
					neighbor.setDistance(travelCost);
					neighbor.setPrevious(currentVisit);
				}
			}
			currentVisit.setVisited(true);
			
			//check if it's time to exit loop
			for (int i = 0; i < vertices.size(); i++) {
				if (!vertices.get(i).isVisited()) 
					break;
				if (i == vertices.size()-1)
					areWeDone = true;
			}
		}
		
		//populate path. v2 to v1 is basically reversed linklist, so copying to real linkedlist to head each time should be correct path
		LinkedList<Vertex> shortestPath = new LinkedList<>();
		for (Vertex temp = v2; temp != v1; temp = temp.getPrevious()) {
		shortestPath.add(0, temp);
	}
	shortestPath.add(0, v1);
		return shortestPath;
	}
	
	//give reference of vertex with same name as string, null if it doesn't exist
	public Vertex getVertexFromString(String s) {
		int index = vertices.indexOf(new Vertex(s));
		if (index == -1) 
			return null;
		else 
			return vertices.get(index);
	}
	
	public String toString() {
		String out = "";
		for (int i = 0; i < edges.size(); i++){
			out += "" + edges.get(i).getStart().getName() + " --(";
			out += "" + edges.get(i).getWeight();
			out += ")--> " + edges.get(i).getEnd().getName() + "\n";
		}
		out += "Total vertices: " + vertices.size() + "\n";
		out += "Total edges: " + edges.size();
		
		return out;
	}
	
	
}
