//  QualityIndicator.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package jmetal.qualityIndicator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.util.read_settings;

/**
 * QualityIndicator class
 */
public class QualityIndicator {
  SolutionSet trueParetoFront_ ;
  double      trueParetoFrontHypervolume_ ;
  Problem     problem_ ; 
  jmetal.qualityIndicator.util.MetricsUtil utilities_  ;
  
  /**
   * Constructor
   * @param paretoFrontFile
   */
  public QualityIndicator(Problem problem, String paretoFrontFile) {
    
	 // System.out.println("i think stuck start here");
	 problem_ = problem ;
     utilities_ = new jmetal.qualityIndicator.util.MetricsUtil() ;
     trueParetoFront_ = utilities_.readNonDominatedSolutionSet(paretoFrontFile);
    
    //System.out.println("i think start end here");
    /*
    String[] stringArray = paretoFrontFile.split("\\/");
    String fileName=read_settings.true_hypervolume_path+"\\"+stringArray[stringArray.length-1];
    System.out.println(fileName);
    
    File file=new File(fileName);
    
    if(file.exists()){
    	try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String s;
			try {
				if((s=br.readLine())!=null){
					trueParetoFrontHypervolume_=Double.valueOf(s);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.out.println("hypervolume file not found");
			e.printStackTrace();
			System.exit(0);
			
		}
    	
    }else{
    	
    	
    	
    	trueParetoFrontHypervolume_ = new Hypervolume().hypervolume(
                trueParetoFront_.writeObjectivesToMatrix(),   
                trueParetoFront_.writeObjectivesToMatrix(),
                problem_.getNumberOfObjectives());
    	
    	try {
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(String.valueOf(trueParetoFrontHypervolume_));
	        output.close();
		} catch (IOException e) {
			System.out.println("file creation failed");
			e.printStackTrace();
			System.exit(0);
		}
		
    	
    }
     */	
   
   
  } // Constructor 
  
  /**
   * Returns the hypervolume of solution set
   * @param solutionSet
   * @return The value of the hypervolume indicator
   */
  public double getHypervolume(SolutionSet solutionSet) {
    return new Hypervolume().hypervolume(solutionSet.writeObjectivesToMatrix(),
                                         trueParetoFront_.writeObjectivesToMatrix(),
                                         problem_.getNumberOfObjectives());
  } // getHypervolume

    
  /**
   * Returns the hypervolume of the true Pareto front
   * @return The hypervolume of the true Pareto front
   */
  public double getTrueParetoFrontHypervolume() {
    return trueParetoFrontHypervolume_ ;
  }
  
  /**
   * Returns the inverted generational distance of solution set
   * @param solutionSet
   * @return The value of the hypervolume indicator
   */
  public double getIGD(SolutionSet solutionSet) {
    return new InvertedGenerationalDistance().invertedGenerationalDistance(
                    solutionSet.writeObjectivesToMatrix(),
                    trueParetoFront_.writeObjectivesToMatrix(),
                    problem_.getNumberOfObjectives());
  } // getIGD
  
 /**
   * Returns the generational distance of solution set
   * @param solutionSet
   * @return The value of the hypervolume indicator
   */
  public double getGD(SolutionSet solutionSet) {
    return new GenerationalDistance().generationalDistance(
                    solutionSet.writeObjectivesToMatrix(),
                    trueParetoFront_.writeObjectivesToMatrix(),
                    problem_.getNumberOfObjectives());
  } // getGD
  
  /**
   * Returns the spread of solution set
   * @param solutionSet
   * @return The value of the hypervolume indicator
   */
  public double getSpread(SolutionSet solutionSet) {
    return new Spread().spread(solutionSet.writeObjectivesToMatrix(),
                               trueParetoFront_.writeObjectivesToMatrix(),
                               problem_.getNumberOfObjectives());
  } // getGD
  
    /**
   * Returns the epsilon indicator of solution set
   * @param solutionSet
   * @return The value of the hypervolume indicator
   */
  public double getEpsilon(SolutionSet solutionSet) {
    return new Epsilon().epsilon(solutionSet.writeObjectivesToMatrix(),
                                 trueParetoFront_.writeObjectivesToMatrix(),
                                 problem_.getNumberOfObjectives());
  } // getEpsilon
  
  public double getHypervolume_Ratio(SolutionSet solutionSet) {
	    double hv_approx_set= new Hypervolume().hypervolume(solutionSet.writeObjectivesToMatrix(),
	                                         trueParetoFront_.writeObjectivesToMatrix(),
	                                         problem_.getNumberOfObjectives());
	    double hv_pareto_set= getTrueParetoFrontHypervolume();
	    return hv_approx_set/hv_pareto_set;
  }
} // QualityIndicator
