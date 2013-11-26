package taAllocation;

import java.util.Vector;

public class Solution {
	
	private Vector<Pair<Lab, TA>> solution = new Vector<Pair<Lab, TA>>();
	
	// holds TAs with no labs
	private Vector<TA> noLabs = new Vector<TA>();
	
	public void addElement (Pair<Lab, TA> elem) {
		solution.add(elem);
	}
	
	public Vector<Pair<Lab, TA>> getSolution() {
		return solution;
	}
	
	public Vector<TA> checkNoLabs() {
		return noLabs;
	}
	
	// give ta with no labs a lab, preferably from ta with a high # of labs
	public void giveLab(TA giver, TA taker, Lab lab) {
		solution.remove(new Pair<Lab, TA>(lab, giver));
		solution.add(new Pair<Lab, TA>(lab, taker));
	}
	
	public void swapLabs(TA ta1, TA ta2, Lab lab1, Lab lab2) {
		Pair<Lab, TA> pair1 = new Pair<Lab, TA>(lab1, ta1);
		Pair<Lab, TA> pair2 = new Pair<Lab, TA>(lab2, ta2);
		solution.remove(pair1);
		solution.remove(pair2);
		pair1.setValue(ta2);
		pair2.setValue(ta1);
		solution.add(pair1);
		solution.add(pair2);
	}
}
		