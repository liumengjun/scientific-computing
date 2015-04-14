package mat;

import java.io.IOException;
import java.util.StringTokenizer;


public class Matrix {
	
	/**
	 * complement(or cofactor,minor:余子式)<br>
	 * @param A double[][] 矩阵
	 * @param i int 决定行
	 * @param j int 决定列
	 * @return Mij double[][] 矩阵A[][]的余子式 Mij
	 */
	public static double[][] complement(double[][] A, int i, int j){
		if( A == null ) return null;
		int m = A.length, n = A[0].length;
		//若A是单行，或单列矩阵，或i,j越界 return null
		if(m<2 || n<2 || m<i || n<j || i<1 || j<1) return null;
		double[][] Mij = new double[n-1][n-1];
		// M(i,j):  A[][]去掉 第i行，第j列
		i--;
		j--;
		int new_i,new_j;
		for(int k=0; k<m; k++){
			if(k == i) continue;	//去掉第 i 行A[i][]
			if(k > i) new_i = k - 1;//将第 k 行移到 k-1 行
			else new_i = k;
			for(int l=0; l<n; l++){
				if(l == j ) continue;	//去掉第 j 列A[][j]
				if(l > j) new_j = l - 1;//将第 l 列移到 l-1 行
				else new_j = l;
				Mij[new_i][new_j] = A[k][l];
			}
		}
		return Mij;
	}
	
	/**
	 * 计算方阵A的行列式(determinant)<br>
	 * 用高斯消元发，得到上三角矩阵U,再求U的行列式
	 * @param A double[][] n*n矩阵
	 * @return |A| double 方阵A的行列式
	 */
	public static double det(double[][] phalanx){
		int n=phalanx[0].length;
		if(phalanx.length != n){
			System.out.println("矩阵A不是方阵!!!");
			return 0;
		}
		
		double[][] A=new double[n][n];//新建矩阵A,保存原矩阵
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				A[i][j]=phalanx[i][j];
		
		boolean exchanged=false;//记录是否进行了'行交换'
		double mk[][]=new double[n][n];//消去矩阵
		//对A进行高斯消元
		for(int k=0;k<n-1;k++){
			int p=k;
			//选主元
			for(int max=p+1;max<n;max++){
				if(Math.abs(A[max][k])>Math.abs(A[p][k]))
					p=max;
			}
			//交换
			if(p!=k){
				double temp;
				for(int j=0;j<n;j++){//交换A
					temp=A[k][j];
					A[k][j]=A[p][j];
					A[p][j]=temp;
				}
				exchanged=!exchanged;
			}
			if(A[k][k]==0)//Non_singlar matrix(奇异矩阵)
				return 0;
			
			for(int i=k+1;i<n;i++)
				mk[i][k]=A[i][k]/A[k][k];
			for(int j=k+1;j<n;j++)//行变换A
				for(int i=k+1;i<n;i++)
					A[i][j]=A[i][j]-mk[i][k]*A[k][j];
		}
		//现在A是上三角阵
		double det=1;
		for(int i=0;i<n;i++)
			det*=A[i][i];
		if(exchanged)
			return -det;
		return det;
	}
	
	/**
	 * 计算方阵A的行列式(determinant)<br>
	 * 用代数余子式的方法计算，递归实现
	 * @param A n*n矩阵
	 * @return |A| 方阵A的行列式
	 */
	public static double det2(double[][] A){
		int n=A[0].length;
		if(A.length != n){
			System.out.println("矩阵A不是方阵!!!");
			return 0;
		}
		//	|A| = a(i,1)*Ai1 + ---- + a(i,n)*Ain, i = 1,2,---,n
		//	此处取i=1，程序中为0
		if(n == 1){			//对于单个数
			return A[0][0];
		}else if(n == 2){	//对于2阶方阵
			return (A[0][0]*A[1][1] - A[0][1]*A[1][0]);
		}else{
			double det = 0.0,det_M0k;
			for(int k=0; k<n; k++){
				double[][] M0k = complement(A, 1, k+1);
				det_M0k = det2(M0k);//余子式M0k的行列式
				if((k & 0x1) == 1)	//A0k = (-1)^(0+k) * det_M0k
					det -= A[0][k]*det_M0k;
				else
					det += A[0][k]*det_M0k;
			}
			return det;
		}
	}
	
