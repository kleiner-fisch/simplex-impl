package simplex;
import java.util.*;

import simplex.Tableau.PivotResult;

public class Simplex {
	private static final PivotResult OPTIMAL_ACHIEVED = null;
	private static final PivotResult INFINITE_OPTIMUM = null;
	double[][] 	tabuleau = {{-11,0,-8,-21,-1,0,0,0,0},
							{3,1,2,3,0,1,0,0,0},
							{2,-1,2,6,0,0,1,0,0},
							{5,0,4,9,0,0,0,1,0},
							{1,0,0,3,1,0,0,0,1}};
	int no_original_variables =4;
	
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
	
	public void simplex_phase_I()
	{
		
		tabuleau = Util.transpose(tabuleau);
		Tableau tabl = new Tableau(tabuleau);
		tabl.checkSoundness();
		int flag1 = 0;
		int flag2 = 0;
//		tabl.pivot();
//		tabl.pivot();
//		tabl.pivot();
		
		int i;
			
		while(flag1==0)
		{
			
			//System.out.println(tabl);
			if(tabl.status==PivotResult.OPTIMAL_ACHIEVED||tabl.status==PivotResult.INFINITE_OPTIMUM)      
				
			{
				System.out.println(tabl.status);
				System.out.println("Test");
				break;
			}
			tabl.pivot();
		}
		
		for(i=1;i<tabl.nrOfColumns();i++)
		{
			if(tabuleau[i][0]==0)
			{
				System.out.println(i);
				
			}
		}
		
		System.out.println(tabl);
		List<Integer> basic_variables;
		basic_variables = tabl.getBasicVariables();
		System.out.println("Basic Variables");
		System.out.println(basic_variables);
		
		for (i=0; i<basic_variables.size(); i++)
	{
			int n = basic_variables.get(i);
		if(n > 4)
		{
		System.out.println(basic_variables.get(i));	
		int basic_row = Util.isUnitVector(tabl.tabuleau[basic_variables.get(i)]);
		System.out.println(basic_row);
		}
	}
	
	}

	double[][] tabuleau_phaseII = {{-7,0,0,0,3,-5},
									{0,0,1,0,7,0},
									{2,1,0,0,-17,1},
									{1,0,0,1,3.66,0.33}};
	
	
	public void simplex_phase_II()
	{
		tabuleau_phaseII = Util.transpose(tabuleau_phaseII);
		Tableau tabl = new Tableau(tabuleau_phaseII);
		System.out.println(tabl);
		tabl.checkSoundness();

		int flag =0;
		while(flag == 0)
		{
			if(tabl.status==PivotResult.OPTIMAL_ACHIEVED||tabl.status==PivotResult.INFINITE_OPTIMUM)      
				
			{
				System.out.println(tabl.status);
				System.out.println("Test");
				break;
			}
			tabl.pivot();

		}
		System.out.println(tabl);
	}
	
	
	

public static 
void main(String[] args)
{
	Simplex simplex = new Simplex();
	simplex.simplex_phase_I();
	//simplex.print_tab();                
}	
}