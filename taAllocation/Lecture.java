package taAllocation;

import java.util.Vector;

/**
 * Creates a Lecture object, extends Entity so it is named.
 * @author Kristopher Maxwell, Charles Cote, Arthur Simon, Florent Duffez
 *
 */
public class Lecture extends Entity {
    private Vector<Lab> labList = new Vector<Lab> ();
    private Timeslot time = null;
    private Course course;

    /**
	 * Constructs a Lecture with name <code>name</code>.
	 * @param name name of the new Lecture.
	 */
	public Lecture(String name) {
		super(name);
	}
	
	/**
	 * Constructs a new Lecture with the same name as the given Lecture.
	 * @param course another Lecture.
	 */
	public Lecture(Lecture lecture) {
		super(lecture);
	}
	
	/**
	 * Constructs a new Lecture with the same name as <code>lecture</code>, with the Course <code>c</code>.
	 * @param lecture another Lecture.
	 * @param c the Course for this Lecture.
	 */
	public Lecture(Lecture lecture, Course c) {
		super(lecture);
		course = c;
	}
	
	/**
	 * Constructs a new Lecture with the name <code>name</code> and the Course <code>c</code>.
	 * @param name name of the new Lecture.
	 * @param c the Course for this Lecture.
	 */
	public Lecture(String name, Course c) {
		super(name);
		course = c;
	}

	/**
	 * Gets the list of Labs this Lecture has.
	 * @return the list <code>labList</code>.
	 */
    public Vector<Lab> getLabList() {
        return labList;
    }

    /**
     * Adds the lab <code>lab</code> to the list of labs.
     * @param lab the lab to be added.
     */
    public void addLab(Lab lab) {
        labList.add(lab);
    }

    /**
     * Gets the Timeslot for this Lecture.
     * @return the Timeslot <code>time</code>.
     */
    public Timeslot getTime () {
        return time;
    }

    /**
     * Sets the Timeslot for this Lecture.
     * @param t the new Timeslot for this Lecture.
     */
    public void setTime(Timeslot t) {
        time=t;
    }
    
    /**
     * Checks if this Lecture has a Timeslot.
     * @return true if this Lecture has a Timeslot, false otherwise.
     */
    public boolean hasTime() {
    	if (time == null)
    		return false;
    	return true;
    }

    /**
     * Gets the Course for this Lecture.
     * @return the Course <code>course</code>.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the Course for this Lecture.
     * @param c the new Course for this Lecture.
     */
    public void setCourse (Course c) {
        course=c;
    }
}