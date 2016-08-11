package mat;

import java.io.IOException;
import java.util.StringTokenizer;


public class Matrix {
	
	/**
	 * complement(or cofactor,minor:����ʽ)<br>
	 * @param A double[][] ����
	 * @param i int ������
	 * @param j int ������
	 * @return Mij double[][] ����A[][]������ʽ Mij
	 */
	public static double[][] complement(double[][] A, int i, int j){
		if( A == null ) return null;
		int m = A.length, n = A[0].length;
		//��A�ǵ��У����о��󣬻�i,jԽ�� return null
		if(m<2 || n<2 || m<i || n<j || i<1 || j<1) return null;
		double[][] Mij = new double[n-1][n-1];
		// M(i,j):  A[][]ȥ�� ��i�У���j��
		i--;
		j--;
		int new_i,new_j;
		for(int k=0; k<m; k++){
			if(k == i) continue;	//ȥ���� i ��A[i][]
			if(k > i) new_i = k - 1;//���� k ���Ƶ� k-1 ��
			else new_i = k;
			for(int l=0; l<n; l++){
				if(l == j ) continue;	//ȥ���� j ��A[][j]
				if(l > j) new_j = l - 1;//���� l ���Ƶ� l-1 ��
				else new_j = l;
				Mij[new_i][new_j] = A[k][l];
			}
		}
		return Mij;
	}
	
	/**
	 * ���㷽��A������ʽ(determinant)<br>
	 * �ø�˹��Ԫ�����õ������Ǿ���U,����U������ʽ
	 * @param A double[][] n*n����
	 * @return |A| double ����A������ʽ
	 */
	public static double det(double[][] phalanx){
		int n=phalanx[0].length;
		if(phalanx.length != n){
			System.out.println("����A���Ƿ���!!!");
			return 0;
		}
		
		double[][] A=new double[n][n];//�½�����A,����ԭ����
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				A[i][j]=phalanx[i][j];
		
		boolean exchanged=false;//��¼�Ƿ������'�н���'
		double mk[][]=new double[n][n];//��ȥ����
		//��A���и�˹��Ԫ
		for(int k=0;k<n-1;k++){
			int p=k;
			//ѡ��Ԫ
			for(int max=p+1;max<n;max++){
				if(Math.abs(A[max][k])>Math.abs(A[p][k]))
					p=max;
			}
			//����
			if(p!=k){
				double temp;
				for(int j=0;j<n;j++){//����A
					temp=A[k][j];
					A[k][j]=A[p][j];
					A[p][j]=temp;
				}
				exchanged=!exchanged;
			}
			if(A[k][k]==0)//Non_singlar matrix(�������)
				return 0;
			
			for(int i=k+1;i<n;i++)
				mk[i][k]=A[i][k]/A[k][k];
			for(int j=k+1;j<n;j++)//�б任A
				for(int i=k+1;i<n;i++)
					A[i][j]=A[i][j]-mk[i][k]*A[k][j];
		}
		//����A����������
		double det=1;
		for(int i=0;i<n;i++)
			det*=A[i][i];
		if(exchanged)
			return -det;
		return det;
	}
	
	/**
	 * ���㷽��A������ʽ(determinant)<br>
	 * �ô�������ʽ�ķ������㣬�ݹ�ʵ��
	 * @param A n*n����
	 * @return |A| ����A������ʽ
	 */
	public static double det2(double[][] A){
		int n=A[0].length;
		if(A.length != n){
			System.out.println("����A���Ƿ���!!!");
			return 0;
		}
		//	|A| = a(i,1)*Ai1 + ---- + a(i,n)*Ain, i = 1,2,---,n
		//	�˴�ȡi=1��������Ϊ0
		if(n == 1){			//���ڵ�����
			return A[0][0];
		}else if(n == 2){	//����2�׷���
			return (A[0][0]*A[1][1] - A[0][1]*A[1][0]);
		}else{
			double det = 0.0,det_M0k;
			for(int k=0; k<n; k++){
				double[][] M0k = complement(A, 1, k+1);
				det_M0k = det2(M0k);//����ʽM0k������ʽ
				if((k & 0x1) == 1)	//A0k = (-1)^(0+k) * det_M0k
					det -= A[0][k]*det_M0k;
				else
					det += A[0][k]*det_M0k;
			}
			return det;
		}
	}
	
