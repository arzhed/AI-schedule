package taAllocation;

public class TA extends Entity {
	
	private Course[3] prefers;
	private Vector<Course> taking;
	
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
