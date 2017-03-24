import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
	final private double inf = Double.POSITIVE_INFINITY;
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
		Scanner reader = new Scanner(file);
		while (reader.hasNext()) {
			//if input file is good, 
			String line[] = reader.nextLine().split(" ");
			if (line.length != 3) {
				//TODO problem, check for empty strings to recover?
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
		//TODO check that v1 and v2 are valid vertex. Is it necessary?
		//init start vertex..
		v1.setDistance(0f);
		
		boolean areWeDone = false;
		while (!areWeDone) {
			//find smallest distance
			double small = inf;
			int smallIndex = -1;
			//for (int i = 0; i < vertices.size(); i++) {
			int j = 0;
			while (smallIndex == -1) {
				if (vertices.get(j).getDistance() < small && vertices.get(j).isVisited() == false)
					smallIndex = j; 
				j++;
			}
			//nothing was smallest, just pick first unvisited
			if (smallIndex == -1) {
//				for (int i = 0; i < vertices.size(); i++) {
//					if (!vertices.get(i).isVisited()) {
//						smallIndex = i;
//						break;
//					}
//				}
				areWeDone = true;
				break;
			}
			
			Vertex currentVisit = vertices.get(smallIndex);
			double travelCost;
			for (int i = 0; i < currentVisit.neighborSize(); i++){
				travelCost = currentVisit.getDistance() + currentVisit.getNeighbor(i).getWeight();
				if (travelCost < currentVisit.getNeighbor(i).getEnd().getDistance() && currentVisit.getNeighbor(i).getEnd().isVisited() == false){
					currentVisit.getNeighbor(i).getEnd().setDistance(travelCost);
					currentVisit.getNeighbor(i).getEnd().setPrevious(currentVisit);
				}
			}
			currentVisit.setVisited(true);
			
			//check if it's time to exit loop
			for (int i = 0; i < vertices.size(); i++) {
				if (!vertices.get(i).isVisited()) {
					areWeDone = false;
					i = vertices.size();
				}
				if (i == vertices.size() -1)
					areWeDone = true;
			}
		}
		return vertices;
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		File test = new File("example.txt");
		Graph fun = new Graph();
		fun.fileImport(test);
		System.out.println("vertices " + fun.vertices.size());
		System.out.println("edges " + fun.edges.size());

		for (int i = 0; i < fun.vertices.size(); i++)
			System.out.println(fun.vertices.get(i).getName() + " distance " + fun.vertices.get(i).getDistance());
//		for (int i = 0; i < fun.edges.size(); i++){
//			System.out.print(fun.edges.get(i).getStart().getName() + " + ");
//			System.out.print(fun.edges.get(i).getEnd().getName() + " + ");
//			System.out.println(fun.edges.get(i).getWeight());
//		}
		
		fun.findPath(fun.vertices.get(0), fun.vertices.get(1));
		for (int i = 0; i < fun.vertices.size(); i++)
			System.out.println(fun.vertices.get(i).getName() + " distance " + fun.vertices.get(i).getDistance());
		
	}
	
}
