//Steven Arnold - COMP 2150 001
//Wrote this part pretty quickly so just made everything static instead of 
//making objects. readBinaryFile fills an int[][] with the file, expandSelection recursively finds selection area
//printImage... prints the image. Everything else in main. No input checking or catching exceptions (homework doesn't specify if needed).

import java.io.*;
import java.util.Scanner;

public class MagicWand {

	
	private static void printImage(int[][] binImage) {
		String thisChar = "";
		for (int i = 0; i < binImage.length; i++) {
			for (int j = 0; j < binImage[0].length; j++) {
				if (binImage[i][j] == 0)
					thisChar = " ";
				else if (binImage[i][j] == 1)
					thisChar = ".";
				else
					thisChar = "?";
				System.out.print(thisChar + " ");
			}
			System.out.println();
		}
	}
	
	private static void printImage(int[][]binImage, int[][] selection) {
		String thisChar = "";
		for (int i = 0; i < binImage.length; i++) {
			for (int j = 0; j < binImage[0].length; j++) {
				if (selection[i][j] == 1)
					thisChar = "*";
				else if (binImage[i][j] == 0)
					thisChar = " ";
				else if (binImage[i][j] == 1)
					thisChar = ".";
				else
					thisChar = "?";
					
				System.out.print(thisChar + " ");
			}
			System.out.println();
		}
	}
	
	//returns 2d array of same size, 1's are selected pixels, 0's are not, -1 also not
	//this is the wrapper for the recursive function
	private static int[][] expandSelection(int row, int col, int[][] binImage) {
		//setup output array
		int[][] selection = new int[binImage.length][binImage[0].length];
		fillWithZero(selection);
		
		//int we will be looking for
		int token = binImage[row][col];
		
		expandSelection(row, col, binImage, selection, token);
		return selection;
	}
	
	//the recursive part
	private static void expandSelection(int row, int col, int[][] binImage, int[][] selection, int token) {
		//exit if bad coordinates
		if (row < 0 || row >= binImage.length || col < 0 || col >= binImage[0].length)
			return;
		//only do this if we haven't been here
		if (selection[row][col] == 0) {
			if (binImage[row][col] == token) {
				selection[row][col] = 1;
				expandSelection(row+1,col,binImage,selection,token);
				expandSelection(row-1,col,binImage,selection,token);
				expandSelection(row,col+1,binImage,selection,token);
				expandSelection(row,col-1,binImage,selection,token);
			}
			else
				//make sure we don't do this comparison again
				selection[row][col] = -1;
		}
		
	}
	
	//just separated this because I thought I might change it. fill given array with 0s
	private static void fillWithZero(int[][] arr) {
		for (int i=0; i < arr.length; i++) {
			for (int j=0; j < arr[0].length; j++) {
				arr[i][j] = 0;
			}
		}
	}
	
	//read from file and output 2d array
	//modified my ElevationVisualizer file reader method
	private static int[][] readBinaryFile(File f) throws FileNotFoundException {
    	//get number of lines so we know how many rows to initialize
    	Scanner reader = new Scanner(f);
    	int lineCount = 1; //init at 1 because we're stealing first line when counting columns
    	int colCount = reader.nextLine().split("\\s").length - 1; //first element is empty string, so length - 1 gives true count
    	while (reader.hasNextLine())
    	{
    	    lineCount++;
    	    reader.nextLine();
    	}
    	
    	//2d array of lineCount x colCount dimension
    	int[][] out = new int[lineCount][colCount];
    	String[] line = new String[colCount];
    	reader = new Scanner(f);
    	
    	//fill in output array
    	for (int i = 0; i < lineCount; i++)
    	{
    		line = reader.nextLine().split("\\s");
    		//length-1 allowed for more use cases in elevationvisualizer,
    		//might be better to use colCount now but I need to turn this in and don't want
    		// to play with it since it works.. we'll assume files are formatted correctly
    		for (int j = 0; j < line.length-1; j++)
    		{
    			//j + 1 because line will contain the first element with an empty string, this evens it out
    			out[i][j] = Integer.parseInt(line[j+1]);
    		}
    	}
    	return out;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		boolean keepGoing;
		
		System.out.println("Welcome to Super Amazing notPhotoshop 2000 Alpha");
		System.out.println("Please move a binary textfile to the project folder");
		System.out.println("What is the file name?: ");
		String fileName = input.nextLine();
		
		//read file and print image
		File pic = new File(fileName);
		int[][] picArray = readBinaryFile(pic);
		printImage(picArray);
		System.out.println("Nice, I guess. I'm not one to judge...");
		
		do {
			System.out.println("Magic Wand initialized. Choose row and column: ");
			int row = input.nextInt();
			int col = input.nextInt();
			input.nextLine();
			int[][] selection = expandSelection(row, col, picArray);
			System.out.println();
			printImage(picArray, selection);
			System.out.println("Coming soon: features! Try again? type 'y' for yes: ");
			keepGoing = ('Y' == input.nextLine().toUpperCase().charAt(0));
		} while(keepGoing);
	}

}
