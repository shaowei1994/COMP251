package A1;

import A1.Chaining.*;
import A1.Open_Addressing.*;
import java.io.*;
import java.util.*;

public class main {

    /**
     * Calculate 2^w
     */
    public static int power2(int w) {
        return (int) Math.pow(2, w);
    }

    /**
     * Uniformly generate a random integer between min and max, excluding both
     */
    public static int generateRandom(int min, int max, int seed) {
        Random generator = new Random();
        //if the seed is equal or above 0, we use the input seed, otherwise not.
        if (seed >= 0) {
            generator.setSeed(seed);
        }
        int i = generator.nextInt(max - min - 1);
        return i + min + 1;
    }

    /**
     * export CSV file
     */
    public static void generateCSVOutputFile(String filePathName, ArrayList<Double> alphaList, ArrayList<Double> avColListChain, ArrayList<Double> avColListProbe) {
        File file = new File(filePathName);
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            int len = alphaList.size();
            fw.append("Alpha");
            for (int i = 0; i < len; i++) {
                fw.append("," + alphaList.get(i));
            }
            fw.append('\n');
            fw.append("Chain");
            for (int i = 0; i < len; i++) {
                fw.append("," + avColListChain.get(i));
            }
            fw.append('\n');
            fw.append("Open Addressing");
            for (int i = 0; i < len; i++) {
                fw.append(", " + avColListProbe.get(i));
            }
            fw.append('\n');
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        /*===========PART 1 : Experimenting with n===================*/
        //Initializing the three arraylists that will go into the output 
        ArrayList<Double> alphaList = new ArrayList<Double>();
        ArrayList<Double> avColListChain = new ArrayList<Double>();
        ArrayList<Double> avColListProbe = new ArrayList<Double>();

        //Keys to insert into both hash tables
        int[] keysToInsert = {164, 127, 481, 132, 467, 160, 205, 186, 107, 179,
            955, 533, 858, 906, 207, 810, 110, 159, 484, 62, 387, 436, 761, 507,
            832, 881, 181, 784, 84, 133, 458, 36};

        //values of n to test for in the experiment
        int[] nList = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
        //value of w to use for the experiment on n
        int w = 10;

        for (int n : nList) {
        	
            //initializing two hash tables with a seed
            Chaining MyChainTable = new Chaining(w, 137);
            Open_Addressing MyProbeTable = new Open_Addressing(w, 137);

            /*Use the hash tables to compute the average number of 
                        collisions over keys keysToInsert, for each value of n. 
                        The format of the three arraylists to fillis as follows:
                        
                        alphaList = arraylist of all tested alphas 
                                   (corresponding to each tested n)
                        avColListChain = average number of collisions for each
                                         Chain experiment 
                                         (make sure the order matches alphaList)
                        avColListProbe =  average number of collisions for each
                                         Linear Probe experiment
                                           (make sure the order matches)
                        The CSV file will output the result which you can visualize
             */
            //ADD YOUR CODE HERE
            
            // Initialized some variables here to store the number of collision using each corresponding hash function
            int totalChainColCount = 0;
            int totalProbeColCount = 0;
            for (int i = 0; i < n; i++) { 
            	int currentKey = keysToInsert[i];
            	           
                // Insert key using Chaining Hash Function
                totalChainColCount += MyChainTable.insertKey(currentKey);

                // Insert key using Probe Hash Function
                totalProbeColCount += MyProbeTable.insertKey(currentKey);
            }

            /* 
                Load Factor (alpha) = n/m
                - n : number of keys distributed uniformly
                - m : number of slots available
            */
            alphaList.add((n/(double)MyProbeTable.m));

            // Get the average number of collisions then add them into the arrayList
            double avgChainColCount = totalChainColCount/(double)n;
            avColListChain.add(avgChainColCount);

            double avgProbeColCount = totalProbeColCount/(double)n;
            avColListProbe.add(avgProbeColCount);

        }

        generateCSVOutputFile("n_comparison.csv", alphaList, avColListChain, avColListProbe);

        /*===========    PART 2 : Test removeKey  ===================*/
 /* In this exercise, you apply your removeKey method on an example.
        Make sure you use the same seed, 137, as you did in part 1. You will
        be penalized if you don't use the same seed.
         */
        //Please not the output CSV will be slightly wrong; ignore the labels.
        ArrayList<Double> removeCollisions = new ArrayList<Double>();
        ArrayList<Double> removeIndex = new ArrayList<Double>();
        int[] keysToRemove = {6, 8, 164, 180, 127, 3, 481, 132, 4, 467, 5, 160,
            205, 186, 107, 179};

        //ADD YOUR CODE HERE

        // Initializing a new hash table
        Open_Addressing MyProbeTablePart2 = new Open_Addressing(w, 137);
    	
        // Insert nList[7] (16) keys into MyProbeTablePart2 using Open_Addressing method
        int insertColCount;
        for (int i = 0; i < nList[7]; i++) {
        	insertColCount = MyProbeTablePart2.insertKey(keysToInsert[i]);
        }
        
        /* 
          Remove the keys within keysToRemove 
          - Max collision per key == Table size,
          	which indicates that the key does not exists within the table.
        */
        for (int i = 0; i < keysToRemove.length; i++) {
            double removeColCount = MyProbeTablePart2.removeKey(keysToRemove[i]);
            removeCollisions.add(removeColCount);
            
            /* If the collision count is equal to the table size, 
             * it indicates that every slot has been searched, and the value does not exist within the table.
             * Thus, we add the value -1 as the index into the `removeIndex list`
            */ 
            if (removeColCount == MyProbeTablePart2.m) {
            	removeIndex.add((double) -1);
            } else {
            	removeIndex.add((double)i);
            }
        }
        
        generateCSVOutputFile("remove_collisions.csv", removeIndex, removeCollisions, removeCollisions);

        /*===========PART 3 : Experimenting with w===================*/

 /*In this exercise, the hash tables are random with no seed. You choose 
                values for the constant, then vary w and observe your results.
         */
        //generating random hash tables with no seed can be done by sending -1
        //as the seed. You can read the generateRandom method for detail.
        //randomNumber = generateRandom(0,55,-1);
        //Chaining MyChainTable = new Chaining(w, -1);
        //Open_Addressing MyProbeTable = new Open_Addressing(w, -1);
        //Lists to fill for the output CSV, exactly the same as in Task 1.
        ArrayList<Double> alphaList2 = new ArrayList<Double>();
        ArrayList<Double> avColListChain2 = new ArrayList<Double>();
        ArrayList<Double> avColListProbe2 = new ArrayList<Double>();

        //ADD YOUR CODE HERE
        
        // Initailized a list of array as "W" to be tested.
        int[] wArray = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24};
        double alpha = 0;
        
