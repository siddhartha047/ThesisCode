/** To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.metaheuritic.abc;

/**
 *
 * @author andy
 */

import java.util.Iterator;
import jmetal.core.*;
import jmetal.util.comparators.CrowdingComparator;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.*;
import jmetal.util.comparators.FitnessComparator;
import jmetal.util.comparators.ObjectiveComparator;

/** 
 *   Implementation of NSGA-II.
 *  This implementation of NSGA-II makes use of a QualityIndicator object
 *  to obtained the convergence speed of the algorithm. This version is used
 *  in the paper:
 *     A.J. Nebro, J.J. Durillo, C.A. Coello Coello, F. Luna, E. Alba 
 *     "A Study of Convergence Speed in Multi-Objective Metaheuristics." 
 *     To be presented in: PPSN'08. Dortmund. September 2008.
 */

public class sid extends Algorithm {

	public sid(Problem problem) {
		super (problem) ;
	} 
              

    public void takePopulation(SolutionSet population,SolutionSet union,int populationSize){
            union.sort(new InvertFitnessComparator());    	
            for(int i=0;i<populationSize;i++){
                population.add(union.get(i));
            }
    }
    
    public void takePopulationLowToHighFitness(SolutionSet population,SolutionSet union,int populationSize){
        union.sort(new Low_HighFitnessComparator());    	
        for(int i=0;i<populationSize;i++){
            population.add(union.get(i));
        }
    }
    
    public void Experiment(){
    	
    	
    	SolutionSet solutionSet=new SolutionSet(6);
    	StaticSolutionSet.MakeTwoObjectiveSolution(solutionSet);
    	//StaticSolutionSet.MakeSolutionSet(solutionSet);
    	
    	solutionSet.printSolutionSet();
    	
    	GridMaking gridMaking=new GridMaking();
    	gridMaking.gridMaking(solutionSet,5);
    	
    	solutionSet.printSolutionSet();
    	
    	SolutionSet population=new SolutionSet(2);
    	
    	takePopulation(population, solutionSet, 2);
    	population.printSolutionSet();
    	
    	
    }

