package taAllocation;

import java.util.Vector;

/**
 * Creates a Instructor object, extends Entity so it is named.
 * @author Kristopher Maxwell, Charles Cote, Arthur Simon, Florent Duffez
 *
 */
public class Instructor extends Entity {
	
	private Vector<TA> prefersTA = new Vector<TA>();
	private Vector<Course> prefersC = new Vector<Course>();
    private Vector<Pair<Course,Lecture>> instructList = new Vector<Pair<Course, Lecture>>();
	
    /**
	 * Constructs a Instructor with name <code>name</code>.
	 * @param name name of the new Instructor.
	 */
	public Instructor(String name) {
		super(name);
	}
	
	/**
	 * Constructs a new Instructor with the same name as the given Instructor.
	 * @param course another Instructor.
	 */
	public Instructor(Instructor instructor) {
		super(instructor);
	}
	
	/**
	 * Sets the preferred TA for a Course the Instructor teaches.
	 * @param ta the preferred TA.
	 * @param c the Course the Instructor prefers the TA for.
	 */
	public void setPrefers(TA ta,Course c){
		prefersTA.add(ta);
		prefersC.add(c);
	}
	
	/**
	 * Gets the list of TAs this Instructor prefers.
	 * @return the list <code>prefersTA</code>.
	 */
	public Vector<TA> getPrefersTA() {
		return prefersTA;
	}
	
	/**
	 * Gets the list of Courses this Instructor teaches and prefers a TA for.
	 * @return the list <code>prefersC</code>.
	 */
	public Vector<Course> getPrefersC() {
		return prefersC;
	}

	/**
	 * Gets the list of Lectures and Courses this Instructor teaches.
	 * @return the list <code>instructList</code>.
	 */
    public Vector<Pair<Course,Lecture>> getInstructList(){
        return instructList;
    }

    /**
     * Adds a Pair<Course, Lecture> to the list this Instructor teaches.
     * @param course the Course this Instructor teaches.
     * @param lecture the Lecture this Instructor teaches.
     */
    public void addInstructList(Course course,Lecture lecture) {
        Pair<Course,Lecture> nPair = new Pair<Course, Lecture>(course,lecture);
        instructList.add(nPair);
    }

}
