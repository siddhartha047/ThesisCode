package jmetal.metaheuritic.abc;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;

public class MembershipEvaluation {
	public static double []ObjMean;
	public static double []Objvar;
	public static double []A;
	public static double []APos;
	public static double []ObjMin;
	public static double []ObjMax;

	
	double m_n;
	double []m_oldM;
	double []m_newM;
	double []m_oldS;
	double []m_newS;
	double totalSolutionNo;
	
	
	
	
	public void Initialization(SolutionSet solutionSet){
		int numberOfOjectives=solutionSet.get(0).numberOfObjectives();
		
		totalSolutionNo=solutionSet.size();
		
		ObjMean=new double[numberOfOjectives];
		Objvar=new double[numberOfOjectives];
		A=new double[numberOfOjectives];
		APos=new double[numberOfOjectives];
		ObjMin=new double[numberOfOjectives];
		ObjMax=new double [numberOfOjectives];
		
		for(int i=0;i<numberOfOjectives;i++){
			ObjMin[i]=Double.MAX_VALUE;
			ObjMax[i]=Double.MIN_VALUE;
		}
		
		m_n=0;
		m_oldM=new double[numberOfOjectives];;
		m_newM=new double[numberOfOjectives];;
		m_oldS=new double[numberOfOjectives];;
		m_newS=new double[numberOfOjectives];;
		
		
		
		for (int i = 0; i < solutionSet.size(); i++) {
			Solution solution = solutionSet.get(i);

			for (int j = i + 1; j < solutionSet.size(); j++) {
				if (i == j)continue;
				
				Solution comPareSolution = solutionSet.get(j);
				pushValue(solution, comPareSolution);

			}
		}
		
		
		
		ObjMean=m_newM;
		
		
		for(int i=0;i<numberOfOjectives;i++){
			Objvar[i]=Math.sqrt(m_newS[i]/(m_n - 1));
			
			//A[i]=SigMoid.CalculateA(ObjMean[i]-2*Objvar[i],.99, ObjMean[i]);
			
			
			//System.out.println(A[i]+"\t"+ObjMean[i]);
			
			
			A[i]=SigMoid.CalculateA(-ObjMean[i]+2*Objvar[i],.99,-ObjMean[i]);
			APos[i]=SigMoid.CalculateA(ObjMean[i]-2*Objvar[i],.99,ObjMean[i]);
			//APos[i]=SigMoid.CalculateA(ObjMin[i],.99,(ObjMax[i]+ObjMin[i])/2);
			
			
			
		}
		/*printArray(ObjMean);
		printArray(Objvar);
		printArray(ObjMin);
		printArray(ObjMax);*/
		
		/*
		printArray(APos);
		printArray(Objvar);
		printArray(ObjMean);*/
		//System.exit(0);
		
		//System.out.println("---------------------------------------");
		/*
		printArray(ObjMean);
		printArray(Objvar);
		printArray(pseduoMin);
		printArray(pseduoMax);
		*/
		
		//printArray(ObjMean);
	}
	
	public void printArray(double []value){
		for(int i=0;i<value.length;i++){
			System.out.printf("%.2f\t",value[i]);
		}
		
		System.out.println();
	}
	
	public void updateMinMax(double x,int i){
        if(x<ObjMin[i])ObjMin[i]=x;
        if(x>ObjMax[i])ObjMax[i]=x;
	}
	
	public void pushValue(Solution solution1,Solution solution2){
		
		double []x1=solution1.get_objectives();
		double []x2=solution2.get_objectives();
		
		double []x=new double[solution1.numberOfObjectives()];
		
		
		m_n++;

        // See Knuth TAOCP vol 2, 3rd edition, page 232
        if (m_n == 1)
        {
        	for(int i=0;i<x.length;i++){
        		x[i]=Math.abs(x1[i]-x2[i]);
        		
        		m_oldM[i] = m_newM[i] = x[i];
                m_oldS[i] = 0.0;
                
                updateMinMax(x1[i], i);
                updateMinMax(x2[i], i);
        	}
        }
        else
        {
        	for(int i=0;i<x.length;i++){
        		
        		x[i]=Math.abs(x1[i]-x2[i]);
        		
	            m_newM[i] = m_oldM[i] + (x[i] - m_oldM[i])/m_n;
	            m_newS[i] = m_oldS[i] + (x[i] - m_oldM[i])*(x[i] - m_newM[i]);
	
	            // set up for next iteration
	            m_oldM[i] = m_newM[i]; 
	            m_oldS[i] = m_newS[i];
	            
	            updateMinMax(x1[i], i);
                updateMinMax(x2[i], i);
        	}
        }
	}

	
}
