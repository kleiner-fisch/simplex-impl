package simplex;

import java.util.List;

public class Util {

	/**
	 * Tests if two double are almost equal.
	 */
	public static boolean areEqual(double a, double b){
		return Math.abs(a - b) < EPSILON;
	}
	
	public static boolean smaller(double a, double b){
		return b - a > EPSILON;
	}

	public final static double EPSILON = 0.0000001;

	/**
	 * Tests if the given vector contains exactly one 1 and all other values are 0.
	 * So it tests if the input vector is (0,...,0,1,0,..,0).
	 * If the vector is a unit vector the return value is the index of the 1. 
	 * If it is not a unit vector the return is -1.
	 */
	public static int isUnitVector(double[] vector){
		double sum = 0 ;
		int result = -1;
		for (int i = 0; i < vector.length; i++) {
			double v = vector[i];
			if(areEqual(v, 1))
				result = i;
			sum += Math.pow(v, 2);
		}
		if(result >= 0 && areEqual(sum, 1))
			return result;
		else
			return -1;
	}
/**
 * Checks if the matrix m is not null, and has at least length 1 in both dimensions
 */
	public static void checkIsMatrix(double[][] m) {
		checkNotIsNull(m);
		
		if(m.length == 0)
			throw new IllegalArgumentException("Array must have at least 1 column!");
		
		if(m[0].length == 0)
			throw new IllegalArgumentException("Array must have at least 1 row!");
		
		for(double[] column : m){
			if(column.length != m[0].length)
				throw new IllegalArgumentException("All rows must have the same length!");	
		}
		
	}
	/**
	 * Checks that the object object is not null and throws an expcetpion if it is.
	 * @param m
	 */
	public static void checkNotIsNull(Object m) {
		if(m == null)
			throw new IllegalArgumentException("Array mustn ot be null!");
	}
	/**
	 * Tests if every element of the vector is >= 0
	 */
	public static boolean isNonPositive(double[] vector){
		for(double v : vector){
			if(greater(v, 0))
				return false;
		}
		return true;
	}
	
	public static boolean geq (double a, double b){
		return !smaller(a,b);
	}
	public static boolean leq (double a, double b){
		return geq(b, a);
	}
	public static boolean greater(double a, double b){
		return smaller(b, a);
	}

}
