package mat;

import static org.junit.Assert.*;

import org.junit.Test;

import static mat.Matrix.*;

public class MatrixTest {

	@Test
	public void testTrue() {
		assertTrue(true);
	}

	@Test
	public void test_changesOfMatrix() {
		double[][] B = { { 1, 2, 3 }, { 5, 6, 4 }, { 3, 4, 5 } };
		showMatrix2D(B);
		exchangeRow(B, 1, 2);
		showMatrix2D(B);
		multiplyRow(B, 1, 1.5);
		showMatrix2D(B);
		addMultiplyRow(B, 1, 2, 1.0);
		showMatrix2D(B);
		exchangeColumn(B, 1, 2);
		showMatrix2D(B);
		multiplyColumn(B, 1, 1.5);
		showMatrix2D(B);
		addMultiplyColumn(B, 1, 2, 0.5);
		showMatrix2D(B);
	}

	@Test
	public void test_cloneMatrix() {
		double[][] B = { { 1, 2, 3 }, { 5, 6, 4 }, { 3, 4, 5 } };
		showMatrix2D(B);
		double[][] C = Matrix.clone(B);
		B[0][0] = 0;
		showMatrix2D(B);
		showMatrix2D(C);
		assertTrue(B[0][0] != C[0][0]);
		C[1][1] = 1000;
		showMatrix2D(B);
		showMatrix2D(C);
		assertTrue(B[1][1] != C[1][1]);
	}

	@Test
	public void test_solveOfCramerRule() {
		double A[][] = { { 21.0, 67.0, 88.0, 73.0 },
				{ 76.0, 63.0, 7.0, 20.0 },
				{ 0.0, 85.0, 56.0, 54.0 },
				{ 19.3, 43.0, 30.2, 29.4 }
		};
		double b[] = { 141.0, 109.0, 218.0, 93.7 };
		double x[] = solveOfCramerRule(A, b);
		showMatrix1D(x);
		assertTrue(true);
	}
	
	@Test
	public void test_add() {
		double[][] A = { { 3, 4, 9 }, { 3, 7, 5 }, { 8, 4, 0 } };
		double[][] B = { { 1, 2, 3 }, { 5, 6, 4 }, { 3, 4, 5 } };
		showMatrix2D(A);
		showMatrix2D(B);
		assertFalse(isEqual(A, B));
		double[][] C = add(A,B);
		double[][] D = add(B,A);
		showMatrix2D(C);
		showMatrix2D(D);
		assertTrue(isEqual(C, D));
		double[][] kA = multiply(A, 2);
		double[][] kB = multiply(B, 2);
		showMatrix2D(kA);
		showMatrix2D(kB);
		double[][] A2 = add(A,A);
		double[][] B2 = add(B,B);
		showMatrix2D(A2);
		showMatrix2D(B2);
		assertTrue(isEqual(kA, A2));
		assertTrue(isEqual(kB, B2));
	}
	
	@Test
	public void test_eigenvalues1() {
		show_log = true;
		double[][] A = {
			{ 2, -1,  0},
			{-1,  2, -1},
			{ 0, -1,  2}
		};
		showMatrix2D(A);
		double[] lambda = eigenvalues(A);
		// 3.4142  0.5858	2
		showMatrix1D(lambda);
	}
	
	@Test
	public void test_eigenvalues2() {
		double[][] A = {
			{2, 8, 9},
			{8, 3, 4},
			{9, 4, 7}
		};
		double[] lambda = eigenvalues(A);
		showMatrix1D(lambda);
	}
	
	@Test
	public void test_eigenvalues3() {
		double[][] A = {
			{ 7,  3, -2},
			{ 3,  4, -1},
			{-2, -1,  3}
		};
		double[] lambda = eigenvalues(A);
		showMatrix1D(lambda);
		
		double[][] A2 = {
			{-12,  3,  3},
			{  3,  1, -2},
			{  3, -2,  7}
		};
		double[] lambda2 = eigenvalues(A2);
		showMatrix1D(lambda2);
	}
	
	@Test
	public void test_mainEigenvalues1() {
		show_log = true;
		double[][] A = {
			{ 2, -1,  0},
			{-1,  2, -1},
			{ 0, -1,  2}
		};
		double[] u = {1, 0, 1};
		showMatrix2D(A);
		double mainLambda = mainEigenvalue(A, u);
		// 3.414214
		System.out.println("main eigenvalue is " + mainLambda);
	}
	
	@Test
	public void test_mainEigenvalues2() {
		show_log = true;
		double[][] A = {
			{ 4, -1,  1},
			{-1,  3, -2},
			{ 1, -2,  3}
		};
		double[] u = {1, 1, 1};
		showMatrix2D(A);
		double mainLambda = mainEigenvalue(A, u);
		// 6
		System.out.println("main eigenvalue is " + mainLambda);
		System.out.println(mainLambda-6.0);
	}
	
	@Test
	public void test_mainEigenvalues3() {
		double[][] A = {
			{2, 8, 9},
			{8, 3, 4},
			{9, 4, 7}
		};
		double[] u = {1, 1, 1};
		showMatrix2D(A);
		double mainLambda = mainEigenvalue(A, u);
		System.out.println("main eigenvalue is " + mainLambda);
	}

}
