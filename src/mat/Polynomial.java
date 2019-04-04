package mat;


public class Polynomial {
	
	/**
	 * �����ʽf(x)�ĵ���f'(x)
	 * @param fx��f(x)�����ϵ��
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
	 * ��������ʽ���p1*p2
	 * @param p1 ����ʽ1
	 * @param p2 ����ʽ2
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
	 * ���ƶ���ʽpoly��ͼ��
	 * @param poly ����ʽ
	 */
	public static void showFigure(double[] poly){
		Figure fig=new Figure();
		fig.drawPoly(poly);
		fig.setVisible(true);
	}
	
	/**
	 * ���ݶ���ʽf,����x���ֵf(x),���������
	 * @param f ����ʽ
	 * @param x �ض���
	 * @return f(x)
	 */
	public static double value(double[] f,double x){
		return value(f,x,false);
	}
	
	/**
	 * ���ݶ���ʽf,����x���ֵf(x)
	 * @param f ����ʽ
	 * @param x �ض���
	 * @param round ����Ƿ�����
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
	 * ���ݶ���ʽf,����x[0:n-1]���ֵf(x[0:n-1])
	 * @param f ����ʽ
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
	 * ����Newton��ֵ����ʽpt,���ض���x��ֵ
	 * @param t ��ϲ�ֵʱ��,ԭ���
	 * @param a Newton��ֵ����ʽϵ��a[0:n-1]
	 * @param x �ض���
	 * @return pt(x)
	 */
	public static double valueNewtonPoly(double[] t,double[] a,double x){
		int n=t.length;
		double ftt=0;
		double[] pt=new double[n];
		pt[0]=1;
		for(int i=1;i<n;i++)//p(t)����ʽ
			pt[i]=pt[i-1]*(x-t[i-1]);
		for(int i=0;i<n;i++)//����p(x)
			ftt+=pt[i]*a[i];
		return ftt;
	}
	
	/**
	 * ����Newton��ֵ����ʽpt,��һ���ض���x[0:n-1]��ֵ
	 * @param t ��ϲ�ֵʱ��,ԭ���
	 * @param a Newton��ֵ����ʽϵ��a[0:n-1]
	 * @param x �ض���[0:n-1]
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
	 * ������ʽת��Ϊ"f(x)=a0+a1x+ ... +an-1x^n-1"(��Ϊstr)����ʽ
	 * @param f ����ʽ
	 * @return str
	 */
	public static String toString(double[] f){
		String var="x";
		return toString(f,var);
	}
	
	/**
	 * ������ʽת��Ϊ"f(x)=a0+a1x+ ... +an-1x^n-1"(��Ϊstr)����ʽ
	 * @param f ����ʽ
	 * @param x һ����
	 * @return str
	 */
	public static String toString(double[] f,double x){
		String var=String.valueOf(x);
		return toString(f,var);
	}
	
	/**
	 * ������ʽת��Ϊ"f(x)=a0+a1x+ ... +an-1x^n-1"(��Ϊstr)����ʽ
	 * @param f ����ʽ
	 * @param var ����x���ַ���
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
	 * ţ�ٱƽ���
	 * NOTE: ��Ҫѡȡǡ���ĵ�����ʼ��
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
