package jmetal.metaheuritic.abc;

public class SigMoid {
	// x -a c-mean
	public static double SigMoidValue(double x, double a, double c) {
		double value = 1 + Math.pow(Math.E, -a * (x - c));
		return 1 / value;
	}

	public static double CalculateA(double x, double p, double c) {
		double value = Math.log(p) - Math.log(1 - p);
		return value / (x - c);
	}
	
	public static double LeftGaussian(double c,double sigma,double x){
		if(x<=c)return 1;
		
		double value=-0.5*(((x-c)*(x-c))/(sigma*sigma));
		value=Math.pow(Math.E, value);
		
		return value;
	}
	
	public static double Linear(double min, double max,double value){
	
		return (value-min)/(max-min);
	}
	
}
