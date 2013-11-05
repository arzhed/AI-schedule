package taAllocation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TAallocation extends PredicateReader implements TAallocationPredicates
{
	static PrintStream traceFile;
	
	private Vector<TA> taList = new Vector<TA>();
	private Vector<Instructor> instructorList = new Vector<Instructor>();
	private Vector<Course> courseList = new Vector<Course>();
	private Vector<Lecture> lectureList = new Vector<Lecture>();
	private Vector<Lab> labList = new Vector<Lab>();
	private Vector<Timeslot> schedule = new Vector<Timeslot>();
	
	private Long maxlabs;
	private Long minlabs;
	
	
	public void a_maxlabs(Long p) {
		maxlabs = p;
	}
	
	public void a_minlabs(Long p) {
		minlabs = p;
	}
	
	public void a_TA(String p) {
		// if ta does not exist, add ta to the list
		if (!e_TA(p))
			taList.add(new TA(p));
		else
			println("Warning: TA already created.");
	}
	
	public boolean e_TA(String p) {
		TA temp = new TA(p);
		
		if (taList.size() == 0)
			return false;
		
		for (int i = 0; i < taList.size(); i++) {
			if (temp.equals(taList.elementAt(i)))
				return true;
		}
		return false;
	}
	
	public void a_instructor(String p) {
		if (!e_instructor(p))
			instructorList.add(new Instructor(p));
		else
			println("Warning: Instructor already created.");
	}
	public boolean e_instructor(String p) {
		Instructor temp = new Instructor(p);
		
		if (instructorList.size() == 0)
			return false;
		
		for (int i = 0; i < instructorList.size(); i++) {
			if (temp.equals(instructorList.elementAt(i)))
				return true;
		}
		return false;
	}
	
	public void a_course(String p) {
		if (!e_course(p)) {
			Course c = new Course(p);
			c.setSenior(false);
			c.setGrad(false);
			courseList.add(c);
		}
		else
			println("Warning: Course already created.");
	}
	
	public boolean e_course(String p) {
		Course temp = new Course(p);
		
		if (courseList.size() == 0)
			return false;
		
		for (int i = 0; i < courseList.size(); i++) {
			if (temp.equals(courseList.elementAt(i)))
				return true;
		}
		return false;
	}
	
	public void a_senior_course(String p) {
		if (!e_senior_course(p)) {
			Course c = new Course(p);
			c.setSenior(true);
			c.setGrad(false);
			courseList.add(c);
        }
		else
			println("Warning: Course already created.");
	}
	
	public boolean e_senior_course(String p) {
		Course c = new Course(p);
		
		if (courseList.size() == 0)
			return false;
		
		for (int i = 0; i < courseList.size(); i++) {
			Course temp = courseList.elementAt(i);
			if (c.equals(temp) && temp.isSenior())
				return true;
		}
		return false;
	}
	
	public void a_grad_course(String p) {
		if (!e_grad_course(p)) {
			Course c = new Course(p);
			c.setSenior(false);
			c.setGrad(true);
			courseList.add(c);
        }
		else
			println("Warning: Course already created.");
	}
	
	public boolean e_grad_course(String p) {
		Course c = new Course(p);
		
		if (courseList.size() == 0)
			return false;
		
		for (int i = 0; i < courseList.size(); i++) {
			Course temp = courseList.elementAt(i);
			if (c.equals(temp) && temp.isGrad())
				return true;
		}
		return false;
	}
	
	public void a_timeslot(String p) {
		if(!e_timeslot(p)) {
			Timeslot t = new Timeslot(p);
			schedule.add(t);
		}else
			println("Warning: Timeslot already created.");

	}
	public boolean e_timeslot(String p) {
		Timeslot t = new Timeslot(p);
		
		if(schedule.size() ==0) {
			return false;
		}else {
		    for (int i=0; i<schedule.size(); i++) {
		    	Timeslot temp = schedule.elementAt(i);
		        if (t.equals(temp))
		        	return true;
		    }
		}
		         return false;

	}
	
	public void a_lecture(String c, String lec) {
		if (!e_lecture(c, lec)) {
			Course tmpCourse = new Course(c);
			for (int i = 0; i < courseList.size(); i++) {
				if (tmpCourse.equals(courseList.elementAt(i))) {
					courseList.elementAt(i).addLecture(new Lecture(lec));
				}
			}
		}
		else
			println("Warning: Lecture already created.");
	}
	// looks through course list, if course exists, check to see if it has the lecture
	// if lecture exists, return true, else continue looping, return false if not found after loop
	public boolean e_lecture(String c, String lec) {
		Course tmpCourse = new Course(c);
		Lecture tmpLec = new Lecture(lec);
		
		if (courseList.size() == 0)
			return false;
		
		for (int i = 0; i < courseList.size(); i++) {
			if (tmpCourse.equals(courseList.elementAt(i))) {
				Vector<Lecture> tmpList = courseList.elementAt(i).getLectures();
				
				if (tmpList.size() == 0)
					continue;
				else { 
					for (int j = 0; j < tmpList.size(); j++) {
						if (tmpLec.equals(tmpList.elementAt(j))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public void a_lab(String c, String lec, String lab) {
		println("Unimplemented");
	}
	public boolean e_lab(String c, String lec, String lab) {
		println("Unimplemented");
		return false;
	}
	
	public void a_instructs(String p, String c, String l) {
		println("Unimplemented");
	}
	public boolean e_instructs(String p, String c, String l) {
		println("Unimplemented");
		return false;
	}
	
	public void a_at(String c, String l, String t) {
		println("Unimplemented");
	}
	public boolean e_at(String c, String l, String t) {
		println("Unimplemented");
		return false;
	}
	
	public void a_knows(String ta, String c) {
		println("Unimplemented");
	}
	public boolean e_knows(String ta, String c) {
		println("Unimplemented");
		return false;
	}
	
	public void a_prefers(String instructor, String ta, String c) {
		
	}
	public boolean e_prefers(String instructor, String ta, String c) {
		println("Unimplemented");
		return false;
	}
	
	public void a_prefers1(String ta, String c) {
		if (!e_prefers1(ta, c)) {
			int I=-1;
			int J=-1;
			for (int i = 0; i < taList.size(); i++) {
				if (ta.equals(taList.elementAt(i).getName())) {
					I=i;
				}
			}
			for (int i = 0; i < courseList.size(); i++) {
				if (c.equals(courseList.elementAt(i).getName())){
					J=i;
				}
			}
			if (I==-1 )
				println("Error : TA not found.");
			else if (J==-1)
				println("Error : Course not found.");
			else
				taList.elementAt(I).setPrefer(courseList.elementAt(J), 1);
		}
		else
			println("Warning: Preference 1 already created.");
	}
	public boolean e_prefers1(String ta, String c) {
		for (int i = 0; i < taList.size(); i++) {
			if (ta.equals(taList.elementAt(i).getName())) {
				if (taList.elementAt(i).getPrefer(1)!=null && c.equals( taList.elementAt(i).getPrefer(1).getName()))
					return true;
				else
					return false;
			}
		}
		println("Error : TA not found.");
		return false;
	}
	
	public void a_prefers2(String ta, String c) {
		if (!e_prefers2(ta, c)) {
			int I=-1;
			int J=-1;
			for (int i = 0; i < taList.size(); i++) {
				if (ta.equals(taList.elementAt(i).getName())) {
					I=i;
				}
			}
			for (int i = 0; i < courseList.size(); i++) {
				if (c.equals(courseList.elementAt(i).getName())){
					J=i;
				}
			}
			if (I==-1 )
				println("Error : TA not found.");
			else if (J==-1)
				println("Error : Course not found.");
			else
				taList.elementAt(I).setPrefer(courseList.elementAt(J), 2);
		}
		else
			println("Warning: Preference 2 already created.");
	}
	public boolean e_prefers2(String ta, String c) {
		for (int i = 0; i < taList.size(); i++) {
			if (ta.equals(taList.elementAt(i).getName())) {
				if (taList.elementAt(i).getPrefer(1)!=null && c.equals( taList.elementAt(i).getPrefer(2).getName()))
					return true;
				else
					return false;
			}
		}
		println("Error : TA not found.");
		return false;
	}
	
	public void a_prefers3(String ta, String c) {
		if (!e_prefers3(ta, c)) {
			int I=-1;
			int J=-1;
			for (int i = 0; i < taList.size(); i++) {
				if (ta.equals(taList.elementAt(i).getName())) {
					I=i;
				}
			}
			for (int i = 0; i < courseList.size(); i++) {
				if (c.equals(courseList.elementAt(i).getName())){
					J=i;
				}
			}
			if (I==-1 )
				println("Error : TA not found.");
			else if (J==-1)
				println("Error : Course not found.");
			else
				taList.elementAt(I).setPrefer(courseList.elementAt(J), 3);
		}
		else
			println("Warning: Preference 3 already created.");
	}
	public boolean e_prefers3(String ta, String c) {
		for (int i = 0; i < taList.size(); i++) {
			if (ta.equals(taList.elementAt(i).getName())) {
				if (taList.elementAt(i).getPrefer(1)!=null && c.equals( taList.elementAt(i).getPrefer(3).getName()))
					return true;
				else
					return false;
			}
		}
		println("Error : TA not found.");
		return false;
	}
	
	public void a_taking(String ta, String c, String l) {
		if (!e_taking( ta, c, l)) {
			println("Unimplemented");
		}else
			println("Warning: this TA is already taking this course and this lab/lecture.");
	}
	public boolean e_taking(String ta, String c, String l) {
		println("Unimplemented");
		return false;
	}
	
	public void a_conflicts(String t1, String t2) {
		if (!e_conflicts(t1, t2)) {
			int I=-1;
			int J=-1;
			for (int i = 0; i < schedule.size(); i++) {
				if (t1.equals(schedule.elementAt(i).getName())){
					I=i;
				}else if (t2.equals(schedule.elementAt(i).getName())){
					J=i;
				}
			}
			if (I==-1 || J==-1)
				println("Error: Timeslot not found.");
			else {
				schedule.elementAt(I).addConflict(schedule.elementAt(J));
				schedule.elementAt(J).addConflict(schedule.elementAt(I));
			}
		}
		else
			println("Warning: timeslot conflict already created.");
	}
	public boolean e_conflicts(String t1, String t2) {
		for (int i = 0; i < schedule.size(); i++) {
			if (t1.equals(schedule.elementAt(i).getName())){
				if (schedule.elementAt(i).getConflict()!= null) {
					for (int j = 0; j < schedule.elementAt(i).getConflict().size(); j++) {
						if (schedule.elementAt(i).getConflict().elementAt(j)!= null && t2.equals(schedule.elementAt(i).getConflict().elementAt(j).getName())) {
							return true;
						}
					}
				}
				return false;
			}
		}
		println("Error: Timeslot not found.");
		return false;
	}
	
	public static void main(String[] args) {
		try {
			traceFile = new PrintStream(new FileOutputStream("trace.out"));
			traceFile.print("Trace taAllocation.Test");
			for (String s: args) traceFile.print(" "+s);
			traceFile.println("\n"+new java.util.Date());
			}
		catch (Exception ex) {traceFile = null;}
		
		PredicateReader env = new TAallocation();
    	String outfilename = "saved.out";
		commandMode(env);
    }
	
	public TAallocation() {
		super("name");
	}
	
	/**
	 * Implment "command mode": repeatedly read lines of predictes
	 * from {@link System#in} and either assert them (if the line starts
	 * with a "!") or evaluate them (and return "true" or "false" to
	 * {@link System#out}. 
	 * @param env the environment that can interpret the predicates.
	 */
	public static void commandMode(PredicateReader env) {
		final int maxBuf = 200;
		byte[] buf = new byte[maxBuf];
		int length;
		try {
			print("\nSisyphus I: query using predicates, assert using \"!\" prefixing predicates;\n !exit() to quit; !help() for help.\n\n> ");
			while ((length=System.in.read(buf))!=-1) {
				String s = new String(buf,0,length);
				s = s.trim();
				if (s.equals("exit")) break;
				if (s.equals("?")||s.equals("help")) {
					s = "!help()";
					println("> !help()");
				}
				if (s.length()>0) {
					if (s.charAt(0)=='!') {
						env.assert_(s.substring(1));
					}
					else { 
						print(" --> "+env.eval(s));
					}
				}
				print("\n> ");
			}
		} catch (Exception e) {
			println("exiting: "+e.toString());
		}
	}
	
	static void println(String s) {
		System.out.println(s);
		traceFile.println(s);
	}

	static void print(String s) {
		System.out.print(s);
		traceFile.print(s);
	}
}