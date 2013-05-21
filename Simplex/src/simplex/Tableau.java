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
	public static double[][] tableau;
	
	
	public static void print(){
		for(double[] innerArray : tableau ){
			for(double i : innerArray){
				System.out.print(i);
			}
			System.out.println();
		}
	}
	
	void pivot (){
	}
}
