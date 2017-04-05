import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SortDisplay extends JPanel {
    static final int THREAD_DELAY = 50;

    private int height = 300, width = 500;
    private int numBars = 100;
    private Integer[] data = new Integer[numBars];  // the data being sorted

    private SortThread currentThread;	// the currently running sort thread

    public SortDisplay() {
        shuffle();
    }

    public boolean isThreadRunning() {
        return (currentThread != null);
    }
    
    public void cancelThread() {
        if (currentThread != null) {
            currentThread.cancel(true);
            currentThread = null;
        }
    }

    public void paintComponent(Graphics g)  {
        super.paintComponent(g);
        int unitHeight = height / numBars,
            unitWidth = width / numBars;

        for (int i = 0; i < data.length; i++) {
            int val = data[i];
            g.setColor(Color.PINK);
            g.fillRect(i*unitWidth, (numBars-val)*unitHeight, unitWidth, val*unitHeight);
            g.setColor(Color.BLACK);
            g.drawRect(i*unitWidth, (numBars-val)*unitHeight, unitWidth, val*unitHeight);
        }
    }
    
    // Populates the elements in the data array from 1 ... numBars, then randomly
    //  shuffles them.
    public void shuffle() {
        for (int i = 0; i < numBars; i++)
            data[i] = (i+1);
        
        for (int k = 0; k < 50; k++) {
            for (int i = 0; i < data.length; i++) {
                int rand = (int)(data.length*Math.random());
                int temp = data[rand];
                data[rand] = data[i];
                data[i] = temp;
            }
        }
        repaint();
    }
    
    // The xxxxSort() methods below just create a new thread of the
    //  appropriate sort algorithm and execute that thread.  These
    //  methods are the means by which the GUI tells the SortDisplay
    //  to run a certain sort.
    public void bubbleSort() {
        (currentThread = new BubbleSortThread()).execute();
    }
    
    public void selectionSort() {
        (currentThread = new SelectionSortThread()).execute();
    }

    public void insertionSort() {
        (currentThread = new InsertionSortThread()).execute();
    }
    
    public void shellSort() {
        (currentThread = new ShellSortThread()).execute();
    }

    public void mergeSort() {
        (currentThread = new MergeSortThread()).execute();
    }
    
    public void heapsort() {
        (currentThread = new HeapsortThread()).execute();
    }
    
    public void quicksort() {
        (currentThread = new QuicksortThread()).execute();
    }
    
    public void monkeysort() {
        (currentThread = new MonkeysortThread()).execute();
    }

    
    // Each sorting algorithm is implemented as its own thread, which
    //  extends this SortThread class.
    private abstract class SortThread extends SwingWorker<Void, Void> {
        // called automatically in response to publish()
        protected void process(java.util.List<Void> list) {
            SortDisplay.this.repaint();
        }
        
        // calls publish(), then pauses the thread for THREAD_DELAY milliseconds
        protected void nextFrame() {
            publish();
            try {
                Thread.sleep(THREAD_DELAY);
            } catch (InterruptedException e) { }
        }
    }

    private class BubbleSortThread extends SortThread {
        protected Void doInBackground() {
            boolean swapDone = true;
            for (int i = 0; !isCancelled() && swapDone && i < data.length; i++) {
                swapDone = false;
                for (int j = 0; !isCancelled() && j < data.length - 1 - i; j++) {
                    if (data[j] > data[j+1]) {
                        int temp = data[j];
                        data[j] = data[j+1];
                        data[j+1] = temp;
                        swapDone = true;
                        nextFrame();
                    }
                }
            }
            return null;
        }
    }
    
    private class SelectionSortThread extends SortThread {
        protected Void doInBackground() {
            for (int i = 0; !isCancelled() && i < data.length - 1; i++) {
                // find the min element from the indices [i, data.length - 1]
                int minIndex = i;
                for (int j = i; !isCancelled() && j < data.length; j++) {
                    if (data[j] < data[minIndex])
                        minIndex = j;
                    nextFrame();    // really shows how slowly selection sort runs
                }
                
                // swap the min element with index i
                Integer temp = data[i];
                data[i] = data[minIndex];
                data[minIndex] = temp;
                nextFrame();
            }
            return null;
        }
    }

    private class InsertionSortThread extends SortThread {
        protected Void doInBackground() {
            for (int i = 1; !isCancelled() && i < data.length; i++) {
                Integer toInsert = data[i];
                int j = i - 1;
                while (!isCancelled() && j >= 0 && data[j] > toInsert) {
                    data[j+1] = data[j];
                    j--;
                    nextFrame();
                }
                data[j+1] = toInsert;
                nextFrame();
            }
            return null;
        }
    }
    
    public class ShellSortThread extends SortThread {
        protected Void doInBackground() {
          int gap = data.length / 2;
          while (!isCancelled() && gap > 0) {
              // sort individual parts of the array using
              //  the current gap size
        	  for (int k = 0; k < gap; k++) {
	              for (int i = gap + k; !isCancelled() && i < data.length; i += gap) {
	                  Integer toInsert = data[i];
	                  // figure out where to insert
	                  int j = i - gap;
	                  while (!isCancelled() && j >= 0 && data[j] > toInsert) {
	                      data[j + gap] = data[j];
	                      j -= gap;
	                      nextFrame();
	                  }
	                  data[j+gap] = toInsert;
	                  nextFrame();
	              }
        	  }
              
              // reduce the gap size
              gap = (gap == 2) ? 1 : (int)(gap / 2.2);
          }
          return null;
        	
        	
//            int gap = data.length / 2;
//            while (!isCancelled() && gap > 0) {
//                // sort individual parts of the array using
//                //  the current gap size
//                for (int i = gap; !isCancelled() && i < data.length; i++) {
//                    Integer toInsert = data[i];
//                    // figure out where to insert
//                    int j = i - gap;
//                    while (!isCancelled() && j >= 0 && data[j] > toInsert) {
//                        data[j + gap] = data[j];
//                        j -= gap;
//                        nextFrame();
//                    }
//                    data[j+gap] = toInsert;
//                    nextFrame();
//                }
//                
//                // reduce the gap size
//                gap = (gap == 2) ? 1 : (int)(gap / 2.2);
//            }
//            return null;
        }
    }
    
    private class MergeSortThread extends SortThread {
        protected Void doInBackground() {
            mergesort(data, 0, data.length - 1);
            return null;
        }

        // Merge sorts the array a between startIndex and endIndex, inclusive
        private void mergesort(Integer[] a, int startIndex, int endIndex) {
            if (!isCancelled() && endIndex - startIndex >= 1) {
                // sort each half
                int midIndex = (startIndex + endIndex) / 2;
                mergesort(a, startIndex, midIndex);
                mergesort(a, midIndex + 1, endIndex);
                
                // merge the two sorted halves together
                Integer[] temp = new Integer[endIndex - startIndex + 1];
                int i = startIndex, j = midIndex + 1, k = 0;
                while (!isCancelled() && i <= midIndex && j <= endIndex) {
                    if (a[i] < a[j])
                        temp[k++] = a[i++];
                    else
                        temp[k++] = a[j++];
                }
                
                while (!isCancelled() && i <= midIndex)
                    temp[k++] = a[i++];
                
                while (!isCancelled() && j <= endIndex)
                    temp[k++] = a[j++];
                    
                // copy into original array
                for (i = 0; !isCancelled() && i < temp.length; i++) {
                    a[startIndex + i] = temp[i];
                    nextFrame();
                }
            }
        }
    }
    
    public class HeapsortThread extends SortThread {
        protected Void doInBackground() {
            // first build a max-heap out of the array data
            for (int i = 1; !isCancelled() && i < data.length; i++) {
                // add the item at index n to the max heap
                int child = i;
                int parent = (child - 1)/2;
                while (!isCancelled() && parent >= 0 && data[child] > data[parent]) {
                    Integer temp = data[parent];
                    data[parent] = data[child];
                    data[child] = temp;
                    child = parent;
                    parent = (child - 1) / 2;
                    nextFrame();
                }
            }
            
            // now, repeatedly remove the top (max) item from the heap, and
            //  place it at the end of the array
            for (int i = 1; !isCancelled() && i <= data.length; i++) {
                // start by swapping the top item with the item at 
                //  the end of the array
                Integer temp = data[data.length - i];
                data[data.length - i] = data[0];
                data[0] = temp;
                nextFrame();
                
                // re-heapify the new root of the heap
                int heapSize = data.length - i;
                int parent = 0;
                while (!isCancelled()) {
                    // check for left child
                    int leftChild = 2*parent + 1;
                    if (leftChild >= heapSize) // parent is a leaf node - done
                        break;
                        
                    // check for right child, and pick the larger of the two children
                    int rightChild = leftChild + 1;
                    int child;
                    if (rightChild < heapSize && data[rightChild] > data[leftChild])
                        child = rightChild;
                    else
                        child = leftChild;
                        
                    // check for parent being smaller than larger child
                    if (data[parent] >= data[child])  // parent is larger than max child - done
                        break;
                    else {
                        // swap parent and larger child
                        temp = data[parent];
                        data[parent] = data[child];
                        data[child] = temp;
                        nextFrame();
                        
                        // move down the heap
                        parent = child;
                    }
                }
            }
            
            return null;
        }
    }

    public class QuicksortThread extends SortThread {
        protected Void doInBackground() {
            quicksort(data, 0, data.length - 1);
            return null;
        }
        
        // Quicksorts the array a between the indices start and end, inclusive.
        private void quicksort(Integer[] a, int start, int end) {
            if (!isCancelled() && start < end) {
                // partition around the pivot
                int pivot = partition(a, start, end);
                // quicksort each part
                quicksort(a, start, pivot - 1);
                quicksort(a, pivot + 1, end);
            }
        }

        // Partitions the array a between the indices start and end, inclusive.
        //  Returns the new location of the pivot element.
    	private int partition(Integer[] a, int start, int end) {
    		// pick a pivot
    		Integer pivot = a[start];

    		int lower = start, upper = end;
    		while (lower < upper) {
        		// look for the first element (from the left) that's greater than the pivot
        		while (lower < end) {
        			if (a[lower] > pivot)
        				break;
        			lower++;
        		}
        		
        		// look for the first element (from the right) that's less than or equal to the pivot
        		while (upper > start) {
        			if (a[upper] <= pivot)
        				break;
        			upper--;
        		}
        			
        		// swap lower/upper indices if lower < upper
        		if (lower < upper) {
        			Integer temp = a[lower];
        			a[lower] = a[upper];
        			a[upper] = temp;
        			nextFrame();
        		}
    		}
    		
    		// swap the pivot (at index start) with index upper
    		Integer temp = a[start];
    		a[start] = a[upper];
    		a[upper] = temp;
    		nextFrame();
    		
    		return upper;
    	}

//        // Partitions the array a between the indices start and end, inclusive.
//        //  Returns the new location of the pivot element.
//        private int partition(Integer[] a, int start, int end)
//        {
//            // randomly pick a pivot element
//            int pivotIndex = start + (int)(Math.random()*(end - start + 1));
//            int pivot = a[pivotIndex];
//            
//            // put the pivot on the right side of the array
//            data[pivotIndex] = data[end];
//            data[end] = pivot;
//            nextFrame();
//
//            int j = start;  // stores final location of pivot
//            
//            // work through the array elements, comparing them against the pivot
//            for (int i = start; !isCancelled() && i < end; i++) {
//                if (data[i] < pivot) {
//                    Integer temp = data[i];
//                    data[i] = data[j];
//                    data[j] = temp;
//                    nextFrame();
//                    j++;
//                }
//            }
//            
//            // move pivot back to position j
//            Integer temp = data[j];
//            data[j] = pivot;
//            data[end] = temp;
//            nextFrame();
//            
//            return j;
//        }
    }
    
    public class MonkeysortThread extends SortThread {
        protected Void doInBackground() {
            while (!isCancelled() && !isSorted()) {
                for (int i = 0; !isCancelled() && i < data.length; i++) {
                    int rand = (int)(data.length*Math.random());
                    Integer temp = data[i];
                    data[i] = data[rand];
                    data[rand] = temp;
                    nextFrame();
                }
            }
            return null;
        }
        
        private boolean isSorted() {
            for (int i = 0; i < data.length - 1; i++)
                if (data[i] > data[i+1])
                    return false;
            return true;
        }
    }
}