	/**
	 * 求方阵A的特征值(eigenvalues)
	 * @param A double[][] 待求矩阵,必须n*n
	 * @return λ[i] double[][] 特征值
	 */
	@Deprecated
	public static double[] eigenvalues(double[][] A){
		return null;
	}
	
	/**
	 * 从标准输入接收数据,并存到向量x[0:n-1]中
	 * @param x 向量
	 */
	public static void getMatrix(double[] x){
		int i,n=x.length; 	boolean error;
		String token,data;StringTokenizer finder;
		do{
			error=false;
			System.out.println("在一行内输入"+n+"个数(用空格' '或逗号','隔开):");
			data=getLine();
			finder=new StringTokenizer(data," ,");
			i=0;
			while(finder.hasMoreTokens() && i<n){
				token=finder.nextToken();
				try{
					x[i]=Double.parseDouble(token);
				}catch(Exception e){
					error=true;System.out.println("Exception:请输入数字");
				}
				i++;
				if(error)
					break;
			}
			if(i!=n){
				error=true;System.out.println("数据不足!!!");
			}
		}while(error);
	}
	
	/**
	 * 从标准输入接收数据,并存到矩阵Am*n中
	 * @param A m*n矩阵
	 */
	public static void getMatrix(double[][] A){
		int m=A.length;
		for(int i=0;i<m;i++){
			System.out.print("第"+(i+1)+"行:\t");
			getMatrix(A[i]);
		}
	}
	
	/**
	 * 输入一行文本,并接受
	 * @return 输入的文本
	 */
	protected static String getLine(){
		StringBuffer buf=new StringBuffer(256);
		int c;
		try{
			while((c=System.in.read())!=-1){
				if(c==13 || c==10)//换行符第一个字节
					break;
				char ch=(char)c;
				buf.append(ch);
			}
			System.in.read();//处理换行符第二个字节
		}
		catch(IOException e){System.err.println(e);}
		return (buf.toString());
	}

	/**
	 * 从标准输入读取一个double数据
	 * @return
	 */
	public static double readLineDouble(){
		String str;
		double data=0;
		boolean error;
		while(true){
			error=false;
			str=getLine();
			try{
				data=Double.parseDouble(str.trim());
			}catch(Exception e){
				error=true;System.out.println("Exception:请输入数字");
			}
			if(!error)
				break;
		}
		return data;
	}
	
	/**
	 * 求两个向量的内积
	 * @param p 向量1
	 * @param q 向量2
	 * @return (p,q)
	 */
	public static double innerProduct(double[] p,double[] q){
		int n=p.length;
		if(q.length!=n){
			System.out.println("两向量的维数不同!!!");
			return 0;
		}
		double res=0;
		for(int i=0;i<n;i++)
			res+=p[i]*q[i];
		return res;
	}
	
