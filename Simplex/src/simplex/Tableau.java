package simplex;

import java.util.ArrayList;
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
	/**
	 * Has the values of the tableau in row order.
	 * Iterating over it will iterate over the rows, represented as lists.
	 */
	List<List<Entry>> rows;
	/**
	 * Has the values of the tableau in column order.
	 * Iterating over it will iterate over the columns, represented as lists.
	 */
	List<List<Entry>> columns;
	
	public Tableau(List<List<Entry>> columns, List<List<Entry>> rows){
		this.columns = columns;
		this.rows = rows;
	}
	
	public Tableau(double[][] tableau){
		Util.checkIsMatrix(tableau);
		rows = new ArrayList<>(tableau.length * tableau[0].length);
		columns = new ArrayList<>(tableau.length * tableau[0].length);
		/*
		 * First we fill the columns, where we can easily iterate ove rthe array
		 */
		for(double[] column : tableau){
			ArrayList<Entry> columnList = new ArrayList<Entry>(column.length);
			for(double v : column){
				Entry e = new Entry(v);
				columnList.add(e);
			}
			columns.add(columnList);
		}
		/*
		 * Then we clumsily iterate over the whole tableau to fill the rows
		 */
		for(int i =0; i < tableau[0].length; i++){
			ArrayList<Entry> row = new ArrayList<Entry>(tableau[0].length);
			rows.add(row);
		}
		for(int currentColumn = 0; currentColumn < columns.size(); currentColumn++){
			List<Entry> column = columns.get(currentColumn);
			for(int currentRow = 0; currentRow < column.size(); currentRow++){
				Entry e = columns.get(currentColumn).get(currentRow);
				rows.get(currentRow).add(e);
			}
		}
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(List<Entry> row : rows){
			sb.append(row).append("\n");
		}
		return sb.toString();
	}
	/**
	 * Returns the column vectors for the basic variables
	 */
	public  List<List<Entry>> getBasicVar(){
		List<List<Entry>> result = new ArrayList<>(rows.size());
		for(List<Entry> column : columns){
			if(Util.isUnitVector(column))
				result.add(column);
		}
		
		return result;
	}
	
	/**
	 * Replaces rows with columns and vice versa.
	 * For this to return a new tableau and not affecting the old tableau
	 * we also have to copy the entries (deep copies instead of shallow ones) 
	 */
	public void transpose(){
		List<List<Entry>> tmp;
		tmp = rows;
		rows = columns;
		columns = tmp;
		
	}
}
