package mat;


/**
 * Linear System	线性方程组
 * 提供求解线性方程组的各种方法,以及一些特殊矩阵
 * @author liumengjun
 * 
 */
public class LinearSystem {
	
	/**
	 * 将正定矩阵A进行Cholesky分解,即LLT分解
	 * @param A n*n正定矩阵
	 * @return L:下三角矩阵
	 */
	public static double[][] CholeskyLLT(double[][] A){
		int n=A.length;
		if(n!=A[0].length){
			System.out.println("Cholesky LLT error:参数矩阵不是方阵!!!");
			return null;
		}
		double[][] L=new double[n][n];
		for(int i=0;i<n;i++)	//保存矩阵A
			for(int j=0;j<n;j++)
				L[i][j]=A[i][j];
		
		for(int k=0;k<n;k++){
			if(L[k][k]<0){
				System.out.println("Cholesky LLT error:参数矩阵不是正定矩阵!!!");
				return null;
			}
			L[k][k]=Math.sqrt(L[k][k]);//开根方
			for(int j=k+1;j<n;j++)//将第k行A[k][k]后的元素置0
				L[k][j]=0;
			for(int i=k+1;i<n;i++)
				L[i][k]=L[i][k]/L[k][k];
			for(int j=k+1;j<n;j++)
				for(int i=k+1;i<n;i++)
					L[i][j]=L[i][j]-L[i][k]*L[j][k];
		}
		return L;
	}
	
	/**
	 * 回代 求解 U*x=b
	 * @param U 已知量,n*n上三角矩阵
	 * @param b 已知量,n*1向量
	 * @return x 未知量,n*1向量,保存结果
	 */
	public static double[] backSubstitution(double U[][],double b[]){
		int n=U.length;
		if(n!=U[0].length || n!=b.length){
			System.out.println("Back Substitution error:各矩阵的长度不一致!!");
			return null;
		}
		double[] x=new double[n];
		double[] bb=new double[n];
		for(int i=0;i<n;i++)//保存b向量到bb
			bb[i]=b[i];
		
		for(int j=n-1;j>=0;j--){
			if(U[j][j]==0){
				System.out.println("Back Substitution error:Nonsinglar matrix(奇异矩阵)!!!");
				return null;
			}
			x[j]=bb[j]/U[j][j];
			for(int i=0;i<j;i++)
				bb[i]=bb[i]-U[i][j]*x[j];
		}
		return x;
	}
	
	/**
	 * 前代 求解L*x=b
	 * @param L 已知量,n*n下三角矩阵
	 * @param b 已知量,n*1向量
	 * @return x 未知量,n*1向量,保存结果
	 */
	public static double[] forwordSubstitution(double L[][],double b[]){
		int n=L.length;
		if(n!=L[0].length || n!=b.length){
			System.out.println("Forword Substitution error:各矩阵的长度不一致!!");
			return null;
		}
		double[] x=new double[n];
		double[] bb=new double[n];
		for(int i=0;i<n;i++)//保存b向量到bb
			bb[i]=b[i];
		
		for(int j=0;j<n;j++){
			if(L[j][j]==0){
				System.out.println("Forword Substitution error:Nonsinglar matrix(奇异矩阵)!!!");
				return null;
			}
			x[j]=bb[j]/L[j][j];
			for(int i=j+1;i<n;i++)
				bb[i]=bb[i]-L[i][j]*x[j];
		}
		return x;
	}
	
