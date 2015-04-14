public class InterPolatation 
{
	public static void main(String[] args) 
	{
		GaussianElimination gauss=new GaussianElimination();
		int n=6;
		double[] t = { 0.0, 0.5, 1.0, 6.0, 7.0, 9.0 };
		double[] ft = { 0.0, 1.6, 2.0, 2.0, 1.5, 0.0 };
		//牛顿插值矩阵
		double[][] a = new double[6][6];

        //生成牛顿插值矩阵；下三角矩阵
		for (int j = 0; j < 6; j++) 
		{
			a[j][0] = 1;
			for (int k = 1; k <= j; k++) 
			{
				a[j][k] = a[j][k - 1] * (t[j] - t[k - 1]);
			}
		}
		System.out.println("A是:");
		gauss.showArray2D(a);
		
		//高斯消元,前代
		double[] x = new double[n];
		gauss.forwordSubstitution(a, ft, x);
		//输出x
		System.out.println("x是:");
		gauss.showArray1D(x);
		
		System.out.println("输入你想求的t点,将得到f(t):");
		double tt = SavitchIn.readLineDouble();
		double ftt=0;
		double[] pt=new double[n];//p(t)多项式
		pt[0]=1;
		for(int i=1;i<n;i++)
			pt[i]=pt[i-1]*(tt-t[i-1]);
		
		for(int i=0;i<n;i++)
			ftt+=pt[i]*x[i];
		
		System.out.println(ftt);

	}
}