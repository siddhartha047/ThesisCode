package jmetal.qualityIndicator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import jmetal.util.read_settings;

class runstat{
	String pichome="C:\\Users\\secret\\Desktop\\Thesis Updates\\PICEAgMATLAB\\PICEAg MATLAB\\subfunctions\\gen\\";
	//String pichome="C:\\Users\\secret\\Desktop\\sidCleanJmetal\\jmetal\\abcGenerations\\0.5\\";
	String truefrontfile;
	String funfilename="pwfg";
	//String funfilename="sidwfg";
	
	
	
	public int dim = 7;
	public int prob = 2;
	
	
	double [][] solutionFront;
	double [][] trueFront;
	double truepf_hvval=1;
	
	public runstat(boolean useD,int di,int pro){
		
		dim=di;
		prob=pro;
		
		funfilename += (new Integer(prob).toString()+'_'+new Integer(dim).toString())+".pf";
		read_settings rd = new read_settings();
		
		if(useD){
			truefrontfile = read_settings.pf_path+"\\wfg"+prob+"_"+dim+"D.pf";
			
			
		}
		else{
			truefrontfile = read_settings.pf_path+"\\wfg"+prob+"_"+dim+".pf";
			//truefrontfile = "C:\\Users\\secret\\Desktop\\sidCleanJmetal\\jmetal\\pf\\RefMx300\\wfg"+prob+"_"+dim+".pf";
		}
		
		System.out.println(funfilename);
		System.out.println(truefrontfile);
		
		jmetal.qualityIndicator.util.MetricsUtil utilities_=  new jmetal.qualityIndicator.util.MetricsUtil() ;
	    
		 solutionFront = utilities_.readFront(pichome+funfilename);
	     trueFront     = utilities_.readFront(truefrontfile);
	     Hypervolume hv = new Hypervolume();
	     
	     
//		 for(int i=0;i<solutionFront.length;i++){
//			 for(int j=0;j<solutionFront[0].length;j++)
//				 System.out.print(solutionFront[i][j] +" ");
//			 System.out.println();
//		 }
	     /*
	     String[] stringArray = truefrontfile.split("\\/");
	     String fileName=read_settings.true_hypervolume_path+"\\"+stringArray[stringArray.length-1];
	     System.out.println("hi "+fileName);
	     
	     File file=new File(fileName);
	     
	     if(file.exists()){
	     	try {
	 			BufferedReader br=new BufferedReader(new FileReader(file));
	 			String s;
	 			try {
	 				if((s=br.readLine())!=null){
	 					truepf_hvval = Double.valueOf(s);
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
	     		     		    	
	    	truepf_hvval = hv.hypervolume(trueFront, trueFront, dim);
	     	
	     	try {
	 			BufferedWriter output = new BufferedWriter(new FileWriter(file));
	 			output.write(String.valueOf(truepf_hvval));
	 	        output.close();
	 		} catch (IOException e) {
	 			System.out.println("file creation failed");
	 			e.printStackTrace();
	 			System.exit(0);
	 		}
	     	
	     }*/

	     
	     
	     //truepf_hvval = hv.hypervolume(trueFront, trueFront, dim);
	     
	     GenerationalDistance gd = new GenerationalDistance();
	     Spread sp = new Spread();
	     
	     double gdval = gd.generationalDistance(solutionFront,trueFront,dim);
	     double igdval = new InvertedGenerationalDistance().invertedGenerationalDistance(solutionFront,trueFront,dim);
	     double spval = sp.spread(solutionFront, trueFront, dim);
	     double hvval =0;
	     
	     hvval=hv.hypervolume(solutionFront, trueFront, dim);
	      
	     System.out.println("GD: "+gdval);
	     System.out.println("IGD: "+igdval);
	     System.out.println("SP: "+spval);
	     System.out.println("HV: "+hvval);
	     System.out.println("TRUE HV: "+truepf_hvval);
	     System.out.println("HV Ratio: "+hvval/truepf_hvval);
	}
	
}

public class piceagstat {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		runstat r=new runstat(false,10,2);
		
		/*
		int di=10;
		
		for(int i=2;i<=9;i++){			
			//runstat r = new runstat(false,di,i);		
			System.out.println("\n-------------------\n");		
			runstat r1 = new runstat(true,di,i);
			System.out.println("\n-------------------\n");
		}
		
		*/
	}

}
