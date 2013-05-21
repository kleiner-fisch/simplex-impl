package simplex;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.omg.PortableInterceptor.SUCCESSFUL;

import simplex.Tableau;
import simplex.Util;


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
		assertFalse("vector is positive",Util.isNonPositive(vector));
	}
	@org.junit.Test
	public void testIsNonPositive2() {
		double[] vector = {-1,-1,1};
		assertFalse("vector has positive elemtn",Util.isNonPositive(vector));
	}
	@org.junit.Test
	public void testIsNonPositive3() {
		double[] vector = {-1,0,0};
		assertTrue("vector is non-positive",Util.isNonPositive(vector));
	}
	@org.junit.Test
	public void testAreEqual(){
		assertTrue(Util.areEqual(1, 1));
		assertTrue(Util.areEqual(0, 0));
		
		assertFalse(Util.areEqual(0.01, 0));
		assertFalse(Util.areEqual(0, -0.01));
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
	
}
