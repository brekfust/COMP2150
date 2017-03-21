/**
 * 
 */

/**
 * @author brekfu
 *
 */

package testPackage;
import java.util.*;
import java.io.*;
public class Tester {

	/**
	 * 
	 */
	public Tester() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	 public static void main(String[] args) throws Exception {
		int[] testArray = new int[5];
		String[] testStringArray = new String[10];
		String testString = new String();
		System.out.print(testArray.length);
		System.out.print("\n" + testStringArray.length + "\n" + testStringArray.length);
		System.out.println(testString.length());
		
//		try{
//		Scanner input = new Scanner(System.in);
//		System.out.print("What?: ");
//		int kk = input.nextInt();
//		System.out.print(testStringArray[-1]);
//		}
//		catch(InputMismatchException e)
//		{
//			System.out.println("Well OK then");
//		}
//		catch(Exception e)
//		{
//			System.out.println("We Don't know");
//		
//		}
		
		//File testFile = new File("test.txt",true);
		
		
		PrintWriter testWriter = new PrintWriter("testFile.txt");
		testWriter.append("ccca");
		testWriter.println("accc");
		testWriter.flush();
	}
	 

}
