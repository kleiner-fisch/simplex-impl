package simplex;

public class Simplex {
	
	/**
	 * Performs a simplex step as described on page 100 of the book.
	 * If this iteration learns the optimal cost is '-inf' it returns null
	 *  
	 * @param tableau the tableau to be a simplex iteration on
	 * @return the resulting tableau with changed basic variables
	 */
	public static double[][] pivot(Tableau tableau){
		checkSoundness(tableau);
		/* 
		 * first we collect all variables with negative reduced costs.
		 * If the list is empty we terminate, as we already have an optimal solution
		 */
		/*
		 * Then we select one variable with index j with neg. reduced cost 
		 * (to handle non-degenerate cases we may need some selection method)
		 */
		/*
		 * Then if the j'th variable has column vector <= 0 the optimal cost is
		 * -infinite and we terminate.
		 */
		/*
		 * Then we compute the ratio x_{B(i)} / u_i for all columns i 
		 * and select the element with the smallest ratio (decreases the costs the most). 
		 */
		/*
		 * Then column i leaves the basis and column j enters it.
		 */
		/*
		 * Make it such, that the column j has at row i ((j,i) is pivot element) 
		 * a 1 and every else 0.
		 */
		
		
		//TODO implement
		return null;
	}
/**
 * Checks if all variables in the basis have reduced cost of 0 
 * and if the columns of the basic variables have exactly one value not zero, 
 * which is equal to 1.
 */
	private static void checkSoundness(Tableau tableau) {
		// check there are nrOfRows -1 unitVectors
		
		// check each unit vector has reduced costs (first column)
		// of 0
		
		// TODO How should it work????
		
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
