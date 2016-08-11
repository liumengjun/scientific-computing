package mat;


/**
 * Linear System	���Է�����
 * �ṩ������Է�����ĸ��ַ���,�Լ�һЩ�������
 * @author liumengjun
 * 
 */
public class LinearSystem {
	
	/**
	 * ����������A����Cholesky�ֽ�,��LLT�ֽ�
	 * @param A n*n��������
	 * @return L:�����Ǿ���
	 */
	public static double[][] CholeskyLLT(double[][] A){
		int n=A.length;
		if(n!=A[0].length){
			System.out.println("Cholesky LLT error:���������Ƿ���!!!");
			return null;
		}
		double[][] L=new double[n][n];
		for(int i=0;i<n;i++)	//�������A
			for(int j=0;j<n;j++)
				L[i][j]=A[i][j];
		
		for(int k=0;k<n;k++){
			if(L[k][k]<0){
				System.out.println("Cholesky LLT error:������������������!!!");
				return null;
			}
			L[k][k]=Math.sqrt(L[k][k]);//������
			for(int j=k+1;j<n;j++)//����k��A[k][k]���Ԫ����0
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
	 * �ش� ��� U*x=b
	 * @param U ��֪��,n*n�����Ǿ���
	 * @param b ��֪��,n*1����
	 * @return x δ֪��,n*1����,������
	 */
	public static double[] backSubstitution(double U[][],double b[]){
		int n=U.length;
		if(n!=U[0].length || n!=b.length){
			System.out.println("Back Substitution error:������ĳ��Ȳ�һ��!!");
			return null;
		}
		double[] x=new double[n];
		double[] bb=new double[n];
		for(int i=0;i<n;i++)//����b������bb
			bb[i]=b[i];
		
		for(int j=n-1;j>=0;j--){
			if(U[j][j]==0){
				System.out.println("Back Substitution error:Nonsinglar matrix(�������)!!!");
				return null;
			}
			x[j]=bb[j]/U[j][j];
			for(int i=0;i<j;i++)
				bb[i]=bb[i]-U[i][j]*x[j];
		}
		return x;
	}
	
	/**
	 * ǰ�� ���L*x=b
	 * @param L ��֪��,n*n�����Ǿ���
	 * @param b ��֪��,n*1����
	 * @return x δ֪��,n*1����,������
	 */
	public static double[] forwordSubstitution(double L[][],double b[]){
		int n=L.length;
		if(n!=L[0].length || n!=b.length){
			System.out.println("Forword Substitution error:������ĳ��Ȳ�һ��!!");
			return null;
		}
		double[] x=new double[n];
		double[] bb=new double[n];
		for(int i=0;i<n;i++)//����b������bb
			bb[i]=b[i];
		
		for(int j=0;j<n;j++){
			if(L[j][j]==0){
				System.out.println("Forword Substitution error:Nonsinglar matrix(�������)!!!");
				return null;
			}
			x[j]=bb[j]/L[j][j];
			for(int i=j+1;i<n;i++)
				bb[i]=bb[i]-L[i][j]*x[j];
		}
		return x;
	}
	
	/**
	 * �ø�˹��Ԫ������Է�����(Linear systems) A*x=b
	 * @param A ��֪��,n*n����
	 * @param b ��֪��,n*1����
	 * @return x δ֪��,n*1����,������
	 */
	public static double[] gaussEliminate(double A[][],double b[]){
		int n=A.length;
		if(n!=A[0].length || n!=b.length){
			System.out.println("Gauss Eliminate error:������ĳ��Ȳ�һ��!!");
			return null;
		}
		double mk[][]=new double[n][n];//��ȥ����
		double AA[][]=new double[n][n];
		for(int i=0;i<n;i++)	//����A����AA
			for(int j=0;j<n;j++)
				AA[i][j]=A[i][j];
		double[] bb=new double[n];
		for(int i=0;i<n;i++)//����b������bb
			bb[i]=b[i];
		
		//��A��b���и�˹��Ԫ
		for(int k=0;k<n-1;k++){
			int p=k;
			//ѡ��Ԫ
			for(int max=p+1;max<n;max++){
				if(Math.abs(AA[max][k])>Math.abs(AA[p][k]))
					p=max;
			}
			//����
			if(p!=k){
				double temp;
				for(int j=0;j<n;j++){//����A
					temp=AA[k][j];
					AA[k][j]=AA[p][j];
					AA[p][j]=temp;
				}
				temp=bb[k];//����b
				bb[k]=bb[p];
				bb[p]=temp;
			}
			if(AA[k][k]==0){
				System.out.println("Gauss Eliminate error:Nonsinglar matrix(�������)!!!");
				return null;
			}
			for(int i=k+1;i<n;i++)
				mk[i][k]=AA[i][k]/AA[k][k];
			for(int i=k+1;i<n;i++)//��AA[k][k]�����Ԫ������
				AA[i][k]=0;
			for(int j=k+1;j<n;j++)//�б任AA
				for(int i=k+1;i<n;i++)
					AA[i][j]=AA[i][j]-mk[i][k]*AA[k][j];
			for(int i=k+1;i<n;i++)//�б任bb
				bb[i]=bb[i]-mk[i][k]*bb[k];
		}
		//����AA�����������ûش����AA*x=bb
		return backSubstitution(AA,bb);
	}
	
	/**
	 * ����A��LU�ֽ�(LU Factorization) A=L*U<br>
	 * �ø�˹��Ԫ(Gaussian Elimination)�������<br>
	 * �����ڷ���
	 * @param A double[][] ���ֽ����,����n*n����
	 * @param L double[][] �ֽ��,���ڴ洢�����Ǿ���,����n*n����
	 * @param U double[][] �ֽ��,���ڴ洢�����Ǿ���,����n*n����
	 * @return boolean �ɹ�����true, ���false
	 */
	public static boolean LUFactorization(double[][] A, double[][] L, double[][] U){
		boolean success = true;
		int n=A.length;
		if(n!=A[0].length || n!=L.length || n!=L[0].length
				|| n!=U.length || n!=U[0].length){
			System.out.println("LU Factorization error:������ĳ��Ȳ�һ��!!");
			return false;
		}
		double mk[][]=new double[n][n];//��ȥ����
		for(int i=0;i<n;i++)	//����A����U,����Ӱ��A��Ԫ��ֵ
			for(int j=0;j<n;j++)
				U[i][j]=A[i][j];
		
		//��U���и�˹��Ԫ
		for(int k=0;k<n-1;k++){
			int p=k;
			//ѡ��Ԫ
			for(int max=p+1;max<n;max++){
				if(Math.abs(U[max][k])>Math.abs(U[p][k]))
					p=max;
			}
			//����
			if(p!=k){
				double temp;
				for(int j=0;j<n;j++){//����A
					temp=U[k][j];
					U[k][j]=U[p][j];
					U[p][j]=temp;
				}
			}
			if(U[k][k]==0){
				System.out.println("LU Factorization error:Nonsinglar matrix(�������)!!!");
				return false;
			}
			for(int i=k+1;i<n;i++)
				mk[i][k]=U[i][k]/U[k][k];
			for(int i=k+1;i<n;i++)//��U[k][k]�����Ԫ������
				U[i][k]=0;
			for(int j=k+1;j<n;j++)//�б任U
				for(int i=k+1;i<n;i++)
					U[i][j]=U[i][j]-mk[i][k]*U[k][j];
		}//����U����������
		//����ȥ����mk[][]��Ԫ��ֵ���浽L[][]������L[i][i]=1.0
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++)
				L[i][j]=mk[i][j];
			L[i][i] = 1.0;
		}
		return success;
	}
	
