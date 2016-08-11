package mat;


public class Polynomial {
	
	/**
	 * 求多项式f(x)的导数f'(x)
	 * @param fx存f(x)各项的系数
	 * @return f'(x)
	 */
	public static double[] derivative(double[] fx){
		int n=fx.length;
		double[] df=new double[n-1];
		for(int i=1;i<n;i++)
			df[i-1]=i*fx[i];
		return df;
	}
	
	/**
	 * 两个多项式相乘p1*p2
	 * @param p1 多项式1
	 * @param p2 多项式2
	 * @return p1*p2
	 */
	public static double[] multiply(double[] p1,double[] p2){
		int n1=p1.length;
		int n2=p2.length;
		double[] newPoly=new double[n1+n2-1];
		for(int i=0;i<n1;i++)
			for(int j=0;j<n2;j++)
				newPoly[i+j]+=p1[i]*p2[j];
		return newPoly;
	}
	
	/**
	 * 绘制多项式poly的图像
	 * @param poly 多项式
	 */
	public static void showFigure(double[] poly){
		Figure fig=new Figure();
		fig.drawPoly(poly);
		fig.setVisible(true);
	}
	
	/**
	 * 根据多项式f,计算x点的值f(x),结果不舍入
	 * @param f 多项式
	 * @param x 特定点
	 * @return f(x)
	 */
	public static double value(double[] f,double x){
		return value(f,x,false);
	}
	
	/**
	 * 根据多项式f,计算x点的值f(x)
	 * @param f 多项式
	 * @param x 特定点
	 * @param round 结果是否舍入
	 * @return f(x)
	 */
	public static double value(double[] f,double x,boolean round){
		int n=f.length;
		double y=f[n-1];
		for(int i=n-2;i>=0;i--){
			y*=x;
			y+=f[i];
		}
		if(round)
			y=(Math.rint(y*10000))/10000;
		return y;
	}
	
	/**
	 * 根据多项式f,计算x[0:n-1]点的值f(x[0:n-1])
	 * @param f 多项式
	 * @param x x[0:n-1]
	 * @return f(x)[0:n-1]
	 */
	public static double[] value(double[] f,double[] x){
		int n=x.length;
		double[] y=new double[n];
		for(int i=0;i<n;i++)
			y[i]=value(f,x[i]);
		return y;
	}
	
	/**
	 * 根据Newton插值多项式pt,求特定点x的值
	 * @param t 拟合插值时的,原象点
	 * @param a Newton插值多项式系数a[0:n-1]
	 * @param x 特定点
	 * @return pt(x)
	 */
	public static double valueNewtonPoly(double[] t,double[] a,double x){
		int n=t.length;
		double ftt=0;
		double[] pt=new double[n];
		pt[0]=1;
		for(int i=1;i<n;i++)//p(t)多项式
			pt[i]=pt[i-1]*(x-t[i-1]);
		for(int i=0;i<n;i++)//计算p(x)
			ftt+=pt[i]*a[i];
		return ftt;
	}
	
	/**
	 * 根据Newton插值多项式pt,求一组特定点x[0:n-1]的值
	 * @param t 拟合插值时的,原象点
	 * @param a Newton插值多项式系数a[0:n-1]
	 * @param x 特定点[0:n-1]
	 * @return pt(x)[0:n-1]
	 */
	public static double[] valueNewtonPoly(double[] t,double[] a,double[] x){
		int n=x.length;
		double[] y=new double[n];
		for(int i=0;i<n;i++)
			y[i]=valueNewtonPoly(t,a,x[i]);
		return y;
	}
	
	/**
	 * 将多项式转化为"f(x)=a0+a1x+ ... +an-1x^n-1"(记为str)的形式
	 * @param f 多项式
	 * @return str
	 */
	public static String toString(double[] f){
		String var="x";
		return toString(f,var);
	}
	
	/**
	 * 将多项式转化为"f(x)=a0+a1x+ ... +an-1x^n-1"(记为str)的形式
	 * @param f 多项式
	 * @param x 一个数
	 * @return str
	 */
	public static String toString(double[] f,double x){
		String var=String.valueOf(x);
		return toString(f,var);
	}
	
	/**
	 * 将多项式转化为"f(x)=a0+a1x+ ... +an-1x^n-1"(记为str)的形式
	 * @param f 多项式
	 * @param var 代替x的字符串
	 * @return str
	 */
	private static String toString(double[] f,String var){
		int n=f.length;
		String str=" ";
		if(f[0]!=0)
			str+=f[0];
		if(f[1]>0)
			str+="+";
		if(f[1]!=0)
			str+=f[1]+"*"+var;
		for(int i=2;i<n;i++){
			if(f[i]>0)
				str+="+";
			if(f[i]==0)
				continue;
			str+=f[i]+"*"+var+"^"+i;
		}
		return str;
	}
	
	public static Double solveOne(double[] f) {
		return solveOne(f, 0);
	}
	
	public static Double solveOne(double[] f, double start) {
		return solveOne0(f, start);
	}
	
	/**
	 * 牛顿逼近法
	 * NOTE: 需要选取恰当的迭代起始点
	 * @param f
	 * @param x
	 * @return
	 */
	private static Double solveOne0(double[] f, double x) {
		double[] fd = derivative(f);
		final double tolerance = 0.00000000001;
		final double tolerance2 = 0.000001;
		
		double y = value(f, x);
		int max_times = 1000;
		do {
			if (Math.abs(y) < tolerance) {
				return x;
			}
			double k = value(fd, x);
			if (Math.abs(k) < tolerance && Math.abs(y) > tolerance2) {
				return null;
			}
			x = x - y/k;   // new x
			y = value(f, x); // new y
			max_times--;
		} while(max_times > 0);
		if (Math.abs(y) < tolerance2) {
			return x;
		}
		return null;
	}
	
	//main
	public static void main(String[] args){
		
		double[] x={-2,-1,0,1,2};
		
		double[] p1={1,1,1};
		double[] p2={1,0,-1};
		double[] p=multiply(p1, p2);

		System.out.println("f1(x)is:");
		System.out.println(toString(p1));
		System.out.println("f2(x)is:");
		System.out.println(toString(p2));
		System.out.println("f(x)is:");
		System.out.println(toString(p));
		double[] dp=derivative(p);
		System.out.println("f'(x)is:");
		System.out.println(toString(dp));
		double[] y=value(p,x);
		Matrix.showMatrix1D(y);
		System.out.println(toString(p,1.2)+"="+value(p,1.2,true));

		Polynomial.showFigure(p1);
		Polynomial.showFigure(p2);
		Polynomial.showFigure(p);

		System.out.println("f1("+solveOne(p1)+") = 0");
		System.out.println("f2("+solveOne(p2, 0.5)+") = 0");
		System.out.println("f2("+solveOne(p2, -0.5)+") = 0");
		System.out.println("f("+solveOne(p, 0.5)+") = 0");
		System.out.println("f("+solveOne(p, -0.5)+") = 0");
	}
}
