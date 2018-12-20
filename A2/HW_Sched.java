package A2;
import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * The way you implement this method will define which order the assignments appear in when you sort.
	 * Return -1 if a1 should appear after a2
	 * Return 1 if a1 should appear before a2
	 * Return 0 if a1 and a2 are equivalent 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		//YOUR CODE GOES HERE, DONT FORGET TO EDIT THE RETURN STATEMENT
		
		if (a1.weight < a2.weight){
			return 1;
		} else if (a1.weight > a2.weight){
			return -1;
		}
		
		// Return 0 if both A1 and A2 have the same deadline.
		return 0;
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to when assignment #i will be completed. output[i] is 0 if assignment #i is never completed.
	 * The homework you complete first will be given an output of 1, the second, 2, etc.
	 */
	public int[] SelectAssignments() {
		//Use the following command to sort your Assignments: 
		//Collections.sort(Assignments, new Assignment());
		//This will re-order your assignments. The resulting order will depend on how the compare function is implemented
		Collections.sort(Assignments, new Assignment());
		
		//Initializes the homeworkPlan, which you must fill out and output
		int[] homeworkPlan = new int[Assignments.size()];
		//YOUR CODE GOES HERE
		
		// Determine the last deadline in the Assignments and use it to create an array of such size
		int maxTimeSlot = 0;
		for (int i = 0; i < Assignments.size(); i++) {
			maxTimeSlot = Math.max(maxTimeSlot, Assignments.get(i).deadline);
		}
		
		// Keep track of time slots, if occupied[i] returns null, the time slot at i empty and available for assignment
		// size is maxTimeSlot + 1 since t = 0 indicates incomplete work.
		Assignment[] occupied = new Assignment[maxTimeSlot + 1];

		for (int i = 0; i < Assignments.size(); i++) {
			Assignment currentAssignment = Assignments.get(i);
			if (occupied[currentAssignment.deadline] == null) {
				
				// Time slot is available, place the assignment -> DONE.
				homeworkPlan[currentAssignment.number] = currentAssignment.deadline;
				occupied[currentAssignment.deadline] = currentAssignment;
			} else {
				
				// Designated time slot is occupied, traverse through the list from backward from deadline 
				//  (t = 0 reserved for incomplete assignment)
				//
				// Note that since the given Assignments are sorted by weights, all the "occupied time slot" will contains 
				// a weight greater or equal to the currently assigning assignment at all time.
				for (int time = currentAssignment.deadline - 1; time > 0; time--) {
					if (occupied[time] == null) {
						// Found an empty time slot, place the assignment inside -> DONE.
						homeworkPlan[currentAssignment.number] = time;
						occupied[time] = currentAssignment;
						break;
					} 
				}
			}
		}
		return homeworkPlan;
	}
}
	



