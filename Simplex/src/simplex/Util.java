package simplex;

import java.util.ArrayList;
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
		int result = NOTHING;
		for (int i = 0; i < vector.length; i++) {
			double v = vector[i];
			if(areEqual(v, 1))
				result = i;
			sum += Math.pow(v, 2);
		}
		if(result >= 0 && areEqual(sum, 1))
			return result;
		else
			return NOTHING;
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
	 * Takes a matrix and creates a new transposed version of it
	 */
	public static double[][] transpose(double[][]  m){
		checkIsMatrix(m);
		Util.checkIsMatrix(m);
		double[][] result = new double[m[0].length][m.length];
		for (int i = 0; i < result.length; i++) {
			for (int k = 0; k < result[0].length; k++) {
				result[i][k] = m[k][i];
			}
		}
		return result;
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
	/**
	 * determines the indice of the smallest value in a array, if there is such.
	 * If there is no unique smallest value -1 is returned
	 */
//	public static int smallest(double... values){
//		if(values.length < 2)
//			throw new IllegalArgumentException("Must compare at least 2 values");
//		int result = 0;
//		double smallest = values[0];
//		for (int i = 1; i < values.length; i++) {
//			if(smaller(values[i], smallest)){
//				result = i;
//				smallest = values[i];
//			} else if(areEqual(smallest, values[i]))
//				result = NOTHING;
//		}
//		return result;
//	}
	/**
	 * Gets the indices of the smallest values.
	 */
	public static List<Integer> smallestIndices(double[] values){
		double min = getSmallestValue(values);
		List<Integer> result = new ArrayList<Integer>();
		
		for (int i = 0; i < values.length; i++) {
			if(areEqual(values[i], min))
				result.add(i);
		}
		return result;
	}
	/**
	 * Gets the minimal element from an array of doubles
	 */
	public static double getSmallestValue(double... values){
		double min = values[0];
		for (int i = 0; i < values.length; i++) {
			if(smaller(values[i], min ))
				min = values[i];
		}
		return min;
	}
	/**
	 * A special result used as return when no indice satisfies the desired condition
	 */
	public static final int NOTHING = -1;
	
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
