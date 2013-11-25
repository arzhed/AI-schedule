package taAllocation;

import java.util.Vector;

public class Lab extends Entity {
	
	private TA ta;
    private Timeslot time;
    private Lecture lecture;
	
	public Lab(String name) {
		super(name);
	}

    public Lab(String name, Lecture lec) {
        super(name);
        lecture=lec;
    }
	
	public Lab(Lab lab) {
		super(lab);
	}

    public TA getTA() {
        return ta;
    }

    public void setTA(TA ta1) {
         ta=ta1;
    }

    public Timeslot getTime() {
        return time;
    }

    public void setTime(Timeslot t) {
        time=t;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lec) {
        lecture=lec;
    }

    public boolean simultaneousLabs(TA ta, Solution solution) {
        Vector<Lab> listAssignedLabToTA = TAallocation.labListPerTA(ta.getName(),solution);
        for (Lab lab : listAssignedLabToTA){
            if (lab.getTime().equals(this.getTime()))
                return true;
        }
        return false;
    }


}