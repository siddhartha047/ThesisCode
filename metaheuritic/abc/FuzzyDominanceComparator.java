package jmetal.metaheuritic.abc;

import java.util.Comparator;
import jmetal.core.Solution;

public class FuzzyDominanceComparator implements Comparator<Solution>
{
    @Override
    
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
		
		boolean nondominated=false;
		
		for (int i = 0; i < mainObjectives.length; i++) {

			double diff=mainObjectives[i]-compareObjectives[i];
			
			double MaxDiff=MembershipEvaluation.ObjMax[i]-MembershipEvaluation.ObjMin[i];
			
			if(Math.abs(diff)>(MaxDiff/2)){
				nondominated=true;
			}
			
			if(diff==0){
				equal++;
			}
			else if(diff<0){
				better++;				
			}
			else{
				worse++;
			}
			
			
			
			double mean=MembershipEvaluation.ObjMean[i]-2*MembershipEvaluation.Objvar[i];
			double var=MembershipEvaluation.Objvar[i];
			double min=MembershipEvaluation.ObjMin[i];
			double max=MembershipEvaluation.ObjMax[i];
			
			//if(diff<=0)value=1;
			//else value=SigMoid.Linear(min, max, diff);
			
			
			//value = SigMoid.LeftGaussian(min, (min+max)/2, diff);
			value = SigMoid.SigMoidValue(diff, MembershipEvaluation.APos[i],MembershipEvaluation.ObjMean[i]);
			dominateBy *= value;
			
			//if(-diff<=0)value=1;
			//else value=SigMoid.Linear(min, max, -diff);
			
			value = SigMoid.SigMoidValue(-diff, MembershipEvaluation.APos[i],MembershipEvaluation.ObjMean[i]);
			//value = SigMoid.LeftGaussian(min, (min+max)/2, -diff);
			dominatedBy *= value;
		}
		
		//if(nondominated)return 0;
		
		//System.out.println(dominateBy+"\t"+dominatedBy);
		
		
	//	dominateBy=(better/(better+worse))*dominateBy;
	//	dominatedBy=(worse/(better+worse))*dominatedBy;
		
		double dominatedByWR=(dominatedBy)/(dominateBy+dominatedBy);
		double dominateByWR=(dominateBy)/(dominateBy+dominatedBy);
		
		//System.out.println(dominateByWR+"\t"+dominatedByWR);
		
		//System.exit(0);
		
		//System.out.println(dominatedBy+"-"+dominateBy+"\t"+dominatedByWR+"-"+dominateByWR);
		
		//System.out.print((dominateByWR-dominatedByWR)+"\t");
		/*
		if(dominateByWR>dominatedByWR){
			return -1;
		}
		else if(dominateByWR<dominatedByWR){
			return 1;
		}
		else{
			return 0;
		} 
			
		*/
		 
		
		if((dominateByWR-dominatedByWR)>.80){
			return -1; 
		}
		else if((dominatedByWR-dominateByWR)>.80){
			return 1;
		} 
		else{
			return 0;
		} 
		
    }
    
}
