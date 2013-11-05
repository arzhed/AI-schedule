package taAllocation;

import java.util.Vector;

public class Instructor extends Entity {
	
	private Vector<TA> prefersTA = new Vector<TA>();
	private Vector<Course> prefersC = new Vector<Course>();
	
	public Instructor(String name) {
		super(name);
	}
	
	public Instructor(Instructor instructor) {
		super(instructor);
	}
	
	public void setPrefers(TA ta,Course c){
		prefersTA.add(ta);
		prefersC.add(c);
	}
	
	public Vector<TA> getPrefersTA() {
		return prefersTA;
	}
	
	public Vector<Course> getPrefersC() {
		return prefersC;
	}
}
