package taAllocation;

public class Timeslot extends Entity {

	private Timeslot conflict;
	
	public Timeslot(String name) {
		super(name);
	}
	
	public Timeslot(Timeslot ts) {
		super(ts);
	}
	
	public void setConflict(Timeslot ts) {
		conflict = ts;
	}
	

}
	