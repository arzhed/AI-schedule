package taAllocation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TAallocation extends PredicateReader implements TAallocationPredicates
{	
	static PrintStream traceFile;
	
	/**
	 * The default for the max time command line parameter. 
	 */
	static final int	DEFAULT_MAX_TIME = 30000;
	
	private static Vector<TA> taList = new Vector<TA>();
	private static Vector<Instructor> instructorList = new Vector<Instructor>();
	private static Vector<Course> courseList = new Vector<Course>();
	private static Vector<Course> juniorCourses = new Vector<Course>();
	private static Vector<Timeslot> schedule = new Vector<Timeslot>();
	
	private static Long maxlabs;
	private static Long minlabs;
	
	
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
			juniorCourses.add(c);
		}
		else
			println("Warning: Course already created.");
	}
	
	public boolean e_course(String p) {
		if (courseList.size() == 0)
			return false;
		
		for (int i = 0; i < courseList.size(); i++) {
			if (p.equals(courseList.elementAt(i).getName()))
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
			if (juniorCourses.contains(c))
				juniorCourses.remove(c);
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
			if (juniorCourses.contains(c))
				juniorCourses.remove(c);
        }
		else
			println("Warning: Course already created.");
	}
	
	public boolean e_grad_course(String p) {
		Course c = new Course(p);
		
		if (!e_course(p))
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
		if (!e_course(c)) {
			println("Error: Course does not exist.");
			return;
		}
		
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
		if (!e_course(c)) {
			println("Error: Course does not exist.");
			return false;
		}
		
		for (int i = 0; i < courseList.size(); i++) {
			if (c.equals(courseList.elementAt(i).getName())) {
				Vector<Lecture> tmpList = courseList.elementAt(i).getLectures();
				
				if (tmpList.size() == 0)
					return false;
				else { 
					for (int j = 0; j < tmpList.size(); j++) {
						if (lec.equals(tmpList.elementAt(j).getName())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public void a_lab(String c, String lec, String lab) {
		if (!e_course(c)) {
            println("Error: Course does not exist.");
            return;
		}

		if(!e_lecture(c, lec)) {
            println("Error: Lecture does not exist.");
            return;
		}
		
        if(!e_lab(c,lec,lab)) {
            for (int i=0; i< courseList.size();i++) {
                if(c.equals(courseList.elementAt(i).getName())){
                    Vector<Lecture> lectureList = courseList.elementAt(i).getLectures();
                    for (int j=0; j<lectureList.size(); j++){
                        if (lec.equals(lectureList.elementAt(j).getName())) {
                            lectureList.elementAt(j).addLab(new Lab(lab));
                        }
                    }
                }
            }
        }
        else println("Warning : lab already created");
	}

	public boolean e_lab(String c, String lec, String lab) {
		if (!e_course(c)) {
            println("Error: Course does not exist.");
            return false;
		}

		if(!e_lecture(c, lec)) {
            println("Error: Lecture does not exist.");
            return false;
		}

        Course tmpCourse = new Course(c);
        Lecture tmpLecture = new Lecture(lec);
        Lab tmpLab = new Lab(lab);

        for(int i=0; i<courseList.size();i++){
            if (tmpCourse.equals(courseList.elementAt(i))) {
                Vector<Lecture> lectureList = courseList.elementAt(i).getLectures();
                for (int j=0;j<lectureList.size();j++) {
                    if (tmpLecture.equals(lectureList.elementAt(j))) {
                        Vector<Lab> labList = lectureList.elementAt(j).getLabList();
                        if (labList.size()==0)
                            return false;
                        for (int k=0;k<labList.size();k++){
                            if (tmpLab.equals(labList.elementAt(k)))
                                return true;
                        }
                    }
                }
            }
        }
		return false;
	}
	
	public void a_instructs(String p, String c, String l) {
		if (!e_instructor(p)) {
			println("Error: Instructor does not exist.");
			return;
		}
		
		if (!e_course(c)) {
			println("Error: Course does not exist.");
			return;
		}
			
        if(!e_lecture(c,l)) {
            println("Error: Lecture does not exist.");
            return;
        }

        if (!e_instructs(p,c,l)){
            for (int i=0;i<instructorList.size();i++) {
                if (p.equals(instructorList.elementAt(i).getName())){
                    instructorList.elementAt(i).getInstructList().add(new Pair<Course, Lecture>(new Course(c), new Lecture(l)));
                }
            }
        }
        else println("Warning : lecture already assigned to instructor" );
	}

	public boolean e_instructs(String p, String c, String l) {
		if (!e_instructor(p)) {
			println("Error: Instructor does not exist.");
			return false;
		}
		
		if (!e_course(c)) {
			println("Error: Course does not exist.");
			return false;
		}
			
        if(!e_lecture(c,l)) {
            println("Error: Lecture does not exist.");
            return false;
        }


        Pair<Course,Lecture> tmpPair = new Pair<Course, Lecture>(new Course(c),new Lecture(l));

        for(int j=0;j<instructorList.size();j++){
            if (instructorList.elementAt(j).getName().equals(p)){
                Vector<Pair<Course,Lecture>> instructList = instructorList.elementAt(j).getInstructList();
                for (int i=0; i<instructList.size();i++) {
                    if (instructList.elementAt(i).equals(tmpPair) && instructList.elementAt(i).equals(tmpPair))
                        return true;
                }
            }
        }
		return false;
	}
	
	public void a_at(String c, String l, String t) {
		OUT : if (!e_at(c, l, t)) {
			for (int i = 0; i < courseList.size(); i++){
				if (c.equals(courseList.elementAt(i).getName())) {
					if (courseList.elementAt(i).getLectures()!=null){
						for (int j = 0; j < courseList.elementAt(i).getLectures().size(); j++){
							if (l.equals(courseList.elementAt(i).getLectures().elementAt(j).getName())) {
								for (int K = 0; K < schedule.size(); K++){
									if (t.equals(schedule.elementAt(K).getName())) {
										if (courseList.elementAt(i).getLectures().elementAt(j).getTime() != null) {
											println("Error: Lecture already has a timeslot.");
											return;
										}
										courseList.elementAt(i).getLectures().elementAt(j).setTime(schedule.elementAt(K));
										break OUT;
									}
								}
							}
							if (courseList.elementAt(i).getLectures().elementAt(j).getLabList()!=null){
								for (int k = 0; k < courseList.elementAt(i).getLectures().elementAt(j).getLabList().size(); k++){
									if (l.equals(courseList.elementAt(i).getLectures().elementAt(j).getLabList().elementAt(k).getName())) {
										for (int K = 0; K < schedule.size(); K++){
											if (t.equals(schedule.elementAt(K).getName())) {
												if (courseList.elementAt(i).getLectures().elementAt(j).getLabList().elementAt(k).getTime() != null) {
													println("Error: Lab already has a timeslot.");
													return;
												}
												courseList.elementAt(i).getLectures().elementAt(j).getLabList().elementAt(k).setTime(schedule.elementAt(K));
												break OUT;
											}
										}
									}
								}
							}
						}
						println("Error : Lab/Lecture or timeslot not found.");
						break OUT;
					}
				}
			}
		}
		else {
			println("Warning: this course and this lab/lecture at this timeslot is already added.");
		}
	}
	public boolean e_at(String c, String l, String t) {
		for (int i = 0; i < courseList.size(); i++){
			if (c.equals(courseList.elementAt(i).getName())) {
				if (courseList.elementAt(i).getLectures()!=null){
					for (int j = 0; j < courseList.elementAt(i).getLectures().size(); j++){
						if (l.equals(courseList.elementAt(i).getLectures().elementAt(j).getName())) {
							if (courseList.elementAt(i).getLectures().elementAt(j).getTime()!=null) {
								if (t.equals(courseList.elementAt(i).getLectures().elementAt(j).getTime().getName())){
									return true;
								}
							}
						}
						if (courseList.elementAt(i).getLectures().elementAt(j).getLabList()!=null){
							for (int k = 0; k < courseList.elementAt(i).getLectures().elementAt(j).getLabList().size(); k++){
								if (l.equals(courseList.elementAt(i).getLectures().elementAt(j).getLabList().elementAt(k).getName())) {
									if (courseList.elementAt(i).getLectures().elementAt(j).getLabList().elementAt(k).getTime()!=null) {
										if (t.equals(courseList.elementAt(i).getLectures().elementAt(j).getLabList().elementAt(k).getTime().getName())){
											return true;
										}
									}
								}
							}
						}
					}
				}
				return false;
			}
		}
		println("Error: Course, lecture/lab, or timeslot not found.");
		return false;
	}
	
	public void a_knows(String ta, String c) {
		OUT : if (!e_knows(ta, c)) {
			for (int i = 0; i < taList.size(); i++){
				if (ta.equals(taList.elementAt(i).getName())) {
					for (int j = 0; j < courseList.size(); j++){
						if (c.equals(courseList.elementAt(j).getName())) {
							taList.elementAt(i).setKnows(courseList.elementAt(j));
							break OUT;
						}
					}
					println("Error : Course not found.");
				}
			}
		}
	}
	public boolean e_knows(String ta, String c) {
		for (int i = 0; i < taList.size(); i++){
			if (ta.equals(taList.elementAt(i).getName())) {
				if (taList.elementAt(i).getKnows()!=null){
					for (int j = 0; j < taList.elementAt(i).getKnows().size(); j++) {
						if (c.equals(taList.elementAt(i).getKnows().elementAt(j).getName())){
							return true;
						}
					}
				}
				return false;
			}
		}
		println("Error : TA not found.");
		return false;
	}
	
	public void a_prefers(String instructor, String ta, String c) {
		OUT : if (!e_prefers(instructor, ta, c)) {
			for (int i = 0; i < instructorList.size(); i++) {
				if (instructor.equals(instructorList.elementAt(i).getName())) {
					for (int j = 0; j < taList.size(); j++) {
						if (ta.equals(taList.elementAt(j).getName())) {
							for (int k = 0; k < courseList.size(); k++) {
								if (c.equals(courseList.elementAt(k).getName())) {
									instructorList.elementAt(i).setPrefers(taList.elementAt(j), courseList.elementAt(k));
									break OUT;
								}
							}
							println("Error : course not found.");
							break OUT;
						}
					}
					println("Error : TA not found.");
					break OUT;
				}
			}
		}
		else
			println("Warning: instructor preference  already created.");
	}
	public boolean e_prefers(String instructor, String ta, String c) {
		for (int i = 0; i < instructorList.size(); i++) {
			if (instructor.equals(instructorList.elementAt(i).getName())) {
				if (instructorList.elementAt(i).getPrefersC()!=null && instructorList.elementAt(i).getPrefersTA()!=null){
					for (int j = 0; j < instructorList.elementAt(i).getPrefersC().size();j++) {
						if (c.equals(instructorList.elementAt(i).getPrefersC().elementAt(j).getName())){
							if (ta.equals(instructorList.elementAt(i).getPrefersTA().elementAt(j).getName())){
								return true;
							}
						}
					}
				}
				return false;
			}
		}
		println("Error : instructor not found.");
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
				if (taList.elementAt(i).getPrefer(2)!=null && c.equals( taList.elementAt(i).getPrefer(2).getName()))
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
				if (taList.elementAt(i).getPrefer(3)!=null && c.equals( taList.elementAt(i).getPrefer(3).getName()))
					return true;
				else
					return false;
			}
		}
		println("Error : TA not found.");
		return false;
	}
	
	public void a_taking(String ta, String c, String l) {
		if (!e_TA(ta)) {
			println("Error: TA does not exist.");
			return;
		}
		
		if (!e_course(c)) {
			println("Error: Course does not exist.");
			return;
		}
			
        if(!e_lecture(c,l)) {
            println("Error: Lecture does not exist.");
            return;
        }
		
		if (!e_taking(ta, c, l)) {
			for (int i = 0; i < taList.size(); i++) {
				if (ta.equals(taList.elementAt(i).getName())) {
					for (int j = 0; j < courseList.size(); j++) {
						if (c.equals(courseList.elementAt(j).getName())) {
							for (int k = 0; k < courseList.elementAt(j).getLectures().size(); k++) {
								if (l.equals(courseList.elementAt(j).getLectures().elementAt(k).getName())) {
									taList.elementAt(i).setTaking(courseList.elementAt(j).getLectures().elementAt(k));
								}
							}
						}
					}
				}
			}
		}
	}
	public boolean e_taking(String ta, String c, String l) {
		if (!e_TA(ta)) {
			println("Error: TA does not exist.");
			return false;
		}
		
		if (!e_course(c)) {
			println("Error: Course does not exist.");
			return false;
		}
			
        if(!e_lecture(c,l)) {
            println("Error: Lecture does not exist.");
            return false;
        }
		
		for (int i = 0; i < taList.size(); i++) {
			if (ta.equals(taList.elementAt(i).getName())) {
				for (int j = 0; j < taList.elementAt(i).getTaking().size(); j++) {
					if (l.equals(taList.elementAt(i).getTaking().elementAt(j))) {
						if (c.equals(taList.elementAt(i).getTaking().elementAt(j).getCourse().getName())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public void a_conflicts(String t1, String t2) {
		if (!e_conflicts(t1, t2)) {
			int I=-1;
			int J=-1;
			for (int i = 0; i < schedule.size(); i++) {
				if (t1.equals(schedule.elementAt(i).getName())){
					I=i;
				} else if (t2.equals(schedule.elementAt(i).getName())){
					J=i;
				}
			}
			if (I==-1)
				a_timeslot(t1);
			if (J==-1)
				a_timeslot(t2);
			else {
				schedule.elementAt(I).addConflict(schedule.elementAt(J));
				schedule.elementAt(J).addConflict(schedule.elementAt(I));
			}
		}
	}
	public boolean e_conflicts(String t1, String t2) {
		if (t1 == t2)
			return true;
		
		for (int i = 0; i < schedule.size(); i++) {
			if (t1.equals(schedule.elementAt(i).getName())){
				if (schedule.elementAt(i).getConflict()!= null) {
					for (int j = 0; j < schedule.elementAt(i).getConflict().size(); j++) {
						if (t2.equals(schedule.elementAt(i).getConflict().elementAt(j).getName())) {
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
			traceFile.print("Trace taAllocation.TAallocation");
			for (String s: args) traceFile.print(" "+s);
			traceFile.println("\n"+new java.util.Date());
			}
		catch (Exception ex) {traceFile = null;}
		
		if (args.length>=1) {
			long runtime = DEFAULT_MAX_TIME;
		    if (args.length<2) {
		    	//printSynopsis();
		    	println("No run time given; assuming run time of "+runtime+" seconds");
		    }
		    else {
		    	runtime = new Long(args[1]).longValue();
		    }
		    
		    PredicateReader env = new TAallocation();
			getInput(args[0],runtime, env);
			String outfilename = makeOutfilename(args[0]);
			output(outfilename, env);
		}
	    else { // go into "command mode" if there's nothing on the command line
	    	PredicateReader env = new TAallocation();
	    	//printSynopsis();
	    	String outfilename = "saved.out";
			commandMode(env);
	    }
		
		if (traceFile!=null) {
			traceFile.println(new java.util.Date());
			traceFile.close();
		}
    }
	
	public TAallocation() {
		super("name");
	}
	
	public static void getInput(String filename, Long runtime, PredicateReader env) {
		try {
			FileInputStream filestream = new FileInputStream(filename);
			BufferedReader buffRead = new BufferedReader(new InputStreamReader(filestream));
			String line;
			
			while ((line = buffRead.readLine()) != null) {
				if (line.length() > 0) {
					// skip over line starting with //, otherwise assert that line
					if (line.startsWith("//"))
						continue;
					else
						env.assert_(line);
				}
			}
			
			buffRead.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Implement "command mode": repeatedly read lines of predictes
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
	
	public static void output(String fileName, PredicateReader env) {
		PrintWriter out;
		try {
			out = new PrintWriter(fileName);
			
			out.println("minlabs(" + minlabs + ")");
			out.println("maxlabs(" + maxlabs + ")");
			out.println();
			out.println("//TIMESLOTS");
			for (int i = 0; i < schedule.size(); i++)
				out.println("timeslot(" + schedule.elementAt(i).getName() + ")");
			for (int i = 0; i < schedule.size(); i++) {
				for (int j = 0; j < schedule.elementAt(i).getConflict().size(); j++)
					out.println("conflicts(" + schedule.elementAt(i).getName() + ", " + 
							schedule.elementAt(i).getConflict().elementAt(j).getName() + ")");
			}
			out.println();
			out.println("//INSTRUCTORS");
			for (int i = 0; i < instructorList.size(); i++)
				out.println("instructor(" + instructorList.elementAt(i).getName() + ")");
			out.println();
			out.println("//TAs");
			for (int i = 0; i < taList.size(); i++)
				out.println("TA(" + taList.elementAt(i).getName() + ")");
			out.println();
			out.println("//COURSES");
			for (int i = 0; i < courseList.size(); i++) {
				if (courseList.elementAt(i).isSenior())
					out.println("senior-course(" + courseList.elementAt(i).getName() + ")");
				else if (courseList.elementAt(i).isGrad())
					out.println("grad-course(" + courseList.elementAt(i).getName() + ")");
				else 
					out.println("course(" + courseList.elementAt(i).getName() + ")");
				for (int j = 0; j < courseList.elementAt(i).getLectures().size(); j++) {
					out.println("lecture(" + courseList.elementAt(i).getLectures().elementAt(j) + ")");
					out.println("at(" + courseList.elementAt(i).getLectures().elementAt(j).getTime().getName() + ")");
					for (int k = 0; k < instructorList.size(); k++) {
						for (int l = 0; l < instructorList.elementAt(k).getInstructList().size(); l++) {
							if (instructorList.elementAt(k).getInstructList().elementAt(l).equals(new Pair<Course,Lecture>(courseList.elementAt(i), courseList.elementAt(i).getLectures().elementAt(j)))) {
								out.println("instructs(" + instructorList.elementAt(k).getName() + ", " + courseList.elementAt(i).getName() + ", " + courseList.elementAt(i).getLectures().elementAt(j).getName() + ")");
							}
						}
					}
					for (int k = 0; k < courseList.elementAt(i).getLectures().elementAt(j).getLabList().size(); k++) {
						out.println("lab(" + courseList.elementAt(i).getLectures().elementAt(j).getLabList().elementAt(k).getName() + ")");
						out.println("at(" + courseList.elementAt(i).getLectures().elementAt(j).getLabList().elementAt(k).getTime().getName() + ")");
					}
				}
				out.println();
			}
			out.println();
			out.println("//Instructor details");
			for (int i = 0; i < instructorList.size(); i++) {
				for (int j = 0; j < instructorList.elementAt(i).getPrefersTA().size(); j++) {
					out.println("prefers(" + instructorList.elementAt(i).getName() + ", " + instructorList.elementAt(i).getPrefersTA().elementAt(j).getName() + ", " + instructorList.elementAt(i).getPrefersC().elementAt(j).getName() + ")");
				}
			}
			out.println();
			out.println("//TA details");
			for (int i = 0; i < taList.size(); i++) {
				for (int j = 0; j < taList.elementAt(i).getTaking().size(); j++) {
					out.println("taking(" + taList.elementAt(i).getName() + ", " + taList.elementAt(i).getTaking().elementAt(j).getCourse().getName() + ", " +taList.elementAt(i).getTaking().elementAt(j).getName() + ")");
				}
				for (int j = 0; j < taList.elementAt(i).getKnows().size(); j++) {
					out.println("knows(" + taList.elementAt(i).getName() + ", " + taList.elementAt(i).getKnows().elementAt(j).getName() + ")");
				}
				for (int j = 0; j < 3; j++) {
					if (taList.elementAt(i).getPrefer(j) != null) {
						out.println("prefers" + j + "(" + taList.elementAt(i).getName() + ", " + taList.elementAt(i).getPrefer(j).getName() + ")");
					}
				}
				out.println();
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		for (int i = 0; i < courseList.size(); i++) {
			if (courseList.elementAt(i).isSenior())
				out.println("senior-course(" + courseList.elementAt(i).getName() + ")");
			else if (courseList.elementAt(i).isGrad())
				out.println("grad-course(" + courseList.elementAt(i).getName() + ")");
			else 
				out.println("course(" + courseList.elementAt(i).getName() + ")");
			for (int j = 0; j < courseList.getLectureList().size(); j++) {
				out.println("lecture(" + courseList.elementAt(i).getLectureList().elementAt(j) + ")");
				out.println("at(" + courseList.elementAt(i).getLectureList().elementAt(j).getTime().getName() + ")");
				for (int k = 0; k < instructorList.size(); k++) {
					for (int l = 0; l < instructorList.getInstructList().size(); l++) {
						
		*/
	}
	
	static void println(String s) {
		System.out.println(s);
		traceFile.println(s);
	}

	static void print(String s) {
		System.out.print(s);
		traceFile.print(s);
	}
	
	static void write(byte[] s, int offset, int count) throws Exception {
		System.out.write(s, offset, count);
		traceFile.write(s, offset, count);
	}
	
	/**
	 * Utility method the create an output filename from an input filename.
	 * @param in A String representing the input filename
	 * @return an appropriate output filename (which is the input filename + ".out")
	 */
	static String makeOutfilename(String in) {
		return in+".out";
	}
	
	static long max(long a, long b) {return a>b?a:b;}
}