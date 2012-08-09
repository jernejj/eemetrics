package atree.util;

public class Util {
	/**
	 * In case where all dimension have same epsilon. If not read vector from input, or set it programaticaly!
	 * 
	 * @param dimension
	 * @param epsilon
	 * @return
	 */
	public static double[] generateEpsilonVector(int dimension, double epsilon) {
		 double[] t = new  double[dimension];
		 for (int i=0; i<dimension; i++) {
			 t[i] = epsilon;
		 }
		 return t;
	}
    public static double divide(double a, double b) {
    	if (b==0) return 0;
    	return a/b;
    }
	public static double euclidian( double[] x,  double[] y) {
		double r=0;
		for (int i=0; i<x.length; i++) {
			r += (x[i]-y[i])*(x[i]-y[i]);
		}
		return Math.sqrt(r);
	}
	public static double euclidian_normalized( double[] x,  double[] y, double[] intervals) {
		double r=0;
		for (int i=0; i<x.length; i++) {
			r += Math.abs(x[i]-y[i])/intervals[i];
		}
		return r/x.length;
	}
	
}
