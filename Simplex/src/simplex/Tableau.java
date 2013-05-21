package simplex;

/**
 * Data class to hold tableaus.
 * If we have an array 
 * 		test[0][1] = 1;
 *		test[1][0] = 2;
 *		test[1][2] = 3;
 * then the first dimension is the column and the 2nd the row ((x,y) coordinates).
 * Note that the topmost row is the the 0th column.
 *
 */
public class Tableau {
	public double[][] tableau;
	
	
	public void print(){
		for(double[] innerArray : tableau ){
			for(double i : innerArray){
				System.out.print(i);
			}
			System.out.println();
		}
	}
	
	public static enum PivotResult{OPTIMAL, INFINITE_COSTS, DECREASED}
	/**
	 * Performs a pivot operation. This means it tries to decrease te cost function.
	 * The result can be 
	 * 	- The tableau is optimal
	 *  - The optimal cost is -infinity
	 *  - The cost is decreased by a change of basis represented in the tableau. 
	 */
	public PivotResult pivot (){
		checkSoundness();
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
		return null;
	}
	/**
	 * Multiplies the defined the row by a constant
	 * @param multiplier the constant to multiply the row by
	 * @param row the row to multiply
	 */
	public void multiplyBy(double multiplier, int row){
		
	}
	/**
	 * Adds a multiplicative of a row to another row.
	 * Be careful that <code>toAdd = addTo</code> may be the case.
	 * 
	 * @param multiplier the constant to multiply <code>toAdd</code> by
	 * @param toAdd the row to add to another row
	 * @param addTo the row which should be changed
	 */
	public void addMultiple(double multiplier, int toAdd, int addTo){
		
	}
	/**
	 * Checks the soundness of the current tableau. 
	 * Makes sure, that we have the correct number of unitVectors (one for each basic variable)
	 * and that the reduced cost of each basic variable 
	 * is 0
	 * 
	 */
	public void checkSoundness(){
		
	}
}
