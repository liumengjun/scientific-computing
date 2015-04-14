package mat;


public class HilbertMatrix {
	
	
	//main
	public static void main(String[] args){
		int n=12;
		double[][] H=Matrix.makeHilbertMatrix(n);
		double[] x=new double[n],dx=new double[n];
		
		double[] b,bb,r=new double[n];
		double[][] L,LT;
		//生成Hilbert matrix
		for(int i=0;i<n;i++)
			x[i]=1.0;
		//令b=H*x
		b=Matrix.matrixMultiply(H,x);
		System.out.println("H*x=b矩阵b是:");
		Matrix.showMatrix1D(b,true);

		//LLT分解
		L=LinearSystem.CholeskyLLT(H);
		LT=Matrix.transpose(L);
		double[] y=LinearSystem.forwordSubstitution(L, b);
		double[] xx=LinearSystem.backSubstitution(LT, y);
		System.out.println("H*x`=b的解x`是:");
		Matrix.showMatrix1D(xx,true);
		//令bb=H*xx
		bb=Matrix.matrixMultiply(H,xx);
		//求残差:r=b-bb
		for(int i=0;i<n;i++)
			r[i]=b[i]-bb[i];
		System.out.println("残差r(即b-H*x`)是:");
		Matrix.showMatrix1D(r,true);
		double norm_bb=Matrix.norm_00(bb);
		System.out.println("b`(即H*x`)的无穷范数是:"+norm_bb);
		System.out.println("残差的无穷范数是:"+Matrix.norm_00(r));
		//求△x:dx=xx-x
		for(int i=0;i<n;i++)
			dx[i]=xx[i]-x[i];
		System.out.println("△x(即x`-x)是:");
		Matrix.showMatrix1D(dx,true);
		double norm_xx=Matrix.norm_00(xx);
		System.out.println("x`的无穷范数是:"+norm_xx);
		System.out.println("△x的无穷范数是:"+Matrix.norm_00(dx));
		
		double norm_H=Matrix.norm_00(H);
		System.out.println("H的无穷范数是:"+norm_H);
		//Cond(H)≥||h||*||x||/||b||
		System.out.println("由\"条件数估计\"得到,cond(H)≥"+norm_H*norm_xx/norm_bb+"。");
	}
}
