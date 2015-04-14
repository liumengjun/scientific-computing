
public class GaussianElimination {
	
	//求解
	public void solve(double[][] A,double[] x,double[] b){
		int n=4;
		double mk[][]=new double[n][n];//消去矩阵
		
		for(int k=0;k<n-1;k++){
			//选主元
			int p=k;
			for(int i=k+1;i<n;i++){
				if(Math.abs(A[p][k])<Math.abs(A[i][k]))
					p=i;
			}
			if(p!=k){//交换
				double temp;
				for(int j=0;j<n;j++){
					temp=A[k][j];
					A[k][j]=A[p][j];
					A[p][j]=temp;
				}
				temp=b[k];
				b[k]=b[p];
				b[p]=temp;
			}
			
			if(A[k][k]==0)
				return;
			for(int i=k+1;i<n;i++)
				mk[i][k]=A[i][k]/A[k][k];
			for(int i=k+1;i<n;i++)//将A[k][k]下面元素置0
				A[i][k]=0;
			for(int j=k+1;j<n;j++)
				for(int i=k+1;i<n;i++)
					A[i][j]=A[i][j]-mk[i][k]*A[k][j];
			
			for(int i=k+1;i<n;i++)
				b[i]=b[i]-mk[i][k]*b[k];
		}
		//现在A是上三角矩阵
		backSubstitution(A,b,x);
	}
	//前代 求解L*xx=bb
	public void forwordSubstitution(double l[][],double bb[],double xx[]){
		for(int j=0;j<bb.length;j++){
			if(l[j][j]==0)
				return;
			xx[j]=bb[j]/l[j][j];
			for(int i=j+1;i<bb.length;i++)
				bb[i]=bb[i]-l[i][j]*xx[j];
		}
	}
	//后代 求解 U*xx=bb
	public void backSubstitution(double U[][],double bb[],double xx[]){
		for(int j=bb.length-1;j>=0;j--){
			if(U[j][j]==0)
				return;
			xx[j]=bb[j]/U[j][j];
			for(int i=0;i<j;i++)
				bb[i]=bb[i]-U[i][j]*xx[j];
		}
	}
	//输出结果
	public void showArray1D(double array[]){
		for(int i=0;i<array.length;i++)
			System.out.print(array[i]+"\t");
		System.out.println();
	}
	public void showArray2D(double[][] array){
		for(int i=0;i<array.length;i++)
		{
			for(int j=0;j<array[i].length;j++)
				System.out.print(array[i][j]+"\t");
			System.out.println();
		}
	}
	public static void main(String args[]){

		double A[][]={{21.0,67.0,88.0,73.0},
							{76.0,63.0,7.0,20.0},
							{0.0,85.0,56.0,54.0},
							{19.3,43.0,30.2,29.4}};
		double b[]={141.0,109.0,218.0,93.7};
		double x[]=new double[4];
		GaussianElimination gauss=new GaussianElimination();
		gauss.solve(A,x,b);
		System.out.println("答案是:");
		gauss.showArray1D(x);
	}
}
