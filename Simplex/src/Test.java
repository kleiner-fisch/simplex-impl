import static org.junit.Assert.*;
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

}
