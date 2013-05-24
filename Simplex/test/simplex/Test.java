package simplex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;

import simplex.Tableau.PivotResult;


public class Test {

	@org.junit.Test
	public void testIsUnitVector1() {
		double[] vector = {0,1};
		assertEquals("The vector is a unit vector",Util.isUnitVector(vector), 1);
	}
	
	@org.junit.Test
	public void testIsUnitVector2() {
		double[] vector = {0,0};
		assertEquals("The vector clearly is not a unitvector",Util.isUnitVector(vector), -1);
	}
	@org.junit.Test
	public void testIsUnitVector3() {
		double[] vector = {-1,0};
		assertEquals("The vector clearly is not a unitvector",Util.isUnitVector(vector), -1);
	}
	@org.junit.Test
	public void testIsUnitVector4() {
		double[] vector = {-1, 1,0};
		assertEquals("The vector clearly is not a unitvector",Util.isUnitVector(vector), -1);
	}
	
	@org.junit.Test
	public void testIsUnitVector5() {
		double[] vector = {1,0,0};
		assertEquals("The vector is a unit vector",Util.isUnitVector(vector), 0);
	}
	/**
	 * The test should throw an excetion as  the tableau does not have any unitvectors
	 */
	@org.junit.Test(expected = IllegalArgumentException.class)
	public void testCheckSoundness1() {
		double[][] tableau = {{1,0,0},{1,0,0},{1,0,0}};
		Tableau t = new Tableau(tableau);
		t.checkSoundness();
		fail("An exception should have been thrown");
	}
	
	@org.junit.Test
	public void testCheckSoundness2() {
		double[][] tableau = {{1,1,1,1}, {0,1,0,0}, {0,0,1,0}, {0,0,0,1}};
		Tableau t = new Tableau(tableau);
		t.checkSoundness();
		assertTrue("If no exception is thrown the test passed", true);
	}
	/**
	 * The test should fail as the unit vectors are not lin. independent
	 */
	@org.junit.Test(expected = IllegalArgumentException.class)
	public void testCheckSoundness3() {
		double[][] tableau = {{1,1,1,1}, {0,1,0,0}, {0,0,1,0}, {0,0,1,0}};
		Tableau t = new Tableau(tableau);
		t.checkSoundness();
		fail("An exception should have been thrown");
	}
	
	/**
	 * An exception should be thrown as the reduced cost of one of the lin. indep. var is not 0
	 */
	@org.junit.Test(expected = IllegalArgumentException.class)
	public void testCheckSoundness4() {
		double[][] tableau = {{1,1,1,1}, {1,1,0,0}, {0,0,1,0}, {0,0,0,1}};
		Tableau t = new Tableau(tableau);
		t.checkSoundness();
		fail("An exception should have been thrown");
	}
	@org.junit.Test
	public void testIsNonPositive1() {
		double[] vector = {1,1,1,1};
		assertFalse("vector is positive",Tableau.isNonPositive(vector));
	}
	@org.junit.Test
	public void testIsNonPositive2() {
		double[] vector = {-1,-1,1};
		assertFalse("vector has positive elemtn",Tableau.isNonPositive(vector));
	}
	@org.junit.Test
	public void testIsNonPositive3() {
		double[] vector = {-1,0,0};
		assertTrue("vector is non-positive",Tableau.isNonPositive(vector));
	}
	@org.junit.Test
	public void testAreEqual(){
		assertTrue(Util.areEqual(1, 1));
		assertTrue(Util.areEqual(0, 0));
		
		assertFalse(Util.areEqual(0.01, 0));
		assertFalse(Util.areEqual(0, -0.01));
	}
	
	@org.junit.Test
	public void testGetSmallestIndices1(){
		double[] vector = {-1,0,0};
		
		List<Integer> expectedIndices = new ArrayList<Integer>();
		expectedIndices.add(0);
		
		assertEquals("The smallest is the 1st", Util.smallestIndices(vector), expectedIndices);
	}
	@org.junit.Test
	public void testGetSmallestIndices2(){
		double[] vector = {-1,-1,0};
		
		List<Integer> expectedIndices = new ArrayList<Integer>();
		expectedIndices.add(0);
		expectedIndices.add(1);
		
		assertEquals("The smallest are the 1st and 2nd", Util.smallestIndices(vector), expectedIndices);
	}
	
