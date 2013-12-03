package taAllocation;

import java.util.Vector;

public class Solution implements Cloneable {
	
	private Vector<Pair<Lab, TA>> solution = new Vector<Pair<Lab, TA>>();
	
	// holds TAs with no labs
	private Vector<TA> noLabs = new Vector<TA>();
	
	// holds TAs with more than MIN_LABS
	private Vector<Pair<TA, Integer>> moreThanMinLabs = new Vector<Pair<TA, Integer>>();
	
	// holds TAs who don't have their first, second, or third preferred courses
	private Vector<TA> noPref1 = new Vector<TA>();
	private Vector<TA> noPref2 = new Vector<TA>();
	private Vector<TA> noPref3 = new Vector<TA>();
	
	// holds TA lab pairs where the TA doesn't know the material for the lab
	private Vector<Pair<Lab, TA>> doesntKnowLab = new Vector<Pair<Lab, TA>>();
	
	public int[] SCV = new int[11];
	public int[] SCVP = new int[] {-50, -5, -10, -10, -20, -35, -30, -10, -25, -5, -10};
	
	public void addElement (Pair<Lab, TA> elem) {
		solution.add(elem);
	}
	
	public Vector<Pair<Lab, TA>> getSolution() {
		return solution;
	}
	
	public Vector<TA> checkNoLabs() {
		return noLabs;
	}
	
	public void addMTML(TA ta, int num) {
		moreThanMinLabs.add(new Pair<TA, Integer>(ta, num));
	}
	
	public Vector<Pair<TA, Integer>> getMTML() {
		return moreThanMinLabs;
	}
	
	// adds a pair to doesntKnowLab
	public void addNolabs(TA ta) {
		noLabs.add(ta);
	}
		
	public Vector<TA> getNoLabs() {
		return noLabs;
	}
	
	public void addPref1(TA ta) {
		if (!noPref1.contains(ta))
			noPref1.add(ta);
	}
	
	public Vector<TA> getPref1() {
		return noPref1;
	}
	
	public void addPref2(TA ta) {
		if (!noPref2.contains(ta))
			noPref2.add(ta);
	}
	
	public Vector<TA> getPref2() {
		return noPref2;
	}
	
	public void addPref3(TA ta) {
		if (!noPref3.contains(ta))
			noPref3.add(ta);
	}
	
	public Vector<TA> getPref3() {
		return noPref3;
	}
	
	// adds a pair to doesntKnowLab
	public void addDoesntKnow(Pair<Lab, TA> pair) {
		doesntKnowLab.add(pair);
	}
	
	public Vector<Pair<Lab, TA>> getDoesntKnow() {
		return doesntKnowLab;
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
		numGive--;
		if (numGive > 0)
			moreThanMinLabs.add(i, new Pair<TA, Integer>(giver, numGive));
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
	
	// clones the instance of this class that calls this method
	protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
		