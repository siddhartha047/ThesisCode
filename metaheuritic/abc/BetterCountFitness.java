package jmetal.metaheuritic.abc;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;

public class BetterCountFitness {
	public void FitnessAssignment(SolutionSet solutionSet){
		
		for(int i=0;i<solutionSet.size();i++){
			Solution solution=solutionSet.get(i);
			
			for(int j=i+1;j<solutionSet.size();j++){
				if(i==j)continue;
				
				Solution comPareSolution=solutionSet.get(j);
				
				CompareSolution(solution,comPareSolution);
				
			}
			
			//System.out.println();
			
		}
		
		//solutionSet.printSolutionSet();
	}
	
	public void CompareSolution(Solution mainSolution,Solution compareSolution){
		double []mainObjectives=mainSolution.get_objectives();
		double []compareObjectives=compareSolution.get_objectives();
		
		double equal=0;
		double better=0;
		double worse=0;
		
		double betterAmount=0;
		
		
		
		for(int i=0;i<mainObjectives.length;i++){
			
			if(mainObjectives[i]==compareObjectives[i]){
				equal++;
			}
			else if(mainObjectives[i]<compareObjectives[i]){
				better++;
			}
			else{
				worse++;
			}
		}
		
		double dominateBy=(better/(better+worse));
		double dominatedBy=1-dominateBy;
		
		double mainFitness=mainSolution.getFitness();
		mainSolution.setFitness(mainFitness+dominatedBy);
		double compareFitness=mainSolution.getFitness();
		compareSolution.setFitness(compareFitness+dominateBy);

	}

}