	@org.junit.Test
	public void testGetSmallestIndices3(){
		double[] vector = {0,0,0};
		
		List<Integer> expectedIndices = new ArrayList<Integer>();
		expectedIndices.add(0);
		expectedIndices.add(1);
		expectedIndices.add(2);
		
		assertEquals("The smallest indices are all", Util.smallestIndices(vector), expectedIndices);
	}
	
	@org.junit.Test
	public void testGetSmallestValue1(){
		double[] vector = {-1,0,0};
		assertEquals("The smallest value is -1", Util.getSmallestValue(vector), -1, Util.EPSILON);
	}
	@org.junit.Test
	public void testGetSmallestValue2(){
		double[] vector = {-2,-2,0};
		assertEquals("There is no smallest", Util.getSmallestValue(vector), -2, Util.EPSILON);
	}
	@org.junit.Test
	public void testGetSmallestValue3(){
		double[] vector = {1,-1,-2};
		assertEquals("The smallest is the 3rd", Util.getSmallestValue(vector), -2, Util.EPSILON);
	}
	
	@org.junit.Test
	public void testGetLexSmallestRow1(){
		double[][] tableau = 
			{
				{1,0,0,0}, 
				{0,1,0,0}, 
				{0,0,1,0}, 
				{0,0,0,1}
			};
//		tableau = Util.transpose(tableau);
		Tableau t = new Tableau(tableau);
		
		List<Integer> participatingRows = new ArrayList<Integer>();
		participatingRows.add(1);
		participatingRows.add(2);
		participatingRows.add(3);
		
		assertEquals("Lex smallest row is the 4th", 3, t.getLexSmallestRow(participatingRows));
	}
	
	@org.junit.Test
	public void testGetLexSmallestRow2(){
		double[][] tableau = 
			{
				{0,0,0,0,0},
				{1,1,0,1,0}, 
				{1,1,0,0,1}, 
				{0,0,1,0,0},
			};
//		tableau = Util.transpose(tableau);
		Tableau t = new Tableau(tableau);
		System.out.println(t.toString());
		
		List<Integer> participatingRows = new ArrayList<Integer>();
		participatingRows.add(1);
		participatingRows.add(2);
		participatingRows.add(3);
		
		assertEquals("Lex smallest row is the 3rd", 2, t.getLexSmallestRow(participatingRows));
	}
	
	@org.junit.Test
	public void testGetLexSmallestRow3(){
		double[][] tableau = 
			{
				{1,0,1}, 
				{1,1,0}, 
				{1,0,0}
			};
		tableau = Util.transpose(tableau);
		Tableau t = new Tableau(tableau);
		
		List<Integer> participatingRows = new ArrayList<Integer>();
		participatingRows.add(0);
		participatingRows.add(1);
		participatingRows.add(2);
		
		assertEquals("Lex smallest row is the 3rd", 2, t.getLexSmallestRow(participatingRows));
	}
	
	@org.junit.Test
	public void testGetLexSmallestRow4(){
		double[][] tableau = 
			{
				{1,-1,1,1}, 
				{1,1,0,-2}, 
				{1,0,1,0}, 
				{1,1,0,1}
			};
		tableau = Util.transpose(tableau);
		Tableau t = new Tableau(tableau);
		
		List<Integer> participatingRows = new ArrayList<Integer>();
		participatingRows.add(1);
		participatingRows.add(2);
		participatingRows.add(3);
		
		assertEquals("Lex smallest row is the 3rd as the 1st does not participate",
				2, t.getLexSmallestRow(participatingRows));
	}
	
