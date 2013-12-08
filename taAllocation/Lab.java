package taAllocation;

import java.util.Vector;

/**
 * Creates a Lab object, extends Entity so it is named.
 * @author Kristopher Maxwell, Charles Cote, Arthur Simon, Florent Duffez
 *
 */
public class Lab extends Entity {
    private Timeslot time;
    private Lecture lecture;
	
    /**
     * Constructs a new Lab with name <code>name</code>.
     * @param name name of the new Lab.
     */
	public Lab(String name) {
		super(name);
	}

	/**
	 * Constructs a new Lab with name <code>name</code> and a lecture <code>lec</code>.
	 * @param name name of the new Lab.
	 * @param lec the lecture for this Lab.
	 */
    public Lab(String name, Lecture lec) {
        super(name);
        lecture=lec;
    }
	
    /**
	 * Constructs a new Lab with the same name as the given Lab.
	 * @param lab another Lab.
	 */
	public Lab(Lab lab) {
		super(lab);
	}

	/**
	 * Gets the Timeslot of this Lab.
	 * @return the timeslot <code>time</code>.
	 */
    public Timeslot getTime() {
        return time;
    }

    /**
     * Sets the Timeslot of this Lab to <code>t</code>.
     * @param t the Timeslot to give to this Lab.
     */
    public void setTime(Timeslot t) {
        time=t;
    }

    /**
     * Gets the Lecture of this Lab.
     * @return the Lecture <code>lecture</code>.
     */
    public Lecture getLecture() {
        return lecture;
    }

    /**
     * Sets the Lecture of this Lab to <code>lec</code>.
     * @param lec the Lecture to give to this Lab.
     */
    public void setLecture(Lecture lec) {
        lecture=lec;
    }

}