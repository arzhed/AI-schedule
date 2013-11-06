package taAllocation;

import java.util.Vector;

//TODO : review the prefers(TA,Course)

public class Instructor extends Entity {
	
	private Vector<TA> prefersTA = new Vector<TA>();
	private Vector<Course> prefersC = new Vector<Course>();
    private Vector<Pair<Course,Lecture>> instructList = new Vector<Pair<Course, Lecture>>();
	
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

    public Vector<Pair<Course,Lecture>> getInstructList(){
        return instructList;
    }

    public void addInstructList(Course course,Lecture lecture) {
        Pair<Course,Lecture> nPair = new Pair<Course, Lecture>(course,lecture);
        instructList.add(nPair);
    }

}
