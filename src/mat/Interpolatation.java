package mat;


public class Interpolatation {

	/**
	 * 在[x1,x2]区间上生成相差均等的(n+1)点<br>若x1>x2,程序自动调换
	 * @param x1 左界限
	 * @param x2 右界限
	 * @param n 指定点数(n+1)
	 * @return 各个点,一个数组
	 */
	public static double[] linspace(double x1,double x2,int n){
		if(x1>x2){
			double temp=x1;
			x1=x2;
			x2=temp;
		}
		double dx=(x2-x1)/n;
		double[] points=new double[n+1];
		points[0]=x1;
		points[n]=x2;
		for(int i=1;i<n;i++)
			points[i]=points[i-1]+dx;
		return points;
	}
	
	/**
	 * 根据Newton基函数,生成Newton插值矩阵
	 * @param t 数据点
	 * @return
	 */
	public static double[][] makeNewtonMatirx(double[] t){
		int n=t.length;
		double[][] N=new double[n][n];
		for(int i=0;i<n;i++){
			N[i][0]=1.0;
			for(int j=1;j<=i;j++)
				N[i][j]=N[i][j-1]*(t[i]-t[j-1]);
		}
		return N;
	}
	
	/**
	 * 生成Vandermonde Matrix
	 * @param t 数据点向量
	 * @return 
	 */
	public static double[][] makeVandMatrix(double[] t){
		int n=t.length;
		double[][] V=new double[n][n];
		for(int i=0;i<n;i++){
			V[i][0]=1.0;
			for(int j=1;j<n;j++)
				V[i][j]=V[i][j-1]*t[i];
		}
		return V;
	}
	
	/**
	 * 根据数据点(x[i],y[i]),根据Newton插值,拟合多项式
	 * @param x x[0:n-1]
	 * @param y y[0:n-1]
	 * @return Newton插值多项式系数
	 */
	public static double[] NewtonFit(double[] x,double[] y){
		double[][] newton=makeNewtonMatirx(x);
		double[] poly=LinearSystem.forwordSubstitution(newton, y);
		return poly;
	}
	
	/**
	 * 根据数据点(x[i],y[i]),拟合多项式插值
	 * @param x x[0:n-1]
	 * @param y y[0:n-1]
	 * @return 多项式的系数
	 */
	public static double[] polyFit(double[] x,double[] y){
		double[][] vand=makeVandMatrix(x);
		double[] poly=LinearSystem.gaussEliminate(vand, y);
		return poly;
	}
	
	/**
	 * 由数据点(x[i],y[i]),三次样条插值
	 * @param x 数据点的x坐标x[0:n-1]
	 * @param y 数据点的y坐标y[0:n-1]
	 * @return 一个4*(n-1)的数组
	 */
	public static double[] cubicSpline(double[] x,double[] y){
		int n=x.length;
		if(n!=y.length){
			System.out.println("数据参数点,xi,yi的个数不一致!!!");
			return null;
		}
		//构造A*res=ft线性方程组
		double[] res=null;	//结果
		double[] ft=new double[4*(n-1)];	//保存y[0:n-1],其他为0
		double[][] A=new double[4*(n-1)][4*(n-1)];	//生成的矩阵
		//根据所给数据点,构造2(n-1)个方程
		for(int i=0;i<n-1;i++){
			A[2*i][4*i]=1;
			A[2*i+1][4*i]=1;
			ft[i]=y[i];
			ft[i+1]=y[i+1];
			for(int j=1;j<4;j++){
				A[2*i][4*i+j]=A[2*i][4*i+j-1]*x[i];
				A[2*i+1][4*i+j]=A[2*i+1][4*i+j-1]*x[i+1];
			}
		}
		//构造(n-2)个一阶导数f'(x[1:n-2])相等,构造(n-2)个二阶导数f''(x[1:n-2])相等
		for(int i=0;i<n-2;i++){
			//一阶
			A[2*n-2+i][4*i+1]=1;
			A[2*n-2+i][4*i+2]=2*x[i+1];
			A[2*n-2+i][4*i+3]=3*x[i+1]*x[i+1];
			A[2*n-2+i][4*i+5]=-1;
			A[2*n-2+i][4*i+6]=-2*x[i+1];
			A[2*n-2+i][4*i+7]=-3*x[i+1]*x[i+1];
			//二阶
			A[3*n-4+i][4*i+2]=2;
			A[3*n-4+i][4*i+3]=6*x[i+1];
			A[3*n-4+i][4*i+6]=-2;
			A[3*n-4+i][4*i+7]=-6*x[i+1];
		}
		//两个端点的二阶导数为0
		A[4*n-6][2]=2;
		A[4*n-6][3]=6*x[0];
		A[4*n-5][4*n-6]=2;
		A[4*n-5][4*n-5]=6*x[n-1];
		//高斯消元求解A*res=ft
		res=LinearSystem.gaussEliminate(A, ft);
		return res;
	}
	
