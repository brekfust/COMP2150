import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class MajorFinderMap {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		//flag for passing input loop
		boolean weDidIt = false;
		String filename = ""; //please the compiler
		do {
			System.out.print("Enter the file name: ");
			try {
			filename = input.nextLine();
			} catch (Exception e) {
				System.out.println("Something went wrong.");
				continue;
			}
			weDidIt = true;
		} while(!weDidIt);
		input.close();
		
		HashMap<String, Integer> majorCount = countMajors(filename);
		if (majorCount == null) 
			System.out.println("Something didn't work out.");
		else 
			System.out.println(majorCount);
			
	}
	
	public static HashMap<String, Integer> countMajors(String filename) throws FileNotFoundException {
		//attempt to load file
		File studentList;
		try { 
			studentList = new File(filename);
		} catch (Exception e) {
			return null;
		}
		Scanner studentScanner = new Scanner(studentList);
		HashMap<String, Integer> studentMap = new HashMap<>();
		while ( studentScanner.hasNext()) {
			String line[] = studentScanner.nextLine().split(",");
			String major = line[2];
			
			if (!studentMap.containsKey(major)) 
				studentMap.put(major, new Integer(1)); //start with 1 if it doesn't exist
			else { 
				Integer temp = studentMap.get(major);
				studentMap.put(major, new Integer((int)temp + 1)); //increment if it does
			}
		}
		
		return studentMap;
	}

}
