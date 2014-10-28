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

public class abc extends Algorithm {

	public abc(Problem problem) {
		super (problem) ;
	} 
              

    public void takePopulation(SolutionSet population,SolutionSet union,int populationSize){
    	
    	
            union.sort(new InvertFitnessComparator());    	
            for(int i=0;i<populationSize;i++){
            	
            	//System.out.println(union.get(i).getFitness()+"\t");
                population.add(union.get(i));
            }
            
            //System.exit(0);
            
            
    }
    
    public void takeHalf(SolutionSet union,int left,int right,SolutionSet population,int populationSize,SolutionSet NotTakenSet){
    	if(population.size()==populationSize){
    		System.out.println("Equal Population");
    		return;
    	}
    	else if(left>right){
    		System.out.println("Left greater than right");
    		return;
    	}
    	else if(left==right){
    		System.out.println("Left right equal");
    		population.add(union.get(right));
    		return;
    	}
    	else if(left==right-1){
    		System.out.println("Take By crowding");
    		
    		Solution solution1=union.get(left);
    		Solution solution2=union.get(right);
    		
    		if(solution1.getCrowdingDistance()>solution2.getCrowdingDistance()){
    			population.add(solution1);
    			NotTakenSet.add(solution2);
    		}
    		else if(solution1.getCrowdingDistance()<solution2.getCrowdingDistance()){
    			population.add(solution2);
    			NotTakenSet.add(solution1);
    		}
    		else{
    			System.out.println("TakeBy fitness");
    			if(solution1.getFitness()>solution2.getFitness()){
    				population.add(solution1);
    				NotTakenSet.add(solution2);
    			}
    			else{
    				population.add(solution2);
    				NotTakenSet.add(solution1);
    			}
    		}
    		return;
    	}
    	    	
    	int middle=(left+right+1)/2;
    	
    	takeHalf(union,left,middle,population,populationSize,NotTakenSet);
    	takeHalf(union,middle+1,right,population,populationSize,NotTakenSet);
    	
    }
    
    public void takeDiversePopulation(SolutionSet population,SolutionSet union,int populationSize){
    	
    	
    	
    	union.sort(new InvertFitnessComparator());
    	int NeedTotal=population.size()+populationSize;
    	
    	while(population.size()!=NeedTotal){
    
    		if(union.size()==0){
    			
    			System.out.println("SOmething wrong");
    			System.exit(0);
    		}
    		SolutionSet NotTaken=new SolutionSet(NeedTotal);
    		takeHalf(union, 0, union.size()-1, population,NeedTotal,NotTaken);
    		
    		union.clear();
    		System.out.println(NotTaken.size());
    		union=NotTaken;
    		
    		
    		
    	}
    	
    	
    	
    }
    
    
    
