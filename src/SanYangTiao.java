public class SanYangTiao {
	public static void main(String[] args) {
		double[][] a = new double[9][9];
		a[1][1] = 1;
		a[2][1] = 1;
		a[2][2] = -2;
		a[2][3] = 4;
		a[2][4] = -8;
		a[3][3] = 2;
		a[3][7] = -2;
		a[4][3] = 2;
		a[4][4] = -12;
		a[5][5] = 1;
		a[6][2] = 1;
		a[6][6] = -1;
		a[7][7] = 2;
		a[7][8] = 6;
		a[8][5] = 1;
		a[8][6] = 1;
		a[8][7] = 1;
		a[8][8] = 1;

		int n = 8;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {

				System.out.print(a[i][j] + "  ");

			}
			System.out.println();

		}

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
			if (a[k][k] == 0)
				break;

			for (int i = k + 1; i <= n; i++) {
				m[i][k] = -a[i][k] / a[k][k];
				l[i][k] = a[i][k] / a[k][k];

			}
			for (int j = k + 1; j <= n; j++)
				for (int i = k + 1; i <= n; i++)
					a[i][j] = a[i][j] + m[i][k] * a[k][j];

		}
		double[] x = new double[n + 1];
		double[] y = new double[n + 1];
		double[] b = new double[n + 1];

		b[1] = -1;
		b[2] = -27;

		for (int j = 1; j <= n; j++) {
			if (l[j][j] == 0)
				break;
			y[j] = b[j] / l[j][j];
			for (int i = j + 1; i <= n; i++) {
				b[i] = b[i] - l[i][j] * (y[j]);
			}
		}
		for (int j = n; j >= 1; j--) {
			if (a[j][j] == 0)
				break;
			x[j] = y[j] / a[j][j];
			for (int i = 1; i <= j - 1; i++) {
				y[i] = y[i] - a[i][j] * (x[j]);
			}
		}
		System.out.println("a1  " + x[2]);
		System.out.println("a2  " + x[1]);
		System.out.println("a3  " + x[5]);
		System.out.println("a4  " + x[8]);
		System.out.println("b1  " + x[6]);
		System.out.println("b2  " + x[3]);
		System.out.println("b3  " + x[4]);
		System.out.println("b5  " + x[7]);

	}
}
