import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Graph {
//This should include a list of Vertexobjects and a list of Edge
//objects as instance variables.  You can use java.util.ArrayListor
//java.util.LinkedListfor these lists.  This class should also include
//the following methods
//o method that loads information from a data file
//o method that runs Dijkstra’s algorithm usinga specified start and end vertex
//o toString method that includes information about the graph’s vertices and edges
	
	
	//TODO implement reset for vertexes, CLEAN UP, choose currentVisit if there is no smallest distance (largest are equal).
	final double inf = Double.POSITIVE_INFINITY;
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;

	public Graph() {
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}
	
	public void fileImport(File file) throws FileNotFoundException {
		//TODO handle exception, including inputmismatch
		//TODO would it be better to read in with array list, sort, and create from there?
		//TODO anything to reduce bigO of this, too big
		//TODO can we ever have a travel cost of 0 on an edge? how should it be considered?
		Scanner reader = new Scanner(file);
		while (reader.hasNext()) {
			//if input file is good, 
			String line[] = reader.nextLine().split(" ");
			if (line.length != 3) {
				//TODO throw invalidformat of some kind
			}
			
			//TODO clean this up yo, this is stupid
			//create vertices, keep index, find index if it already exists
			int v1;
			int v2;
			if (!vertices.contains(new Vertex(line[0]))) {
				v1 = vertices.size();
				vertices.add(new Vertex(line[0]));
			} else {
				v1 = vertices.indexOf(new Vertex(line[0]));
			}
			if (!vertices.contains(new Vertex(line[1]))) {
				v2 = vertices.size();
				vertices.add(new Vertex(line[1]));
			} else {
				v2 = vertices.indexOf(new Vertex(line[1]));
			}
			
			//get weight
			double weight = Double.parseDouble(line[2]);
			
			//create edge
			edges.add(new Edge(vertices.get(v1), vertices.get(v2), weight));
			//tell first vertex what its neighbor is
			//if file is valid, second neighbor will get that info eventually?
			vertices.get(v1).addNeighbor(edges.get(edges.size()-1));
		}
	}
	
	//attempt 1, don't think we'll use the edge list at all... is this right?
	public ArrayList<Vertex> findPath(Vertex v1, Vertex v2) {
		v1.setDistance(0f);
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

			//nothing less than infinity was found, there is no path from v1 to v2
			if (small == inf) {
				//TODO throw some type of exception, create a new one?
			}
			
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
		
		//populate path (in reverse)
		ArrayList<Vertex> shortestPath = new ArrayList<>();
		for (Vertex temp = v2; temp != v1; temp = temp.getPrevious()) {
			shortestPath.add(temp);
		}
		shortestPath.add(v1);
		//TODO maybe use reverse iterator or something to avoid actually reversing the list? or provide linkedlist?
		Collections.reverse(shortestPath);
		
		return shortestPath;
		
	}
	
//	public boolean isThisARealVertexName(String s) {
//		return vertices.contains(new Vertex(s));
//	}
	
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
			out += "" + edges.get(i).getStart().getName() + " ---(";
			out += "" + edges.get(i).getWeight();
			out += ")--> " + edges.get(i).getEnd().getName() + "\n";
		}
		out += "Total vertices: " + vertices.size() + "\n";
		out += "Total edges: " + edges.size();
		
		return out;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		File test = new File("example.txt");
		Graph fun = new Graph();
		fun.fileImport(test);
		System.out.println("vertices " + fun.vertices.size());
		System.out.println("edges " + fun.edges.size());

//		for (int i = 0; i < fun.vertices.size(); i++)
//			System.out.println(fun.vertices.get(i).getName() + " distance " + fun.vertices.get(i).getDistance());
		for (int i = 0; i < fun.edges.size(); i++){
			System.out.print(fun.edges.get(i).getStart().getName() + " + ");
			System.out.print(fun.edges.get(i).getEnd().getName() + " + ");
			System.out.println(fun.edges.get(i).getWeight());
		}
		ArrayList<Vertex> somePath = fun.findPath(fun.vertices.get(0), fun.vertices.get(5));
		for (int i = 0; i < fun.vertices.size(); i++)
			System.out.println(fun.vertices.get(i).getName() + " distance " + fun.vertices.get(i).getDistance());
		for (int i = 0; i < somePath.size(); i ++)
			System.out.println(somePath.get(i).getDistance() + " -> " + somePath.get(i).getName() + " ");
		
	}
	
}
