package taAllocation;

import java.util.Vector;

/**
 * Creates a TA object, extends Entity so it is named.
 * @author Kristopher Maxwell, Charles Cote, Arthur Simon, Florent Duffez
 *
 */
public class TA extends Entity {
	
	private Course[] prefers = new Course[3];
	private Vector<Lecture> taking = new Vector<Lecture>();
	private Vector<Course> knows = new Vector<Course>();
	private Vector<Timeslot> schedule = new Vector<Timeslot>();
	
	/**
	 * Constructs a TA with name <code>name</code>.
	 * @param name name of the new TA.
	 */
	public TA(String name) {
		super(name);
	}
	
	/**
	 * Constructs a new TA with the same name as the given TA.
	 * @param ta another TA.
	 */
	public TA(TA ta) {
		super(ta);
	}
	
	/**
	 * Sets one of the preferred courses for this TA.
	 * @param c the course to be preferred.
	 * @param preference if this course is the first, second, or third preference.
	 */
	public void setPrefer(Course c, int preference) {
		prefers[preference - 1] = c;
		setKnows(c);
	}
	
	/**
	 * Gets one of the preferred courses for this TA.
	 * @param preference the number of the preferred course.
	 * @return the preferred course of number <code>preference</code>.
	 */
	public Course getPrefer(int preference) {
		return prefers[preference - 1];
	}
	
	/**
	 * Sets one of the Lectures this TA is taking.
	 * @param lec the Lecture to be added to this TAs schedule.
	 */
	public void setTaking(Lecture lec) {
		taking.add(lec);
		schedule.add(lec.getTime());
	}
	
	/**
	 * Gets the list of Lectures this TA is taking.
	 * @return the list of Lectures <code>taking</code>.
	 */
	public Vector<Lecture> getTaking() {
		return taking;
	}
	
	/**
	 * Sets the TA to know a course if he does not already know it.
	 * @param c the course to add to the list <code>knows</code>.
	 */
	public void setKnows(Course c){
		for (int i = 0; i < knows.size(); i++) {
			if (c.equals(knows.elementAt(i))) {
				return;
			}
		}
		knows.add(c);
	}
	
	/**
	 * Gets the list of Courses this TA knows.
	 * @return the list of Courses <code>knows</code>.
	 */
	// remember all TAs know junior courses! (list is in TAallocation)
	public Vector<Course> getKnows(){
		return knows;
	}
}
