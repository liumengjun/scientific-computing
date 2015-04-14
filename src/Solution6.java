public class Solution6 {

	public static void main(String[] args) {
		int n = 2;
		double[] b = { 0, 0.26, 0.28, 3.31 };
		// double[] b={0,0.27,0.25,3.33};
		double[][] a = new double[4][n + 1];

		a[1][1] = 0.16;
		a[1][2] = 0.10;
		a[2][1] = 0.17;
		a[2][2] = 0.11;
		a[3][1] = 2.02;
		a[3][2] = 1.29;
		System.out.println("This is 矩阵 a");
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= n; j++) {
				System.out.print(a[i][j] + "  ");

			}
			System.out.println();
		}
		double[][] at = new double[n + 1][4];
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= n; j++) {
				at[j][i] = a[i][j];

			}

		}
		System.out.println("This is a 的转置");
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= 3; j++) {
				System.out.print(at[i][j] + "  ");

			}
			System.out.println();

		}
		double[][] a1 = new double[n + 1][n + 1];
		double[] b1 = new double[n + 1];
		int i1, j1, k1;
		for (i1 = 1; i1 <= n; i1++) {
			for (j1 = 1; j1 <= n; j1++) {
				a1[i1][j1] = 0;
				b1[i1] = 0;
				for (k1 = 1; k1 <= 3; k1++) {
					a1[i1][j1] += at[i1][k1] * a[k1][j1];
					b1[i1] += at[i1][k1] * b[k1];
				}
			}
		}
		System.out.println("This is A{a的转置与a的乘积）");
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				System.out.print(a1[i][j] + "  ");

			}
			System.out.println();

		}
		System.out.println("This is B{a的转置与b的乘积）");
		for (int i = 1; i <= n; i++)

			System.out.println(b1[i] + "  ");

		/** ******************************************* */

		double[][] m = new double[n + 1][n + 1];
		double[][] l = new double[n + 1][n + 1];
		for (int m1 = 1; m1 <= n; m1++) {

			for (int m2 = 1; m2 <= n; m2++) {
				if (m1 == m2) {
					m[m1][m2] = 1;
					l[m1][m2] = 1;

				} else {
					m[m1][m2] = 0;
					l[m1][m2] = 0;
				}
			}

		}
		for (int k = 1; k <= n - 1; k++) {
			if (a1[k][k] == 0)
				break;
			for (int i = k + 1; i <= n; i++) {
				m[i][k] = -a1[i][k] / a1[k][k];
				l[i][k] = a1[i][k] / a1[k][k];

			}
			for (int j = k + 1; j <= n; j++) {
				for (int i = k + 1; i <= n; i++) {
					a1[i][j] = a1[i][j] + m[i][k] * a1[k][j];
				}

			}
		}
		/** ********* */
		double[] x = new double[n + 1];
		double[] y = new double[n + 1];

		for (int j = 1; j <= n; j++) {
			if (l[j][j] == 0)
				break;
			y[j] = b1[j] / l[j][j];
			for (int i = j + 1; i <= n; i++) {
				b1[i] = b1[i] - l[i][j] * (y[j]);
			}
		}
		for (int j = n; j >= 1; j--) {
			if (a1[j][j] == 0)
				break;
			x[j] = y[j] / a1[j][j];
			for (int i = 1; i <= j - 1; i++) {
				y[i] = y[i] - a1[i][j] * (x[j]);
			}
		}
		System.out.println("This is x");
		for (int i = 1; i <= n; i++) {
			System.out.println(x[i]);
		}
		System.out.println("put in a number for x1:");
		double x1 = SavitchIn.readLineDouble();
		System.out.println("put in a number for x2:");
		double x2 = SavitchIn.readLineDouble();
		double sum = x1 * x[1] + x2 * x[2];

		System.out.println(sum);

	}
}
