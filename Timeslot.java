package taAllocation;

import java.util.Vector;

public class Timeslot extends Entity {

	private Vector<Timeslot> conflict = new Vector<Timeslot>();
	
	public Timeslot(String name) {
		super(name);
	}
	
	public Timeslot(Timeslot ts) {
		super(ts);
	}
	
	public void addConflict(Timeslot ts) {
		conflict.add(ts);
	}

    public Vector<Timeslot> getConflict() {
        return conflict;
    }
	
	
}