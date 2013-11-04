package taAllocation;

import java.util.Vector;

public class Course extends Entity {
	private Vector<Lecture> lectureList = new Vector<Lecture>();
	private boolean senior;
	private boolean grad;
	
	
	public Course(String name) {
		super(name);
	}
	
	public Course(Course course) {
		super(course);
	}
	
	// returns list of lectures
	public Vector<Lecture> getLectures() {
		return lectureList;
	}
	
	public void addLecture(Lecture lec) {
		lectureList.add(lec);
	}
	
	public boolean isSenior() {
		return senior;
	}
	
	public void setSenior(boolean sen) {
		senior = sen;
	}
	
	public boolean isGrad() {
		return grad;
	}
	
	public void setGrad(boolean gr) {
		grad = gr;
	}
		
}
