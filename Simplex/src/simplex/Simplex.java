package simplex;

import java.util.*;

import simplex.Simplex.SimplexResult;
import simplex.Tableau.PivotResult;
import static org.junit.Assert.*;

public class Simplex {
	/**
	 * Indicates what kind of optimal solution the LP has 
	 */
	public static enum SimplexResult{FINITE_OPTIMUM, INFINITE_OPTIMUM, INFEASABLE}

	/**
	 * Holds the number of artifical variables introduced in pahse 1
	 */
	public int noOfOrgVariables ;

	public Tableau inputTableau;
	/**
	 * Uses the simplex algortihm to find an optimal solution for the linear
	 * progrmaming problem.
	 * 
	 * @param t
	 *            the input tableau
	 * @return the solved tableau. The solution (infeasable, infinite optimum
	 *         and variable assignment of finite optimum) can be read from the
	 *         tableau.
	 */
	public Tableau simplex(Tableau t) {
		inputTableau = t;
		t= simplex_phase_I(t);
		
		t = phaseII(t);
		
		return t;
	}

	private Tableau phaseII(Tableau t) {
		computeCosts(t);
		
		// do pivot until potimum/infite
		t.pivot();
		while(t.status == PivotResult.BASIS_CHANGED){
			t.pivot();
		}
		if(t.status == PivotResult.INFINITE_OPTIMUM)
			t.result = SimplexResult.INFINITE_OPTIMUM;
		else if(t.status == PivotResult.OPTIMAL_ACHIEVED)
			t.result = SimplexResult.FINITE_OPTIMUM;
		
		return t;
	}

	public void computeCosts(Tableau t) {
		double[] basicCostsBInvA = multBasicCostsBInverseA(t);
		for(int x = 1; x < t.nrOfColumns(); x++){
			t.tableau[x][0] = inputTableau.tableau[x][0] - basicCostsBInvA[x-1];
		}
		
		double[] basicCosts = getBasicCosts(t);
		for(int i = 0; i < basicCosts.length; i++){
			t.tableau[0][0] -= basicCosts[i] * t.tableau[0][i+1]; 
		}
	}

	/**
	 * Gets the basic costs, assuming that the original tableau is available.
	 * The order of the values corresponds to the order of the basic variables in the input tableau.
	 */
	public double[] getBasicCosts(Tableau t){
		List<Integer> basicRows = new ArrayList<Integer>(t.basicVarRowToColumn.keySet());
		Collections.sort(basicRows);
		List<Integer> basicCostsIndices = new ArrayList<Integer>();
		for(Integer row : basicRows){
			basicCostsIndices.add(t.basicVarRowToColumn.get(row));
		}
		double[] basicCosts = new double[basicCostsIndices.size()];
		int counter =0;
		for(Integer i : basicCostsIndices){
			basicCosts[counter] = inputTableau.tableau[i][0];
					counter++;
		}
		return basicCosts;
	}
	/**
	 * calculates c_B * (B^-1 * A), under the assumption that tableau t contains
	 *  (B^-1 * A).
	 */
	private double[] multBasicCostsBInverseA(Tableau t) {
		double[] basicCosts = getBasicCosts(t);
		double[] result = new double[t.nrOfColumns() -1];
		for (int x = 0; x < t.nrOfColumns() -1; x++) {
			for (int y = 0; y < t.nrOfRows()-1; y++) {
				result[x] += basicCosts[y] * t.tableau[x+1][y+1];  
			}
		}
		return result;
	}

	public Tableau simplex_phase_I(Tableau t) {
		t = makebPositive(t);
		t = createAuxillaryLP(t);
		t.pivot();
		while(t.status == PivotResult.BASIS_CHANGED){
			t.pivot();
		}
		if(Util.smaller(t.tableau[0][0], 0)){
			t.result = Simplex.SimplexResult.INFEASABLE;
			return t;
		}
		t = removeArtVariablesFromBasis(t);
		t = removeArtVariablesFromColumns(t);
		
		return t;
	}
	/**
	 * Removes the artifical variables that were added at the beginning of phase 1
	 */
	public Tableau removeArtVariablesFromBasis(Tableau t) {
		Tableau result = new Tableau(t);
		List<Integer> basicColumns = t.getBasicVariables();
		// First we determine the artifical rows
		List<Integer> artificalVarRows = new ArrayList<Integer>();
		for(Integer i : basicColumns){
			if(i > noOfOrgVariables)
				artificalVarRows.add(Util.isUnitVector(t.tableau[i]));
		}
		// then we get the artifical rows out of the basis
		for (Integer i : artificalVarRows) {
			int nonZeroEntry = t.getIndexOfFirstNonZero(i);
			if(nonZeroEntry == Util.NOTHING || nonZeroEntry > noOfOrgVariables)
				removeRow(i, result);
			else
				t.changeBasis(i, nonZeroEntry);
		}
		// Test if the row -> column map is correct
		for (int row = 1; row < result.nrOfRows(); row++) {
			assertTrue((result.basicVarRowToColumn.get(row) > 0) &&
					(result.basicVarRowToColumn.get(row) < result.nrOfColumns())); 
		}
		return result;
	}
	
