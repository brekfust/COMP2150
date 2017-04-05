import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SortVisualizer extends JFrame {

	private JTextField  tf = new JTextField(10);
    private JButton[]   buttons;
    private String[]    buttonLabels = {"Bubble sort", "Selection sort", "Insertion sort", "Shell sort", "Merge sort", "Heapsort", "Quicksort", "Monkeysort", "Shuffle"};

    private SortDisplay canvas = new SortDisplay();

    // The constructor sets various parameters of the window (title, size, etc.) and
    //  adds the individual GUI elements into the window.
    public SortVisualizer() {
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Sort Visualizer v0.99b");
        setSize(517, 439);  // 413

        buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttons.length; i++)
            buttons[i] = new JButton(buttonLabels[i]);
        
        // each button must be registered with an action listener to let it know what to do when pressed
        ButtonHandler bh = new ButtonHandler();
        for (JButton b : buttons)
            b.addActionListener(bh);

        // cp will be the "content pane" of this JFrame -- we add the GUI elements to the content pane,
        //  rather than to the JFrame directly
        JPanel cp = new JPanel();
        cp.setLayout(new BorderLayout());
        cp.add(tf, BorderLayout.NORTH);
        cp.add(canvas, BorderLayout.CENTER);
        
        // bp is a container panel to hold the three buttons on the lower part of the GUI
        JPanel bp = new JPanel();
        bp.setLayout(new GridLayout(3, 4));
        for (JButton b : buttons)
            bp.add(b);

        cp.add(bp, BorderLayout.SOUTH);

        setContentPane(cp);
        setVisible(true);
    }

    // Enables or disables all buttons on GUI EXCEPT the one at the specified index.
    public void updateButtons(int index) {
        // No sort thread currently running - disable all buttons except the specified one, and
        //  change that one's text to "stop"
        if (!canvas.isThreadRunning()) {
            for (int i = 0; i < buttons.length; i++) {
                if (i == index) {
                    buttons[i].setEnabled(true);
                    buttons[i].setText("Stop");
                } else {
                    buttons[i].setEnabled(false);
                }
            }
        }
        
        // Sort thread already running - enable all buttons, and change the specified one's text back
        //  to its original content
        else {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setEnabled(true);
                if (i == index)
                    buttons[i].setText(buttonLabels[index]);
            }
        }
    }
    
    // Event handler for button presses.
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // find out which button was pressed
            Object src = e.getSource();
            int buttonIndex = 0;
            for (int i = 0; i < buttons.length; i++) {
                if (src == buttons[i]) {
                    buttonIndex = i;
                    break;
                }
            }

            // enable/disable all other buttons if any button besides Shuffle was pressed
            if (buttonIndex != buttons.length - 1)
                updateButtons(buttonIndex);
            
            // now do the actions associated with the pressed button
            if (canvas.isThreadRunning())
                canvas.cancelThread();
            else {
                switch (buttonIndex) {
                    case 0:
                        tf.setText("Executing bubble sort...");
                        canvas.bubbleSort();
                        break;
                    case 1:
                        tf.setText("Executing selection sort...");
                        canvas.selectionSort();
                        break;
                    case 2:
                        tf.setText("Executing insertion sort...");
                        canvas.insertionSort();
                        break;
                    case 3:
                        tf.setText("Executing Shell sort...");
                        canvas.shellSort();
                        break;
                    case 4:
                        tf.setText("Executing merge sort...");
                        canvas.mergeSort();
                        break;
                    case 5:
                        tf.setText("Executing heapsort...");
                        canvas.heapsort();
                        break;
                    case 6:
                        tf.setText("Executing quicksort...");
                        canvas.quicksort();
                        break;
                    case 7:
                        tf.setText("Executing monkeysort... Abandon all hope");
                        canvas.monkeysort();
                        break;
                    default:
                        tf.setText("Every day I'm shufflin'...");
                        canvas.shuffle();
                }
            }
        }
    }
    
    
    // main method - just instantiates the SortVisualizer class
    public static void main(String[] args) {
        new SortVisualizer();
    }
}
