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
}