        // Traverse through the above array using one w at a time.
        for (int i = 0; i < wArray.length; i++) {
        	
        	// Initialize the some variables to store the average amount collision counts for 10 simulations using one W.
        	double avgChainColCount10 = 0; 
        	double avgProbeColCount10 = 0; 
        	// let number of keys to be inserted = 16;
			int randomNumbersRequired = 16;
			
						
			// The FOR loop will run 10 times for 10 simulations
        	for (int count = 0; count < 1; count++) {
        		
        		// Initialize two hash tables with a no seed
    			Chaining MyChainTable2 = new Chaining(wArray[i], -1);
    			Open_Addressing MyProbeTable2 = new Open_Addressing(wArray[i], -1);
    			
				// Generate a list (16 total) of random numbers without duplicates
				ArrayList<Integer> randomKeysToInsert = new ArrayList<Integer>();
				while(randomKeysToInsert.size() <= randomNumbersRequired) {
					int randomNumber = generateRandom(0,55, -1);
					if (!randomKeysToInsert.contains(randomNumber)) {
						randomKeysToInsert.add(randomNumber);
					}
				}
	    		
				// Initialize two variable to store the total number of collisions per W per simulation
				int totalChainColCount2 = 0;
	            int totalProbeColCount2 = 0;
	            for (int m = 0; m < randomNumbersRequired; m++) { 
	            	int currentKey = randomKeysToInsert.get(m);
	            	           
	                // Chaining Hash Function
	                totalChainColCount2 += MyChainTable2.insertKey(currentKey);
	
	                // Probe Hash Function
	                totalProbeColCount2 += MyProbeTable2.insertKey(currentKey);
	            }
	
	            /* 
	                Load Factor (alpha) = n/m
	                - n : number of keys distributed uniformly
	                - m : number of slots available
	            */
	            
	        	alpha = (randomNumbersRequired/(double) MyProbeTable2.m);
	            // Add the average amount of collisions per W per simulation into `avgChainColCount10` and `avgChainColCount10` 
	            avgChainColCount10 += totalChainColCount2/(double)randomNumbersRequired;
	            avgProbeColCount10 += totalProbeColCount2/(double)randomNumbersRequired;

        	}
        	
            // Once we have the total average of collision per W for 10 simulations, we find the average of the 10 and store it within the corresponding array below
        	alphaList2.add(alpha);
            avColListChain2.add(avgChainColCount10/(double)10);
            avColListProbe2.add(avgProbeColCount10/(double)10);
        }
		generateCSVOutputFile("w_comparison.csv", alphaList2, avColListChain2, avColListProbe2);
    }

}