	@org.junit.Test
	public void testGetLexSmallestRow5(){
		double[][] tableau = {{1,-1,1,1}, {1,0,0,-2}, {1,0,1,0}, {1,1,0,1}};
		tableau = Util.transpose(tableau);
		Tableau t = new Tableau(tableau);
		
		List<Integer> participatingRows = new ArrayList<Integer>();
		
		participatingRows.add(1);
		participatingRows.add(2);
		
		assertEquals("Lex smallest row is the 2nd as the 1st does not participate",
				1, t.getLexSmallestRow(participatingRows));
	}
	
	@org.junit.Test
	public void testSmaller(){
		assertFalse(Util.smaller(1, 1));
		assertFalse(Util.smaller(0, 0));
		
		assertFalse(Util.smaller(0.01, 0));
		assertFalse(Util.smaller(0, -0.01));
		
		assertTrue(Util.smaller(-0.01, 0));
		assertTrue(Util.smaller(0, 0.01));
	}
	@org.junit.Test
	public void testGreater(){
		assertFalse(Util.greater(1, 1));
		assertFalse(Util.greater(0, 0));
		
		assertTrue(Util.greater(0.01, 0));
		assertTrue(Util.greater(0, -0.01));
		
		assertFalse(Util.greater(-0.01, 0));
		assertFalse(Util.greater(0, 0.01));
	}
	@org.junit.Test
	public void testleq(){
		assertTrue(Util.leq(1, 1));
		assertTrue(Util.leq(0, 0));
		
		assertFalse(Util.leq(0.01, 0));
		assertFalse(Util.leq(0, -0.01));
		
		assertTrue(Util.leq(-0.01, 0));
		assertTrue(Util.leq(0, 0.01));
	}
	@org.junit.Test
	public void testgeq(){
		assertTrue(Util.geq(1, 1));
		assertTrue(Util.geq(0, 0));
		
		assertTrue(Util.geq(0.01, 0));
		assertTrue(Util.geq(0, -0.01));
		
		assertFalse(Util.geq(-0.01, 0));
		assertFalse(Util.geq(0, 0.01));
	}

	@org.junit.Test
	public void test1(){
		double[][] tableau = 
			{
				{1,2,1,1}, 
				{0,1,0,0}, 
				{0,0,1,0} 
			};
		
		tableau = Util.transpose(tableau);
		Tableau t = new Tableau(tableau);
		
		System.out.println("column "+t.nrOfColumns());
		System.out.println("rows "+t.nrOfRows());
		System.out.println(tableau[1][0]+"\n");
		System.out.println(Arrays.toString(tableau[0]));
		
	}
	
	@org.junit.Test
	public void testChangeBasis1(){
		double[][] tableauArray = 
			{
				{120,0,-4,0,2,4,0},
				{10,0,1.5,1,1,-0.5,0},
				{0,1,-1,0,-1,1,0},
				{10,0,2.5,0,1,-1.5,1},
			};
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		
		double[][] expectedResultArray = 
			{
				{136,0,0,0,3.6,1.6,1.6},
				{4,0,0,1,0.4,0.4,-0.6},
				{4,1,0,0,-0.6,0.4,0.4},
				{4,0,1,0,0.4,-0.6,0.4},
			};
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		
		tableau.changeBasis(2, 3);
		
		assertEquals("Taken from book p. 103, middle tableua", expectedTableau, tableau);
	}
	
	@org.junit.Test
	public void testPivot1(){
		ArrayExamples examples = new ArrayExamples();
		double[][] tableauArray = examples.page115_1st; 
		tableauArray= Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		
		tableau.pivot();
		assertEquals("Taken from book p. 115, top tableua",
				PivotResult.BASIS_CHANGED, tableau.status);
		
		double[][] expectedResultArray = examples.page115_2nd;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		
		assertEquals("Taken from book p. 115, middle tableua", expectedTableau, tableau);
	}
	
	@org.junit.Test
	public void testPivot2(){
		ArrayExamples examples = new ArrayExamples();
		double[][] tableauArray = examples.page115_2nd;
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		
		tableau.pivot();
		assertEquals("Taken from book p. 115, middle tableua",
				PivotResult.BASIS_CHANGED, tableau.status);
		
		double[][] expectedResultArray = examples.page115_3rd;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		
		assertEquals("Taken from book p. 115, bottom tableua", expectedTableau, tableau);
	}
	
