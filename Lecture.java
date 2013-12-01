package taAllocation;

import java.util.Vector;

public class Lecture extends Entity {
    private Vector<Lab> labList = new Vector<Lab> ();
    private Timeslot time = null;
    private Course course;

	public Lecture(String name) {
		super(name);
	}
	
	public Lecture(Lecture lecture) {
		super(lecture);
	}
	
	public Lecture(Lecture lecture, Course c) {
		super(lecture);
		course = c;
	}
	
	public Lecture(String name, Course c) {
		super(name);
		course = c;
	}

    public Vector<Lab> getLabList() {
        return labList;
    }

    public void addLab(Lab lab) {
        labList.add(lab);
    }

    public Timeslot getTime () {
        return time;
    }

    public void setTime(Timeslot t) {
        time=t;
    }
    
    public boolean hasTime() {
    	if (time == null)
    		return false;
    	return true;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse (Course c) {
        course=c;
    }
}