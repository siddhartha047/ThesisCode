package jmetal.metaheuristics.MonteSid;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.util.comparators.OverallConstraintViolationComparator;

public class MRanking {
	
	private static final Comparator constraint_ = new OverallConstraintViolationComparator();
	
	public SolutionSet getFrontZero(SolutionSet solutionSet) {
		
		SolutionSet frontZero=new SolutionSet(solutionSet.size());
		
		int[] dominateMe = new int[solutionSet.size()];
		List<Integer>[] iDominate = new List[solutionSet.size()];

		for (int p = 0; p < solutionSet.size(); p++) {
			iDominate[p] = new LinkedList<Integer>();
			dominateMe[p] = 0;
		}

		for (int p = 0; p < (solutionSet.size() - 1); p++) {

			for (int q = p + 1; q < solutionSet.size(); q++) {
				int flagDominate =constraint_.compare(solutionSet.get(p),solutionSet.get(q));
		        if (flagDominate == 0) {
		          flagDominate =compare(solutionSet.get(p),solutionSet.get(q));
		        }
				if (flagDominate == -1) {
					iDominate[p].add(new Integer(q));
					dominateMe[q]++;
				} else if (flagDominate == 1) {
					iDominate[q].add(new Integer(p));
					dominateMe[p]++;
				} else {
					// System.out.println(p+"\tnondominate\t"+q);
				}
			}

		}
		
		for (int p = 0; p < solutionSet.size(); p++) {
		      if (dominateMe[p] == 0) {
		        frontZero.add(solutionSet.get(p));
		      }
		} 
		
		for(int p=0;p<solutionSet.size();p++){
			for(int q=0;q<frontZero.size();q++){
				if(solutionSet.get(p)==frontZero.get(q)){
					solutionSet.remove(p);
				}
			}
		}
		
		
		return frontZero;

	}

	public int compare(Solution solution1, Solution solution2) {
		int dominate1;
		int dominate2;

		dominate1 = 0;
		dominate2 = 0;

		int flag;

		double value1, value2;
		for (int i = 0; i < 2; i++) {
			value1 = solution1.mObjective[i];
			value2 = solution2.mObjective[i];

			if (value1 < value2) {
				flag = -1;
			} else if (value1 > value2) {
				flag = 1;
			} else {
				flag = 0;
			}

			if (flag == -1) {
				dominate1 = 1;
			}

			if (flag == 1) {
				dominate2 = 1;
			}
		}

		if (dominate1 == dominate2) {
			return 0; // No one dominate the other
		}
		if (dominate1 == 1) {
			return -1; // solution1 dominate
		}
		return 1; // solution2 dominate
	}

}
