package simplex;
import java.util.*;

import simplex.Tableau.PivotResult;

public class Simplex {
	double[][] 	tabuleau = {{-11,0,-8,-21,-1,0,0,0,0},
							{3,1,2,3,0,1,0,0,0},
							{2,-1,2,6,0,0,1,0,0},
							{5,0,4,9,0,0,0,1,0},
							{1,0,0,3,1,0,0,0,1}};
	
	/**
	 * Uses the simplex algortihm to find an optimal solution for the linear progrmaming problem.
	 * 
	 * @param t the input tableau
	 * @return the solved tableau. The solution 
	 * (infeasable, infinite optimum and variable assignment of finite optimum) 
	 * can be read from the tableau. 
	 */
	public Tableau simplex(Tableau t){
		// TODO implement
		return null;
	}
	
	public void test()
	{
		
		tabuleau = Util.transpose(tabuleau);
		Tableau tabl = new Tableau(tabuleau);
		int i = 0;
		System.out.println(tabl);
		
			/*Check for negative reduced cost */
			
			
				tabl.pivot();
				
			
		
	
	}
	
	public void print_tab()
	{
		for(int i=0;i<tabuleau.length; i++)
		{
			System.out.println(tabuleau[0][i]);
		}
	}
	

public static void main(String[] args)
{
	Simplex simplex = new Simplex();
	simplex.test();
//	simplex.print_tab();                
}	
}