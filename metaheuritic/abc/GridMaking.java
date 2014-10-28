package jmetal.metaheuritic.abc;

import java.util.Arrays;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;

public class GridMaking {
	double []min;
	double []max;
	double []delta;
	double gridNo;

	int  noOfObjectives;
	
	public void intializeMinMaxDelta(SolutionSet solutionSet ){
		noOfObjectives=solutionSet.get(0).numberOfObjectives();
		min=new double[noOfObjectives];
		max=new double[noOfObjectives];
		delta=new double[noOfObjectives];
		
		Arrays.fill(min, Double.MAX_VALUE);
		Arrays.fill(max, Double.MIN_VALUE);
		
		
		for(int i=0;i<solutionSet.size();i++){
			Solution solution=solutionSet.get(i);
			
			double []objective=solution.get_objectives();
			
			for(int j=0;j<objective.length;j++){
				if(objective[j]<min[j])min[j]=objective[j];
				if(objective[j]>max[j])max[j]=objective[j];
			}
		}
		
		for(int i=0;i<delta.length;i++){
			
			double dummy=(max[i]-min[i])/gridNo;
			
			dummy=dummy/2;
			
			max[i]=max[i]+dummy;
			min[i]=min[i]-dummy;
			
			delta[i]=(max[i]-min[i])/gridNo;
		}
		
	}
	
	public int GetRank(double objValue,int objectiveNo){
		
		int incValue=1;
		int value=0;
		double cmpValue=min[objectiveNo]+delta[objectiveNo];
		
		while(!(objValue<cmpValue)){
			value+=incValue;
			cmpValue+=delta[objectiveNo];
		}
		
		return value;
		
	}
	
	public void assignFitness(SolutionSet solutionSet){
		
		double []gridFitness=new double[(int)gridNo];
		double []gridReduction=new double[(int)gridNo];
		
		
		for(int i=0;i<gridFitness.length;i++){
			gridFitness[i]=1-i/(gridNo);
			
			//System.out.println(gridFitness[i]);
			//gridFitness[i]=Math.pow(Math.E,-i);
			gridReduction[i]=(1-i/(gridNo))/2;
		}
		
		
		for(int i=0;i<solutionSet.size();i++){
			Solution solution=solutionSet.get(i);
			
			double []objective=solution.get_objectives();
			
			double []count=new double[(int)gridNo];
			Arrays.fill(count, 0);
			
			for(int j=0;j<objective.length;j++){
				double value=objective[j];
				int index=GetRank(value,j);
				//System.out.println(index+"->"+max[j]+"->"+value);
				
				count[index]=count[index]+1;
				
			}
			
			
			
			double fitness=0;
			for(int k=0;k<count.length;k++){
				
				//System.out.println(count[k]);
				
				fitness+=count[k]*(gridFitness[k]-gridReduction[k]);
			}
			
			solution.setFitness(fitness);
		}
		
		
	}
	
	public void gridMaking(SolutionSet solutionSet,double noOfpart){
		gridNo=noOfpart;
		intializeMinMaxDelta(solutionSet);
		assignFitness(solutionSet);
		
	}
}