	/**
	 * 用高斯消元求解线性方程组(Linear systems) A*x=b
	 * @param A 已知量,n*n矩阵
	 * @param b 已知量,n*1向量
	 * @return x 未知量,n*1向量,保存结果
	 */
	public static double[] gaussEliminate(double A[][],double b[]){
		int n=A.length;
		if(n!=A[0].length || n!=b.length){
			System.out.println("Gauss Eliminate error:各矩阵的长度不一致!!");
			return null;
		}
		double mk[][]=new double[n][n];//消去矩阵
		double AA[][]=new double[n][n];
		for(int i=0;i<n;i++)	//保存A矩阵到AA
			for(int j=0;j<n;j++)
				AA[i][j]=A[i][j];
		double[] bb=new double[n];
		for(int i=0;i<n;i++)//保存b向量到bb
			bb[i]=b[i];
		
		//对A与b进行高斯消元
		for(int k=0;k<n-1;k++){
			int p=k;
			//选主元
			for(int max=p+1;max<n;max++){
				if(Math.abs(AA[max][k])>Math.abs(AA[p][k]))
					p=max;
			}
			//交换
			if(p!=k){
				double temp;
				for(int j=0;j<n;j++){//交换A
					temp=AA[k][j];
					AA[k][j]=AA[p][j];
					AA[p][j]=temp;
				}
				temp=bb[k];//交换b
				bb[k]=bb[p];
				bb[p]=temp;
			}
			if(AA[k][k]==0){
				System.out.println("Gauss Eliminate error:Nonsinglar matrix(奇异矩阵)!!!");
				return null;
			}
			for(int i=k+1;i<n;i++)
				mk[i][k]=AA[i][k]/AA[k][k];
			for(int i=k+1;i<n;i++)//将AA[k][k]下面的元素置零
				AA[i][k]=0;
			for(int j=k+1;j<n;j++)//行变换AA
				for(int i=k+1;i<n;i++)
					AA[i][j]=AA[i][j]-mk[i][k]*AA[k][j];
			for(int i=k+1;i<n;i++)//行变换bb
				bb[i]=bb[i]-mk[i][k]*bb[k];
		}
		//现在AA是上三角阵，用回代求解AA*x=bb
		return backSubstitution(AA,bb);
	}
	