	/**
	 * ��Gauss_Seidel�����������A*x=b;<br>Ĭ��(��x=x-init��2_norm�ݲ�Ϊ1*10^-8)
	 * @param A n*n�ķ��������(non singular matrix)
	 * @param b n*1����
	 * @param init n*1����;��ʼ�²�ֵ
	 * @param max ����������
	 * @return n*1�ľ���,A*x=b�Ľ��ƽ�
	 */
	public static double[] GaussSeidelIteration(double[][] A,double[] b,double[] init,int max){
		int n=A.length;
		if(n!=A[0].length || n!=b.length || n!=init.length){
			System.out.println("Gauss_Seidel error:������ĳ��Ȳ�һ��!!");
			return null;
		}
		double[] x=new double[n];
		double[] dx=new double[n];
		double bb;//�洢b[i]
		double delta=1E-8; //����ǰ���x(��x-init)�����̶�
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
			}//��(index+1)��
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
	 * ��Jacobi�����������A*x=b;<br>Ĭ��(��x=x-init��2_norm�ݲ�Ϊ1*10^-5)
	 * @param A n*n�ķ��������(non singular matrix)
	 * @param b n*1����
	 * @param init n*1����;��ʼ�²�ֵ
	 * @param max ����������
	 * @return n*1�ľ���,A*x=b�Ľ��ƽ�
	 */
	public static double[] JacobiIteration(double[][] A,double[] b,double[] init,int max){
		int n=A.length;
		if(n!=A[0].length || n!=b.length || n!=init.length){
			System.out.println("Jacobi Iteration error:������ĳ��Ȳ�һ��!!");
			return null;
		}
		double[] x=new double[n];
		double[] dx=new double[n];
		double bb;//�洢b[i]
		double delta=1E-5; //����ǰ���x(��x-init)�����̶�
		for(int index=0;index<max;index++){
			for(int i=0;i<n;i++){
				bb=b[i];
				for(int j=0;j<n;j++){
					if(i!=j)
						bb=bb-A[i][j]*init[j];
				}
				x[i]=bb/A[i][i];
			}//��(index+1)��
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
		System.out.println("A��:");
		Matrix.showMatrix2D(A);
		System.out.println("b��:");
		Matrix.showMatrix1D(b);
		System.out.println("The answer is(����):");
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
		System.out.println("��֤L*U:");
		Matrix.showMatrix2D(Matrix.matrixMultiply(L, U),true);
	}
}
