package taAllocation;

import java.util.Vector;

public class Solution {
	
	private Vector<Pair<Lab, TA>> solution = new Vector<Pair<Lab, TA>>();
	
	// holds TAs with no labs
	private Vector<TA> noLabs = new Vector<TA>();
	
	// holds TAs with more than MIN_LABS
	private Vector<Pair<TA, Integer>> moreThanMinLabs = new Vector<Pair<TA, Integer>>();
	
	public void addElement (Pair<Lab, TA> elem) {
		solution.add(elem);
	}
	
	public Vector<Pair<Lab, TA>> getSolution() {
		return solution;
	}
	
	public Vector<TA> checkNoLabs() {
		return noLabs;
	}
	
	
	public void makeNoLabs(Vector<TA> ta) {
		boolean elem;
		for (TA t : ta) {
			elem = false;
			for (Pair<Lab, TA> pair : solution) {
				if (t.equals(pair.getValue())) {
					elem = true;
					break;
				}
			}
			if (!elem) {
				noLabs.add(t);
			}
		}
	}
	
	
	public void makeMTML(Vector<TA> ta, int min_labs) {
		int numGive;
		for (TA t : ta) {
			numGive = 0;
			for (Pair<Lab, TA> pair : solution) {
				if (t.equals(pair.getValue())) {
					numGive++;
				}
			}
			if (numGive > min_labs) {
				moreThanMinLabs.add(new Pair<TA, Integer>(t, (numGive - min_labs)));
			}
		}
	}
			
	
	// give ta with no labs a lab, preferably from ta with a high # of labs
	public void giveLab(TA giver, TA taker, Lab lab) {
		solution.remove(new Pair<Lab, TA>(lab, giver));
		solution.add(new Pair<Lab, TA>(lab, taker));
		int i = 0;
		int numGive = 0;
		for (Pair<TA, Integer> pair : moreThanMinLabs) {
			if (pair.getKey().equals(giver)) {
				numGive = pair.getValue();
				break;
			}
			i++;
		}
		moreThanMinLabs.remove(i);
		moreThanMinLabs.add(i, new Pair<TA, Integer>(giver, (numGive - 1)));
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
		