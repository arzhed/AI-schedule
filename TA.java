package taAllocation;

public class TA extends Entity {
	
	private Course[3] prefers;
	private Vector<Lecture> taking;
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
