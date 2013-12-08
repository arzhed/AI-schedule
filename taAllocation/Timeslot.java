package taAllocation;

import java.util.Vector;

/**
 * Creates a Timeslot object, extends Entity so it is named.
 * @author Kristopher Maxwell, Charles Cote, Arthur Simon, Florent Duffez
 *
 */
public class Timeslot extends Entity {

	private Vector<Timeslot> conflict = new Vector<Timeslot>();
	
	/**
	 * Constructs a Timeslot with name <code>name</code>.
	 * @param name name of the new Timeslot.
	 */
	public Timeslot(String name) {
		super(name);
	}
	
	/**
	 * Constructs a new Timeslot with the same name as the given Timeslot.
	 * @param ts another Timeslot.
	 */
	public Timeslot(Timeslot ts) {
		super(ts);
	}
	
	/**
	 * Adds a conflict to the list <code>conflict</code>.
	 * @param ts the Timeslot conflicting with this one.
	 */
	public void addConflict(Timeslot ts) {
		conflict.add(ts);
	}

	/**
	 * Gets the list of conflicts with this Timeslot.
	 * @return the list of conflicts.
	 */
    public Vector<Timeslot> getConflict() {
        return conflict;
    }
	
	
}