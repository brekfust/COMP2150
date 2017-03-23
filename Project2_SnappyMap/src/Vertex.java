
public class Vertex {
//	This should include an instance variable for the vertex’s name, as well as 
//	instance variables for the various quantities needed in Dijkstra’s algorithm 
//	(distance, visited status, and previous vertex).You can assume that all vertices
//	in the graph will have unique names
	
	final private double inf = Double.POSITIVE_INFINITY;
	private double distance;
	private boolean isVisited;
	private Vertex previous;
	
	public Vertex() {
		distance = inf;
		isVisited = false;
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
