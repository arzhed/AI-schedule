package taAllocation;

import java.util.Vector;

public class Lecture extends Entity {
    private Vector<Lab> labList = new Vector<Lab> ();
    private Timeslot time;
    private Course course;

	public Lecture(String name) {
		super(name);
	}
	
	public Lecture(Lecture lecture) {
		super(lecture);
	}

    public Vector<Lab> getLabList() {
        return labList;
    }

    public Timeslot getTime () {
        return time;
    }

    public Course getCourse() {
        return course;
    }
}