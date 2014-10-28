package jmetal.metaheuritic.abc;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;

public class ModifiedCombineFitness {
				
		double maxGeneration;
		double min=0;
		double max=0;
		double delta=0;
		double noOfactiveSolution=0;
		
		
		public static double fit_mean;
		public static double fit_var;
		public static double fit_A;
		
		
		public static double crowd_mean;
		public static double crowd_var;
		public static double crowd_A;
		
		public ModifiedCombineFitness(double maxmimumGeneration){
			maxGeneration=maxmimumGeneration;
		}
		
		public void setMinMaxDelta(SolutionSet solutionSet){
			 min=Double.MAX_VALUE;
			 max=Double.MIN_VALUE;
			
			noOfactiveSolution=0;
			
			for(int i=0;i<solutionSet.size();i++){
				Solution solution=solutionSet.get(i);
				
				double crowd=solution.getCrowdingDistance();
				if(Double.isInfinite(crowd)){
					continue;
				}
				
				if(crowd>max)max=crowd;
				if(crowd<min)min=crowd;
				noOfactiveSolution+=1;
			}
			
			double noOfsol=solutionSet.size();
			
			
			delta=(max-min)/noOfactiveSolution;
			
			//System.out.println(max+"\t"+min+"\t"+delta);
			
		}
		
		public double getRankValue(double crowd){
			double incValue=1/noOfactiveSolution;
			double value=incValue;
			double cmpValue=min+delta;
			
			while(cmpValue<crowd){
				value+=incValue;
				cmpValue+=delta;
			}
			
			
			
			return value;
		}
		
		public double getFuzzValue(double crowd){
			double sigma=crowd_var;
			double mean=crowd_mean;
			
			
			double value2=-((crowd-mean)*(crowd-mean))/(2*sigma*sigma);
			
			value2=Math.pow(Math.E,value2);			
			//System.out.println(mean+"\t"+sigma+"\t"+crowd+"\t"+value2);
			
			return value2;
		}
		
		public void printSolutionInfile(SolutionSet solutionSet,int generationNo){
			
		
			
			String path="C:\\Users\\secret\\Desktop\\sidCleanJmetal\\jmetal\\abcGenerations\\InspectionFitness\\generation"+generationNo;			
			solutionSet.printObjectivesToFile(path,true );
			
			
		}
		
		
		public void Combine(SolutionSet solutionSet,int generationNo){
			
			
			printSolutionInfile(solutionSet,generationNo);
			
			//String path="C:\\Users\\secret\\Desktop\\sidCleanJmetal\\jmetal\\abcGenerations\\InspectionFitness\\generation"+generationNo;
			
			//solutionSet.printObjectivesToFile(path);
			
			/*
			 
			 */
			//setMinMaxDelta(solutionSet);
			//setNormalDistribution(solutionSet);
			
			
			
			setMeanVariance(solutionSet);
			System.out.println(generationNo);
			
			double startx=0.0;
			double endx = 0.0;
			double x = startx + ((endx-startx)/(maxGeneration-1))*(generationNo-1);
			
			for(int i=0;i<solutionSet.size();i++){
				Solution solution=solutionSet.get(i);
				
				double fit=solution.getFitness();
				double crowd=solution.getCrowdingDistance();
							
				double fuzCrowd=1;
				
				if(Double.isInfinite(crowd)){
					fuzCrowd=1;
				}
				else{
					//fuzCrowd=getRankValue(crowd);
					fuzCrowd=getFuzzValue(crowd);
				}
				
				
				//System.out.println(fuzCrowd);
				
				//fuzCrowd=x+(1-x)*(fuzCrowd);
				
				
				double fuzFit=fit;
				fuzFit=SigMoid.SigMoidValue(fit, fit_A, fit_mean);
				
				//double comBineFitness=fuzFit*fuzCrowd;
				double comBineFitness=fuzFit;
				solution.setFitness(comBineFitness);
				
			}
			
			
			
		}		
		
		public static void setMeanVariance(SolutionSet solutionSet){

			double fitm_n=0;
			double fitm_oldM=0;
			double fitm_newM=0;
			double fitm_oldS=0;
			double fitm_newS=0;
			
			double crowdm_n=0;
			double crowdm_oldM=0;
			double crowdm_newM=0;
			double crowdm_oldS=0;
			double crowdm_newS=0;
			
			
			for(int i=0;i<solutionSet.size();i++){
				Solution solution=solutionSet.get(i);
				
				double fitx=solution.getFitness();
				///////////////
			
				fitm_n++;

	            // See Knuth TAOCP vol 2, 3rd edition, page 232
	            if (fitm_n == 1)
	            {
	                fitm_oldM = fitm_newM = fitx;
	                fitm_oldS = 0.0;
	            }
	            else
	            {
	            	fitm_newM = fitm_oldM + (fitx - fitm_oldM)/fitm_n;
	            	fitm_newS = fitm_oldS + (fitx - fitm_oldM)*(fitx - fitm_newM);
	    
	                // set up for next iteration
	            	fitm_oldM = fitm_newM; 
	            	fitm_oldS = fitm_newS;
	            }
				
				
				
				///////////////////////
	            double crowdx=solution.getCrowdingDistance();
				
				if(Double.isInfinite(crowdx))continue;
				
				crowdm_n++;

	            // See Knuth TAOCP vol 2, 3rd edition, page 232
	            if (crowdm_n == 1)
	            {
	            	crowdm_oldM = crowdm_newM = crowdx;
	            	crowdm_oldS = 0.0;
	            }
	            else
	            {
	            	crowdm_newM = crowdm_oldM + (crowdx - crowdm_oldM)/crowdm_n;
	            	crowdm_newS = crowdm_oldS + (crowdx - crowdm_oldM)*(crowdx - crowdm_newM);
	    
	                // set up for next iteration
	            	crowdm_oldM = crowdm_newM; 
	            	crowdm_oldS = crowdm_newS;
	            }
				
			}
			
			
			//System.out.println(fit[0]+"\t"+fit[1]);
			//System.out.println(crowd[0]+"\t"+crowd[1]);
			
			fit_mean=fitm_newM; 
			fit_var=Math.sqrt(fitm_newS/(fitm_n - 1));
			
			crowd_mean=crowdm_newM;
			crowd_var=Math.sqrt(crowdm_newS/(crowdm_n - 1));
			
			
			
			
			//if(fit_var==0)
			
			fit_A=SigMoid.CalculateA(fit_mean+2*fit_var,.99,fit_mean);
			crowd_A=SigMoid.CalculateA(crowd_mean+2*crowd_var,.99,crowd_mean);
			
		//	System.out.println(fit_mean+"\t"+fit_var+"\t"+fit_A);
		//	System.out.println(crowd_mean+"\t"+crowd_var+"\t"+crowd_A);
			
		}

	
}
