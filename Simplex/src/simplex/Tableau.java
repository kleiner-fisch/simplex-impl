package simplex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
	
	public Tableau(double[][] tableau){
		this.tableau = tableau;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < nrOfRows(); k++) {
			for (int i = 0; i < nrOfColumns(); i++) {
				sb.append(tableau[i][k] + "\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	@Override
	public boolean equals(Object obj){
	       if (obj == this) {
	            return true;
	        }
	        if (obj == null || obj.getClass() != this.getClass()) {
	            return false;
	        }
	        Tableau t = (Tableau) obj;
	        if (t.nrOfColumns() != nrOfColumns() || t.nrOfRows() != nrOfRows()) 
	        	return false;
	        
	        for (int k = 0; k < nrOfRows(); k++) {
				for (int i = 0; i < nrOfColumns(); i++) {
					if(! Util.areEqual(t.tableau[i][k], tableau[i][k]))
						return false;
				}
	        }
		return true;
		
	}
	public static enum PivotResult{OPTIMAL, INFINITE_COSTS, BASIS_CHANGED}
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
		 * first find a variable with negative reduced costs.
		 * If we do not find one, we already have an optimal solution
		 */
		int enteringVariable = Util.NOTHING;
		for (int i = 1; i < nrOfColumns(); i++) {
			if(Util.smaller(tableau[i][0], 0)){
				enteringVariable = i;
				break;
			}
		}
		if(enteringVariable == Util.NOTHING)
			return PivotResult.OPTIMAL;
		/*
		 * Then if the j'th variable has column vector <= 0 the optimal cost is
		 * -infinite and we terminate.
		 */
		if(Util.isNonPositive(tableau[enteringVariable]))
			return PivotResult.INFINITE_COSTS;

		/*
		 * Then we collect all rows that have a ratio equal to the minRatio
		 * And determine the lex-smallest of those rows.
		 */
		double minRatio = getMinRatio(enteringVariable);
		List<Integer> minRatioRows = getMinRatioRows(minRatio, enteringVariable);
		int leavingVariable = getLexSmallestRow(minRatioRows);
		changeBasis(leavingVariable, enteringVariable);
		
		return PivotResult.BASIS_CHANGED;
	}
	/**
	 * Turns the given column into a unit-vector (with first element of column = 0),
	 * and the 1 at the row'th position. For this row-operations are used.
	 * 
	 * @param column defines together with <code>row</code> the pivpt element
	 */
	public void changeBasis(int pivotX, int pivotY){
		/*
		 * Then column i leaves the basis and column j enters it.
		 * Make it such, that the column j has at row i ((j,i) is pivot element) 
		 * a 1 and every else 0.
		 */
		multiplyBy(1 / tableau[pivotX][pivotY], pivotY);
//		System.out.println(toString());
		for (int row = 0; row < nrOfRows(); row++) {
			if(row != pivotY){
				addMultiple(-tableau[pivotX][row], pivotY, row);
			}
//			System.out.println(toString());
		}
	}
	/**
	 * For a list of vectors decides which vector is the lexicographically
	 * smallest vector
	 * 
	 * @param indices the indices of the rows that participate in the comparison
	 */
	public int getLexSmallestRow(List<Integer> indices){
		for (int i = 1; i < nrOfColumns(); i++) {
			double[] vector = new double[indices.size()];
			/*
			 * Create the columns to be compared
			 */
			for (int k = 0; k < indices.size(); k++) {
				vector[k] = tableau[i][indices.get(k)];
			}
			System.out.println("column: "+Arrays.toString(vector));
			System.out.println("indices: "+indices);
			List<Integer> smallest = Util.smallestIndices(vector);
			System.out.println("Smallets: "+smallest);
			if(smallest.size() == 1){
				return indices.get(smallest.get(0));
			}else{
				// we remove the non-smallest indices
				List<Integer> indicesTmp = new ArrayList<Integer>();
				for (Integer index : smallest) {
					indicesTmp.add(indices.get(index));
				}
				indices = indicesTmp;
			}
		}
		throw new RuntimeException("Probably only reachable if the lex-smallest is not unique, which we do assume to be.");
	}
	
	/**
	 * Gets the rows that have the minRatio
	 */
	private List<Integer> getMinRatioRows(double minRatio, int pivotColumn) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < tableau[pivotColumn].length; i++) {
			double currentRatio = tableau[0][i] / tableau[pivotColumn][i];
			if(Util.areEqual(currentRatio, minRatio))
				result.add(i);
		}
		return result;
	}


	/**
	 * Calculates the minimum ratio x_{B(i)} / u_i for all rows i where u_i > 0 
	 * and the given column.
	 * note that <code>x_{B(i)} = tableau[0][i]</code> 
	 * and <code>u_i = tableau[0][pivotColumn]</code>   
	 */
	private double getMinRatio(int pivotColumn){
		double minRatio = Double.POSITIVE_INFINITY;
		for (int i = 1; i < nrOfRows(); i++) {
			if(Util.greater(tableau[pivotColumn][i],0)){
				double currentRatio = tableau[0][i] / tableau[pivotColumn][i];
				if(Util.smaller(currentRatio, minRatio))
					minRatio = currentRatio;
			}
		}
		return minRatio;
	}
	
	/**
	 * Multiplies the defined the row by a constant
	 * @param multiplier the constant to multiply the row by
	 * @param row the row to multiply
	 */
	public void multiplyBy(double multiplier, int row){
		for(int i = 0; i < nrOfColumns(); i++){
			tableau[i][row] *= multiplier;
		}
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
		for(int i = 0; i < nrOfColumns(); i++){
			tableau[i][addTo] += tableau[i][toAdd] * multiplier; 
		}
	}
	
	
	/**
	 * Checks the soundness of the current tableau. 
	 * Makes sure, that we have the correct number of unitVectors (one for each basic variable)
	 * and that the reduced cost of each basic variable 
	 * is 0
	 * 
	 */
	public void checkSoundness(){
		Util.checkIsMatrix(tableau);
		
		if(nrOfRows() > nrOfColumns())
			throw new IllegalArgumentException("The tableau has at most as many rows as it has columns!");
		
		/*
		 * This part ensures there are m linear independent unit vectors.
		 * m is the (number of rows - 1) in the tableau
		 */
		List<Integer> indices = getBasicVariables();
		checkUnitVectorsAreLinIndep(indices);
		
		if(indices.size() != nrOfRows() -1)
			throw new IllegalArgumentException("Tableau should have "+(nrOfRows() -1)+" basic variables!"+
					"Actual nr of basic var: "+indices.size());
		/*
		 * Now we ensure that the unit-vectors are linearly independend (no two are equal)
		 */
		if(indices.size() != new HashSet<Integer>(indices).size())
			throw new IllegalArgumentException("There are some unitvectors that have 1 at the same position!");
	}
	/**
	 * For this tableau gets the column indices of the basic variables.
	 * They have reduced cost = 0 and their column is a unit-vector. 
	 */
	public List<Integer> getBasicVariables(){
		List<Integer> indices = new ArrayList<Integer>();
		for (int column = 1; column < nrOfColumns(); column++) {
			int row = Util.isUnitVector(tableau[column]);
			if( row >= 0 && Util.areEqual(tableau[column][0], 0)){
				indices.add(column);
			}
		}
		return indices;
	}
	/**
	 * Verifies that the unit vectors in the indices are lineary independend
	 * @param indices
	 */
	public void checkUnitVectorsAreLinIndep(List<Integer> indices){
		List<Integer> tmp = new ArrayList<Integer>();
		for (Integer column : indices) {
			if(!tmp.contains(Util.isUnitVector(tableau[column])))
				tmp.add(Util.isUnitVector(tableau[column]));
			else
				throw new IllegalArgumentException("Unit vectors are not lin. independend");
		}
	}
	public int nrOfColumns(){
		return tableau.length;
}

	public int nrOfRows(){
		return tableau[0].length;
	}
}
