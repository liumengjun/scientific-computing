public class Solution3 {
	public static void main(String[] args) {
		double[] t = { 0, 0.0, 0.5, 1.0, 6.0, 7.0, 9.0 };
		double[] y1 = { 0, 0.0, 1.6, 2.0, 2.0, 1.5, 0.0, };

		double[][] a = new double[7][7];
		double[] tem = { 1.0, 1, 1, 1, 1, 1, 1 };
		for (int j = 1; j <= 6; j++) {
			for (int k = 1; k <= j; k++) {
				if (k == 1)
					a[j][k] = 1;
				else {
					 
					a[j][k] =  a[j][k - 1] * (t[j] - t[k - 1]);
				}

				System.out.print(a[j][k] + " ");
			}
			System.out.println();
		}
		int n = 6;
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
			for (int j = k + 1; j <= n; j++) {
				for (int i = k + 1; i <= n; i++) {
					a[i][j] = a[i][j] + m[i][k] * a[k][j];
				}

			}
		}

		double[] x = new double[n + 1];
		double[] y = new double[n + 1];
		double[] b = new double[n + 1];
		 
		for (int i = 1; i <= n; i++) {
			b[i] = y1[i];
		}
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
		for (int i = 1; i <= n; i++) {
			System.out.println(x[i]);
		}
		System.out.println("put in a number of t:");
		double te = SavitchIn.readLineDouble();
		int tet = 1;
		double[][] aa = new double[n + 1][n + 1];
		for (int j = 1; j <= 6; j++) {
			for (int k = 1; k <= j; k++) {
				if (k == 1)
					aa[j][k] = 1;
				else {
					 
					aa[j][k] = aa[j][k - 1] * (te - t[k - 1]);
				}

				 
			}

		}
		double ttt=0;
		for(int i=1;i<=n;i++){
			for(int j=1;j<=n;j++){
				if(i==j)
				ttt=ttt+x[i]*aa[i][j];
			}
			
		}
		System.out.println(ttt);

	}
}