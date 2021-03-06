package taAllocation;

import java.util.Vector;
import java.util.Iterator;

/**
 * Creates a Solution object, implements Comparable so it can be sorted in a list.
 * @author Kristopher Maxwell, Charles Cote, Arthur Simon, Florent Duffez
 *
 */
public class Solution implements Comparable<Solution> {
	
	private Vector<Pair<Lab, TA>> solution = new Vector<Pair<Lab, TA>>();

    /**holds TAs with no labs  */
    private Vector<TA> noLabs = new Vector<TA>();

    /**holds TAs with more than MIN_LABS     */
	private Vector<Pair<Integer, TA>> moreThanMinLabs = new Vector<Pair<Integer, TA>>();
    /** holds TAs with at least MIN_LABS     */
	private Vector<Pair<Integer, TA>> atLeastMinLabs = new Vector<Pair<Integer, TA>>();

    /**holds TAs who don't have their first preferred courses     */
	private Vector<TA> noPref1 = new Vector<TA>();
    /**     *  holds TAs who don't have their second preferred courses     */
	private Vector<TA> noPref2 = new Vector<TA>();
    /**  holds TAs who don't have their third preferred courses */
	private Vector<TA> noPref3 = new Vector<TA>();
	
	private Vector<Pair<Vector<Lab>, TA>> moreThan2Courses = new Vector<Pair<Vector<Lab>, TA>>();
	
	/** holds TA lab pairs where the TA doesn't know the material for the lab*/
	private Vector<Pair<Lab, TA>> doesntKnowLab = new Vector<Pair<Lab, TA>>();
	
	public int[] SCV = new int[11];
	public int[] SCVP = new int[] {-50, -5, -10, -10, -20, -35, -30, -10, -25, -5, -10};
	public int total = 0;

    /**
     * Copy constructor for cloning in mutation method
     * @param  s
     */
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
	
	/**
	  * required default constructor
	 /*
	public Solution () {
	
	}

    /**
     * clean all data structures
     */
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

    /**
     * add element to solution
     * @param elem
     */
	public void addElement (Pair<Lab, TA> elem) {
		solution.add(elem);
	}

	/**
	  * @return solution assignments vector
	  */
	public Vector<Pair<Lab, TA>> getSolution() {
		return solution;
	}

    /**
     * @return  the vector of TAs not assigned to any lab
     */
	public Vector<TA> checkNoLabs() {
		return noLabs;
	}

    /**
     * Add <code>TA ta</code> to the vector moreThanMinLabs
     * @param ta
     * @param num - number of labs <code>TA ta</code> is assigned to
     */
	public void addMTML(TA ta, int num) {
		for (Pair<Integer, TA> pair : moreThanMinLabs) {
			if (pair.getKey().equals(ta)) {
				return;                       t
			}
		}
		moreThanMinLabs.add(new Pair<Integer, TA>(num, ta));
	}

    /**
     * @return list of TAs with more than min labs
     */
	public Vector<Pair<Integer, TA>> getMTML() {
		return moreThanMinLabs;
	}

    /**
     * Add <code>TA ta</code> to vector atLeastMinLabs
     * @param ta
     * @param num - number of labs TA ta is assigned to
     */
	public void addALML(TA ta, int num) {
		for (Pair<Integer, TA> pair : atLeastMinLabs) {
			if (pair.getKey().equals(ta)) {
				return;
			}
		}
		atLeastMinLabs.add(new Pair<Integer, TA>(num, ta));
	}

    /**
     * @return  list of TAs with at least min labs
     */
	public Vector<Pair<Integer, TA>> getALML() {
		return atLeastMinLabs;
	}

    /**
     * add TA ta that is not assigned to any lab to the corresponding data structure : the vector of TAs <code>noLabs</code>
     * @param ta
     */
	public void addNolabs(TA ta) {
		if (!noLabs.contains(ta))
			noLabs.add(ta);
	}

    /**
     * @return the vector <code>noLabs</code> of TAs not assigned to any lab.
     */
	public Vector<TA> getNoLabs() {
		return noLabs;
	}

    /**
     * Add TA ta to the vector noPref1 that holds TAs not assigned to their 1st preferred course
     * @param ta
     */
	public void addPref1(TA ta) {
		if (!noPref1.contains(ta))
			noPref1.add(ta);
	}

    /**
     * @return the vector of TAs not assigned to their 1st prefered courses
     */
	public Vector<TA> getPref1() {
		return noPref1;
	}

    /**
     * Add TA ta to the vector noPref2 that holds TAs not assigned to their 2nd preferred course
     * @param ta
     */
	public void addPref2(TA ta) {
		if (!noPref2.contains(ta))
			noPref2.add(ta);
	}

    /**
     * @return the vector of TAs not assigned to their 2nd prefered courses
     */
	public Vector<TA> getPref2() {
		return noPref2;
	}

    /**
     * Add TA ta to the vector noPref3 that holds TAs not assigned to their 3rd preferred course
     * @param ta
     */
	public void addPref3(TA ta) {
		if (!noPref3.contains(ta))
			noPref3.add(ta);
	}

    /**
     * @return the vector of TAs not assigned to their 3rd prefered courses
     */
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
	
	/**
	  * @return the vector of TAs assigned to labs of more than two different courses
	  */
	public Vector<Pair<Vector<Lab>, TA>> getManyCourses() {
		return moreThan2Courses;
	}

    /**
     * adds a pair to doesntKnowLab
     * @param pair
     */
	public void addDoesntKnow(Pair<Lab, TA> pair) {
		if (!doesntKnowLab.contains(pair))
			doesntKnowLab.add(pair);
	}

    /**
     * @return vector of TAs assigned to labs they don't know the material to
     */
	public Vector<Pair<Lab, TA>> getDoesntKnow() {
		return doesntKnowLab;
	}


    /**
     *  gives a lab from a ta to another; updates all vectors in solution
	 *	@param TA, TA, Lab
     */
	public void giveLab(TA giver, TA taker, Lab lab) {
		
		Pair<Lab, TA> oldPair = new Pair<Lab, TA>(lab, giver);
		int i = 0;
		for (Iterator<Pair<Lab, TA>> it = solution.iterator(); it.hasNext();) {
			Pair<Lab, TA> pair = it.next();
			if (oldPair.equals(pair)) {
				it.remove();
				break;
			}
			i++;
		}
		solution.add(new Pair<Lab, TA>(lab, taker));
		
		i = 0;
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
		
		for (int j = 1; j < 4; j++) {
			if ((giver.getPrefer(j) != null) && (giver.getPrefer(j).equals(lab.getLecture().getCourse()))) {
				if (j == 1) {
					addPref1(giver);
					break;
				} else if (j == 2) {
					addPref2(giver);
					break;
				} else if (j == 3) {
					addPref3(giver);
					break;
				}
			}
		}
		for (int j = 1; j < 4; j++) {
			if ((taker.getPrefer(j) != null) && (taker.getPrefer(j).equals(lab.getLecture().getCourse()))) {
				if (j == 1) {
					noPref1.remove(taker);
					break;
				} else if (j == 2) {
					noPref2.remove(taker);
					break;
				} else if (j == 3) {
					noPref3.remove(taker);
					break;
				}
			}
		}
	}

    /**
     * trade labs between two tas
     * @param ta1
     * @param ta2
     * @param lab1
     * @param lab2
     */
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
		