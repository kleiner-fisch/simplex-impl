package simplex;
import java.util.*;

public class Simplex {
	double[][] 	tabuleau = {{-11,0,-8,-21,-1,0,0,0,0},
							{3,1,2,3,0,1,0,0,0},
							{2,-1,2,6,0,0,1,0,0},
							{5,0,4,9,0,0,0,1,0},
							{1,0,0,3,1,0,0,0,1}};
	
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