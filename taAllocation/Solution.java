package taAllocation;

import java.util.Vector;
import java.util.Iterator;

public class Solution implements Comparable<Solution> {
	
	private Vector<Pair<Lab, TA>> solution = new Vector<Pair<Lab, TA>>();
	
	// holds TAs with no labs
	private Vector<TA> noLabs = new Vector<TA>();
	
	// holds TAs with more than MIN_LABS
	private Vector<Pair<Integer, TA>> moreThanMinLabs = new Vector<Pair<Integer, TA>>();
	private Vector<Pair<Integer, TA>> atLeastMinLabs = new Vector<Pair<Integer, TA>>();
	
	// holds TAs who don't have their first, second, or third preferred courses
	private Vector<TA> noPref1 = new Vector<TA>();
	private Vector<TA> noPref2 = new Vector<TA>();
	private Vector<TA> noPref3 = new Vector<TA>();
	
	private Vector<Pair<Vector<Lab>, TA>> moreThan2Courses = new Vector<Pair<Vector<Lab>, TA>>();
	
	// holds TA lab pairs where the TA doesn't know the material for the lab
	private Vector<Pair<Lab, TA>> doesntKnowLab = new Vector<Pair<Lab, TA>>();
	
	public int[] SCV = new int[11];
	public int[] SCVP = new int[] {-50, -5, -10, -10, -20, -35, -30, -10, -25, -5, -10};
	public int total = 0;
	
	public Solution (Solution s) {
		for (Pair<Lab, TA> pair : s.getSolution()) {
			solution.add(new Pair<Lab, TA>(pair.getKey(), pair.getValue()));
		}
		for (TA ta : s.getNoLabs()) {
			noLabs.add(ta);
		}
		for (Pair<Integer, TA> pair : s.getMTML()) {
			moreThanMinLabs.add(new Pair<Integer, TA>(pair.getKey(), pair.getValue()));
		}
		for (TA ta : s.getPref1()) {
			noPref1.add(ta);
		}
		for (TA ta : s.getPref2()) {
			noPref2.add(ta);
		}
		for (TA ta : s.getPref3()) {
			noPref3.add(ta);
		}
		for (Pair<Vector<Lab>, TA> pair : s.getManyCourses())
		{
			Vector<Lab> labs = new Vector<Lab>();
			for (Lab lab : pair.getKey()) {
				labs.add(lab);
			}
			moreThan2Courses.add(new Pair<Vector<Lab>, TA>(labs, pair.getValue()));
		}
	}
	
	public Solution () {
	
	}
	
	public void wipe() {
		noLabs = new Vector<TA>();
		moreThanMinLabs = new Vector<Pair<Integer, TA>>();
		atLeastMinLabs = new Vector<Pair<Integer, TA>>();
		noPref1 = new Vector<TA>();
		noPref2 = new Vector<TA>();
		noPref3 = new Vector<TA>();
		moreThan2Courses = new Vector<Pair<Vector<Lab>, TA>>();
		doesntKnowLab = new Vector<Pair<Lab, TA>>();
	}
		
	
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
		for (Pair<Integer, TA> pair : moreThanMinLabs) {
			if (pair.getKey().equals(ta)) {
				return;
			}
		}
		moreThanMinLabs.add(new Pair<Integer, TA>(num, ta));
	}
	
	public Vector<Pair<Integer, TA>> getMTML() {
		return moreThanMinLabs;
	}
	
	/*
	public void addALML(TA ta, int num) {
		Pair<TA, Integer> pair = new Pair<TA, Integer>(ta, num);
		if (!atLeastMinLabs.contains(pair))
	*/
	
	//public Vector<Pair<Integer, TA>>
	public void addNolabs(TA ta) {
		if (!noLabs.contains(ta))
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
	
	public void addManyCourses(Vector<Lab> labList, TA ta) {
		for (Pair<Vector<Lab>, TA> pair : moreThan2Courses) {
			if (pair.getValue().equals(ta)) {
				return;
			}
		}
		moreThan2Courses.add(new Pair<Vector<Lab>, TA>(labList, ta));
	}
	
	public Vector<Pair<Vector<Lab>, TA>> getManyCourses() {
		return moreThan2Courses;
	}
	
	// adds a pair to doesntKnowLab
	public void addDoesntKnow(Pair<Lab, TA> pair) {
		if (!doesntKnowLab.contains(pair))
			doesntKnowLab.add(pair);
	}
	
	public Vector<Pair<Lab, TA>> getDoesntKnow() {
		return doesntKnowLab;
	}
	
	// give ta with no labs a lab, preferably from ta with a high # of labs
	public void giveLab(TA giver, TA taker, Lab lab) {
		Pair<Lab, TA> oldPair = new Pair<Lab, TA>(lab, giver);
		int j = 0;
		for (Iterator<Pair<Lab, TA>> it = solution.iterator(); it.hasNext();) {
			Pair<Lab, TA> pair = it.next();
			if (oldPair.equals(pair)) {
				it.remove();
				break;
			}
			j++;
		}
		solution.add(new Pair<Lab, TA>(lab, taker));
		int i = 0;
		int numGive = 0;
		for (Pair<Integer, TA> pair : moreThanMinLabs) {
			if (pair.getKey().equals(giver)) {
				numGive = pair.getKey();
				numGive--;
				moreThanMinLabs.remove(i);
				if (numGive > 0)
					moreThanMinLabs.add(i, new Pair<Integer, TA>(numGive, giver));
				break;
			}
			i++;
		}
		i = 0;
		for (Pair<Lab, TA> pair : doesntKnowLab) {
			if (pair.getKey().equals(lab) && pair.getValue().equals(giver)) {
				doesntKnowLab.remove(i);
				break;
			}
			i++;
		}
			
		for (Course c : taker.getKnows()) {
			if (c.equals(lab.getLecture().getCourse())) {
				return;
			}
		}
		doesntKnowLab.add(new Pair<Lab, TA>(lab, taker));
	}
	
	public void swapLabs(TA ta1, TA ta2, Lab lab1, Lab lab2) {
		Pair<Lab, TA> pair1 = new Pair<Lab, TA>(lab1, ta1);
		Pair<Lab, TA> pair2 = new Pair<Lab, TA>(lab2, ta2);
		int numPairsRemoved = 0;
		int i = 0;
		for (Iterator<Pair<Lab, TA>> it = solution.iterator(); it.hasNext();) {
			Pair<Lab, TA> pair = it.next();
			if (pair1.equals(pair)) {
				it.remove();
				numPairsRemoved++;
			}
			else if (pair2.equals(pair)) {
				it.remove();
				numPairsRemoved++;
			}
			
			if (numPairsRemoved >= 2)
				break;
			
			i++;
		}
		pair1.setValue(ta2);
		pair2.setValue(ta1);
		solution.add(pair1);
		solution.add(pair2);
	}
	
	
	@Override
	public int compareTo(Solution o) {
		return this.total - o.total;
	}
}
		