package jmetal.metaheuritic.abc;
import java.util.Arrays;
import java.util.Collection;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.util.comparators.ObjectiveComparator;

public class ObjectiveFitness {
	public  int numberOfjectives;
	public  double []mean;
	public  double []min;
	public  double []max;
	public  double []variance;
	public  double []aValue;  
	public double []customMean;
	
	public ObjectiveFitness(int numberOfObjective){
		numberOfjectives=numberOfObjective;
		//System.out.println(CalculateA(0,.99,4));
	}
	
	//x -a c-mean
	public double SigMoid(double x,double a,double c){
		double value=1+Math.pow(Math.E, -a*(x-c));
		return 1/value;
	}
	
	public double CalculateA(double x,double p,double c){
		double value=Math.log(p)-Math.log(1-p);
		return value/(x-c);
	}
	
	public void intializeFuzzyAndBetterCount(SolutionSet solutionSet){
		for(int i=0;i<solutionSet.size();i++){
			Solution solution=solutionSet.get(i);
			double []fuzz=solution.get_fuzzy_objectives();
			Arrays.fill(fuzz, 1);
		}
	}
	
	public void FuzzyFication(SolutionSet solutionSet)
	{
		initializeTheValues(solutionSet);
		intializeFuzzyAndBetterCount(solutionSet);
		
		
		for(int i=0;i<solutionSet.size();i++){
			Solution solution=solutionSet.get(i);
			
			for(int j=i+1;j<solutionSet.size();j++){
				if(i==j)continue;
				
				Solution comPareSolution=solutionSet.get(j);
				
				CompareSolution(solution,comPareSolution);
				
			}
			
			//System.out.println();
			
		}
		
		fitNessAssignMent(solutionSet);
		
		
		
		//solutionSet.printSolutionSet();
		/*
		for(int i=0;i<solutionSet.size();i++){
			Solution solution=solutionSet.get(i);
			printDoubleArray(solution.getBetterCount());
			printDoubleArray(solution.get_fuzzy_objectives());
		}
		*/
		
		
		
		//System.out.println(CalculateFuzzyValue(2,0));
		
		/*
		printDoubleArray(mean);
		printDoubleArray(variance);
		printDoubleArray(min);
		printDoubleArray(max);
		printDoubleArray(aValue);
		*/
		
	}
	
	public void fitNessAssignMent(SolutionSet solutionSet){
		int solutionSize=solutionSet.size();
		
		for(int i=0;i<solutionSet.size();i++){
			
			Solution solution=solutionSet.get(i);
			
			Computefitness(solution,solutionSize);
			
		}
	}
	
	public void Computefitness(Solution solution,int N){
		double []betterCount=solution.getBetterCount();
		double []fuzzyObjetives=solution.get_fuzzy_objectives();
		
		double sum=0;
		
		for(int i=0;i<numberOfjectives;i++){
			sum+=(betterCount[i]*fuzzyObjetives[i])/((N-1));
		}
		
		//System.out.println(sum/numberOfjectives);
		solution.setFitness(sum);
	}
	
	public void CompareSolution(Solution mainSolution,Solution compareSolution){
		double []mainObjectives=mainSolution.get_objectives();
		double []compareObjectives=compareSolution.get_objectives();
		
		double []mainFuzzyObjectives=mainSolution.get_fuzzy_objectives();
		double []compareFuzzyObjectives=compareSolution.get_fuzzy_objectives();
		
		double []mainBetterCount=mainSolution.getBetterCount();
		double []compareBetterCount=compareSolution.getBetterCount();
		
		
		
		for(int i=0;i<mainObjectives.length;i++){
			
			if(mainObjectives[i]==compareObjectives[i]){
				continue;
			}
			else if(mainObjectives[i]<compareObjectives[i]){
				mainBetterCount[i]+=1;
			}
			else{
				compareBetterCount[i]+=1;
			}
			
			double diff=(mainObjectives[i]-compareObjectives[i]);
			double fuzz=CalculateFuzzyValue(diff,i);
			mainFuzzyObjectives[i]*=fuzz;
			double fuzz2=CalculateFuzzyValue(-diff,i);
			compareFuzzyObjectives[i]*=fuzz2;
		}
		
		
	}
	
	public double CalculateFuzzyValue(double diff,int index){
		return SigMoid(diff, aValue[index], mean[index]);
	}
	
	public void initializeTheValues(SolutionSet solutionSet){
		mean=new double[numberOfjectives];
		min=new double[numberOfjectives];
		max=new double[numberOfjectives];
		variance=new double[numberOfjectives];
		aValue=new double[numberOfjectives];
		customMean=new double[numberOfjectives];
		
		
		
		for(int i=0;i<numberOfjectives;i++){
			solutionSet.sort(new ObjectiveComparator(i));
			
			min[i]=solutionSet.get(0).getObjective(i);
			max[i]=solutionSet.get(solutionSet.size()-1).getObjective(i);
			
			calculateMeanVariance(solutionSet,i);
			
		}
		
		calculateAValue();
	}
	
	public void calculateAValue(){
		for(int i=0;i<numberOfjectives;i++){
			
			double min=mean[i]-variance[i];
			double max=mean[i]+variance[i];
			
			customMean[i]=(min+max)/2;
			
			double point=min-max;
			double value=.99;
			
			aValue[i]=CalculateA(point, value, customMean[i]);
		}
	}
	
	public void printDoubleArray(double []array){
		for(int i=0;i<array.length;i++){
			System.out.printf("%.2f\t",array[i]);
		}
		System.out.println();
	}
	public void calculateMeanVariance(SolutionSet solutionSet,int index){
		double sum=0;
		double N=solutionSet.size();
		for(int i=0;i<N;i++){
			Solution solution=solutionSet.get(i);
			double value=solution.getObjective(index);
			
			sum+=value;
		}
		
		double mn=sum/N;
		
		mean[index]=mn;
		
	//	System.out.print(index+" mean "+mn);
	
		double temp=0;
		for(int i=0;i<N;i++){
			Solution solution=solutionSet.get(i);
			double value=solution.getObjective(index);
			
			temp+=(mn-value)*(mn-value);
		}
		
		double vari=Math.sqrt(temp/N);
		
		this.variance[index]=vari;
		
		//System.out.println("\tvariance "+vari);
		
	}
	
	
	
	
}
