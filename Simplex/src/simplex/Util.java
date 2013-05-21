package simplex;

import java.util.List;

public class Util {

	/**
	 * Tests if two double are almost equal.
	 */
	public static boolean areEqual(double a, double b){
		return Math.abs(a - b) < Util.EPSILON;
	}

	public final static double EPSILON = 0.0000001;

//	public static int nrOfColumn(double[][] tableau){
//		return tableau[0].length;
//	}
//
//	public static int nrOfRows(double[][] tableau){
//		return tableau.length;
//	}

	/**
	 * Tests if the given vector contains exactly one 1 and all other values are 0.
	 * So it tests if the input vector is (0,...,0,1,0,..,0)
	 */
//	public static boolean isUnitVector(double[] vector){
//		boolean seenNotZero = false;
//		double sum = 0 ;
//		for(double v : vector){
//			if(seenNotZero && v != 0){
//				return false;
//			} else if( v != 0){
//				seenNotZero = true;
//			}
//			sum += v;
//		}
//		return areEqual(sum, 1);
//	}
/**
 * Checks if the matrix m is not null, and has at least length 1 in both dimensions
 */
	public static void checkIsMatrix(double[][] m) {
		// TODO Auto-generated method stub
		
	}

}
