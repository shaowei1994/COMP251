package A1;

import static A1.main.*;

public class Open_Addressing {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public int[] Table;

    //Constructor for the class. sets up the data structure for you
    protected Open_Addressing(int w, int seed) {

        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
        this.Table = new int[m];
        //empty slots are initalized as -1, since all keys are positive
        for (int i = 0; i < m; i++) {
            Table[i] = -1;
        }

    }

    /**
     * Implements the hash function g(k)
     */
    public int probe(int key, int i) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
        
        // h(k) = ((A * k) mod 2^w) >> (w - r)
        int chainHashValue = (int)((A * key) % Math.pow(2, w));
        chainHashValue = chainHashValue >> (w - r);

        // g(k, i) = ((h(k) + i) mod 2^r) where
        int probeHashValue = (int)((chainHashValue + i) % Math.pow(2, r));

        return probeHashValue;
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table[hashValue] == -1;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	
        for (int i = 0; i < m; i++) {
            // Acquire the hash value to place the key in.
        	int hashValue = probe(key, i);
        	
        	// Check if the selected slot is empty 
        	if(isSlotEmpty(hashValue)) {
        		// Place the key within the table and return the value i (which equals to the collision count)
                Table[hashValue] = key;
        		return i;
        	}
        }

        // return m (size of Table) if all the location has been checked
        return m;
    }

    /**
     * Removes key k from hash table. Returns the number of collisions
     * encountered
     */
    public int removeKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
        
        for (int i = 0; i < m; i++) {
        	int hashValue = probe(key, i);
        	if (Table[hashValue] == key) {
        		// Remove the key from the table by setting the value of the location to -1
        		Table[hashValue] = -1;
        		return i;
        	}
        }
        
        // return m (size of Table) if all the location has been checked
        return m ;
    }

}