	/**
	 * ����A������ֵ(eigenvalues)
	 * @param A double[][] �������,����n*n
	 * @return ��[i] double[][] ����ֵ
	 */
	@Deprecated
	public static double[] eigenvalues(double[][] A){
		return null;
	}
	
	/**
	 * �ӱ�׼�����������,���浽����x[0:n-1]��
	 * @param x ����
	 */
	public static void getMatrix(double[] x){
		int i,n=x.length; 	boolean error;
		String token,data;StringTokenizer finder;
		do{
			error=false;
			System.out.println("��һ��������"+n+"����(�ÿո�' '�򶺺�','����):");
			data=getLine();
			finder=new StringTokenizer(data," ,");
			i=0;
			while(finder.hasMoreTokens() && i<n){
				token=finder.nextToken();
				try{
					x[i]=Double.parseDouble(token);
				}catch(Exception e){
					error=true;System.out.println("Exception:����������");
				}
				i++;
				if(error)
					break;
			}
			if(i!=n){
				error=true;System.out.println("���ݲ���!!!");
			}
		}while(error);
	}
	
	/**
	 * �ӱ�׼�����������,���浽����Am*n��
	 * @param A m*n����
	 */
	public static void getMatrix(double[][] A){
		int m=A.length;
		for(int i=0;i<m;i++){
			System.out.print("��"+(i+1)+"��:\t");
			getMatrix(A[i]);
		}
	}
	
	/**
	 * ����һ���ı�,������
	 * @return ������ı�
	 */
	protected static String getLine(){
		StringBuffer buf=new StringBuffer(256);
		int c;
		try{
			while((c=System.in.read())!=-1){
				if(c==13 || c==10)//���з���һ���ֽ�
					break;
				char ch=(char)c;
				buf.append(ch);
			}
			System.in.read();//�����з��ڶ����ֽ�
		}
		catch(IOException e){System.err.println(e);}
		return (buf.toString());
	}

	/**
	 * �ӱ�׼�����ȡһ��double����
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
				error=true;System.out.println("Exception:����������");
			}
			if(!error)
				break;
		}
		return data;
	}
	
	/**
	 * �������������ڻ�
	 * @param p ����1
	 * @param q ����2
	 * @return (p,q)
	 */
	public static double innerProduct(double[] p,double[] q){
		int n=p.length;
		if(q.length!=n){
			System.out.println("��������ά����ͬ!!!");
			return 0;
		}
		double res=0;
		for(int i=0;i<n;i++)
			res+=p[i]*q[i];
		return res;
	}
	
