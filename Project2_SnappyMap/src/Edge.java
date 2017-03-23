
public class Edge {
//	class: This should include instance variables for the start and end vertices,
//	as well as the edge’s weight
	
	private Vertex start;
	private Vertex end;
	private double weight;
	
	public Edge(Vertex s, Vertex e, double w) {
		start = s;
		end = e;
		weight = w;
	}
	
	public Vertex getStart() {
		return start;
	}
	
	public Vertex getEnd() {
		return end;
	}
	
	public double getWeight() {
		return weight;
	}
}
