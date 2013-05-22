package simplex;

import java.util.Iterator;

import simplex.Tableau.SimplexResult;

/**
 * Class used to show some example simplex runs
 *
 */
public class Main {
	/**
	 * The number of random tableaus to be created and optimised
	 */
	static final int nrOfSimplexRuns = 10;
	
	public static void main(String[] args) {
		Simplex s = new Simplex();
		int nrOfPivotOperations = 0;
		int nrOfInfeasable = 0;
		int nrOfInfOptimum = 0;
		int nrOfFiniteOptiumum = 0;
		
		for (int i = 0; i < nrOfSimplexRuns; i++) {
			StringBuilder sb = new StringBuilder();
			Tableau t = Tableau.createRandomTableau(3, 3, 3);
			sb.append("Created tableau\n" + t.toString());
			System.out.println(sb.toString());
			
			t = s.simplex(t);
			printSummary(t);
			nrOfPivotOperations += t.pivotCounter;
			switch (t.result) {
			case FINITE_OPTIMUM:
				nrOfFiniteOptiumum++;
				break;
			case INFEASABLE:
				nrOfInfeasable++;
				break;
			case INFINITE_OPTIMUM:
				nrOfInfOptimum++;
				break;
			default:
				throw new RuntimeException();
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Average number of pivot operations per run: ");
		sb.append((double)nrOfPivotOperations /(double) nrOfSimplexRuns );
		
		int sum = nrOfInfOptimum + nrOfInfeasable + nrOfFiniteOptiumum;
		sb.append("% of unbounded LP: ");
		sb.append((double)nrOfInfOptimum /(double) sum );
		
		sb.append("\n% of infeasable LP: ");
		sb.append((double)nrOfInfeasable /(double) sum );
		
		sb.append("\n% of finite LP: ");
		sb.append((double)nrOfFiniteOptiumum /(double) sum );
		
		System.out.println(sb.toString());
	}

	private static void printSummary(Tableau t) {
		StringBuilder sb = new StringBuilder();
		sb.append("\nSolved tableau\n" + t.toString()+"\n");
		sb.append("The propblem ");
		switch (t.result) {
		case FINITE_OPTIMUM:
			sb.append("has a "+ SimplexResult.FINITE_OPTIMUM+" at "+ t.tableau[0][0]);
			sb.append("The optimal solution is ");
			for (Double d : t.tableau[0]) {
				sb.append(d+" ");
			}
			break;
		case INFEASABLE:
			sb.append("is "+ SimplexResult.INFEASABLE);
			break;
		case INFINITE_OPTIMUM:
			sb.append("has an "+ SimplexResult.INFINITE_OPTIMUM);
			break;
		default:
			throw new RuntimeException();
		}
		System.out.println(sb.toString());
	}
}
