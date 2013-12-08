package taAllocation;

import java.util.Vector;

/**
 * Creates a Course object, extends Entity so it is named.
 * @author Kristopher Maxwell, Charles Cote, Arthur Simon, Florent Duffez
 *
 */
public class Course extends Entity {
	private Vector<Lecture> lectureList = new Vector<Lecture>();
	private boolean senior;
	private boolean grad;
	
	/**
	 * Constructs a Course with name <code>name</code>.
	 * @param name name of the new course.
	 */
	public Course(String name) {
		super(name);
	}
	
	/**
	 * Constructs a new Course with the same name as the given Course.
	 * @param course another Course.
	 */
	public Course(Course course) {
		super(course);
	}
	
	/**
	 * Gets the list of all lectures in this course.
	 * @return lectureList.
	 */
	public Vector<Lecture> getLectures() {
		return lectureList;
	}
	
	/**
	 * Adds a lecture to lectureList.
	 * @param lec the lecture to add.
	 */
	public void addLecture(Lecture lec) {
		lectureList.add(lec);
	}
	
	/**
	 * Checks if a course is a senior course.
	 * @return true if this is a senior course.
	 */
	public boolean isSenior() {
		return senior;
	}
	
	/**
	 * Sets if this course should be senior or not.
	 * @param sen a boolean value, true if setting to senior, false if not.
	 */
	public void setSenior(boolean sen) {
		senior = sen;
	}
	
	/**
	 * Checks if a course is a grad course.
	 * @return true if this is a grad course.
	 */
	public boolean isGrad() {
		return grad;
	}
	
	/**
	 * Sets if this course should be grad or not.
	 * @param gr a boolean value, true if setting to grad, false if not.
	 */
	public void setGrad(boolean gr) {
		grad = gr;
	}
		
}