	@org.junit.Test
	public void testPivot3(){
		ArrayExamples examples = new ArrayExamples();
		double[][] tableauArray = examples.page114_3rd;
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		tableau.checkSoundness();
		
		tableau.pivot(4, 4);
		assertEquals("Taken from book p. 114, top tabl.",
				PivotResult.BASIS_CHANGED, tableau.status);
		
		double[][] expectedResultArray = examples.page114_4th;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		expectedTableau.checkSoundness();
		
		assertEquals("Taken from book p. 1154, bottom tableua", expectedTableau, tableau);
	}
	@org.junit.Test
	public void testPivot4(){
		ArrayExamples examples = new ArrayExamples();
		double[][] tableauArray = examples.page114_4th;
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		tableau.checkSoundness();
		
		tableau.pivot(3, 4);
		assertEquals("Taken from book p. 114, bot. tabl.",
				PivotResult.BASIS_CHANGED, tableau.status);
		
		double[][] expectedResultArray = examples.page115_1st;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		expectedTableau.checkSoundness();
		
		assertEquals("Taken from book p. 115, top tableua", expectedTableau, tableau);
	}
	@org.junit.Test
	public void testPivot5(){
		double[][] tableauArray = examples.page115_3rd;
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		tableau.checkSoundness();
		
		tableau.pivot();
		assertEquals("Taken from book p. 115, bot. tabl. " +
				"There are no variables with neg. reduced costs.",
				PivotResult.OPTIMAL_ACHIEVED, tableau.status);
		
		double[][] expectedResultArray = examples.page115_3rd;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		expectedTableau.checkSoundness();
		
		assertEquals("As we achieved the optimum nothing should change", expectedTableau, tableau);
	}
	@org.junit.Test
	public void testPivot6(){
		double[][] tableauArray = examples.allRedCostsNeg;
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		tableau.checkSoundness();
		
		tableau.pivot(1, 3);
		assertEquals("All reduced costs are negative." +
				"So the optimum is -infinite",
				PivotResult.INFINITE_OPTIMUM, tableau.status);
		
		double[][] expectedResultArray = examples.allRedCostsNeg;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		expectedTableau.checkSoundness();
		
		assertEquals("The optimum is -infinite, so nothing should change", expectedTableau, tableau);
	}
	@org.junit.Test
	public void testRemoveRow1(){
		double[][] tableauArray = examples.randomTableau1;
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		
		double[][] expectedResultArray = examples.randomTableau1_without_last_row;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		
		Simplex s = new Simplex();
		s.removeRow(tableau.nrOfRows()-1, tableau);
		
		assertEquals("The last row shhould be removed", expectedTableau, tableau);
	}
	@org.junit.Test
	public void testRemoveRow2(){
		double[][] tableauArray = examples.allRedCostsNeg;
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		System.out.println(tableau);
		
		double[][] expectedResultArray = examples.allRedCostsNeg_without_2nd_row;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		
		Simplex s = new Simplex();
		s.removeRow(1, tableau);
		System.out.println(tableau);
		
		assertEquals("The last row shhould be removed", expectedTableau, tableau);
	}
	@org.junit.Test
	public void testIntroduceArtVariables(){
		double[][] tableauArray = examples.page114_1st;
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		
		Simplex s = new Simplex();
		tableau = s.createAuxillaryLP(tableau);
		
		double[][] expectedResultArray = examples.page114_3rd;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		expectedTableau.checkSoundness();
		
		Map<Integer, Integer> expectedMap = new HashMap<Integer, Integer>();
		for (int i = 1; i < 5; i++) {
			expectedMap.put(i, i+4);
		}
		
		assertEquals(expectedMap, tableau.basicVarRowToColumn);
		assertEquals("The resulting tableau after introducing art. variables", expectedTableau, tableau);
	}
	
	@org.junit.Test
	public void testMakeBPositive(){
		double[][] tableauArray = examples.randomTableau1;
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		
		Simplex s = new Simplex();
		tableau = s.makebPositive(tableau);
		
		double[][] expectedResultArray = examples.randomTableau2;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		
		assertEquals("The resulting tableau after making b positive", expectedTableau, tableau);
	}
	
