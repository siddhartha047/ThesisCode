package jmetal.metaheuritic.abc;

import java.util.Comparator;
import jmetal.core.*;


public class FuzzyFitnessComparator implements Comparator<Solution>
{
    @Override
    // x better than y:-1
    // x worse than y: 1
    public int compare(Solution x, Solution y)
    {
        
    	double[] mainObjectives = x.get_objectives();
		double[] compareObjectives = y.get_objectives();
		
		double dominateBy=1;
		double dominatedBy=1;
		
		double better=0;
		double equal=0;
		double worse=0;
		
		double value;
		
		for (int i = 0; i < mainObjectives.length; i++) {

			double diff=mainObjectives[i]-compareObjectives[i];
			
			if(diff==0){
				equal++;
				//System.out.println("rare");
			}
			else if(diff<0){
				better++;				
			}
			else{
				worse++;
			}
			
			
			
			value = SigMoid.SigMoidValue(diff, HowMuchBetterFitness.APos[i],HowMuchBetterFitness.ObjMean[i]);
			dominateBy *= value;
			
			value = SigMoid.SigMoidValue(-diff, HowMuchBetterFitness.APos[i],HowMuchBetterFitness.ObjMean[i]);
			dominatedBy *= value;
		}
		
		double dominateByNorm=dominateBy/(dominateBy+dominatedBy);
		
		if(Math.random()>dominateByNorm)return 1;
		else return -1;
		
		/*if(dominateBy>dominatedBy){
			return 1;
		}
		else if(dominateBy<dominatedBy){
			return -1;
		}
		else {
			return 0;
		}*/
	}
    
}
