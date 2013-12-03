package taAllocation;

import java.util.Vector;

public class TA extends Entity {
	
	private Course[] prefers = new Course[3];
	private Vector<Lecture> taking = new Vector<Lecture>();
	private Vector<Course> knows = new Vector<Course>();
	private Vector<Timeslot> schedule = new Vector<Timeslot>();
	
	public TA(String name) {
		super(name);
	}
	
	public TA(TA ta) {
		super(ta);
	}
	
	public void setPrefer(Course c, int preference) {
		prefers[preference - 1] = c;
		setKnows(c);
	}
	
	public Course getPrefer(int preference) {
		return prefers[preference - 1];
	}
	
	public void setTaking(Lecture lec) {
		taking.add(lec);
		schedule.add(lec.getTime());
	}
	
	public Vector<Lecture> getTaking() {
		return taking;
	}
	
	public void setKnows(Course c){
		for (int i = 0; i < knows.size(); i++) {
			if (c.equals(knows.elementAt(i))) {
				return;
			}
		}
		knows.add(c);
	}
	
	// remember all TAs know junior courses! (list is in TAallocation)
	public Vector<Course> getKnows(){
		return knows;
	}
}