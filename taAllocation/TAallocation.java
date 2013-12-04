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
import java.util.Collections;
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
	private static final int	NUM_SOLUTIONS = 20;
	private static final int STOP_TIME = 500;
	
	private static Vector<TA> taList = new Vector<TA>();
	private static Vector<Instructor> instructorList = new Vector<Instructor>();
	private static Vector<Course> courseList = new Vector<Course>();
	private static Vector<Course> juniorCourses = new Vector<Course>();
	private static Vector<Timeslot> schedule = new Vector<Timeslot>();
    private static Vector<Lab> labList = new Vector<Lab>();
	
	private static Long maxlabs = (long)3;
	private static Long minlabs = (long)1;
	
	private static Long startTime = (long)0;
	
	private static Solution bestSolution;
	

	public void a_maxlabs(Long p) {
		maxlabs = p;
	}
	
	public void a_minlabs(Long p) {
		minlabs = p;
	}
	
	public void a_TA(String p) {
		// if ta does not exist, add ta to the list
		if (!e_TA(p)) {
			TA newTA = new TA(p);
			for (int i = 0; i < juniorCourses.size(); i++) {
				newTA.setKnows(juniorCourses.get(i));
			}
			taList.add(new TA(p));
		}		
		else
			println("Warning: TA already created.");
	}
	
	public boolean e_TA(String p) {
		TA temp = new TA(p);
		
		if (taList.size() == 0)
			return false;
		
		for (int i = 0; i < taList.size(); i++) {
			if (temp.equals(taList.get(i)))
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
			if (temp.equals(instructorList.get(i)))
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
			if (p.equals(courseList.get(i).getName()))
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
			Course temp = courseList.get(i);
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
			Course temp = courseList.get(i);
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
		    	Timeslot temp = schedule.get(i);
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
				if (tmpCourse.equals(courseList.get(i))) {
					courseList.get(i).addLecture(new Lecture(lec, courseList.get(i)));
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
			if (c.equals(courseList.get(i).getName())) {
				Vector<Lecture> tmpList = courseList.get(i).getLectures();
				
				if (tmpList.size() == 0)
					return false;
				else { 
					for (int j = 0; j < tmpList.size(); j++) {
						if (lec.equals(tmpList.get(j).getName())) {
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
                if(c.equals(courseList.get(i).getName())){
                    Vector<Lecture> lectureList = courseList.get(i).getLectures();
                    for (int j=0; j<lectureList.size(); j++){
                        if (lec.equals(lectureList.get(j).getName())) {
                            Lab tmpLab = new Lab(lab,lectureList.get(j));
                            lectureList.get(j).addLab(tmpLab);
                            labList.add(tmpLab);
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
            if (tmpCourse.equals(courseList.get(i))) {
                Vector<Lecture> lectureList = courseList.get(i).getLectures();
                for (int j=0;j<lectureList.size();j++) {
                    if (tmpLecture.equals(lectureList.get(j))) {
                        Vector<Lab> labList = lectureList.get(j).getLabList();
                        if (labList.size()==0)
                            return false;
                        for (int k=0;k<labList.size();k++){
                            if (tmpLab.equals(labList.get(k)))
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
                if (p.equals(instructorList.get(i).getName())){
                   for (Course C : courseList)
                		if (C.getName().equals(c))
                			for (Lecture L : C.getLectures())
                				if (L.getName().equals(l))
                					instructorList.get(i).getInstructList().add(new Pair<Course,Lecture>(C,L));
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
            if (instructorList.get(j).getName().equals(p)){
                Vector<Pair<Course,Lecture>> instructList = instructorList.get(j).getInstructList();
                for (int i=0; i<instructList.size();i++) {
                    if (instructList.get(i).equals(tmpPair) && instructList.get(i).equals(tmpPair))
                        return true;
                }
            }
        }
		return false;
	}
	
	public void a_at(String c, String l, String t) {
		OUT : if (!e_at(c, l, t)) {
			for (int i = 0; i < courseList.size(); i++){
				if (c.equals(courseList.get(i).getName())) {
					if (courseList.get(i).getLectures()!=null){
						for (int j = 0; j < courseList.get(i).getLectures().size(); j++){
							if (l.equals(courseList.get(i).getLectures().get(j).getName())) {
								for (int K = 0; K < schedule.size(); K++){
									if (t.equals(schedule.get(K).getName())) {
										if (courseList.get(i).getLectures().get(j).getTime() != null) {
											println("Error: Lecture already has a timeslot.");
											return;
										}
										courseList.get(i).getLectures().get(j).setTime(schedule.get(K));
										break OUT;
									}
								}
							}
							if (courseList.get(i).getLectures().get(j).getLabList()!=null){
								for (int k = 0; k < courseList.get(i).getLectures().get(j).getLabList().size(); k++){
									if (l.equals(courseList.get(i).getLectures().get(j).getLabList().get(k).getName())) {
										for (int K = 0; K < schedule.size(); K++){
											if (t.equals(schedule.get(K).getName())) {
												if (courseList.get(i).getLectures().get(j).getLabList().get(k).getTime() != null) {
													println("Error: Lab already has a timeslot.");
													return;
												}
												courseList.get(i).getLectures().get(j).getLabList().get(k).setTime(schedule.get(K));
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
			if (c.equals(courseList.get(i).getName())) {
				if (courseList.get(i).getLectures()!=null){
					for (int j = 0; j < courseList.get(i).getLectures().size(); j++){
						if (l.equals(courseList.get(i).getLectures().get(j).getName())) {
							if (courseList.get(i).getLectures().get(j).getTime()!=null) {
								if (t.equals(courseList.get(i).getLectures().get(j).getTime().getName())){
									return true;
								}
							}
						}
						if (courseList.get(i).getLectures().get(j).getLabList()!=null){
							for (int k = 0; k < courseList.get(i).getLectures().get(j).getLabList().size(); k++){
								if (l.equals(courseList.get(i).getLectures().get(j).getLabList().get(k).getName())) {
									if (courseList.get(i).getLectures().get(j).getLabList().get(k).getTime()!=null) {
										if (t.equals(courseList.get(i).getLectures().get(j).getLabList().get(k).getTime().getName())){
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
				if (ta.equals(taList.get(i).getName())) {
					for (int j = 0; j < courseList.size(); j++){
						if (c.equals(courseList.get(j).getName())) {
							taList.get(i).setKnows(courseList.get(j));
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
			if (ta.equals(taList.get(i).getName())) {
				if (taList.get(i).getKnows()!=null){
					for (int j = 0; j < taList.get(i).getKnows().size(); j++) {
						if (c.equals(taList.get(i).getKnows().get(j).getName())){
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
				if (instructor.equals(instructorList.get(i).getName())) {
					for (int j = 0; j < taList.size(); j++) {
						if (ta.equals(taList.get(j).getName())) {
							for (int k = 0; k < courseList.size(); k++) {
								if (c.equals(courseList.get(k).getName())) {
									instructorList.get(i).setPrefers(taList.get(j), courseList.get(k));
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
			if (instructor.equals(instructorList.get(i).getName())) {
				if (instructorList.get(i).getPrefersC()!=null && instructorList.get(i).getPrefersTA()!=null){
					for (int j = 0; j < instructorList.get(i).getPrefersC().size();j++) {
						if (c.equals(instructorList.get(i).getPrefersC().get(j).getName())){
							if (ta.equals(instructorList.get(i).getPrefersTA().get(j).getName())){
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
				if (ta.equals(taList.get(i).getName())) {
					I=i;
				}
			}
			for (int i = 0; i < courseList.size(); i++) {
				if (c.equals(courseList.get(i).getName())){
					J=i;
				}
			}
			if (I==-1 )
				println("Error : TA not found.");
			else if (J==-1)
				println("Error : Course not found.");
			else
				taList.get(I).setPrefer(courseList.get(J), 1);
		}
		else
			println("Warning: Preference 1 already created.");
	}
	public boolean e_prefers1(String ta, String c) {
		for (int i = 0; i < taList.size(); i++) {
			if (ta.equals(taList.get(i).getName())) {
				if (taList.get(i).getPrefer(1)!=null && c.equals( taList.get(i).getPrefer(1).getName()))
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
				if (ta.equals(taList.get(i).getName())) {
					I=i;
				}
			}
			for (int i = 0; i < courseList.size(); i++) {
				if (c.equals(courseList.get(i).getName())){
					J=i;
				}
			}
			if (I==-1 )
				println("Error : TA not found.");
			else if (J==-1)
				println("Error : Course not found.");
			else
				taList.get(I).setPrefer(courseList.get(J), 2);
		}
		else
			println("Warning: Preference 2 already created.");
	}
	public boolean e_prefers2(String ta, String c) {
		for (int i = 0; i < taList.size(); i++) {
			if (ta.equals(taList.get(i).getName())) {
				if (taList.get(i).getPrefer(2)!=null && c.equals( taList.get(i).getPrefer(2).getName()))
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
				if (ta.equals(taList.get(i).getName())) {
					I=i;
				}
			}
			for (int i = 0; i < courseList.size(); i++) {
				if (c.equals(courseList.get(i).getName())){
					J=i;
				}
			}
			if (I==-1 )
				println("Error : TA not found.");
			else if (J==-1)
				println("Error : Course not found.");
			else
				taList.get(I).setPrefer(courseList.get(J), 3);
		}
		else
			println("Warning: Preference 3 already created.");
	}
	public boolean e_prefers3(String ta, String c) {
		for (int i = 0; i < taList.size(); i++) {
			if (ta.equals(taList.get(i).getName())) {
				if (taList.get(i).getPrefer(3)!=null && c.equals( taList.get(i).getPrefer(3).getName()))
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
				if (ta.equals(taList.get(i).getName())) {
					for (int j = 0; j < courseList.size(); j++) {
						if (c.equals(courseList.get(j).getName())) {
							for (int k = 0; k < courseList.get(j).getLectures().size(); k++) {
								if (l.equals(courseList.get(j).getLectures().get(k).getName())) {
									taList.get(i).setTaking(courseList.get(j).getLectures().get(k));
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
			if (ta.equals(taList.get(i).getName())) {
				for (int j = 0; j < taList.get(i).getTaking().size(); j++) {
					if (l.equals(taList.get(i).getTaking().get(j))) {
						if (c.equals(taList.get(i).getTaking().get(j).getCourse().getName())) {
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
				if (t1.equals(schedule.get(i).getName())){
					I=i;
				} else if (t2.equals(schedule.get(i).getName())){
					J=i;
				}
			}
			if (I==-1)
				a_timeslot(t1);
			if (J==-1)
				a_timeslot(t2);
			if (I != -1 && J != -1) {
				schedule.get(I).addConflict(schedule.get(J));
				schedule.get(J).addConflict(schedule.get(I));
			} else
				a_conflicts(t1, t2);
			
		}
	}
	public boolean e_conflicts(String t1, String t2) {
		if (t1 == t2)
			return true;
		
		for (int i = 0; i < schedule.size(); i++) {
			if (t1.equals(schedule.get(i).getName())){
				if (schedule.get(i).getConflict()!= null) {
					for (int j = 0; j < schedule.get(i).getConflict().size(); j++) {
						if (t2.equals(schedule.get(i).getConflict().get(j).getName())) {
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

    public static Solution randomGeneration() {
        Solution sol = new Solution();
        TAallocation taa = new TAallocation();
        int random;
        int taSize=taList.size();
        int i=0;
        OUT : for (Lab lab : labList) {
            TA ta;
            i=0;
            do {
                random = (int) (Math.random() * taSize);
                ta =  taList.get(random);
                i++;
                if(i== 5*taSize)
                    break OUT;
            }   while ( labCount(ta.getName(),sol) >= maxlabs || taa.simultaneousLabs(ta,lab,sol) || taa.conflictsCourses(ta,lab));

            sol.addElement(new Pair<Lab, TA>(lab, ta));
        }
        
        if(i== 5*taSize)
            return new Solution();

        return sol;
    }

    public boolean simultaneousLabs(TA ta, Lab lab, Solution solution) {
        Vector<Lab> listAssignedLabToTA = TAallocation.labListPerTA(ta.getName(),solution);
        for (Lab labAs : listAssignedLabToTA){
            if(e_conflicts(labAs.getTime().getName(), lab.getTime().getName()))
                return true;
        }
        return false;
    }

    private boolean conflictsCourses(TA ta, Lab lab) {
        for(Lecture lec : ta.getTaking()) {
            if(e_conflicts(lec.getTime().getName(), lab.getTime().getName()))
                return true;
            }
        return false;
    }
	
	public static void main(String[] args) {
		startTime = System.currentTimeMillis();
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
		    	println("No run time given; assuming run time of "+runtime+" milliseconds");
		    }
		    else {
		    	runtime = new Long(args[1]).longValue();
		    }
		    
		    PredicateReader env = new TAallocation();
			getInput(args[0],runtime, env);
			
			// create the first generation set
			TAallocation TAa = new TAallocation();
			
			Solution[] S = new Solution[NUM_SOLUTIONS];
			
			for (int i = 0; i < NUM_SOLUTIONS; i++) {
				S[i] = new Solution();
				do {
					while (S[i].getSolution().isEmpty())
						S[i] = randomGeneration();
				} while (!TAa.checkHardConstraints(S[i]));
				System.out.println("HC OK for " + i);
				System.out.println("SC Score for " + i + ": " + TAa.checkSoftConstraints(S[i]));
			}
			bestSolution = S[0];
			
			String outfilename = makeOutfilename(args[0]);
			
			Vector<Solution> tempSet;
	        // loop and mutate set until time runs out
	        while ((System.currentTimeMillis() - startTime) < runtime) {
	        	// check if time to stop and output final solution
	        	if ((System.currentTimeMillis() - startTime) > runtime - STOP_TIME)
	        		break;
	        	
	        	// mutate all solutions
	        	tempSet = new Vector<Solution>();
	        	long startMutate = System.currentTimeMillis();
	        	Solution temp;
	        	for (Solution s : S) {
	        		tempSet.add(s);
	        		temp = TAa.mutate(s);
	        		tempSet.add(temp);
	        		TAa.checkSoftConstraints(temp);
	        	}
	        	System.out.println("Mutation took " + (System.currentTimeMillis() - startMutate) + " milliseconds.");
	        	
	        	// sort the solutions, with lowest penalty at index 0
	        	Collections.sort(tempSet, Collections.reverseOrder());
	        	
	        	// check if best solution is significantly better, output it if so
	        	if (tempSet.get(0).total - 10 > bestSolution.total) {
	        		outputSolution(outfilename);
	        	}
	        	
	        	// set best solution
	        	bestSolution = tempSet.get(0);
	        	System.out.println("Best solution penalty = " + bestSolution.total);
	        	System.out.println("Worst solution penalty = " + tempSet.get(tempSet.size() - 1).total);
	        	
	        	// select 75% of set from best solutions, and 25% randomly from the remainder
	        	int numBest = (int)Math.ceil(NUM_SOLUTIONS * 0.75);
	        	int numRandom = (int)Math.floor(NUM_SOLUTIONS * 0.25);
	        	for (int i = 0; i < numBest; i++) {
	        		S[i] = tempSet.get(0);
	        		tempSet.remove(S[i]);
	        	}
	        	for (int j = numBest; j < numRandom; j++) {
	        		int random = (int) (Math.random() * tempSet.size());
	        		S[j] = tempSet.get(random);
	        		tempSet.remove(S[j]);
	        	}
	        }
			outputSolution(outfilename);
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
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Main took " + (endTime - startTime) + "ms to run.");
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
				out.println("timeslot(" + schedule.get(i).getName() + ")");
			for (int i = 0; i < schedule.size(); i++) {
				for (int j = 0; j < schedule.get(i).getConflict().size(); j++)
					out.println("conflicts(" + schedule.get(i).getName() + ", " + 
							schedule.get(i).getConflict().get(j).getName() + ")");
			}
			out.println();
			out.println("//INSTRUCTORS");
			for (int i = 0; i < instructorList.size(); i++)
				out.println("instructor(" + instructorList.get(i).getName() + ")");
			out.println();
			out.println("//TAs");
			for (int i = 0; i < taList.size(); i++)
				out.println("TA(" + taList.get(i).getName() + ")");
			out.println();
			out.println("//COURSES");
			for (int i = 0; i < courseList.size(); i++) {
				if (courseList.get(i).isSenior())
					out.println("senior-course(" + courseList.get(i).getName() + ")");
				else if (courseList.get(i).isGrad())
					out.println("grad-course(" + courseList.get(i).getName() + ")");
				else 
					out.println("course(" + courseList.get(i).getName() + ")");
				for (int j = 0; j < courseList.get(i).getLectures().size(); j++) {
					out.println("lecture(" + courseList.get(i).getName() + ", " + courseList.get(i).getLectures().get(j).getName() + ")");
					if (courseList.get(i).getLectures().get(j).hasTime())
						out.println("at(" + courseList.get(i).getName() + ", " + courseList.get(i).getLectures().get(j).getName() + ", " +	 courseList.get(i).getLectures().get(j).getTime().getName() + ")");
					for (int k = 0; k < instructorList.size(); k++) {
						for (int l = 0; l < instructorList.get(k).getInstructList().size(); l++) {
							if (instructorList.get(k).getInstructList().get(l).equals(new Pair<Course,Lecture>(courseList.get(i), courseList.get(i).getLectures().get(j)))) {
								out.println("instructs(" + instructorList.get(k).getName() + ", " + courseList.get(i).getName() + ", " + courseList.get(i).getLectures().get(j).getName() + ")");
							}
						}
					}
					for (int k = 0; k < courseList.get(i).getLectures().get(j).getLabList().size(); k++) {
						out.println("lab(" + courseList.get(i).getName() + ", " + courseList.get(i).getLectures().get(j).getName() + ", " + courseList.get(i).getLectures().get(j).getLabList().get(k).getName() + ")");
						out.println("at(" + courseList.get(i).getName() + ", " + courseList.get(i).getLectures().get(j).getLabList().get(k).getName() + ", " + courseList.get(i).getLectures().get(j).getLabList().get(k).getTime().getName() + ")");
					}
				}
				out.println();
			}
			out.println();
			out.println("//Instructor details");
			for (int i = 0; i < instructorList.size(); i++) {
				for (int j = 0; j < instructorList.get(i).getPrefersTA().size(); j++) {
					out.println("prefers(" + instructorList.get(i).getName() + ", " + instructorList.get(i).getPrefersTA().get(j).getName() + ", " + instructorList.get(i).getPrefersC().get(j).getName() + ")");
				}
			}
			out.println();
			out.println("//TA details");
			for (int i = 0; i < taList.size(); i++) {
				for (int j = 0; j < taList.get(i).getTaking().size(); j++) {
					out.println("taking(" + taList.get(i).getName() + ", " + taList.get(i).getTaking().get(j).getCourse().getName() + ", " +taList.get(i).getTaking().get(j).getName() + ")");
				}
				for (int j = 0; j < taList.get(i).getKnows().size(); j++) {
					out.println("knows(" + taList.get(i).getName() + ", " + taList.get(i).getKnows().get(j).getName() + ")");
				}
				for (int j = 1; j < 4; j++) {
					if (taList.get(i).getPrefer(j) != null) {
						out.println("prefers" + j + "(" + taList.get(i).getName() + ", " + taList.get(i).getPrefer(j).getName() + ")");
					}
				}
				out.println();
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void outputSolution(String fileName) {
		int total = 0;
		PrintWriter out;
		try {
			out = new PrintWriter(fileName);
			
			// prints instructs predicates from bestSolution
			for (Pair<Lab, TA> pair : bestSolution.getSolution())
				out.println("instructs(" + pair.getValue().getName() + ", " + pair.getKey().getLecture().getCourse().getName()
						+ ", " + pair.getKey().getName() + ")");
			
			// prints length of search
			out.println("//Search time: " + (System.currentTimeMillis() - startTime) + " milliseconds");
			
			// prints info on soft constraint violations
			for (int i = 0; i <= 10; i++) {
				total += bestSolution.SCV[i];
				out.println("// " + i + " 	: " + (bestSolution.SCV[i]/bestSolution.SCVP[i])
						+ " 	* " + bestSolution.SCVP[i] + " 	= " + bestSolution.SCV[i]);
			}
			out.println("//Total: " + total);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public int taCountPerLab(Lab lab,Solution S)
	{
		int taCOUNT = 0;
		Vector<Pair<Lab, TA>> solution = S.getSolution();
		for (Pair<Lab, TA> P : solution)
            if (P.getKey().equals(lab)
                    && lab.getLecture().equals(P.getKey().getLecture())
                    && lab.getLecture().getCourse().equals(P.getKey().getLecture().getCourse()))
                taCOUNT++;
		return taCOUNT;
	}
	
	public static Vector<Lab> labListPerTA(String TA,Solution S)
	{
		Vector<Lab> labList = new Vector<Lab>();
		Vector<Pair<Lab, TA>> solution = S.getSolution();
		for (Pair<Lab, TA> P : solution)
			if (P.getValue().getName().equals(TA))
				labList.add(P.getKey());
		return labList;
	}
	
	public static int labCount(String TA,Solution S)
	{
		return labListPerTA(TA, S).size();
	}
	
	
	public boolean checkHC5(Solution S, TA ta) {
		Vector<Lab> labListTA = labListPerTA(ta.getName(), S);
		for (Lab l : labListTA ) {
			for (Lab l2 : labListTA ) {
				//no TA is assigned simultaneous labs
				if ( (!l2.equals(l) || ! l2.getLecture().equals(l.getLecture()) || ! l2.getLecture().getCourse().equals(l.getLecture().getCourse())) && e_conflicts(l.getTime().getName(), l2.getTime().getName())) {
                    //System.out.println("one or more TA is assigned simultaneous labs");
                    return false;
                }
			}
		}
		return true;
	}
	
	
	public boolean checkHC6(Solution S, TA ta) {
		Vector<Lab> labListTA = labListPerTA(ta.getName(), S);
		for (Lecture L : ta.getTaking()) {
			for (Lab l : labListTA ) {
				//no TA is assigned a lab that conflicts with his/her own courses
                if (e_conflicts(l.getTime().getName(), L.getTime().getName())) {
					//System.out.println("one or more TA is assigned a lab that conflicts with his/her own courses");
                    return false;
				}
			}
		}
		return true;
	}
	
	
	public boolean checkHardConstraints(Solution S){
		
		int labCOUNT;
		for (TA ta : taList)
		{
			labCOUNT=labCount(ta.getName(),S);
			//every TA is assigned at most MAX_LABS labs
			if (labCOUNT>maxlabs) {
                //System.out.println("every TA is not assigned at most MAX_LABS labs");
				return false;
            }
			//every TA is assigned at least MIN_LABS labs (if the TA *has* a lab assignment)
			if (labCOUNT!=0 && labCOUNT<minlabs) {
                //System.out.println("every TA is not assigned at least MIN_LABS labs (if the TA *has* a lab assignment)");
                return false;
            }
			
			if (!checkHC5(S, ta) || !checkHC6(S, ta)) {
				return false;
			}
		}

        for (Lab lab : labList)
            // no lab has more than one TA assigned to it
            // and
            // every lab has a TA assigned to it
            if (taCountPerLab(lab,S)!=1){
                //System.out.println("one or more lab has more than one TA assigned to it or not every lab has a TA assigned to it");
                return false;
            }
		
		return true;
	}
	
	public int checkSC0(TA ta,Solution S)
	{
		// Each TA should be funded (that is, they should teach at least one course)
		int labcount = labCount(ta.getName(), S);
		if (labcount > minlabs) 
			S.addMTML(ta, (int)(labcount - minlabs));
		if (labCount(ta.getName(), S) == 0) {
			S.addNolabs(ta);
			return -50;
		}
		return 0;
	}
	
	public int checkSC1(TA ta,Solution S)
	{
		// TAs should get their first choice course
		for (Pair<Lab, TA> solution : S.getSolution())
			if (solution.getValue().equals(ta) 
					&& e_prefers1(ta.getName(), solution.getKey().getLecture().getCourse().getName()))
					return 0;
		S.addPref1(ta);
		return -5;
	}
	
	public int checkSC2(TA ta,Solution S)
	{
		// TAs should get their first or second choice course 
		for (Pair<Lab, TA> solution : S.getSolution())
			if (solution.getValue().equals(ta) && (e_prefers1(ta.getName(), solution.getKey().getLecture().getCourse().getName()) || e_prefers2(ta.getName(), solution.getKey().getLecture().getCourse().getName())))
				return 0;
		S.addPref1(ta);
		S.addPref2(ta);
		return -10;
	}
	
	public int checkSC3(TA ta,Solution S)
	{
		// TAs should get their first or second or third choice course 
		for (Pair<Lab, TA> solution : S.getSolution()) {
			if (solution.getValue().equals(ta)
					&& (e_prefers1(ta.getName(), solution.getKey().getLecture().getCourse().getName())
					|| e_prefers2(ta.getName(), solution.getKey().getLecture().getCourse().getName())
					|| e_prefers3(ta.getName(), solution.getKey().getLecture().getCourse().getName())))
				return 0;
		}
		S.addPref1(ta);
		S.addPref2(ta);
		S.addPref3(ta);
		return -10;
	}
	
	public int checkSC4(TA ta,Solution S)
	{
		// TAs should have all their labs in the same course 
		Vector<Lab> labList = labListPerTA(ta.getName(),S);
        if (!labList.isEmpty()){
            Course courseLab0 = labList.get(0).getLecture().getCourse();
            for (Lab l : labList)
                if (!l.getLecture().getCourse().equals(courseLab0))
                    return -20;
        }
		return 0;
	}
	
	public int checkSC5(TA ta,Solution S)
	{
		// TAs should have all their labs in no more than 2 courses
		Vector<Lab> labList = labListPerTA(ta.getName(), S);
		Vector<Course> CourseList = new Vector<Course>();
		for (Lab l : labList) {
			CourseList.add(l.getLecture().getCourse());
		}
		if (CourseList.isEmpty())
		{
			return 0;
		} else {
			for (int i = 0; i < CourseList.size(); i++) {
				for (int j = i; j < CourseList.size(); j++) {
						if (i != j) {
							if (CourseList.get(i).equals(CourseList.get(j))) {
								CourseList.remove(j);
							}
						}
				}
			}
		}
		if (CourseList.size() <= 2) {
			return 0;
		}
		S.addManyCourses(labList, ta);
		return -35;
		//return (CourseList.size() - 1) * -10;
	}
	
	public int checkSC6(TA ta,Solution S)
	{
		int total = 0;
		// TAs should not teach a lab for a course for which they don't know the subject matter
		Vector<Lab> labList = labListPerTA(ta.getName(),S);
		for (Lab l : labList)
			if (!e_knows(ta.getName(),l.getLecture().getCourse().getName())) {
					S.addDoesntKnow(new Pair<Lab, TA>(l, ta));
					total -= 30;
			}
		return total;
	}
	
	public int checkSC7(TA ta,Solution S)
	{
		// TAs should not teach two labs of distinct courses at the senior level
		Vector<Lab> labList = labListPerTA(ta.getName(), S);
		Vector<Course> snrCourseList = new Vector<Course>();
		for (Lab l : labList) {
			if (l.getLecture().getCourse().isSenior())
			{
				snrCourseList.add(l.getLecture().getCourse());
			}
		}
		if (snrCourseList.isEmpty())
		{
			return 0;
		} else {
			for (int i = 0; i < snrCourseList.size(); i++) {
				for (int j = 0; j < snrCourseList.size(); j++) {
						if (i != j) {
							if (snrCourseList.get(i).equals(snrCourseList.get(j))) {
								snrCourseList.remove(j);
							}
						}
				}
			}
		}
		if (snrCourseList.isEmpty()) {
			return 0;
		}
		return (snrCourseList.size() - 1) * -10;
		
	}
	
	public int checkSC8(TA ta,Solution S,int leastNBLabs)
	{
		int total = 0;
		// TAs should not teach more than one more lab than the TA that teaches the least number of labs. 
		if (labCount(ta.getName(), S) > leastNBLabs + 1 )
			total -= 25;
		return total;
	}
	
	public int checkSC9(TA ta,Solution S,int NBLabs)
	{
		// TAs should all teach the same number of labs. 
		if (labCount(ta.getName(), S) !=  NBLabs)
			return -5;
		return 0;
	}
	
	public int checkSC10(TA ta,Solution S)
	{
		// If the instructor requested particular TAs for his/her course,
        // each of the lecture the instructor is teaching for that course should be taught by one of the requested TAs
		int total = 0;
		for (Instructor I : instructorList)
			for (Pair<Course,Lecture> P : I.getInstructList())
				if (e_prefers(I.getName(), ta.getName(), P.getKey().getName()))
					for (Lab L : P.getValue().getLabList())
						for (Pair<Lab, TA> solution : S.getSolution())
                            if (solution.getKey().equals(L)
                                    && solution.getKey().getLecture().equals(L.getLecture())
                                    && solution.getKey().getLecture().getCourse().equals(L.getLecture().getCourse())
                                    && !solution.getValue().equals(ta))
                            	total -= 10;
		return total;
	}
	
	public int checkSoftConstraints(Solution S)
	{
		int penalty = 0;
		int leastNBLabs=100;
		for (TA ta : taList)
			if (labCount(ta.getName(), S)<leastNBLabs)
				leastNBLabs=labCount(ta.getName(), S);
		for (int j = 0; j < 11; j++)
			S.SCV[j] = 0;
		for (TA ta : taList)
		{
			S.SCV[0] += checkSC0(ta,S);
			S.SCV[1] += checkSC1(ta,S);
			S.SCV[2] += checkSC2(ta,S);
			S.SCV[3] += checkSC3(ta,S);
			S.SCV[4] += checkSC4(ta,S);
			S.SCV[5] += checkSC5(ta,S);
			S.SCV[6] += checkSC6(ta,S);
			S.SCV[7] += checkSC7(ta,S);
			S.SCV[8] += checkSC8(ta,S,leastNBLabs);
			S.SCV[9] += checkSC9(ta,S,leastNBLabs);
			S.SCV[10] += checkSC10(ta,S);
		}
		System.out.println("SC0: " + (-S.SCV[0]/50));
		System.out.println("SC1: " + (-S.SCV[1]/5));
		System.out.println("SC2: " + (-S.SCV[2]/10));
		System.out.println("SC3: " + (-S.SCV[3]/10));
		System.out.println("SC4: " + (-S.SCV[4]/20));
		System.out.println("SC5: " + (-S.SCV[5]/35));
		System.out.println("SC6: " + (-S.SCV[6]/30));
		System.out.println("SC7: " + (-S.SCV[7]/10));
		System.out.println("SC8: " + (-S.SCV[8]/25));
		System.out.println("SC9: " + (-S.SCV[9]/5));
		System.out.println("SC10: " + (-S.SCV[10]/10));
		System.out.println("Least number of labs: " + leastNBLabs);
		for (int i : S.SCV) {
			penalty += i;
		}
		S.total = penalty;
		return penalty;
	}
	
	// randomly select one of several mutation functions, apply to the given solution and return a new solution
	public Solution mutate(Solution s) {
		Solution newSol;
		int random;
		if (s.getSolution().size() == 1)
			return s;
		while (true) {
			random = (int) (Math.random() * 4);
			System.out.println(random);
			
			switch (random) {
			case 0:
				if (s.checkNoLabs().isEmpty())
					continue;
				newSol = makeGiveToNoLabs(s);
				break;
			case 1:
				if (s.getDoesntKnow().isEmpty())
					continue;
				newSol = makeRemoveUnknownCourse(s);
				break;
			case 2:
				if (s.getPref3().isEmpty())
					continue;
				newSol = makeGetPreferenceCourse(s);
				break;
			case 3:
				if (s.getManyCourses().isEmpty())
					continue;
				newSol = makeLessThan2Courses(s);
				break;
			default:
				newSol = makeRandomChange(s);
				break;
			}
			
			// if newSol is empty (a mutation function returned an error or couldn't find a suitable solution) randomly make a change
			if (newSol.getSolution().isEmpty()) {
				newSol = makeRandomChange(s);
			}
			
			return newSol;
		}
		
		// if there is a ta with no labs, give them a lab from another ta
		/*if (!s.checkNoLabs().isEmpty()) {
			
		}
		
		// if ta doesn't know a lab they teach, give it to someone that does know
		if (!s.getDoesntKnow().isEmpty()) {
			
		}
		// try giving a ta their first, second, or third preference course if they don't have it
		if (!s.getPref3().isEmpty()) {
			
		}
		// try to give away or swap out a lab whose course is different from the other labs
		if (!s.getManyCourses().isEmpty()) {
			
		}
		return new Solution();*/
	}
	
	// if there is a ta with no labs, give them a lab from another ta
	private Solution makeGiveToNoLabs(Solution s) {
		// sort taList by the number of labs each ta has
		Solution clone;
		int randomTA;
		int randomGiver;
		int numLabsGiven;
		for (int i = 0; i < 5; i++) {
			numLabsGiven = 0;
			clone = new Solution(s);
			Vector<TA> noLabs = clone.checkNoLabs();
			randomTA = (int) (Math.random() * clone.checkNoLabs().size());
			TA ta = noLabs.get(randomTA);
			int randomLab;
			Lab lab;
			int c = 0;
			do {
				System.out.println("Here 0, " + i);
				randomGiver = (int) (Math.random() * clone.getMTML().size());
				TA giver = clone.getMTML().get(randomGiver).getKey();
				if (labCount(giver.getName(), s) == 0)
					continue;
				randomLab = (int) (Math.random() * labListPerTA(giver.getName(), s).size());
				lab = labListPerTA(giver.getName(), s).get(randomLab);
				Solution clone2 = new Solution();
				clone2 = new Solution(clone);
				clone2.giveLab(giver, ta, lab);
				if (checkHC5(clone2, ta) && (checkHC6(clone2, ta))) {
					clone.giveLab(giver, ta, lab);
					numLabsGiven++;
				}
				System.out.println(c);
				c++;
			} while ((numLabsGiven < minlabs) && (c < 20));
			if ((numLabsGiven == minlabs) && checkHardConstraints(clone)) {
				System.out.println(numLabsGiven == minlabs);
				System.out.println("Success 0");
				return clone;
			}
			System.out.println("i = " + i);
		}
		return new Solution();
	}
	
	// if ta doesn't know a lab they teach, give it to someone that does know
	private Solution makeRemoveUnknownCourse(Solution s) {
		Solution clone;
		int i = 0;
		int randomPair;
		int randomTaker;
		Pair<Lab, TA> pair;
		TA taker;
		clone = new Solution();
		
		// choose a random Lab TA pair 
		for (int j = 0; j < 5; j++) {
			int c = 0;
			do {
				if (c >= 25)
					return new Solution();
				randomPair = (int) (Math.random() * s.getDoesntKnow().size());
				pair = s.getDoesntKnow().get(randomPair);
				c++;
			} while (labCount(pair.getValue().getName(), s) <= minlabs);
			do {
				//System.out.println("Here 1, " + i);
				i++;
				randomTaker = (int) (Math.random() * taList.size());
				taker = taList.get(randomTaker);
				// if the taker has less than maxlabs and knows the material try giving it to him
				if (labCount(taker.getName(), s) < maxlabs && taker.getKnows().contains(pair.getKey().getLecture().getCourse())) {
					clone = new Solution(s);
					clone.giveLab(pair.getValue(), taker, pair.getKey());
					if (checkHardConstraints(clone)) {
						System.out.println("Success 1");
						return clone;
					}
				}
			} while (i < taList.size());
		}
		return new Solution();
	}
	
	// try giving a ta their first, second, or third preference course if they don't have it
	private Solution makeGetPreferenceCourse(Solution s) {
		Solution clone;
		int max;
		int random;
		TA ta;
		Pair <Lab, TA> pair = new Pair<Lab, TA>(null, null);
		Course course;
		for (int j = 0; j < 5; j++) {
			// pick a random ta with no preference course, with more than minlabs
			int c = 0;
			do {
				if (c >= 25)
					return new Solution();
				random = (int) (Math.random() * s.getPref3().size());
				ta = s.getPref3().get(random);
				c++;
			} while (labCount(ta.getName(), s) <= minlabs);
			int i = 0;
			// get random lab ta pair, take lab if preferred
			do {
				//System.out.println("Here 2, " + i);
				i++;
				random = (int) (Math.random() * s.getSolution().size());
				pair = s.getSolution().get(random);
				course = pair.getKey().getLecture().getCourse();
				if (course == ta.getPrefer(1) || course == ta.getPrefer(2) || course == ta.getPrefer(3)) {
					// make a clone of the current solution
					clone = new Solution(s);
					// if the ta has a lab it can give without hitting minlabs, give it
					if (labCount(s.getSolution().get(random).getValue().getName(), s) > minlabs &&
							labCount(ta.getName(), s) < maxlabs) {
						clone.giveLab(pair.getValue(), ta, pair.getKey());
						if (checkHardConstraints(clone)) {
							System.out.println("Success 2");
							return clone;
						}
					}
				}
			} while (i < s.getSolution().size());
		}
		return new Solution();
	}
	
	// try to give away or swap out a lab whose course is different from the other labs
	private Solution makeLessThan2Courses(Solution s) {
		Solution clone;
		int random;
		TA ta;
		TA taker;
		Pair <Lab, TA> pair = new Pair<Lab, TA>(null, null);
		Vector<Lab> labList;
		for (int j = 0; j < 5; j++) {
			// pick a random ta with more than 2 courses and more than minlabs
			int c = 0;
			do {
				if (c >= 25)
					return new Solution();
				random = (int) (Math.random() * s.getManyCourses().size());
				ta = s.getManyCourses().get(random).getValue();
				c++;
			} while (labCount(ta.getName(), s) <= minlabs);
			labList = s.getManyCourses().get(random).getKey();
			
			// count how many labs of each course the ta has
			Vector<Pair<Course, Vector<Lab>>> courses = new Vector<Pair<Course, Vector<Lab>>>();
			START: for (Lab lab : labList) {
				for (Pair<Course, Vector<Lab>> coursePair : courses) {
					if (coursePair.getKey() == lab.getLecture().getCourse()) {
						coursePair.getValue().add(lab);
						break START;
					}
				}
				Vector<Lab> vec = new Vector<Lab>();
				vec.add(lab);
				courses.add(new Pair<Course, Vector<Lab>>(lab.getLecture().getCourse(), vec));
				
			}
			
			// find course with least # of labs
			Pair<Course, Vector<Lab>> lowest = courses.get(0);
			for (Pair<Course, Vector<Lab>> coursePair : courses) {
				if (coursePair.getValue().size() < lowest.getValue().size()) {
					lowest = coursePair;
				}
			}
			
			int i = 0;
			// pick a random lab ta pair, then try to give away lab from course
			do {
				//System.out.println("Here 3, " + i);
				random = (int) (Math.random() * taList.size());
				taker = taList.get(random);
				
				if (labCount(taker.getName(), s) < maxlabs) {
					clone = new Solution(s);
					random = (int) (Math.random() * lowest.getValue().size());
					clone.giveLab(ta, taker, lowest.getValue().get(random));
					if (checkHardConstraints(clone)) {
						System.out.println("Success 3");
						return clone;
					}
				}
				i++;
			} while (i < taList.size());
		}
		return new Solution();
	}
	
	private Solution makeRandomChange(Solution s) {
		Solution clone;
		int random;
		Pair<Lab, TA> pair1;
		Pair<Lab, TA> pair2;
		
		for (int i = 0; i < s.getSolution().size(); i++) {
			System.out.println("Here d, " + i);
			// get 2 random pairs from solution
			random = (int) (Math.random() * s.getSolution().size());
			pair1 = s.getSolution().get(random);
			random = (int) (Math.random() * s.getSolution().size());
			pair2 = s.getSolution().get(random);
			
			clone = new Solution(s);
			
			// swap labs, then test against hard constraints
			clone.swapLabs(pair1.getValue(), pair2.getValue(), pair1.getKey(), pair2.getKey());
			if (checkHardConstraints(clone)) {
				System.out.println("Success D");
				return clone;
			}
		}
		return s;
	}
}