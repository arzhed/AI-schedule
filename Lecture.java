package taAllocation;

public class Lecture extends Entity {
	public Lecture(String name) {
		super(name);
	}
	
	public Lecture(Lecture lecture) {
		super(lecture);
	}
}