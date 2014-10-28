//  BinaryTournament.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package jmetal.operators.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import jmetal.core.Operator;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.metaheuritic.abc.FuzzyFitnessComparator;
import jmetal.metaheuritic.abc.InvertFitnessComparator;
import jmetal.metaheuritic.abc.SidModifiedDominanceComparator;
import jmetal.util.comparators.*;
import jmetal.util.PseudoRandom;

/**
 * This class implements an binary tournament selection operator
 */
public class SidBinaryTournament extends Selection {

  /**
   * Stores the <code>Comparator</code> used to compare two
   * solutions
   */
  private Comparator dominance_comparator_;
  private Comparator fitNess_comparator_;
  private Comparator sid_dominanceComparator;
  private Comparator fuzzyfitnessComparator;

  /**
   * Constructor
   * Creates a new Binary tournament operator using a BinaryTournamentComparator
   */
  
  private int a_[];
  
  /**
   *  index_ stores the actual index for selection
   */
  private int index_ = 0;
  
  
  public SidBinaryTournament(HashMap<String, Object> parameters){
  	super(parameters) ;
  	
  	if ((parameters != null) && (parameters.get("comparator") != null))
  	{
  		dominance_comparator_ = (Comparator) parameters.get("comparator") ;
  	}
  		
  	else
  	{
      //comparator_ = new BinaryTournamentComparator();
  		System.out.println("combined comparator");
  		dominance_comparator_ = new DominanceComparator();  	
  		fitNess_comparator_ = new InvertFitnessComparator();
  		sid_dominanceComparator=new SidModifiedDominanceComparator();
  		fuzzyfitnessComparator=new FuzzyFitnessComparator();
  	}
  		
  } // BinaryTournament
  
  /**
  * Performs the operation
  * @param object Object representing a SolutionSet
  * @return the selected solution
  */
  public Object execute(Object object){
	    
		SolutionSet solutionSet = (SolutionSet) object;
		Solution solution1, solution2;
		
		
		
		
		
		solution1 = solutionSet.get(PseudoRandom.randInt(0,
				solutionSet.size() - 1));
		solution2 = solutionSet.get(PseudoRandom.randInt(0,
				solutionSet.size() - 1));
		/*
		int flag = dominance_comparator_.compare(solution1, solution2);
		if (flag == 1){
		      return solution1;
		}
		else if (flag == -1){
		      return solution2;
		}
		else{
			if (PseudoRandom.randDouble() < 0.5)
				return solution1;
			else
				return solution2;
		}
			*/
		
		/*
		
		if (solutionSet.size() >= 2)
			while (solution1 == solution2)
				solution2 = solutionSet.get(PseudoRandom.randInt(0,
						solutionSet.size() - 1));
    
		*/
		
		
		int flag = dominance_comparator_.compare(solution1, solution2);
		if (flag == -1)
		      return solution1;
		    else if (flag == 1)
		      return solution2;
		else {
			
			if(solution1.getFitness() ==0 || solution2.getFitness()==0){
				double crowding1 = solution1.getCrowdingDistance();
			    double crowding2 = solution2.getCrowdingDistance();
			    
			    return (crowding1>crowding2 )?solution1:solution2;
			    
			    
			}
			
			flag = fuzzyfitnessComparator.compare(solution1, solution2);

			if (flag == 1)
				return solution1;
			else if (flag == -1)
				return solution2;
			else {
				if (PseudoRandom.randDouble() < 0.5)
					return solution1;
				else
					return solution2;
			}

		}
		
  } // execute
} // BinaryTournament
