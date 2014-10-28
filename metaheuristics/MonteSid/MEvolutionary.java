package jmetal.metaheuristics.MonteSid;

import java.net.Socket;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.util.Distance;
import jmetal.util.comparators.CrowdingComparator;
import jmetal.util.comparators.ObjectiveComparator;

public class MEvolutionary {
	
	public void takeSolution(SolutionSet population,SolutionSet actualSolutionSet,int remain){

		
		int numberOfObjective=actualSolutionSet.get(0).numberOfObjectives();		
		SolutionSet solutionSet=new SolutionSet(actualSolutionSet.size());
		SolutionSet takenSolution=new SolutionSet(actualSolutionSet.size());
		
		double []idealPoint=new double[numberOfObjective];
		
		for(int i=0;i<actualSolutionSet.size();i++)solutionSet.add(actualSolutionSet.get(i));
		
		for(int i=0;i<numberOfObjective;i++){
			solutionSet.sort(new ObjectiveComparator(i));
			idealPoint[i]=solutionSet.get(0).getObjective(i);
			takenSolution.add(solutionSet.get(0));			
			solutionSet.remove(0);
			remain--;
		}
		
		//assign 1 objective
		
		for(int i=0;i<solutionSet.size();i++){
			Solution solution=solutionSet.get(i);
			solution.mObjective[1]=getDistanceIdeal(idealPoint, solution);
		}
		
		//assign 0 objective
		
		
		
//		AssignMObjective(takenSolution, solutionSet);
		
		TwoObjectiveSolution(takenSolution,solutionSet,remain);
		for(int i=0;i<takenSolution.size();i++){
			population.add(takenSolution.get(i));
		}
	}
	
	
	
	public void TwoObjectiveSolution(SolutionSet takenSolution, SolutionSet solutionSet, int remain){
		
		Distance distance = new Distance();
		
		while(remain>0){
			
			AssignMObjective(takenSolution, solutionSet);
			
			MRanking mRanking=new MRanking();
			
			SolutionSet front=mRanking.getFrontZero(solutionSet);
			
			//System.out.println(front.size());
			
			if(front.size()<remain){
				
				for(int i=0;i<front.size();i++){
					takenSolution.add(front.get(i));
					remain--;
				}
			}
			else{
				distance.crowdingDistanceAssignment(front, front.get(0).numberOfObjectives());
				front.sort(new CrowdingComparator());
				for (int k = 0; k < remain; k++) {
					takenSolution.add(front.get(k));
					
				} // for
				
				remain=0;
			}
			
			
		}
		
	}
	
	
	public double getDistanceIdeal(double []ideal, Solution sol2){
		double d=0;
		
		for(int i=0;i<sol2.numberOfObjectives();i++){
			d+=(ideal[i]-sol2.getObjective(i))*(ideal[i]-sol2.getObjective(i));
		}
		
		return Math.sqrt(d);
	}
	
	public double getDistance(Solution sol1, Solution sol2){
		double d=0;
		
		for(int i=0;i<sol1.numberOfObjectives();i++){
			d+=(sol1.getObjective(i)-sol2.getObjective(i))*(sol1.getObjective(i)-sol2.getObjective(i));
		}
		
		return Math.sqrt(d);
	}
	
	
	
	public void AssignMObjective(SolutionSet takenSolution, SolutionSet solutionSet){
		for(int i=0;i<solutionSet.size();i++){
			
			Solution solution=solutionSet.get(i);
			
			double minDistance=Double.MAX_VALUE;
			
			for(int j=0;j<takenSolution.size();j++){			
				double distance= getDistance(solution,takenSolution.get(j));				
				if(minDistance<distance)minDistance=distance;
			}
			
			solution.mObjective[0]=1/minDistance;			
		}		
	}

}
