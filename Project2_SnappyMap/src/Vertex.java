import java.util.ArrayList;

public class Vertex {
//	This should include an instance variable for the vertex’s name, as well as 
//	instance variables for the various quantities needed in Dijkstra’s algorithm 
//	(distance, visited status, and previous vertex).You can assume that all vertices
//	in the graph will have unique names
	
	final double inf = Double.POSITIVE_INFINITY;
	private String name;
	private double distance;
	private boolean isVisited;
	private Vertex previous;
	//vertex containing a list of edges may go against the idea of the given
	//project structure, but this seemed like the easiest way to lower the runtime
	private ArrayList<Edge> neighbors;
	
	public Vertex(String n) {
		name = n;
		distance = inf;
		isVisited = false;
		neighbors = new ArrayList<>();
	}
	
	//need this to do a proper arraylist.contains
	//we're only worried about checking for the name when using equals/contains
	//this is probably bad practice.
	public boolean equals(Object o) {
		if (o instanceof Vertex) {
			Vertex isIt = (Vertex)o;
			return (isIt.getName().toUpperCase().equals(this.name.toUpperCase()));
		}
		return false;
	}
	
	//apparently need this to override equals
	public int hashCode() {
		return 1;
	}
	
	public void addNeighbor(Edge e) {
		neighbors.add(e);
	}
	
	public int neighborSize() {
		return neighbors.size();
	}
	
	public Edge getNeighbor(int index) {
		return neighbors.get(index);
	}
	
	public String getName() {
		return name;
	}
	
	public void setDistance(double d) {
		distance = d;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public boolean isVisited() {
		return isVisited;
	}
	
	public void setVisited(boolean b) {
		isVisited = b;
	}
	
	public Vertex getPrevious() {
		return previous;
	}
	
	public void setPrevious(Vertex v) {
		previous = v;
	}
}
