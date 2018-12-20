package A2;
import java.util.Arrays;

public class GreedyTester {
	public static void main(String[] args) {
		
		//This is the typical kind of input you will be tested with. The format will always be the same
		//Each index represents a single homework. For example, homework zero has weight 23 and deadline t=3.
		int[] weights = new int[] {23, 60, 14, 25, 7}; 
		int[] deadlines = new int[] {3, 1, 2, 1, 3};
		int m = weights.length;
		
		//This is the declaration of a schedule of the appropriate size
		HW_Sched schedule =  new HW_Sched(weights, deadlines, m);
		
		//This call organizes the assignments and outputs homeworkPlan
		int[] res = schedule.SelectAssignments();
		System.out.println("Should be: \n[3, 1, 2, 0, 0]");
		System.out.println("Actually: \n" + Arrays.toString(res));

		// custom tests
		weights = new int[] {23, 60, 14, 25, 7, 6}; 
		deadlines = new int[] {1, 1, 3, 2, 4, 4};
		m = weights.length;
		
		schedule =  new HW_Sched(weights, deadlines, m);
		
		res = schedule.SelectAssignments();
		System.out.println("Should be: \n[0, 1, 3, 2, 4, 0]");
		System.out.println("Actually: \n" + Arrays.toString(res));

		weights = new int[] {10, 40, 30, 20, 20}; 
		deadlines = new int[] {1, 3, 4, 2, 2};
		m = weights.length;
		
		schedule =  new HW_Sched(weights, deadlines, m);
		
		res = schedule.SelectAssignments();
		System.out.println("Should be: \n[0, 3, 4, 2, 1] OR [0, 3, 4, 1, 2]");
		System.out.println("Actually: \n" + Arrays.toString(res));

		weights = new int[] {30, 20, 15, 48, 29}; 
		deadlines = new int[] {2, 3, 4, 2, 3};
		m = weights.length;
		
		schedule =  new HW_Sched(weights, deadlines, m);
		
		res = schedule.SelectAssignments();
		System.out.println("Should be: \n[1, 0, 4, 2, 3]");
		System.out.println("Actually: \n" + Arrays.toString(res));

		weights = new int[] {4, 4, 5, 2, 3}; 
		deadlines = new int[] {2, 3, 1, 1, 5};
		m = weights.length;
		
		schedule =  new HW_Sched(weights, deadlines, m);
		
		res = schedule.SelectAssignments();
		System.out.println("Should be: \n[2, 3, 1, 0, 4]");
		System.out.println("Actually: \n" + Arrays.toString(res));

		weights = new int[] {1, 1, 2, 3, 2}; 
		deadlines = new int[] {4, 3, 4, 5, 2};
		m = weights.length;
		
		schedule =  new HW_Sched(weights, deadlines, m);
		
		res = schedule.SelectAssignments();
		System.out.println("Should be: \n[3, 2, 4, 5, 1] OR [1, 3, 4, 5, 2]");
		System.out.println("Actually: \n" + Arrays.toString(res));

		weights = new int[] {10, 10, 10, 10, 10}; 
		deadlines = new int[] {1, 1, 3, 4, 5};
		m = weights.length;
		
		schedule =  new HW_Sched(weights, deadlines, m);
		
		res = schedule.SelectAssignments();
		System.out.println("Should be: \n[1, 0, 2, 3, 4] OR [0, 1, 2, 3, 4]");
		System.out.println("Actually: \n" + Arrays.toString(res));


		weights = new int[] {30, 30, 20, 15, 90}; 
		deadlines = new int[] {3, 3, 2, 4, 1};
		m = weights.length;
		
		schedule =  new HW_Sched(weights, deadlines, m);
		
		res = schedule.SelectAssignments();
		System.out.println("Should be: \n[3, 2, 0, 4, 1] OR [2, 3, 0, 4, 1]");
		System.out.println("Actually: \n" + Arrays.toString(res));


		weights = new int[] {50, 49, 51, 39, 49}; 
		deadlines = new int[] {3, 2, 3, 2, 3};
		m = weights.length;
		
		schedule =  new HW_Sched(weights, deadlines, m);
		
		res = schedule.SelectAssignments();
		System.out.println("Should be: \n[2, 1, 3, 0, 0] OR [2, 0, 3, 0, 1]");
		System.out.println("Actually: \n" + Arrays.toString(res));


		// others'
		int[] weights2 = new int[] {10, 20, 11}; 
		int[] deadlines2 = new int[] {1, 2, 2};
		int m2 = weights2.length;

		HW_Sched schedule2 = new HW_Sched(weights2, deadlines2, m2);

		int[] res2 = schedule2.SelectAssignments();

		boolean test2 = true;
		boolean test2_bad = true;

		int[] result2 = new int[] {0, 2, 1};
		int[] result2_bad = new int[] {1, 2, 0};

		for (int i = 0; i < 3; i++) {
			if (res2[i] != result2[i]) {
				test2 = false;
			}
		}
		for (int i = 0; i < 3; i++) {
		if (res2[i] != result2_bad[i]) {
		test2_bad = false;
		}
		}

		if (test2) {
			System.out.println("Test 2: good optimization");
		}
		else if (test2_bad) {
			System.out.println("Test 2: bad optimization");
		}
		else {
			System.out.println("Test 2: fail");
		}
	}
		
}