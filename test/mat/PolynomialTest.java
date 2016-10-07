package mat;

import static org.junit.Assert.*;

import org.junit.Test;

public class PolynomialTest {

	@Test
	public void test() {
		Polynomial p = new Polynomial(3, 2);
		System.out.println(p.toString());
		p.negativeSelf();
		System.out.println(p.toString());
		assertTrue(true);
		
		Polynomial p2 = p.clone();
		assertFalse(p == p2);
		assertTrue(p.equals(p2));
		Polynomial p0 = p2.add(p.negativeSelf());
		assertTrue(p0.isZero());
	}
	
	@Test
	public void test_multiply() {
		Polynomial p = new Polynomial(3, 2),
				p2 = new Polynomial(new double[]{1,2,1});
		System.out.println(p.toString());
		System.out.println(p2.toString());
		Polynomial pp2 = p.multiply(p2), p2p = p2.multiply(p);
		System.out.println(pp2);
		assertTrue(pp2.isEqual(p2p));
		
		Polynomial p3 = p.multiply(3);
		System.out.println(p3);
		Double dividor = p3.divideBy(p);
		System.out.println(dividor);
		assertNotNull(dividor);
		assertTrue(3.0 == dividor.doubleValue());
	}
	
	@Test
	public void test_value() {
		Polynomial p = new Polynomial(3, 2);
		double v = p.value(1);
		assertTrue(5.0 == v);
		double z = p.solveOne();
		System.out.println(z);
		assertTrue(-1.5 == z);
	}
}
