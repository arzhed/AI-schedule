package taAllocation;

import java.util.Vector;

public class Solution {
	
	private Vector<Pair<Lab, TA>> solution = new Vector<Pair<Lab, TA>>();
	
	public void addElement (Pair<Lab, TA> elem) {
		solution.add(elem);
	}
	
	public Vector<Pair<Lab, TA>> getSolution() {
		return solution;
	}
	
}
		