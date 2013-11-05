package taAllocation;

import java.util.Vector;

public class TA extends Entity {
	
	private Course[] prefers = new Course[3];
	private Vector<Course> taking;
	private Vector<Course> knows;
	private Vector<Timeslot> schedule;
	
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