	public SolutionSet execute() throws JMException, ClassNotFoundException {
		
		
		//Experiment();
		//System.exit(0);
		
		int populationSize;
		int maxEvaluations;
		int evaluations;

		QualityIndicator indicators; // QualityIndicator object
		int requiredEvaluations; // Use in the example of use of the
		// indicators object (see below)

		SolutionSet population;
		SolutionSet offspringPopulation;
		SolutionSet union;

		Operator mutationOperator;
		Operator crossoverOperator;
		Operator selectionOperator;

		Distance distance = new Distance();

		//Read the parameters
		populationSize = ((Integer) getInputParameter("populationSize")).intValue();
		maxEvaluations = ((Integer) getInputParameter("maxEvaluations")).intValue();
		indicators = (QualityIndicator) getInputParameter("indicators");

		//Initialize the variables
		population = new SolutionSet(populationSize);
		//population = generate_initpop(populationSize);
		
		evaluations = 0;

		requiredEvaluations = 0;

		//Read the operators
		mutationOperator = operators_.get("mutation");
		crossoverOperator = operators_.get("crossover");
		selectionOperator = operators_.get("selection");

		// Create the initial solutionSet
		Solution newSolution;
		for (int i = 0; i < populationSize; i++) {
			newSolution = new Solution(problem_);
			problem_.evaluate(newSolution);
			problem_.evaluateConstraints(newSolution);
			evaluations++;
			population.add(newSolution);
		} //for       
		
		//System.exit(1);
		
		
		//int numberOfObjectives=new Solution(problem_).numberOfObjectives();
		//ObjectiveFitness fitNessAssignment=new ObjectiveFitness(numberOfObjectives);
		
		//BetterCountFitness better=new BetterCountFitness();
		HowMuchBetterFitness better=new HowMuchBetterFitness();
		//CombineFitnessAssignment combine=new CombineFitnessAssignment(maxEvaluations/populationSize);
		
		ModifiedCombineFitness combine=new ModifiedCombineFitness(maxEvaluations/populationSize);
		GridMaking gridMaking=new GridMaking();
		
		int GenerationNo=0;
		int maxGenerationNo=maxEvaluations/populationSize;
		double start=1;
		double end=10;
		
		// Generations 
		int CombinedUsedTimes=0;
		while (evaluations < maxEvaluations) {
			GenerationNo++;
			// Create the offSpring solutionSet      
			offspringPopulation = new SolutionSet(populationSize);
			Solution[] parents = new Solution[2];
			for (int i = 0; i < (populationSize / 2); i++) {
				if (evaluations < maxEvaluations) {
					//obtain parents
					parents[0] = (Solution) selectionOperator.execute(population);
					parents[1] = (Solution) selectionOperator.execute(population);
					Solution[] offSpring = (Solution[]) crossoverOperator.execute(parents);
					mutationOperator.execute(offSpring[0]);
					mutationOperator.execute(offSpring[1]);
					problem_.evaluate(offSpring[0]);
					problem_.evaluateConstraints(offSpring[0]);
					problem_.evaluate(offSpring[1]);
					problem_.evaluateConstraints(offSpring[1]);
					
					//added this two line
					offSpring[0].setFitness(0);
					offSpring[1].setFitness(0);
					
					offspringPopulation.add(offSpring[0]);
					offspringPopulation.add(offSpring[1]);
					evaluations += 2;
				} // if                            
			} // for

			// Create the solutionSet union of solutionSet and offSpring
			union = ((SolutionSet) population).union(offspringPopulation);
			
			Ranking ranking = new Ranking(union);

			int remain = populationSize;
			int index = 0;
			SolutionSet front = null;
			population.clear();

			front = ranking.getSubfront(index);

			 //System.out.println("Front: "+front.size());

			while ((remain > 0) && (remain >= front.size())) {

				//System.out.println("pareto");
				
				distance.crowdingDistanceAssignment(front,problem_.getNumberOfObjectives());

				 
				 
				for (int k = 0; k < front.size(); k++) {
					population.add(front.get(k));
				} // for

				// Decrement remain
				remain = remain - front.size();

				// Obtain the next front
				index++;
				if (remain > 0) {
					front = ranking.getSubfront(index);
				}
			}

			// Remain is less than front(index).size, insert only the best one
			if (remain > 0) { // front contains individuals to insert
				
				//System.out.println("fitness");
				
				
				/*
				int modulusValue=(int)(start+((start-end)/(1-(double)maxGenerationNo))*((double)GenerationNo-1));
				
				
				
				//System.out.println(modulusValue);
				
				
				better.Initialization(front);
				
			//	if(CombinedUsedTimes%modulusValue==0){better.Initialization(front);				}
				
				CombinedUsedTimes++;
				
				
				
				better.FitnessAssignment(front);
				distance.crowdingDistanceAssignment(front,problem_.getNumberOfObjectives());
				combine.Combine(front,GenerationNo);
				
				//front.sort(new CrowdingComparator());
				*/
				
				gridMaking.gridMaking(front,10);
				
				takePopulation(population, front, remain);
				
				
				
				
				remain = 0;
			}
			
			/*
			if ((indicators != null) &&
					(requiredEvaluations == 0)) {
				double HV = indicators.getHypervolume(population);
				if (HV >= (0.98 * indicators.getTrueParetoFrontHypervolume())) {
					requiredEvaluations = evaluations;
				} // if
			} // if
			*/
			
			
		} // while

		// Return as output parameter the required evaluations
		setOutputParameter("evaluations", requiredEvaluations);

		// Return the first non-dominated front
		Ranking ranking = new Ranking(population);
		return ranking.getSubfront(0);

	}


	private SolutionSet generate_initpop(int populationSize) {
		// TODO Auto-generated method stub
		
		return null;
	} 
}