 
public class zuiXiaoErCheng {
 

	public static void main(String[] args) {

		System.out.println("Put in the number of n:");
		int n = SavitchIn.readLineInt();

		double[] t = { 0, 0, 1, 2, 3, 4, 5, };
		double[] b = { 0, 1, 2.7, 5.8, 6.6, 7.5, 9.9 };
		double[][] a = new double[7][n + 1];

		for (int i = 1; i <= 6; i++) {
			for (int j = 1; j <= n; j++) {
				if (j == 1)
					a[i][1] = 1;
				else
					a[i][j] = Math.pow(t[i], j - 1);

			}
		}
		for (int i = 1; i <= 6; i++) {
			for (int j = 1; j <= n; j++) {
				System.out.print(a[i][j] + "  ");

			}
			System.out.println();
		}
		double[][] at = new double[n + 1][7];
		for (int i = 1; i <= 6; i++) {
			for (int j = 1; j <= n; j++) {
				at[j][i] = a[i][j];

			}

		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= 6; j++) {
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
				for (k1 = 1; k1 <= 6; k1++) {
					a1[i1][j1] += at[i1][k1] * a[k1][j1];
					b1[i1] += at[i1][k1] * b[k1];
				}
			}
		}

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				System.out.print(a1[i][j] + "  ");

			}
			System.out.println();

		}
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
		for (int i = 1; i <= n; i++) {
			System.out.println(x[i]);
		}
		System.out.println("put in a number of t:");
		double te = SavitchIn.readLineDouble();
		double sum = 0;
		;
		for (int i = 1; i <= n; i++) {

			sum += x[i] * Math.pow(te, i - 1);

		}
		System.out.println(sum);

	}

	 
	 
}