	/**
	 * 矩阵A的LU分解(LU Factorization) A=L*U<br>
	 * 用高斯消元(Gaussian Elimination)方法求解<br>
	 * 仅限于方阵
	 * @param A double[][] 待分解矩阵,必须n*n矩阵
	 * @param L double[][] 分解后,用于存储下三角矩阵,必须n*n矩阵
	 * @param U double[][] 分解后,用于存储上三角矩阵,必须n*n矩阵
	 * @return boolean 成功返回true, 否侧false
	 */
	public static boolean LUFactorization(double[][] A, double[][] L, double[][] U){
		boolean success = true;
		int n=A.length;
		if(n!=A[0].length || n!=L.length || n!=L[0].length
				|| n!=U.length || n!=U[0].length){
			System.out.println("LU Factorization error:各矩阵的长度不一致!!");
			return false;
		}
		double mk[][]=new double[n][n];//消去矩阵
		for(int i=0;i<n;i++)	//保存A矩阵到U,不会影响A中元素值
			for(int j=0;j<n;j++)
				U[i][j]=A[i][j];
		
		//对U进行高斯消元
		for(int k=0;k<n-1;k++){
			int p=k;
			//选主元
			for(int max=p+1;max<n;max++){
				if(Math.abs(U[max][k])>Math.abs(U[p][k]))
					p=max;
			}
			//交换
			if(p!=k){
				double temp;
				for(int j=0;j<n;j++){//交换A
					temp=U[k][j];
					U[k][j]=U[p][j];
					U[p][j]=temp;
				}
			}
			if(U[k][k]==0){
				System.out.println("LU Factorization error:Nonsinglar matrix(奇异矩阵)!!!");
				return false;
			}
			for(int i=k+1;i<n;i++)
				mk[i][k]=U[i][k]/U[k][k];
			for(int i=k+1;i<n;i++)//将U[k][k]下面的元素置零
				U[i][k]=0;
			for(int j=k+1;j<n;j++)//行变换U
				for(int i=k+1;i<n;i++)
					U[i][j]=U[i][j]-mk[i][k]*U[k][j];
		}//现在U是上三角阵
		//将消去矩阵mk[][]的元素值保存到L[][]，并置L[i][i]=1.0
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++)
				L[i][j]=mk[i][j];
			L[i][i] = 1.0;
		}
		return success;
	}
	
	/**
	 * 用Gauss_Seidel迭代方法求解A*x=b;<br>默认(△x=x-init的2_norm容差为1*10^-8)
	 * @param A n*n的非奇异矩阵(non singular matrix)
	 * @param b n*1矩阵
	 * @param init n*1矩阵;初始猜测值
	 * @param max 最大迭代次数
	 * @return n*1的矩阵,A*x=b的近似解
	 */
	public static double[] GaussSeidelIteration(double[][] A,double[] b,double[] init,int max){
		int n=A.length;
		if(n!=A[0].length || n!=b.length || n!=init.length){
			System.out.println("Gauss_Seidel error:各矩阵的长度不一致!!");
			return null;
		}
		double[] x=new double[n];
		double[] dx=new double[n];
		double bb;//存储b[i]
		double delta=1E-8; //迭代前后△x(即x-init)的容忍度
		for(int index=0;index<max;index++){
			for(int i=0;i<n;i++){
				bb=b[i];
				for(int j=0;j<n;j++){
					if(j<i)
						bb=bb-A[i][j]*x[j];
					else if(j==i)
						continue;
					else
						bb=bb-A[i][j]*init[j];
				}
				x[i]=bb/A[i][i];
			}//第(index+1)次
			for(int i=0;i<n;i++){
				dx[i]=x[i]-init[i];
				init[i]=x[i];
			}
			double err=Matrix.norm_2(dx);
			if(err<delta)
				break;
		}
		return x;
	}
	
	/**
	 * 用Jacobi迭代方法求解A*x=b;<br>默认(△x=x-init的2_norm容差为1*10^-5)
	 * @param A n*n的非奇异矩阵(non singular matrix)
	 * @param b n*1矩阵
	 * @param init n*1矩阵;初始猜测值
	 * @param max 最大迭代次数
	 * @return n*1的矩阵,A*x=b的近似解
	 */
	public static double[] JacobiIteration(double[][] A,double[] b,double[] init,int max){
		int n=A.length;
		if(n!=A[0].length || n!=b.length || n!=init.length){
			System.out.println("Jacobi Iteration error:各矩阵的长度不一致!!");
			return null;
		}
		double[] x=new double[n];
		double[] dx=new double[n];
		double bb;//存储b[i]
		double delta=1E-5; //迭代前后△x(即x-init)的容忍度
		for(int index=0;index<max;index++){
			for(int i=0;i<n;i++){
				bb=b[i];
				for(int j=0;j<n;j++){
					if(i!=j)
						bb=bb-A[i][j]*init[j];
				}
				x[i]=bb/A[i][i];
			}//第(index+1)次
			for(int i=0;i<n;i++){
				dx[i]=x[i]-init[i];
				init[i]=x[i];
			}
			double err=Matrix.norm_2(dx);
			if(err<delta)
				break;
		}
		return x;
	}
	
	//main
	public static void main(String args[]){
		/*
		double A[][]={{21.0,67.0,88.0,73.0},
					{76.0,63.0,7.0,20.0},
					{0.0,85.0,56.0,54.0},
					{19.3,43.0,30.2,29.4}};
		double b[]={141.0,109.0,218.0,93.7};
		double x[];
		x=gaussEliminate(A, b);
		System.out.println("A是:");
		Matrix.showMatrix2D(A);
		System.out.println("b是:");
		Matrix.showMatrix1D(b);
		System.out.println("The answer is(答案是):");
		Matrix.showMatrix1D(x,true);
		*/
		int n = 3;
		double[][] A={{10,-2,-2},
					  {-2,10,-1},
					  {-1,-2,3}};
		/*
		double[] b={1,0.5,1};
		double[] init={1,1,1};
		int max=20;
		double[] x=GaussSeidelIteration(A, b, init, max);
		Matrix.showMatrix1D(x);
		*/
		double[][] L = new double[n][n];
		double[][] U = new double[n][n];
		LUFactorization(A, L, U);
		System.out.println("A:");
		Matrix.showMatrix2D(A);
		System.out.println("L:");
		Matrix.showMatrix2D(L);
		System.out.println("U:");
		Matrix.showMatrix2D(U);
		System.out.println("验证L*U:");
		Matrix.showMatrix2D(Matrix.matrixMultiply(L, U),true);
	}
}