	/**
	 * 由数据点(x[i],y[i]),用三次样条插值求解点xi[0:n-1]的值
	 * @param x  数据点的x坐标x[0:n-1]
	 * @param y  数据点的y坐标y[0:n-1]
	 * @param xi 待求点xi[0:n-1]
	 * @return f(xi)
	 */
	public static double[] spline(double[] x,double[] y,double[] xi){
		int n=xi.length;
		double[] p=cubicSpline(x,y);
		double[] yi=new double[n];
		for(int i=0;i<n;i++){
			yi[i]=valueSpline(p, x, xi[i]);
		}
		return yi;
	}
	
	/**
	 * 根据三次样条插值结果splineP[],以及插值原象点t[]<br>求解特定点x的值.
	 * @param splineP 三次样条插值结果splineP[]
	 * @param t 插值原象点t[]
	 * @param x 特定点
	 * @return f(x)
	 */
	public static double valueSpline(double[] splineP,double[] t,double x){
		int n=t.length;
		double y=0;
		if(x<t[0])
			return y;
		for(int i=1;i<n;i++){
			if(x<=t[i]){
				y=splineP[4*i-1];
				for(int j=2;j>=0;j--){
					y*=x;
					y+=splineP[4*i+j-4];
				}
				return y;
			}
		}
		return y;
	}
	
	//main
	public static void main(String[] args){
		/*
		double[] t={0.0,0.5,1.0,6.0,7.0,9.0};
		double[] ft={0.0,1.6,2.0,2.0,1.5,0.0};
		double[] polyfit=polyFit(t,ft);
		System.out.println("polyfit x=");
		Matrix.showMatrix1D(polyfit);
		double[] ftt=Polynomial.value(polyfit, t);
		Matrix.showMatrix1D(ftt);
		Polynomial.showFigure(polyfit);
		
		double[] newFit=NewtonFit(t,ft);
		System.out.println("Newton fit x=");
		Matrix.showMatrix1D(newFit);
		double[] y=Polynomial.valueNewtonPoly(t, newFit, t);
		Matrix.showMatrix1D(y);
		*/
		double[] xi={-2,0,1};
		double[] yi={-27,-1,0};
		double[] ans=cubicSpline(xi,yi);
		System.out.println("ans:");
		Matrix.showMatrix1D(ans);
		double[] yy=spline(xi, yi, xi);
		Matrix.showMatrix1D(yy);
		double[] datax=linspace(-2, 2, 10000);
		double[] datay=spline(xi,yi,datax);
		System.out.println("x点:");
		Matrix.showMatrix1D(datax);
		System.out.println("f(x)");
		Matrix.showMatrix1D(datay);
		Figure fig=new Figure();
		fig.setUnitPixels(10);
		fig.setOrigin(300, 200);
		fig.drawLines(datax, datay);
		fig.drawPoints(xi, yi);
		fig.setVisible(true);
	}

}