	/**
	 * 求方阵A的逆阵,利用 "行列变换消元方法(row,col transform Elimination)原理
	 * @param A n*n方阵
	 * @return A的逆阵
	 */
	public static double[][] inverse(double[][] A){
		int n=A.length;
		if(A[0].length !=n){
			System.out.println("参数矩阵不是方阵!!!");
			return null;
		}
		int i, j, k, n2 = n+n;
		double[][] invA=new double[n][n];
		//定义 tMatrix = A|I (n行,2n列)
		double[][] tMatrix = new double[n][n2];
		//初始化Initialization
		for ( i=0; i<n; i++){
			for ( j=0; j<n; j++)
				tMatrix[i][j] = A[i][j];        
		}
		for (i=0; i<n; i++){
			for (j=n; j<n2; j++)
				tMatrix[i][j] = 0.0;
			tMatrix[i][n+i] = 1.0;        
		}//Initialization over!
		
		for (i=0; i<n; i++){//处理列:Process Cols
			double base = tMatrix[i][i];
			if (Math.abs(base) < 1E-45){//求逆矩阵过程中被零除
				for(j=i+1; j<n; j++){
					if(Math.abs(tMatrix[j][i]) > 1E-45){//在第i行下面，找一行  tMatrix[j][i] != 0
						for (k=0; k<n2; k++)//第i行 + 第j行,使得 tMatrix[i][i] != 0
							tMatrix[i][k] = tMatrix[i][k] + tMatrix[j][k];
						break;
					}
				}
				base = tMatrix[i][i];
				if (Math.abs(base) < 1E-45)//再次判断
					return null;
			}
			for (j=0; j<n; j++){//行:row
				if (j == i) continue;
				double times = tMatrix[j][i]/base;
				for (k=0; k<n2; k++){//col
					tMatrix[j][k] = tMatrix[j][k] - times*tMatrix[i][k];
				}
			}        
			for (k=0; k<n2; k++){
				tMatrix[i][k] /= base;
			}
		}
		//从tMatrix中提取A的逆阵invA
		for ( i=0; i<n; i++){
			for ( j=0; j<n; j++)
				invA[i][j] = tMatrix[i][j+n];        
		}    
		return invA;
	}
	
	/**
	 * 求方阵A的逆阵,利用 inv_A = (1/|A|) * A* 公式<br>
	 * 		A8 为A的伴随矩阵（Adjoint matrix）
	 * @param A A n*n方阵
	 * @return A的逆阵
	 */
	public static double[][] inverse2(double[][] A){
		int n=A.length;
		if(A[0].length !=n){
			System.out.println("参数矩阵不是方阵!!!");
			return null;
		}
		double det_A;//A的行列式
		double[][] invA=new double[n][n];	//保存逆阵
		if(n==1){
			if(A[0][0] == 0)
				return null;
			invA[0][0] = 1/A[0][0];
		}else if(n==2){
			det_A = A[0][0]*A[1][1] - A[0][1]*A[1][0];
			if(det_A == 0)
				return null;
			invA[0][0]=A[1][1]/det_A;
			invA[0][1]=-A[0][1]/det_A;
			invA[1][0]=-A[1][0]/det_A;
			invA[1][1]=A[0][0]/det_A;
		}else{
			det_A = det2(A);//A的行列式
			if(det_A == 0)
				return null;
			//对所有i,j 求代数余子式 Aij = (-1)^(i+j) * Mij
			for(int i=0; i<n; i++){
				for(int j=0; j<n; j++){
					double[][] minor = complement(A, i+1, j+1);
					double Mij = det2(minor);	//余子式minor的行列式
					if(((i+j) & 0x1) == 1)		//Aij = (-1)^(i+j) * Mij
						invA[j][i] = - Mij/det_A;
					else
						invA[j][i] = Mij/det_A;
				}
			}
		}//end if_else
		return invA;
	}
	
	/**
	 * 求上三角矩阵(Upper triangular matrix)的逆矩阵invU<br>
	 *      invU[][]也是上三角矩阵
	 * @param U double[][] n*n上三角矩阵
	 * @return invU 上三角矩阵的逆阵
	 */
	public static double[][] inverseUpTriMatrix(double[][] U){
		int n=U.length;
		if(U[0].length !=n){
			System.out.println("参数矩阵不是方阵!!!");
			return null;
		}
		for(int i=0; i<n; i++){
			if(U[i][i]==0)//主对角线上有个元素为0，奇异矩阵
				return null;
		}
		double[][] invU = new double[n][n];
		//设置invU[][]主对角线上元素的值
		for(int i=0; i<n; i++){
			invU[i][i] = 1/U[i][i];
		}
		//斜向上依次求出 invU[][] 第d条对角线上元素的值
		double sum;
		for(int d=1; d<n; d++){			//第d条对角线
			for(int k=0; k<n-d; k++){	//根据 U[k][]*invU[][k+d]=0 求出 invU[k][k+d]
				sum = 0;
				for(int i=1; i<=d; i++)
					sum +=U[k][k+i]*invU[k+i][k+d];
				invU[k][k+d] = -sum/U[k][k];
			}
		}
		return invU;
	}
	
