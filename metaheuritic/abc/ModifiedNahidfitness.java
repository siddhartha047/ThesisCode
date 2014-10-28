package jmetal.metaheuritic.abc;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.util.comparators.ObjectiveComparator;

public class ModifiedNahidfitness {
	private double phimatrix[][];
        double fmins[];
        double fmaxs[];
         
	public double membershipfunction(double crispval,double C,double Sigma){
		double c=C;
		double phi=Sigma;
                
              //  System.out.println("c "+C+" sigma: "+Sigma);
          //double c=-1;      
          //double phi=.5;
		if(crispval<c)
			return 1;
		else
			return Math.exp(-.5*Math.pow((crispval-c)/(phi), 2));
	}
	
	public double modifiedmembershipfunction(double crispval){
        double c=-1;      
        double phi=.5;
        //System.out.println(crispval+" fuz: "+Math.exp(-.5*Math.pow((crispval-c)/(phi), 2)));
		if(crispval<c)
			return 1;
		else
			return Math.exp(-.5*Math.pow((crispval-c)/(phi), 2));
	}
	
	public void fuzzyfication(SolutionSet ss){
		int numberofsolutions = ss.size();
		int numberofobjectives=ss.get(0).numberOfObjectives();
		phimatrix = new double[numberofsolutions][numberofsolutions];
		fmins=new double[numberofobjectives];
        fmaxs=new double[numberofobjectives];
        setfmaxfmins(ss);

        // printing phimatrix
		//System.out.println("d");
	//	 for (int i = 0; i < fmaxs.length; i++) {
		//		System.out.println(fmaxs[i]);
	//	 }
     
        
	        for (int i = 0; i < numberofsolutions; i++){
	        	for (int j = 0; j < numberofsolutions; j++) {
	        		if (i>=j) 
		        			continue;
		        	double phiprodu=1,phiprodv=1;
		        	for (int k = 0; k < numberofobjectives; k++) {
		        		Solution u = ss.get(i);
		                Solution v = ss.get(j);
		                double xu = u.getObjective(k);
		                double xv = v.getObjective(k);
		                double diffuv, diffvu, diffnorm;
		                
		                diffuv=  xu - xv;
		                diffnorm = diffuv/fmaxs[k];
		                if(Double.isNaN(diffnorm))continue;
		                //System.out.print(diffuv+" "+fmaxs[k]);
		                //System.out.print(diffnorm+" ");
		                 phiprodu *= modifiedmembershipfunction(diffnorm);
		                 phiprodv *= modifiedmembershipfunction(-diffnorm);
	        		}
	        		//System.out.println();
	        		//System.out.println(phiprodu+ "->"+phiprodv);
	        		phimatrix[i][j] = phiprodu;
	        		phimatrix[j][i] = phiprodv;
	        	}
	     	}
	   /*  
        for (int i = 0; i < numberofsolutions; i++){
        	for (int j = 0; j < numberofsolutions; j++) 
        		System.out.printf("%.4f\t",phimatrix[i][j]);
        	System.out.println();
        }
        */
	}
        
	public void FitnessAssignment(SolutionSet solutionSet){
		fuzzyfication(solutionSet);
		int numberofsolution=solutionSet.size();
		for (int i = 0; i < numberofsolution; i++) {
			double sum=0;
			for (int j = 0; j < numberofsolution; j++) {
				if(i==j) continue;
				sum+=phimatrix[i][j]/(phimatrix[i][j]+phimatrix[j][i]);
			}
			solutionSet.get(i).setFitness(sum/(double)(numberofsolution-1));
		}
	}
	
    void setfmaxfmins(SolutionSet ss){
    	int numberofsolutions = ss.size();
		int numberofobjectives=ss.get(0).numberOfObjectives();
    	for (int i = 0; i < fmaxs.length; i++) {
			fmaxs[i] = -99999;
		}
		
		for (int k = 0; k < numberofobjectives; k++) {
	        for (int i = 0; i < numberofsolutions; i++){
	        	for (int j = 0; j < numberofsolutions; j++) {
	        		if(i>=j) continue;
					double usum = 0;
		        	double vsum = 0;

	                Solution u = ss.get(i);
	                Solution v = ss.get(j);
	                double xu = u.getObjective(k);
	                double xv = v.getObjective(k);
	                double diffuv, diffvu, diffabs;
	                
	                diffuv=  xu - xv;
	                diffvu = xv - xu;
	                diffabs = Math.abs(diffuv);
	               
	                if(fmaxs[k]<diffabs) {
	                	fmaxs[k] = diffabs;
	                }
	                
	                   // double c=(fmins[k]-fmaxs[k]);
	                   // double sigma=(fmaxs[k]-fmins[k])/2;
	                    
		                //double normalizedValue=modifiedmembershipfunction(diff,c,sigma);
		                
		               //   System.out.println("u: "+xu+" v:"+xv+" diff:"+diff+" mem: "+normalizedValue);
		                
		               // if(flag)
		                	//usum+=normalizedValue;
		               // else
		               // 	//vsum+=normalizedValue;
		                
            	
	        		//phimatrix[i][j]=usum;
	        		//phimatrix[j][i]=vsum;
	        	}
        	}
        
        }
    }
}