    public void takeDiversePopulationBySequence(SolutionSet population,SolutionSet union,int populationSize){
    	
    	union.sort(new InvertFitnessComparator());
    	int NeedTotal=population.size()+populationSize;
    	
    	while(population.size()!=NeedTotal){
        		
    		SolutionSet NotTaken=new SolutionSet(NeedTotal);
    		
    		
    		for(int i=1;i<union.size();i=i+2){
    			
    			if(population.size()==NeedTotal)break;
    			
    			Solution solution1=union.get(i-1);
    			Solution solution2=union.get(i);
    			
    			if(solution1.getCrowdingDistance()>solution2.getCrowdingDistance()){
        			population.add(solution1);
        			NotTaken.add(solution2);
        		}
        		else if(solution1.getCrowdingDistance()<solution2.getCrowdingDistance()){
        			population.add(solution2);
        			NotTaken.add(solution1);
        		}
        		else{
        			System.out.println("TakeBy fitness");
        			if(solution1.getFitness()>solution2.getFitness()){
        				population.add(solution1);
        				NotTaken.add(solution2);
        			}
        			else{
        				population.add(solution2);
        				NotTaken.add(solution1);
        			}
        		}
    		}
    		
    		
    		union.clear();
    		System.out.println(NotTaken.size());
    		union=NotTaken;
    		
    		
    		
    	}
    
    }
    
    
    public void TakeNumberOfObjectives(SolutionSet union,SolutionSet population,int left,int right,int maxTake,int NeedTotal,SolutionSet NotTaken){
    	if(right>union.size()){
    		right=union.size();
    	}
    	
    	SolutionSet firstLevelSolution=new SolutionSet(right-left);
    	
    	for(int i=left;i<right;i++){
    		firstLevelSolution.add(union.get(i));
    	}
    	
    	
    	
    	
    	firstLevelSolution.sort(new CrowDingDistanceComparator());
/*
    	for(int i=0;i<firstLevelSolution.size();i++){
    		System.out.println(firstLevelSolution.get(i).getCrowdingDistance());
    	}
    
*/
    	 //System.out.println(firstLevelSolution.size()+"\t max "+maxTake);
    	
    	for(int i=0;i<firstLevelSolution.size();i++){
    		if(i==maxTake){
    			for(int j=i;j<firstLevelSolution.size();j++){
    				
    				NotTaken.add(firstLevelSolution.get(j));
    			}
    			return;
    		}
    		if(population.size()==NeedTotal){
    			//System.out.println("Equal");
    			return;
    		}
    		population.add(firstLevelSolution.get(i));    		
    	}
    }
    
    
    public void takeDiversePopulationByFitnessDivision(SolutionSet population,SolutionSet union,int populationSize){
    	
    	//print will be lower to higher
    	union.sort(new InvertFitnessComparator());
    	
    	/*
    	for(int i=0;i<union.size();i++){
    		System.out.println(union.get(i).getFitness());
    	}
    	
    	System.exit(0);
    	*/
    	
    	int NeedTotal=population.size()+populationSize;
    	
    	ModifiedCombineFitness.setMeanVariance(union);
    	double meanFitness=ModifiedCombineFitness.fit_mean;
    	double sd=ModifiedCombineFitness.fit_var;
    	
    	int numberOfObjective=union.get(0).numberOfObjectives();
    	
    	int iteration=0;
    	int Take=(numberOfObjective+1)/2;
    	
    	while(population.size()!=NeedTotal){
    		
    		SolutionSet NotTaken=new SolutionSet(union.size());
    		
    		int left=0;
    		int right=2*numberOfObjective;    		
    		
    		double limit= 0;
    		
    		limit=meanFitness+(iteration)*sd;
    		
    		
    		if(iteration>0){
    			Take=(numberOfObjective+1)*1/2;
    		}
    		
    		/*
    		if(iteration==0){
    			limit=meanFitness;
    		}
    		else if(iteration==1 ){
    			limit=meanFitness-2*sd;
    		}
    		else if(iteration == 2){
    			limit=meanFitness-2*sd;
    		}
    		else 
    		{
    			limit=0;
    		}
    		*/
    		
    		
    		for(left=0;left<union.size()&&union.get(left).getFitness()<limit;left=right,right=left+2*numberOfObjective){
    			TakeNumberOfObjectives(union,population,left,right,Take,NeedTotal,NotTaken);
    			if(population.size()==NeedTotal)return;
    		}
    		
    		
    		
    		
    		//System.out.println("Lackings "+(NeedTotal-population.size()));
    		
    		for(int i=left;i<union.size();i++){
    			NotTaken.add(union.get(i));
    		}
    		
    		union.clear();
    		union=NotTaken;
    		iteration++;
    		
    	}
    	
    	/*
    	  for( ;left<union.size()&&union.get(left).getFitness()>(meanFitness-2*sd);left=right,right=left+2*numberOfObjective){    			
    			TakeNumberOfObjectives(union,population,left,right,numberOfObjective,NeedTotal,NotTaken);
    			if(population.size()==NeedTotal)return;;
    		}
    		
    		System.out.println("Var Lackings "+(NeedTotal-population.size()));
    		
    		for( ;left<union.size();left=right,right=left+2*numberOfObjective){
    			TakeNumberOfObjectives(union,population,left,right,numberOfObjective,NeedTotal,NotTaken);
    			if(population.size()==NeedTotal)return;;
    		}
    		
    		System.out.println("final Lackings "+(NeedTotal-population.size()));
    		//System.out.println(population.size()+"\t"+NeedTotal+"\t"+NotTaken.size());
    		
    		union.clear();
    		union=NotTaken;*/
    		
    }
 
    
    public void takeDiversePopulationByDimension(SolutionSet population,SolutionSet union,int populationSize){
    	union.sort(new InvertFitnessComparator());
    	int NeedTotal=population.size()+populationSize;
    	
    	int unionSize=union.size();
    	int popsize=population.size();
    	
    	int numberOfObjective=union.get(0).numberOfObjectives();
    	
    	while(population.size()!=NeedTotal){
    		
    		SolutionSet NotTaken=new SolutionSet(NeedTotal);
    		
    		int left=0;
    		int right=2*numberOfObjective;
    		
    	//	System.out.println(unionSize+"\t"+popsize);
    		
    		for(left=0;left<union.size();left=right,right=left+2*numberOfObjective){
    			
    			
    			TakeNumberOfObjectives(union,population,left,right,numberOfObjective,NeedTotal,NotTaken);
    			if(population.size()==NeedTotal)return;;
    		}
    		//System.out.println(population.size()+"\t"+NeedTotal+"\t"+NotTaken.size());
    		
    		union.clear();
    		union=NotTaken;
    		
    	}
    		
    }
    
    
    public void takePopulationLowToHighFitness(SolutionSet population,SolutionSet union,int populationSize){
        union.sort(new Low_HighFitnessComparator());    	
        for(int i=0;i<populationSize;i++){
            population.add(union.get(i));
        }
    }
    
