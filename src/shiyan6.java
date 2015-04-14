
public class shiyan6 
{

	/**
	 * @param args
	 */
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		double[][] a = new double[3][2];
		System.out.println("请输入矩阵A的各个值a[][]:");
		for (int i = 0; i <3; i++) 
		{
			for (int j =0; j <2; j++) 
			{
				System.out.println("a"+(i+1)+(j+1)+"=");
				int m = SavitchIn.readLineInt();
				a[i][j]=m;
			}
		}
		
		
		
		
		
		
		//打印出矩阵A1
		for (int i = 0; i <3; i++) 
		{
			for (int j =0; j <2; j++) 
			{
				System.out.print(a[i][j]+ "  ");

			}
			System.out.println();
		}
		//求矩阵A1的转置	
		double[][] at = new double[2][3];
		for (int j =0; j <2; j++) 
		{
			for (int i =0; i <=3; i++) 
			{
				at[j][i] = a[i][j];

			}

		}
		//打印出矩阵A1的转置
		for (int j =0; j <2; j++) 
		{
			for (int i =0; i <=3; i++) 
			{
				System.out.print(at[j+1][i+1] + "  ");

			}
			System.out.println();

		}
		
		//*double[][] a1 = new double[n + 1][n + 1];
		double[] b1= new double[3];
		for (int j =0; j <3; j++) 
		{
			System.out.println("b"+(j+1)+"=");
			int m = SavitchIn.readLineInt();
			b1[j]=m;
		}
      //打印出b1
		for (int j =0; j <3; j++) 
		{
			System.out.print(b1[j]+ "  ");

		}
		System.out.println();
		
		double[][] A = new double[2][2];
		double[] b= new double[3];
		for (int i1 = 0; i1 <2; i1++) 
		{
			for (int j1 =0; j1 <2; j1++) 
			{
				//a1[i1][j1] = 0;
				//b1[i1] = 0;
				for (int k1 = 0; k1 <3; k1++) 
				{
					A[i1][j1] += at[i1][k1] * a[k1][j1];
					b1[i1] += at[i1][k1] * b[k1];
				}
			}
		}
		

		for (int i = 0; i <2; i++) 
		{
			for (int j = 1; j <2; j++) 
			{
				System.out.print(A[i][j] + "  ");

			}
			System.out.println();

		}
		for (int i =0; i <2; i++)

			System.out.println(b[i] + "  ");

		/** ******************************************* */

		double[][] m = new double[1 + 1][1 + 1];
		for (int m1 = 1; m1 <= 1; m1++) {

			for (int m2 = 1; m2 <= 1; m2++) {
				if (m1 == m2) {
					m[m1][m2] = 1;

				} else 
				{
					m[m1][m2] = 0;
				}
			}

		}
		for (int k = 1; k <= 1; k++) 
		{
			if (A[k][k] == 0)
				break;
			for (int i = k + 1; i <= 1; i++) 
			{
				m[i][k] = -A[i][k] / A[k][k];
				m[i][k] = A[i][k] / A[k][k];

			}
			for (int j = k + 1; j <= 1; j++) 
			{
				for (int i = k + 1; i <= 1; i++) 
				{
					A[i][j] = A[i][j] + m[i][k] * A[k][j];
				}

			}
		}
		/** ********* */
		double[] x = new double[1 + 1];
		double[] y = new double[1 + 1];

		for (int j = 1; j <= 1; j++) {
			if (m[j][j] == 0)
				break;
			y[j] = b1[j] / m[j][j];
			for (int i = j + 1; i <=1; i++) {
				b1[i] = b1[i] - m[i][j] * (y[j]);
			}
		}
		for (int j = 1; j >= 1; j--) {
			if (A[j][j] == 0)
				break;
			x[j] = y[j] / A[j][j];
			for (int i = 1; i <= j - 1; i++) {
				y[i] = y[i] - A[i][j] * (x[j]);
			}
		}
		for (int i = 1; i <=1; i++) {
			System.out.println(x[i]);
		}
		System.out.println("put in a number of t:");
		double te = SavitchIn.readLineDouble();
		double sum = 0;
		;
		for (int i = 1; i <=1; i++) {

			sum += x[i] * Math.pow(te, i - 1);

		}
		System.out.println(sum); 
		
		


	}

}
