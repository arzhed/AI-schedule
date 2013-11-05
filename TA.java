package taAllocation;

import java.util.Vector;

public class TA extends Entity {
	
	private Course[] prefers = new Course[3];
	private Vector<Course> taking = new Vector<Course>();
	private Vector<Course> knows = new Vector<Course>();
	private Vector<Timeslot> schedule = new Vector<Timeslot>();
	
	public TA(String name) {
		super(name);
	}
	
	public TA(TA ta) {
		super(ta);
	}
	
	public void setPrefer(Course c, int preference) {
		prefers[preference] = c;
	}
	
	public Course getPrefer(int preference) {
		return prefers[preference];
	}
	
	public Vector<Course> getTaking() {
		return taking;
	}
	
}
