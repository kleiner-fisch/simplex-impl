package simplex;

public class Simplex {
	
	/**
	 * Performs a simplex step as described on page 100 of the book.
	 * If this iteration learns the optimal cost is '-inf' it returns null
	 *  
	 * @param tableau the tableau to be a simplex iteration on
	 * @return the resulting tableau with changed basic variables
	 */
	public static double[][] pivot(double[][] tableau){
		checkSoundness(tableau);
		return null;
	}
/**
 * Checks if all variables in the basis have reduced cost of 0 
 * and if the columns of the basic variables have exactly one value not zero, 
 * which is equal to 1.
 */
	private static void checkSoundness(double[][] tableau) {
		// check there are nrOfRows -1 unitVectors
		
		// check each unit vector has reduced costs (first column)
		// of 0
		
		// TODO Auto-generated method stub
		
	}
	

	public static void main(String[] args){
		double[][] test = new double[2][3];
		test[0][1] = 1;
		test[1][0] = 2;
		test[1][2] = 3;
		Tableau tableau = new Tableau(test);
		
		System.out.println(tableau);
		
		tableau.transpose();
		
		System.out.println(tableau);
//		print(test);
				
	}
	
	public static void print(int[][] array){
		for(int[] innerArray : array ){
			for(int i : innerArray){
				System.out.print(i);
			}
			System.out.println();
		}
	}
}