	/**
	 * 求下三角矩阵（lower triangular matrix）的逆矩阵invL<br>
	 * invL也是下三角矩阵<br>
	 * 其实可以将L转置得LT，调用inverseUpTriMatrix(LT)得invLT,再将invLT，不过此处没有那么做
	 * @param L double[][] n*n下三角矩阵
	 * @return invL 下角矩阵的逆阵
	 */
	public static double[][] inverseLowTriMatrix(double[][] L){
		int n=L.length;
		if(L[0].length !=n){
			System.out.println("参数矩阵不是方阵!!!");
			return null;
		}
		for(int i=0; i<n; i++){
			if(L[i][i]==0)//主对角线上有个元素为0，奇异矩阵
				return null;
		}
		double[][] invL = new double[n][n];
		//设置invL[][]主对角线上元素的值
		for(int i=0; i<n; i++){
			invL[i][i] = 1/L[i][i];
		}
		//斜向下依次求出 invL[][] 第d条对角线上元素的值
		double sum;
		for(int d=1; d<n; d++){			//第d条对角线
			for(int k=n-1; k>=d; k--){	//根据 U[k][]*invL[][k-d]=0 求出 invU[k][k-d]
				sum = 0;
				for(int i=1; i<=d; i++)
					sum +=L[k][k-i]*invL[k-i][k-d];
				invL[k][k-d] = -sum/L[k][k];
			}
		}
		return invL;
	}
	
	/**
	 * 生成n*n的HilbertMatrix
	 * @param n 维数 
	 * @return Hilbert_Matrix
	 */
	public static double[][] makeHilbertMatrix(int n){
		double[][] H=new double[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				H[i][j]=1.0/(i+j+1);
		return H;
	}
	
	/**
	 * 生成一个n阶方阵
	 * @param n 阶数
	 * @return n阶方阵
	 */
	public static double[][] makeIdentity(int n){
		double[][] I=new double[n][n];
		for(int i=0;i<n;i++)
			I[i][i]=1;
		return I;
	}
	
	/**
	 * 蛇形矩阵是由1开始的自然数依次排列成的一个矩阵上三角形。 
	 * @param n 阶数
	 * @return n*n的蛇行矩阵
	 */
	public static double[][] makeSnakeMatrix(int n){
		double[][] snake = new double[n][n];
		double temp = 1;
		for(int i=0;i<n;i++){
			for(int j=0;j<=i;j++){
				snake[i-j][j] = temp++;  
			}
		}
		return snake;
	}
	
	/**
	 * 操作c*r=A
	 * @param c m*1的列矩阵
	 * @param r 1*n的行矩阵
	 * @return A:m*n的矩阵
	 */
	public static double[][] matrixMultiply(double[] c,double[] r){
		if(c==null || r==null)
			return null;
		int m=c.length,n=r.length;
		double A[][]=new double[m][n];
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++)
				A[i][j]=c[i]*r[j];
		return A;
	}
	
	/**
	 * 操作 y*A=b
	 * @param y m维 向量
	 * @param A m*n矩阵
	 * @return b n维 向量
	 */
	public static double[] matrixMultiply(double[] y,double[][] A){
		if(y==null || A==null)
			return null;
		int m=A.length,n=A[0].length,ly=y.length;
		if(m!=ly){
			System.out.println("矩阵长度不一致!!!");
			return null;
		}
		double[] b=new double[n];
		for(int j=0;j<n;j++){
			b[j]=0;//初始b[j]
			for(int i=0;i<m;i++)
				b[j] += y[i] * A[i][j];
		}
		return b;
	}
	
	/**
	 * 操作 A*x=b
	 * @param A m*n矩阵
	 * @param x n 向量
	 * @return b m维 向量
	 */
	public static double[] matrixMultiply(double[][] A,double[] x){
		if(A==null || x==null)
			return null;
		int m=A.length,n=A[0].length,lx=x.length;
		if(n!=lx){
			System.out.println("矩阵长度不一致!!!");
			return null;
		}
		double[] b=new double[m];
		for(int i=0;i<m;i++){
			b[i]=0;//初始b[i]
			for(int j=0;j<n;j++)
				b[i] += A[i][j] * x[j];
		}
		return b;
	}
	