	/**
	 * ����A������,���� "���б任��Ԫ����(row,col transform Elimination)ԭ��
	 * @param A n*n����
	 * @return A������
	 */
	public static double[][] inverse(double[][] A){
		int n=A.length;
		if(A[0].length !=n){
			System.out.println("���������Ƿ���!!!");
			return null;
		}
		int i, j, k, n2 = n+n;
		double[][] invA=new double[n][n];
		//���� tMatrix = A|I (n��,2n��)
		double[][] tMatrix = new double[n][n2];
		//��ʼ��Initialization
		for ( i=0; i<n; i++){
			for ( j=0; j<n; j++)
				tMatrix[i][j] = A[i][j];        
		}
		for (i=0; i<n; i++){
			for (j=n; j<n2; j++)
				tMatrix[i][j] = 0.0;
			tMatrix[i][n+i] = 1.0;        
		}//Initialization over!
		
		for (i=0; i<n; i++){//������:Process Cols
			double base = tMatrix[i][i];
			if (Math.abs(base) < 1E-45){//�����������б����
				for(j=i+1; j<n; j++){
					if(Math.abs(tMatrix[j][i]) > 1E-45){//�ڵ�i�����棬��һ��  tMatrix[j][i] != 0
						for (k=0; k<n2; k++)//��i�� + ��j��,ʹ�� tMatrix[i][i] != 0
							tMatrix[i][k] = tMatrix[i][k] + tMatrix[j][k];
						break;
					}
				}
				base = tMatrix[i][i];
				if (Math.abs(base) < 1E-45)//�ٴ��ж�
					return null;
			}
			for (j=0; j<n; j++){//��:row
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
		//��tMatrix����ȡA������invA
		for ( i=0; i<n; i++){
			for ( j=0; j<n; j++)
				invA[i][j] = tMatrix[i][j+n];        
		}    
		return invA;
	}
	
	/**
	 * ����A������,���� inv_A = (1/|A|) * A* ��ʽ<br>
	 * 		A8 ΪA�İ������Adjoint matrix��
	 * @param A A n*n����
	 * @return A������
	 */
	public static double[][] inverse2(double[][] A){
		int n=A.length;
		if(A[0].length !=n){
			System.out.println("���������Ƿ���!!!");
			return null;
		}
		double det_A;//A������ʽ
		double[][] invA=new double[n][n];	//��������
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
			det_A = det2(A);//A������ʽ
			if(det_A == 0)
				return null;
			//������i,j ���������ʽ Aij = (-1)^(i+j) * Mij
			for(int i=0; i<n; i++){
				for(int j=0; j<n; j++){
					double[][] minor = complement(A, i+1, j+1);
					double Mij = det2(minor);	//����ʽminor������ʽ
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
	 * �������Ǿ���(Upper triangular matrix)�������invU<br>
	 *      invU[][]Ҳ�������Ǿ���
	 * @param U double[][] n*n�����Ǿ���
	 * @return invU �����Ǿ��������
	 */
	public static double[][] inverseUpTriMatrix(double[][] U){
		int n=U.length;
		if(U[0].length !=n){
			System.out.println("���������Ƿ���!!!");
			return null;
		}
		for(int i=0; i<n; i++){
			if(U[i][i]==0)//���Խ������и�Ԫ��Ϊ0���������
				return null;
		}
		double[][] invU = new double[n][n];
		//����invU[][]���Խ�����Ԫ�ص�ֵ
		for(int i=0; i<n; i++){
			invU[i][i] = 1/U[i][i];
		}
		//б����������� invU[][] ��d���Խ�����Ԫ�ص�ֵ
		double sum;
		for(int d=1; d<n; d++){			//��d���Խ���
			for(int k=0; k<n-d; k++){	//���� U[k][]*invU[][k+d]=0 ��� invU[k][k+d]
				sum = 0;
				for(int i=1; i<=d; i++)
					sum +=U[k][k+i]*invU[k+i][k+d];
				invU[k][k+d] = -sum/U[k][k];
			}
		}
		return invU;
	}
	
	/**
	 * �������Ǿ���lower triangular matrix���������invL<br>
	 * invLҲ�������Ǿ���<br>
	 * ��ʵ���Խ�Lת�õ�LT������inverseUpTriMatrix(LT)��invLT,�ٽ�invLT�������˴�û����ô��
	 * @param L double[][] n*n�����Ǿ���
	 * @return invL �½Ǿ��������
	 */
	public static double[][] inverseLowTriMatrix(double[][] L){
		int n=L.length;
		if(L[0].length !=n){
			System.out.println("���������Ƿ���!!!");
			return null;
		}
		for(int i=0; i<n; i++){
			if(L[i][i]==0)//���Խ������и�Ԫ��Ϊ0���������
				return null;
		}
		double[][] invL = new double[n][n];
		//����invL[][]���Խ�����Ԫ�ص�ֵ
		for(int i=0; i<n; i++){
			invL[i][i] = 1/L[i][i];
		}
		//б����������� invL[][] ��d���Խ�����Ԫ�ص�ֵ
		double sum;
		for(int d=1; d<n; d++){			//��d���Խ���
			for(int k=n-1; k>=d; k--){	//���� U[k][]*invL[][k-d]=0 ��� invU[k][k-d]
				sum = 0;
				for(int i=1; i<=d; i++)
					sum +=L[k][k-i]*invL[k-i][k-d];
				invL[k][k-d] = -sum/L[k][k];
			}
		}
		return invL;
	}
	
	/**
	 * ����n*n��HilbertMatrix
	 * @param n ά�� 
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
	 * ����һ��n�׷���
	 * @param n ����
	 * @return n�׷���
	 */
	public static double[][] makeIdentity(int n){
		double[][] I=new double[n][n];
		for(int i=0;i<n;i++)
			I[i][i]=1;
		return I;
	}
	
	/**
	 * ���ξ�������1��ʼ����Ȼ���������гɵ�һ�������������Ρ� 
	 * @param n ����
	 * @return n*n�����о���
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
	 * ����c*r=A
	 * @param c m*1���о���
	 * @param r 1*n���о���
	 * @return A:m*n�ľ���
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
	 * ���� y*A=b
	 * @param y mά ����
	 * @param A m*n����
	 * @return b nά ����
	 */
	public static double[] matrixMultiply(double[] y,double[][] A){
		if(y==null || A==null)
			return null;
		int m=A.length,n=A[0].length,ly=y.length;
		if(m!=ly){
			System.out.println("���󳤶Ȳ�һ��!!!");
			return null;
		}
		double[] b=new double[n];
		for(int j=0;j<n;j++){
			b[j]=0;//��ʼb[j]
			for(int i=0;i<m;i++)
				b[j] += y[i] * A[i][j];
		}
		return b;
	}
	
	/**
	 * ���� A*x=b
	 * @param A m*n����
	 * @param x n ����
	 * @return b mά ����
	 */
	public static double[] matrixMultiply(double[][] A,double[] x){
		if(A==null || x==null)
			return null;
		int m=A.length,n=A[0].length,lx=x.length;
		if(n!=lx){
			System.out.println("���󳤶Ȳ�һ��!!!");
			return null;
		}
		double[] b=new double[m];
		for(int i=0;i<m;i++){
			b[i]=0;//��ʼb[i]
			for(int j=0;j<n;j++)
				b[i] += A[i][j] * x[j];
		}
		return b;
	}
	
	/**
	 * ����A*B=C
	 * @param A m*s����
	 * @param B s*n����
	 * @return C��m*n����
	 */
	public static double[][] matrixMultiply(double[][] A,double[][] B){
		if(A==null || B==null)
			return null;
		int m=A.length,s=A[0].length;
		int ss=B.length,n=B[0].length;
		if(s!=ss){
			System.out.println("���󳤶Ȳ�һ��!!!");
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
	 * ��������x�ġ޷�ʽ
	 * @param x ����
	 * @return x�ġ޷�ʽ
	 */
	public static double norm_00(double[] x){
		int max=0,n=x.length;
		for(int i=1;i<n;i++)
			if(Math.abs(x[i]) > Math.abs(x[max]))
				max=i;
		return Math.abs(x[max]);
	}
	
	/**
	 * �������A�ġ޷�ʽ<br>
	 * (�з���),Aÿһ��Ԫ�ؾ���ֵ֮�͵����ֵ
	 * @param A ����
	 * @return A�ġ޷�ʽ
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
	 * ��������x��1��ʽ
	 * @param x  ����
	 * @return x��1_norm
	 */
	public static double norm_1(double[] x){
		double sum=0;int n=x.length;
		for(int i=0;i<n;i++)
			sum+=Math.abs(x[i]);
		return sum;
	}
	
	/**
	 * �������A��1��ʽ<br>
	 * (�з���)��Aÿһ��Ԫ�ؾ���ֵ֮�͵����ֵ
	 * @param A ����
	 * @return A��1��ʽ
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
	 * ��������x��2��ʽ
	 * @param x ����
	 * @return x��2��ʽ
	 */
	public static double norm_2(double[] x){
		int n=x.length;double sum=0,res=0;
		for(int i=0;i<n;i++)
			sum+=(x[i]*x[i]);
		res=Math.sqrt(sum);
		return res;
	}
	
	/**
	 * �������A��2��ʽ<br>
	 * (�׷���,��A'A����ֵ��i������ߦ�m��ƽ����,����A'ΪA��ת�þ���). 
	 * @param A ����
	 * @return A��2��ʽ
	 */
	@Deprecated
	public static double norm_2(double[][] A){
		double max=0;
		return max;
	}
	
	/**
	 * �����������룬����4Ϊ
	 * @param x nά����
	 */
	public static void round(double[] x){
		if(x==null)	return;
		for(int i=0; i<x.length; i++)
			x[i] = Math.rint(x[i]*10000)/10000;
	}
	
	/**
	 * �����������룬����4Ϊ
	 * @param A m*n����
	 */
	public static void round(double[][] A){
		if(A==null)	return;
		for(int i=0; i<A.length; i++)
			round(A[i]);
	}
	
	/**
	 * ���n*1�ľ��� �� 1*n�ľ��� 
	 * ��������������
	 * @param x n*1�����1*n����
	 */
	public static void showMatrix1D(double x[]){
		showMatrix1D(x,false);
	}
	
	/**
	 * @param x n*1�����1*n����
	 * @param round true,��������,����4λС��;or,��������������
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
	 * ���n*n
	 * ��������������
	 * @param A n*n����
	 */
	public static void showMatrix2D(double[][] A){
		showMatrix2D(A,false);
	}
	
	/**
	 * ���n*n
	 * @param A n*n����
	 * @param round true,��������,����4λС��;or,��������������
	 */
	public static void showMatrix2D(double[][] A,boolean round){
		if(A==null)
			return;
		for(int i=0;i<A.length;i++)
			showMatrix1D(A[i],round);
	}

	/**
	 * ������Aת��,��������ת�þ���
	 * @param A
	 * @return A��ת��
	 */
	public static double[][] transpose(double[][] A){
		int m=A.length,n=A[0].length;
		double[][] tranA=new double[n][m];
		for(int i=0;i<n;i++)
			for(int j=0;j<m;j++)
				tranA[i][j]=A[j][i];
		return tranA;
	}
	
	//main �������Ϸ���
	public static void main(String[] args){
		/*
		//double[][] A={{1,-1},{-3,2}};
		double[][] A={{1,1,1},{1,2,3},{1,3,6}};
		System.out.println("A:");
		showMatrix2D(A);
		
		//��ת��
		double[][] transA=transpose(A);
		System.out.println("transA:");
		showMatrix2D(transA);
		
		//����ʽ����˹��Ԫ����
		double determinant = det(A);
		System.out.println("��˹��Ԫ������|A| = "+determinant);
		
		//��ʾ��������ʽ
		for(int k=0; k<2; k++){
			System.out.println("����ʽA1"+(k+1)+":");
			double[][] cofactor = complement(A, 1, k+1);
			showMatrix2D(cofactor);
		}
		//����ʽ����������ʽ����
		double determinant2 = det2(A);
		System.out.println("��������ʽ������|A| = "+determinant2);
		//�����
		double[][] invA = inverse2(A);
		System.out.println("�����invA:");
		showMatrix2D(invA);
		*/
		/*
		//double[][] L= {{1,0},{-3,2}};
		double[][] L={{1,0,0},{1,2,0},{1,3,6}};
		System.out.println("�����Ǿ���L:");
		showMatrix2D(L);
		
		double[][] invL = inverseLowTriMatrix(L);
		System.out.println("�����invL:");
		showMatrix2D(invL);
		System.out.println("��֤L*invL:");
		showMatrix2D(matrixMultiply(L, invL));
		
		double[][] U=transpose(L);
		System.out.println("LT:");
		showMatrix2D(U);
		System.out.println("�����Ǿ���U:");
		showMatrix2D(U);
		
		double[][] invU = inverseUpTriMatrix(U);
		System.out.println("�����invU:");
		showMatrix2D(invU);
		System.out.println("��֤U*invU:");
		showMatrix2D(matrixMultiply(U, invU));
		*/
		
		//double[][] A = {{1, 2, 3}, {5, 6, 4}, {3, 4, 5}};
		double[][] A = { {0, 1}, {3, 4} };
		double[][] invA = inverse(A);
		System.out.println("��ȥ����invA:");
		showMatrix2D(invA);
		System.out.println("��֤A*invA:");
		showMatrix2D(matrixMultiply(A, invA),true);
		
		double[][] invA2 = inverse2(A);
		System.out.println("��������ʽ����invA2:");
		showMatrix2D(invA2);
		System.out.println("��֤A*invA2:");
		showMatrix2D(matrixMultiply(A, invA2));
	}
}
