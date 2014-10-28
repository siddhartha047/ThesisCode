package jmetal.metaheuritic.abc;

import java.util.Comparator;
import java.util.ArrayList;
import jmetal.core.*;

public class InvertGridListSortComparator implements Comparator<ArrayList<Solution>>
{
    @Override
    public int compare(ArrayList<Solution>l1,ArrayList<Solution>l2)
    {
    	double fitness1=0;
    	double fitness2=0;
    	
    	if(l1.isEmpty()){
    		fitness1=0;
    	}
    	else{
    		fitness1=l1.get(0).getFitness();
    	}
    	
    	if(l2.isEmpty()){
    		fitness2=0;
    	}
    	else{
    		fitness2=l2.get(0).getFitness();
    	}
    	
    	
        if(fitness1<fitness2){
        	return 1;
        }
        if(fitness1>fitness2){
        	return -1;
        }
    	
        return 0;
    }
    
}