	@org.junit.Test
	public void testRemoveArtVariables(){
		double[][] tableauArray = examples.page115_3rd;
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		tableau.checkSoundness();
		Map<Integer, Integer> map= new HashMap<Integer, Integer>();
		map.put(1, 1);
		map.put(2, 2);
		map.put(3, 7);
		map.put(4, 3);
		tableau.basicVarRowToColumn = map;
		
		Simplex s = new Simplex();
		s.noOfOrgVariables = 4;
		tableau = s.removeArtVariablesFromBasis(tableau);
		tableau = s.removeArtVariablesFromColumns(tableau);
		
		double[][] expectedResultArray = examples.page116_1st;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		
		Map<Integer, Integer> expectedMap = new HashMap<Integer, Integer>();
		for (int i = 1; i < 4; i++) {
			expectedMap.put(i, i);
		}
		
		assertEquals(expectedMap, tableau.basicVarRowToColumn);
		assertEquals("Check the book", expectedTableau, tableau);
	}
	
	@org.junit.Test
	public void testGetBasicCosts(){
		double[][] tableauArray = examples.page114_1st;
		tableauArray = Util.transpose(tableauArray);
		Tableau inputTableau = new Tableau(tableauArray);
		
		double[][] finalTableauArray = examples.page116_1st;
		finalTableauArray = Util.transpose(finalTableauArray);
		Tableau finalTableau = new Tableau(finalTableauArray);
		
		Map<Integer, Integer> finalMap = new HashMap<Integer, Integer>();
		for (int i = 1; i < 4; i++) {
			finalMap.put(i, i);
		}
		finalTableau.basicVarRowToColumn = finalMap;
		
		Simplex s = new Simplex();
		s.inputTableau = inputTableau;
		s.noOfOrgVariables = 4;
		
		double[] expectedBasicCosts = {1,1,1};
		
		double[] basicCosts = s.getBasicCosts(finalTableau);
		
		assertTrue(Arrays.equals(expectedBasicCosts, basicCosts));
	}
	@org.junit.Test
	public void testComputeCosts(){
		double[][] inputTableauArray = examples.page114_2nd;
		inputTableauArray = Util.transpose(inputTableauArray);
		Tableau inputTableau = new Tableau(inputTableauArray);
		
		double[][] tableauArray = examples.page115_2nd_without_costs;
		tableauArray = Util.transpose(tableauArray);
		Tableau t = new Tableau(tableauArray);
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 6);
		map.put(2, 2);
		map.put(3, 7);
		map.put(4, 3);
		
		t.basicVarRowToColumn = map;
		
		Simplex s = new Simplex();
		s.inputTableau = inputTableau;
		s.noOfOrgVariables = 4;
		
		s.computeCosts(t);
		
		double[][] expectedResultArray = examples.page115_2nd;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		
		assertEquals(expectedTableau, t);
	}
	@org.junit.Test
	public void testSimplexPhase1(){
		double[][] tableauArray = examples.page114_1st;
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		
		Simplex s = new Simplex();
		tableau = s.simplex_phase_I(tableau);
		
		double[][] expectedResultArray = examples.page116_1st;
		expectedResultArray = Util.transpose(expectedResultArray);
		Tableau expectedTableau = new Tableau(expectedResultArray);
		
		assertEquals("Check the book", expectedTableau, tableau);
	}
	
	@org.junit.Test
	public void testSimplex_phase_I(){
		double[][] tableauArray = examples.infeasable;
		tableauArray = Util.transpose(tableauArray);
		Tableau tableau = new Tableau(tableauArray);
		
		Simplex s = new Simplex();
		tableau = s.simplex_phase_I(tableau);
		
		assertEquals("As there are two contradictory eq. constraints the LP is infeasable",
				Simplex.SimplexResult.INFEASABLE, tableau.result);
	}
	
	ArrayExamples examples;
	@Before
	public void setup(){
		examples = new ArrayExamples();
	}
	
}
