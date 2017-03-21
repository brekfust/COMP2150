/**
 * GUI application to read and visualize elevation data.
 * Starting point for HW 5.
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class ElevationVisualizer extends JFrame {

    // panel that displays the elevation data
    private class DisplayPanel extends JPanel {
        private int[][] data;       // elevation data stored in a 2D array
        private int[] path;         // path data - element i in this array corresponds to the row of
        							//  column i that should be chosen to try to minimize elevation differences

        public void setData(int[][] data) {
            this.data = data;
        }

        public void setPath(int[] path) {
            this.path = path;
        }

        // overrides the Swing paintComponent method -- defines how to draw this panel
        public void paintComponent(Graphics g) {
            if (dataLoaded) {
                int max = findMax(data);
                for (int r = 0; r < data.length; r++) {		// each pixel on the panel = 1 value in the data array
                    for (int c = 0; c < data[r].length; c++) {
                        int h = (int)((double)data[r][c]/max*255);  // lighter = higher, darker = lower
                        g.setColor(new Color(h, h, h));
                        g.drawRect(c, r, 1, 1);
                    }
                }
            }

            if (pathRequested) {
                for (int i = 0; i < path.length; i++) {
                    g.setColor(Color.GREEN);
                    g.drawRect(i, path[i], 1, 1);
                }
            }
        }
    }

    // event handler for button presses
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {	// gets called whenever an "action event" (e.g., button press) occurs
            Object src = e.getSource();
            if (src == bLoadFile)
                chooseFile();
            else if (src == bFindPath)
                findPath();
        }
    }

    private DisplayPanel    pDisplay = new DisplayPanel();
    private JButton         bLoadFile = new JButton("Load new data file"),
    						bFindPath = new JButton("Find a path");
    private JLabel          lStatus = new JLabel("Use the Load button to open a data file");
    private boolean         dataLoaded = false,     // has the user loaded map data yet?
    						pathRequested = false;  // has the user requested a path yet?

    // constructor - handles GUI construction tasks
    public ElevationVisualizer() {
        ButtonHandler bh = new ButtonHandler(); // register event handler for buttons
        bLoadFile.addActionListener(bh);
        bFindPath.addActionListener(bh);

        JPanel cp = new JPanel();       // content pane
        cp.setLayout(new BorderLayout());

        JPanel bp = new JPanel();       // panel to hold load/path buttons
        bp.setLayout(new GridLayout(1, 2));
        bp.add(bLoadFile);
        bp.add(bFindPath);

        cp.add(BorderLayout.NORTH, lStatus);
        cp.add(BorderLayout.CENTER, pDisplay);
        cp.add(BorderLayout.SOUTH, bp);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Elevation Data Visualizer");
        setContentPane(cp);
        setSize(600, 600);
        setVisible(true);
    }

    // opens a file chooser dialog to read elevation data, and calls
    //  readElevationData to import that data into the application
    private void chooseFile() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            try {
                pDisplay.setData(readElevationData(f));
                dataLoaded = true;
                pathRequested = false;
                repaint();      // triggers the paintComponent method to be called
                lStatus.setText(f.getName() + " successfully opened!");
            } catch (FileNotFoundException e) {
                lStatus.setText("Can't find " + f.getName());
            } catch (ArrayIndexOutOfBoundsException e) {
                lStatus.setText(f.getName() + " seems to have inconsistent rows and columns");
            } catch (NumberFormatException e) {
                lStatus.setText(f.getName() + " seems to contain non-integer data");
            }
        }
    }

    // **** COMPLETE THIS METHOD ****
    // reads elevation data from the specified file into a 2-D array
    // returns the array created
    // throws:  FileNotFoundException if file isn't found
    //          ArrayIndexOutOfBoundsException if each row in the file doesn't have the same number of columns
    //          NumberFormatException if file contains non-integer data
    private int[][] readElevationData(File f) throws FileNotFoundException, ArrayIndexOutOfBoundsException, NumberFormatException {
    	//get number of lines so we know how many rows to initialize
    	Scanner reader = new Scanner(f);
    	int lineCount = 1; //init at 1 because we're stealing first line when counting columns
    	int colCount = reader.nextLine().split("\\s{3}").length - 1; //first element is empty string, so length - 1 gives true count
    	while (reader.hasNextLine())
    	{
    	    lineCount++;
    	    reader.nextLine();
    	}
    	int[][] out = new int[lineCount][colCount];
    	String[] line = new String[colCount];
    	reader = new Scanner(f);
    	for (int i = 0; i < lineCount; i++)
    	{
    		line = reader.nextLine().split("\\s{3}");
    		//loop to line.length-1 instead of colCount, this will allow for a row with too many columns to pass through
    		//but it will be caught by chooseFile
    		for (int j = 0; j < line.length-1; j++)
    		{
    			//j + 1 because line will contain the first element with an empty string, this evens it out
    			out[i][j] = Integer.parseInt(line[j+1]);
    		}
    	}
    	return out;
    }

    private void findPath() {
        if (!dataLoaded)
            lStatus.setText("Can't find path yet, must load a data file first!");
        else {
            pDisplay.setPath(findPath(pDisplay.data));
            pathRequested = true;
            repaint();      // triggers the paintComponent method to be called
            lStatus.setText("West-east path computed!");
        }
    }

    // **** COMPLETE THIS METHOD ****
    // finds and returns a west-east path through the area whose elevation is stored in data
    private int[] findPath(int[][] data) {
    	//we can assume all rows have the same columns by this point
    	int[] path = new int[data[0].length];
    	
    	//init location var with starting point
    	int loc = indexOfMinFromCol(data, 0);
    	int here, up, down, right;
    	//init first path position, loop won't do it
    	path[0] = loc;
    	for (int i = 1; i < data.length; i++)
    	{
    		here = data[loc][i-1];
    		//give each direction a -1 if it doesn't exist it won't be chosen later
    		//don't consider up if we're at the top
    		if (loc - 1 >= 0)
    			up = Math.abs(here - data[loc-1][i]);
    		else
    			up = -1;
    		//don't consider down if we're at the bottom
    		if (loc + 1 <= data.length)
    			down = Math.abs(here - data[loc+1][i]);
    		else
    			down = -1;
    		//right is OK
    		right = Math.abs(here - data[loc][i]);
    		
    		//look for smallest number, starting with up
    		if (up > right && right != -1)// right beats up
    		{
    			if (right > down && down != -1) //down wins
    			{
    				loc++;
    			} 
    			else //right wins
    			{
    				//do nothing, next spot will be in same row
    			}
    		} else if (up > down && down != -1) //down wins (again) 
    		{
    			loc++;
    		} else { //up wins
    			loc--;
    		}
    		path[i] = loc;
    	}
    	return path;
    }

    // **** COMPLETE THIS METHOD ****
    // finds and returns the max element from the array a
    public static int findMax(int[][] a) {
    	int max = 0;
    	for (int i = 0; i < a.length; i++)
    	{
    		for (int j = 0; j < a[i].length; j++)
    		{
    			if (a[i][j] > max)
    				max = a[i][j];
    		}
    	
    	}
    	return max;
    }

    // **** COMPLETE THIS METHOD ****
    // finds and returns the index of the min element from column c of the array a
    public static int indexOfMinFromCol(int[][] a, int c) {
    	int minIndex = 0;
    	int minValue = a[0][c]; //grab first value from column for init
    	for (int i = 1; i < a.length; i++)
    	{
    		if (a[i][c] < minValue)
    		{
    			minValue = a[i][c];
    			minIndex = i;
    		}
    	}
    	return minIndex;
    }

    public static void main(String[] args) {
        new ElevationVisualizer();
    }
}