    public void Experiment(){
    	
    	int populationSize = ((Integer) getInputParameter("populationSize")).intValue();
    	int maxEvaluations = ((Integer) getInputParameter("maxEvaluations")).intValue();
    	  
    	
    	
    	//BetterCountFitness fitNess=new BetterCountFitness();
    	HowMuchBetterFitness fitNess=new HowMuchBetterFitness();
    	Distance distance = new Distance();
    	ModifiedCombineFitness combine=new ModifiedCombineFitness(maxEvaluations/populationSize);
    	
    	SolutionSet solutionSet=new SolutionSet(6);
    	StaticSolutionSet.MakeTwoObjectiveSolution(solutionSet);
    	//StaticSolutionSet.MakeSolutionSet(solutionSet);
    	
    	solutionSet.printSolutionSet();
    	fitNess.Initialization(solutionSet);    	
    	fitNess.FitnessAssignment(solutionSet);
    	
    	solutionSet.printSolutionSet();
    	
		distance.crowdingDistanceAssignment(solutionSet,2);
		
		solutionSet.printSolutionSet();
		
		
		combine.Combine(solutionSet,0);		
		solutionSet.printSolutionSet();
		
		
    	
    }

	public SolutionSet execute() throws JMException, ClassNotFoundException {
		
		
	//	Experiment();
	//	System.exit(0);
		
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
				
				
				
				int modulusValue=(int)(start+((start-end)/(1-(double)maxGenerationNo))*((double)GenerationNo-1));
				
				
				
				//System.out.println(modulusValue);
				
				
				better.Initialization(front);
				
			//	if(CombinedUsedTimes%modulusValue==0){better.Initialization(front);				}
				
				CombinedUsedTimes++;
				
				
				
				better.FitnessAssignment(front);
				distance.crowdingDistanceAssignment(front,problem_.getNumberOfObjectives());
				//combine.Combine(front,GenerationNo);
			
				
				//takePopulation(population, front, remain);
				
				//takeDiversePopulation(population, front, remain);
				//takeDiversePopulationBySequence(population,front,remain);
				//takeDiversePopulationByDimension(population,front,remain);
				takeDiversePopulationByFitnessDivision(population,front,remain);
				
				if(population.size()!=populationSize){
					System.out.println("Failure");
					System.exit(0);
					
				}
				
				System.out.println(GenerationNo);
				
				String path="C:\\Users\\secret\\Desktop\\sidCleanJmetal\\jmetal\\abcGenerations\\InspectionFitness\\generation"+GenerationNo;			
				population.printObjectivesToFile(path,true );
				
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