	public Tableau removeArtVariablesFromColumns(Tableau t){
		Tableau result = new Tableau(t);
		// then we remove the columns of the artifical rows
		double[][] array = new double[noOfOrgVariables +1][result.nrOfRows()];
		for (int column = 0; column < noOfOrgVariables +1; column++) {
			array[column] = Arrays.copyOf(result.tableau[column], result.nrOfRows());
		}
		result.tableau = array;
		return result;
	}
	public void removeRow(Integer i, Tableau t) {
		double[][] tableauArray = new double[t.nrOfColumns()][t.nrOfRows()-1];
		int offset = 0;
		for (int y = 0; y < t.nrOfRows() -1; y++) {
			// skip the row to remove
			if(y == i){
				offset++;
			}
			for (int x = 0; x < t.nrOfColumns(); x++) {
				tableauArray[x][y] = t.tableau[x][y + offset]; 
			}
		}
		// remove the old row->column pointer and downgrade all rows in the pointers by 1
		t.basicVarRowToColumn.remove(i);
		for(Integer row : t.basicVarRowToColumn.keySet()){
			if(row > i){
				t.basicVarRowToColumn.put(row-1,t.basicVarRowToColumn.get(row));
				t.basicVarRowToColumn.remove(row);
			}
				
		}
		t.tableau = tableauArray;
	}

	/**
	 * Create the auxillary tableau thtat has artifical variables and a new cost vector. 
	 */
	public Tableau createAuxillaryLP(Tableau t) {
		noOfOrgVariables = t.nrOfColumns() - 1;
		double[][] newArray = new double[t.nrOfColumns() + t.nrOfRows() - 1][t.nrOfRows()];
		Tableau newTableau = new Tableau(newArray);
		// Copy b
		newTableau.tableau[0] = Arrays.copyOf(t.tableau[0], t.nrOfRows());
		// copy A
		for (int i = 1; i < noOfOrgVariables + 1; i++) {
			newTableau.tableau[i] = Arrays.copyOf(t.tableau[i], t.nrOfRows());
			newTableau.tableau[i][0] = 0;
		}
		// insert E
		for (int i = 1; i < t.nrOfRows(); i++) {
			newTableau.tableau[i + noOfOrgVariables][i] = 1; 
			newTableau.basicVarRowToColumn.put(i, i + noOfOrgVariables);
		}
		// insert reduced costs and the total current costs
		for (int x = 0; x < noOfOrgVariables +1; x++) {
			for (int y = 1; y < t.nrOfRows(); y++) {
				newTableau.tableau[x][0] -= t.tableau[x][y]; 
			}
		}
		return newTableau;
	}

	/**
	 * Enforces that all elements of the 0th column are >= 0, by applying 
	 * row multiplicaitons if necessary. 
	 */
	public Tableau makebPositive(Tableau t) {
		double[][] resultArray = new double[t.nrOfColumns()][t.nrOfRows()];
		for (int i = 0; i < t.nrOfColumns(); i++) {
			resultArray[i] = Arrays.copyOf(t.tableau[i], t.nrOfRows());
		}
		Tableau result = new Tableau(resultArray);
		for (int row = 1; row < t.nrOfRows(); row++) {
			if(Math.signum(result.tableau[0][row]) < 0){
				result.multiplyBy(-1, row);
			}
		}
		return result;
	}

	public Tableau simplex_phase_II(Tableau t) {
		return null;
	}

	public static void main(String[] args) {
		Simplex simplex = new Simplex();
//		simplex.simplex_phase_I();
	}
}