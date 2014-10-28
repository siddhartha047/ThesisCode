package jmetal.metaheuritic.abc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.util.comparators.DominanceComparator;
import jmetal.util.comparators.OverallConstraintViolationComparator;

public class zhenan_fuzzyranking {	  
	  /**
	   * The <code>SolutionSet</code> to rank
	   */
	  private SolutionSet   solutionSet_ ;
	  
	  /**
	   * An array containing all the fronts found during the search
	   */
	  private SolutionSet[] ranking_  ;
	  
	  /**
	   * stores a <code>Comparator</code> for dominance checking
	   */
	  private static final Comparator dominance_ = new DominanceComparator();
	  
	  /**
	   * stores a <code>Comparator</code> for Overal Constraint Violation Comparator
	   * checking
	   */
	  private static final Comparator constraint_ = new OverallConstraintViolationComparator();
	    
	  private double alpha = 0.5;
	  /** 
	   * Constructor.
	   * @param solutionSet The <code>SolutionSet</code> to be ranked.
	   */       
	  public zhenan_fuzzyranking(SolutionSet solutionSet) {        
	    solutionSet_ = new SolutionSet() ;
	    solutionSet_ = solutionSet_.union(solutionSet); // copy the solutionSet

	    // front[i] contains the list of individuals belonging to the front i
	    List<Solution> [] front = new List[solutionSet_.size()+1];
	    

	    // Initialize the fronts 
	    for (int i = 0; i < front.length; i++)
	      front[i] = new LinkedList<Solution>();
	       
	    int initialrank = 0;
	    int num_rankassigned = 0;
    	ModifiedNahidfitness mnf = new ModifiedNahidfitness();
        
    	int sz = solutionSet_.size();
	    while(solutionSet_.size()!=0){
	    	//mnf.fuzzyfication(solutionSet);
	    	System.out.println("before fitness: "+solutionSet_.size());
	    	if(solutionSet_.size()<2){
	    		front[initialrank].add(solutionSet_.get(0));
	    		initialrank++;
	    		break;
	    	}
	    	
	    	mnf.FitnessAssignment(solutionSet_);
	    	//solutionSet_.printSolutionSet();
	    	ArrayList <Integer> marked_solutionidx = new ArrayList(); 
	    	for (int i = 0; i < solutionSet_.size(); i++) {
				if(solutionSet_.get(i).getFitness()>=alpha ) {
					solutionSet_.get(i).setRank(initialrank);
					front[initialrank].add(solutionSet_.get(i));
					//System.out.println("added: "+solutionSet_.get(i));
					marked_solutionidx.add(i);
				}				
			}
			//System.out.println("front sz:"+front[initialrank].size()+" ini "+initialrank);
			for (int j = marked_solutionidx.size()-1; j >=0 ; j--) {
				//Solution s = front[initialrank].get(j);
				int d = marked_solutionidx.get(j);
				//System.out.println("--> "+s);
				//System.out.println(solutionSet_.size()+" solutionSet->_"+d);
				solutionSet_.remove(d);
			}
			initialrank ++;
	    }
	   
	    ranking_ = new SolutionSet[initialrank];
	    Iterator<Solution> it1; 
	    //0,1,2,....,i-1 are front, then i fronts
	    for (int j = 0; j < initialrank; j++) {
	      ranking_[j] = new SolutionSet(front[j].size());
	      it1 = front[j].iterator();
	      while (it1.hasNext()) {
	                ranking_[j].add(it1.next());       
	      }
	    }
	    
	  }
	  /**
	   * Returns a <code>SolutionSet</code> containing the solutions of a given rank. 
	   * @param rank The rank
	   * @return Object representing the <code>SolutionSet</code>.
	   */
	  public SolutionSet getSubfront(int rank) {
	    return ranking_[rank];
	  } // getSubFront

	  /** 
	  * Returns the total number of subFronts founds.
	  */
	  public int getNumberOfSubfronts() {
	    return ranking_.length;
	  } // getNumberOfSubfronts
} // Ranking