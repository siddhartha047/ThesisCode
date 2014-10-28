package jmetal.metaheuristics.MonteSid;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;

public class MonteCarloCluster {
	public void cluster(SolutionSet solutionSet){
		
		double MinDistance=Double.MAX_VALUE;
		double MaxDistance=-1;
		
		for(int i=0;i<solutionSet.size();i++){
			Solution solution=solutionSet.get(i);
			
			double distance=getDistanceFromOrigin(solution);
			
			solution.distance=distance;
			
			if(distance>MaxDistance)MaxDistance=distance;
			if(distance<MinDistance)MinDistance=distance;
			
		}		
		
	}
	
	public double getDistanceFromOrigin(Solution solution){
		
		double distance=0;
		
		for(int i=0;i<solution.numberOfObjectives();i++){
			distance +=solution.getObjective(i)*solution.getObjective(i);
		}
		
		return Math.sqrt(distance);
	}
}
