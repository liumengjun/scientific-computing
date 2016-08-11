package mat;


public class HilbertMatrix {
	
	
	//main
	public static void main(String[] args){
		int n=12;
		double[][] H=Matrix.makeHilbertMatrix(n);
		double[] x=new double[n],dx=new double[n];
		
		double[] b,bb,r=new double[n];
		double[][] L,LT;
		//����Hilbert matrix
		for(int i=0;i<n;i++)
			x[i]=1.0;
		//��b=H*x
		b=Matrix.matrixMultiply(H,x);
		System.out.println("H*x=b����b��:");
		Matrix.showMatrix1D(b,true);

		//LLT�ֽ�
		L=LinearSystem.CholeskyLLT(H);
		LT=Matrix.transpose(L);
		double[] y=LinearSystem.forwordSubstitution(L, b);
		double[] xx=LinearSystem.backSubstitution(LT, y);
		System.out.println("H*x`=b�Ľ�x`��:");
		Matrix.showMatrix1D(xx,true);
		//��bb=H*xx
		bb=Matrix.matrixMultiply(H,xx);
		//��в�:r=b-bb
		for(int i=0;i<n;i++)
			r[i]=b[i]-bb[i];
		System.out.println("�в�r(��b-H*x`)��:");
		Matrix.showMatrix1D(r,true);
		double norm_bb=Matrix.norm_00(bb);
		System.out.println("b`(��H*x`)���������:"+norm_bb);
		System.out.println("�в���������:"+Matrix.norm_00(r));
		//���x:dx=xx-x
		for(int i=0;i<n;i++)
			dx[i]=xx[i]-x[i];
		System.out.println("��x(��x`-x)��:");
		Matrix.showMatrix1D(dx,true);
		double norm_xx=Matrix.norm_00(xx);
		System.out.println("x`���������:"+norm_xx);
		System.out.println("��x���������:"+Matrix.norm_00(dx));
		
		double norm_H=Matrix.norm_00(H);
		System.out.println("H���������:"+norm_H);
		//Cond(H)��||h||*||x||/||b||
		System.out.println("��\"����������\"�õ�,cond(H)��"+norm_H*norm_xx/norm_bb+"��");
	}
}
