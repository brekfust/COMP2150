import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MappingApp {
//This is the client program where all user input will be made. 
//It should contain an instance of Graphand allow the user to load a data file,
//view the currently loaded graph, or find the shortestpath between two vertices
//in the currently loaded graph.  As mentioned before, display both the optimal
//cost as well as the path itself.
	
	public static void main(String[] args) {
		//File graphFile;
		Graph fun = new Graph();
		Scanner input = new Scanner(System.in);
		
		System.out.println("Launching SnappleMaps..");
		System.out.println("Please place your file in the project folder.");
		
		//Receive file name and attempt to load graph
		boolean isWeird;
		do {
			isWeird = false;
			System.out.print("What is the file name?: ");
			try { 
				File graphFile = new File(input.nextLine());
				fun.fileImport(graphFile);
			} catch (Exception e) {
				//not seeing much reason to get more specific with exception type catches
				isWeird = true;
				System.out.println("You did something weird. Try again.");
			}
		} while(isWeird);
		
		System.out.println("Cool, cool cool cooooooool.");
		
		boolean shouldIRepeat;
		do {
			shouldIRepeat = false;
			
			//start main menu
			System.out.println("What do you want to do?");
			System.out.println("1. View the edges of my graph.");
			System.out.println("2. Find the shortest path between vertices.");
			System.out.print("Type 1 or 2: ");
			int choice;
			try {
			//this looks weird but charAt(0) is more readable to me than subString(0,1)
			choice = Integer.parseInt("" + input.nextLine().charAt(0));
			} catch (Exception e) {
				shouldIRepeat = true;
				System.out.println("Yeah OK, try again, please.");
				continue;
			}
			if (choice != 1 && choice != 2) {
				shouldIRepeat = true;
				System.out.println("Try entering an actual choice. For real.");
				continue;
			}
			
			//first choice - print map
			if (choice == 1) {
				System.out.println(fun);
				System.out.println("Can you really read all that? Do you feel better?");
			}
			
			//second choice - find path
			if (choice == 2) {
				boolean yetAnotherRepeatFlag;
				//stupid compiler making me fill these with something ugh
				Vertex v1 = null;
				Vertex v2 = null;
				
				//get vertices to create path with
				do {
					yetAnotherRepeatFlag = false;
					
					//get first vertex
					System.out.print("Whats the starting vertex?: ");
					try {
						v1 = fun.getVertexFromString(input.nextLine());
					} catch (Exception e) {
						yetAnotherRepeatFlag = true;
						System.out.println("Say again?");
						continue;
					}
					if (v1 == null) {
						yetAnotherRepeatFlag = true;
						System.out.println("I don't think that was on your list..");
						continue;
					}
					
					//get second vertex
					System.out.print("Whats the ending vertex?: ");
					try {
						v2 = fun.getVertexFromString(input.nextLine());
					} catch (Exception e) {
						yetAnotherRepeatFlag = true;
						System.out.println("Say again?");
						continue;
					}
					if (v2 == null) {
						yetAnotherRepeatFlag = true;
						System.out.println("I don't think that was on your list..");
						continue;
					}
				} while(yetAnotherRepeatFlag);
				
				//algorithm and output
				ArrayList<Vertex> coolPath = fun.findPath(v1, v2);
				for (int i = 0; i < coolPath.size(); i ++)
					System.out.println(coolPath.get(i).getDistance() + " -> " + coolPath.get(i).getName() + " ");
			}
			
			//shall we go again?
			System.out.println("good stuff thats some good stuff right there man, go again?");
			System.out.print("Type Y for yes, anything else for no: ");
			//init to no so it works OK if exception occurs
			char yesOrNo = 'N';
			try {
				yesOrNo = input.nextLine().toUpperCase().charAt(0);
			} catch (Exception e) {
				System.out.println("Heh, I'll take that as a no.");
				//shouldIRepeat should be false already so don't do anything
			}
			if (yesOrNo == 'Y') {
				shouldIRepeat = true;
				System.out.println("K bye.");
			}
		} while(shouldIRepeat);
		

	}
}
