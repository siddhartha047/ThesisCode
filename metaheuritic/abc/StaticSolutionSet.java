package jmetal.metaheuritic.abc;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;



public class StaticSolutionSet {
	public static SolutionSet MakeSolutionSet(SolutionSet solutionSet){
		Solution s1=new Solution(5); 
		Solution s2=new Solution(5);
		Solution s3=new Solution(5);
		Solution s4=new Solution(5);
		
		double []objectives1=s1.get_objectives();
		double []objectives2=s2.get_objectives();
		double []objectives3=s3.get_objectives();
		double []objectives4=s4.get_objectives();
		
		
		
		objectives1[0]=1; objectives1[1]=2; objectives1[2]=3; objectives1[3]=4; objectives1[4]=5;
		objectives2[0]=1; objectives2[1]=1; objectives2[2]=4; objectives2[3]=6; objectives2[4]=9;
		objectives3[0]=9; objectives3[1]=8; objectives3[2]=7; objectives3[3]=1; objectives3[4]=5;
		objectives4[0]=1; objectives4[1]=1; objectives4[2]=2; objectives4[3]=5; objectives4[4]=9;
		
		
		
		solutionSet.add(s1);
		solutionSet.add(s2);
		solutionSet.add(s3);
		//solutionSet.add(s4);
		
		return solutionSet;
	}

	public static SolutionSet MakeTwoObjectiveSolution(SolutionSet solutionSet){
		Solution s1=new Solution(2); 
		Solution s2=new Solution(2);
		Solution s3=new Solution(2);
		Solution s4=new Solution(2);
		Solution s5=new Solution(2);
		
		double []objectives1=s1.get_objectives();
		double []objectives2=s2.get_objectives();
		double []objectives3=s3.get_objectives();
		double []objectives4=s4.get_objectives();
		double []objectives5=s5.get_objectives();
		
		
		/*
		objectives1[0]=1; objectives1[1]=6;
		objectives2[0]=2; objectives2[1]=4; 
		objectives3[0]=3.5; objectives3[1]=2.5; 
		objectives4[0]=4; objectives4[1]=2;
		objectives5[0]=5; objectives5[1]=1;
		*/
		objectives1[0]=1; objectives1[1]=1;
		objectives2[0]=2.5; objectives2[1]=2; 
		objectives3[0]=3; objectives3[1]=3; 
		objectives4[0]=4.3; objectives4[1]=4;
		objectives5[0]=5.9; objectives5[1]=5;
		
		
		
		solutionSet.add(s1);
		solutionSet.add(s2);
		solutionSet.add(s3);
		solutionSet.add(s4);
		solutionSet.add(s5);
		
		return solutionSet;
	}

}