	/**
	 * 操作A*B=C
	 * @param A m*s矩阵
	 * @param B s*n矩阵
	 * @return C即m*n矩阵
	 */
	public static double[][] matrixMultiply(double[][] A,double[][] B){
		if(A==null || B==null)
			return null;
		int m=A.length,s=A[0].length;
		int ss=B.length,n=B[0].length;
		if(s!=ss){
			System.out.println("矩阵长度不一致!!!");
			return null;
		}
		double[][] C=new double[m][n];
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++){
				double sum=A[i][0]*B[0][j];
				for(int k=1;k<s;k++)
					sum+=A[i][k]*B[k][j];
				C[i][j]=sum;
			}
		return C;
	}
	
	/**
	 * 计算向量x的∞范式
	 * @param x 向量
	 * @return x的∞范式
	 */
	public static double norm_00(double[] x){
		int max=0,n=x.length;
		for(int i=1;i<n;i++)
			if(Math.abs(x[i]) > Math.abs(x[max]))
				max=i;
		return Math.abs(x[max]);
	}
	
	/**
	 * 计算矩阵A的∞范式<br>
	 * (行范数),A每一行元素绝对值之和的最大值
	 * @param A 矩阵
	 * @return A的∞范式
	 */
	public static double norm_00(double[][] A){
		int m=A.length,n=A[0].length;
		double max=0,temp;
		for(int j=0;j<n;j++)
			max+=Math.abs(A[0][j]);
		for(int i=1;i<m;i++){
			temp=0;
			for(int j=0;j<n;j++)
				temp+=Math.abs(A[i][j]);
			if(temp>max)
				max=temp;
		}
		return max;
	}
	
	/**
	 * 计算向量x的1范式
	 * @param x  向量
	 * @return x的1_norm
	 */
	public static double norm_1(double[] x){
		double sum=0;int n=x.length;
		for(int i=0;i<n;i++)
			sum+=Math.abs(x[i]);
		return sum;
	}
	
	/**
	 * 计算矩阵A的1范式<br>
	 * (列范数)，A每一列元素绝对值之和的最大值
	 * @param A 矩阵
	 * @return A的1范式
	 */
	public static double norm_1(double[][] A){
		int m=A.length,n=A[0].length;
		double max=0,temp;
		for(int i=0;i<m;i++)
			max+=Math.abs(A[i][0]);
		for(int j=1;j<n;j++){
			temp=0;
			for(int i=0;i<m;i++)
				temp+=Math.abs(A[i][j]);
			if(temp>max)
				max=temp;
		}
		return max;
	}
	
	/**
	 * 计算向量x的2范式
	 * @param x 向量
	 * @return x的2范式
	 */
	public static double norm_2(double[] x){
		int n=x.length;double sum=0,res=0;
		for(int i=0;i<n;i++)
			sum+=(x[i]*x[i]);
		res=Math.sqrt(sum);
		return res;
	}
	
	/**
	 * 计算矩阵A的2范式<br>
	 * (谱范数,即A'A特征值λi中最大者λm的平方根,其中A'为A的转置矩阵). 
	 * @param A 矩阵
	 * @return A的2范式
	 */
	@Deprecated
	public static double norm_2(double[][] A){
		double max=0;
		return max;
	}
	
	/**
	 * 进行四舍五入，保留4为
	 * @param x n维向量
	 */
	public static void round(double[] x){
		if(x==null)	return;
		for(int i=0; i<x.length; i++)
			x[i] = Math.rint(x[i]*10000)/10000;
	}
	
	/**
	 * 进行四舍五入，保留4为
	 * @param A m*n矩阵
	 */
	public static void round(double[][] A){
		if(A==null)	return;
		for(int i=0; i<A.length; i++)
			round(A[i]);
	}
	
	/**
	 * 输出n*1的矩阵 或 1*n的矩阵 
	 * 不进行四舍五入
	 * @param x n*1矩阵或1*n矩阵
	 */
	public static void showMatrix1D(double x[]){
		showMatrix1D(x,false);
	}
	
	/**
	 * @param x n*1矩阵或1*n矩阵
	 * @param round true,四舍五入,保留4位小数;or,不进行四舍五入
	 */
	public static void showMatrix1D(double x[],boolean round){
		if(x==null)
			return;
		for(int i=0;i<x.length;i++){
			if(round)
				System.out.print(Math.rint(x[i]*10000)/10000+"\t");
			else
				System.out.print(x[i]+"\t");
		}
		System.out.println();
	}
	
	/**
	 * 输出n*n
	 * 不进行四舍五入
	 * @param A n*n矩阵
	 */
	public static void showMatrix2D(double[][] A){
		showMatrix2D(A,false);
	}
	
	/**
	 * 输出n*n
	 * @param A n*n矩阵
	 * @param round true,四舍五入,保留4位小数;or,不进行四舍五入
	 */
	public static void showMatrix2D(double[][] A,boolean round){
		if(A==null)
			return;
		for(int i=0;i<A.length;i++)
			showMatrix1D(A[i],round);
	}

	/**
	 * 将矩阵A转置,并返回其转置矩阵
	 * @param A
	 * @return A的转置
	 */
	public static double[][] transpose(double[][] A){
		int m=A.length,n=A[0].length;
		double[][] tranA=new double[n][m];
		for(int i=0;i<n;i++)
			for(int j=0;j<m;j++)
				tranA[i][j]=A[j][i];
		return tranA;
	}
	
	//main 测试以上方法
	public static void main(String[] args){
		/*
		//double[][] A={{1,-1},{-3,2}};
		double[][] A={{1,1,1},{1,2,3},{1,3,6}};
		System.out.println("A:");
		showMatrix2D(A);
		
		//求转置
		double[][] transA=transpose(A);
		System.out.println("transA:");
		showMatrix2D(transA);
		
		//行列式，高斯消元方法
		double determinant = det(A);
		System.out.println("高斯消元方法：|A| = "+determinant);
		
		//显示部分余子式
		for(int k=0; k<2; k++){
			System.out.println("余子式A1"+(k+1)+":");
			double[][] cofactor = complement(A, 1, k+1);
			showMatrix2D(cofactor);
		}
		//行列式，代数余子式方法
		double determinant2 = det2(A);
		System.out.println("代数余子式方法：|A| = "+determinant2);
		//逆矩阵
		double[][] invA = inverse2(A);
		System.out.println("逆矩阵invA:");
		showMatrix2D(invA);
		*/
		/*
		//double[][] L= {{1,0},{-3,2}};
		double[][] L={{1,0,0},{1,2,0},{1,3,6}};
		System.out.println("下三角矩阵L:");
		showMatrix2D(L);
		
		double[][] invL = inverseLowTriMatrix(L);
		System.out.println("逆矩阵invL:");
		showMatrix2D(invL);
		System.out.println("验证L*invL:");
		showMatrix2D(matrixMultiply(L, invL));
		
		double[][] U=transpose(L);
		System.out.println("LT:");
		showMatrix2D(U);
		System.out.println("上三角矩阵U:");
		showMatrix2D(U);
		
		double[][] invU = inverseUpTriMatrix(U);
		System.out.println("逆矩阵invU:");
		showMatrix2D(invU);
		System.out.println("验证U*invU:");
		showMatrix2D(matrixMultiply(U, invU));
		*/
		
		//double[][] A = {{1, 2, 3}, {5, 6, 4}, {3, 4, 5}};
		double[][] A = { {0, 1}, {3, 4} };
		double[][] invA = inverse(A);
		System.out.println("消去法求invA:");
		showMatrix2D(invA);
		System.out.println("验证A*invA:");
		showMatrix2D(matrixMultiply(A, invA),true);
		
		double[][] invA2 = inverse2(A);
		System.out.println("代数余子式法求invA2:");
		showMatrix2D(invA2);
		System.out.println("验证A*invA2:");
		showMatrix2D(matrixMultiply(A, invA2));
	}
}
