/**
 * 
 */
package taAllocation;

import java.util.TreeSet;

import taAllocation.Predicate.ParamType;

/**
 * This interface should be <code>implement</code>ed by any class that reads TA allocation 
 * predicates.  The implementing class should also <code>extend</code> the
 * class {@link taAllocation.PredicateReader}.  
 * <p>
 * The method declarations here form the
 * stubs you will need to implement all the predicates for the assignments.
 * The static String definitions here don't need to be overridden: they will
 * automatically work with {@link taAllocation.PredicateReader} to yield help 
 * information when the predicate "!help()" is interpreted. 
 * <p>
 * For more information on the semantics of these predicates, see the 
 * CPSC 433 F06 assignment specification.
 * 
 * <p>Copyright: Copyright (c) 2005-2006, Department of Computer Science, University 
 * of Calgary.  Permission to use, copy, modify, distribute and sell this 
 * software and its documentation for any purpose is hereby granted without 
 * fee, provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in supporting 
 * documentation.  The Department of Computer Science makes no representations
 * about the suitability of this software for any purpose.  It is provided
 * "as is" without express or implied warranty.</p>
 *
 * @author <a href="http://www.cpsc.ucalgary.ca/~kremer/">Rob Kremer</a>
 *
 */
public interface TAallocationPredicates {
	public static String h_maxlabs = "assert the maximum number, <id>, of labs a TA should teach";
	public void a_maxlabs(Long p);
	
	public static String h_minlabs = "assert the minimum number, <id>, of labs a TA should teach";
	public void a_minlabs(Long p);
	
	public static String h_TA = "query or assert <id> is a TA";
	public void a_TA(String p);
	public boolean e_TA(String p);
	
	public static String h_instructor = "query or assert <id> is an instructor";
	public void a_instructor(String p) ;
	public boolean e_instructor(String p);
	
	public static String h_course = "query or assert <id> is a course";
	public void a_course(String p);
	public boolean e_course(String p);
	
	public static String h_senior_course = "query or assert <id> is a senior course";
	public void a_senior_course(String p);
	public boolean e_senior_course(String p);
	
	public static String h_grad_course = "query or assert <id> is a grad course";
	public void a_grad_course(String p);
	public boolean e_grad_course(String p);
	
	public static String h_timeslot = "query or assert <id> is a timeslot";
	public void a_timeslot(String p);
	public boolean e_timeslot(String p);
	
	public static String h_lecture = "query or assert course <id1> has a lecture <id2>";
	public void a_lecture(String c, String lec);
	public boolean e_lecture(String c, String lec);
	
	public static String h_lab = "query or assert course <id1> and lecture <id2> has a lab <id3>";
	public void a_lab(String c, String lec, String lab);
	public boolean e_lab(String c, String lec, String lab);
	
	public static String h_instructs = "query or assert TA/intructor <id1> instructs couse <id2> and lab/lecture <id3>";
	public void a_instructs(String p, String c, String l);
	public boolean e_instructs(String p, String c, String l);
	
	public static String h_at = "query or assert course <id1> and lab/lecture <id2> is at timeslot <id3>";
	public void a_at(String c, String l, String t);
	public boolean e_at(String c, String l, String t);
	
	public static String h_knows = "query or assert TA <id> knows the material for course <id2>";
	public void a_knows(String ta, String c);
	public boolean e_knows(String ta, String c);
	
	public static String prefers = "query or assert Instructor <id> prefers TA <id2> for his/her course <id3>";
	public void a_prefers(String instructor, String ta, String c);
	public boolean e_prefers(String instructor, String ta, String c);
	
	public static String prefers1 = "query or assert TA <id> has course <id2> as his/her 1st preference";
	public void a_prefers1(String ta, String c);
	public boolean e_prefers1(String ta, String c);
	
	public static String prefers2 = "query or assert TA <id> has course <id2> as his/her 2nd preference";
	public void a_prefers2(String ta, String c);
	public boolean e_prefers2(String ta, String c);
	
	public static String prefers3 = "query or assert TA <id> has course <id2> as his/her 3rd preference";
	public void a_prefers3(String ta, String c);
	public boolean e_prefers3(String ta, String c);
	
	public static String h_taking = "query or assert TA <id1> is taking course <id2> and lab/lecture <id3>";
	public void a_taking(String ta, String c, String l);
	public boolean e_taking(String ta, String c, String l);
	
	public static String h_conflicts = "query or assert timeslot <id1> conflicts with timeslot <id2>";
	public void a_conflicts(String t1, String t2);
	public boolean e_conflicts(String t1, String t2);

}
