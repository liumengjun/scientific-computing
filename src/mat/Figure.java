package mat;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * class Figure 用以画出多项式poly的图像
 * @author liumengjun
 */
@SuppressWarnings("serial")
public class Figure extends JFrame{
	private static int figure_count = 0;
	private double[] poly;			//多项式
	private double[] lineX,lineY;	//线
	private double[] pointX,pointY;	//点
	private int unitPixels;		//图像中每个单位的像素个数
	private Origin origin;		//原点
	private boolean userOrigin;	//自定义原点
	
	private boolean drawLines;	//是否画线
	private boolean drawPoints;	//是否画点
	private boolean drawPoly;	//是否画多项式
	
	/**
	 * 构造方法,传递多项式给,Figure对象
	 * @param poly
	 */
	public Figure(){
		//基本设置
		super();
		this.setTitle("函数图象");
		this.setSize(400,300);
		this.setLocation(200 + figure_count*30,200 + figure_count*30);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		figure_count++;
		this.addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e) {
				 if (figure_count <= 1) {
					 System.exit(0);
				 } else {
					 figure_count--;
				 }
			 }
		});
		
		unitPixels=30;	//在我的机器上,一厘米大约30像素
		userOrigin=false;
		drawLines=false;
		drawPoints=false;
		drawPoly=false;
		origin=new Origin(200,150);//创建原点
		
		Container content=this.getContentPane();
		content.setBackground(Color.white);
	}
	
	/**
	 * 主画图方法
	 */
	public void paint(Graphics g){
		super.paint(g);
//		//添加一个透明的Panel，只为测试
//		JPanel p = new JPanel();
//		p.setOpaque(true);
//		this.add(p);
		if(!userOrigin){
			origin.Corner_X=getWidth()/2;
			origin.Corner_Y=getHeight()/2;
		}
		drawAxes(g);//画坐标轴
		if(drawLines){
			drawLines(g);
		}
		if(drawPoints){
			drawPoints(g);
		}
		if(drawPoly){
			drawPoly(g);
		}
	}

	/**
	 * 画坐标轴
	 * @param g
	 */
	private void drawAxes(Graphics g) {
		int width=this.getWidth();
		int height=this.getHeight();
		int x=origin.Corner_X,y=origin.Corner_Y;
		g.setColor(Color.black);
		g.drawLine(0, y, width, y);//x坐标轴
		g.drawLine(x, 0, x, height);//y坐标轴
		g.drawString("0", x, y);//原点
		int index=1;int i;
		for(i=x-unitPixels;i>0;i-=unitPixels,index++){
			g.drawLine(i, y, i, y-5);
			g.drawString("-"+index, i-7, y+12);
		}
		for(i=x+unitPixels,index=1;i<width;i+=unitPixels,index++){
			g.drawLine(i, y, i, y-5);
			g.drawString(String.valueOf(index), i-2, y+12);
		}
		for(i=y-unitPixels,index=1;i>0;i-=unitPixels,index++){
			g.drawLine(x, i, x+5, i);
			g.drawString(String.valueOf(index), x-14, i+5);
		}
		for(i=y+unitPixels,index=1;i<height;i+=unitPixels,index++){
			g.drawLine(x, i, x+5, i);
			g.drawString("-"+index, x-18, i+5);
		}
	}

	/**
	 * 画出(x[i],y[i])与(x[i+1],y[i+1])之间的线
	 * @param x
	 * @param y
	 */
	public void drawLines(double[] x,double[] y){
		if(x.length != y.length){
			System.out.println("两参数长度不一致");
			return;
		}
		lineX=x;
		lineY=y;
		drawLines=true;
		repaint();
	}
	
	private void drawLines(Graphics g){
		int n=lineX.length;
		int x1=(int)lineX[0]*unitPixels;
		int y1=(int)lineY[0]*unitPixels;
		x1+=origin.Corner_X;
		y1=origin.Corner_Y-y1;
		int x2,y2;
		g.setColor(Color.magenta);
		for(int i=1;i<n;i++){
			x2=(int)lineX[i]*unitPixels;
			x2+=origin.Corner_X;
			y2=(int)lineY[i]*unitPixels;
			y2=origin.Corner_Y-y2;
			g.drawLine(x1, y1, x2, y2);
			x1=x2;y1=y2;
		}
	}
	
	/**
	 * 画出(x[i],y[i])点
	 * @param x
	 * @param y
	 */
	public void drawPoints(double[] x,double[] y){
		if(x.length != y.length){
			System.out.println("两参数长度不一致");
			return;
		}
		pointX=x;
		pointY=y;
		drawPoints=true;
		repaint();
	}
	
	private void drawPoints(Graphics g){
		int n=pointX.length;
		int px,py;
		g.setColor(Color.green);
		for(int i=0;i<n;i++){
			px=(int)pointX[i]*unitPixels;
			px+=origin.Corner_X;
			py=(int)pointY[i]*unitPixels;
			py=origin.Corner_Y-py;
			g.fillOval(px-2, py-2, 4, 4);
		}
	}
	
	/**
	 * 画出多项式poly图像
	 * @param poly
	 */
	public void drawPoly(double[] poly){
		this.poly=poly;
		drawPoly=true;
		repaint();
	}
	
	private void drawPoly(Graphics g){
		String polyStr=Polynomial.toString(poly);
		int width=this.getWidth();
		g.setColor(Color.blue);
		g.setFont(new Font("Serif",Font.BOLD,16));
		g.drawString("f(x)="+polyStr, 20,getHeight()-20);
		
		for(int x=0;x<width;x++)
			g.drawLine(x,value(x),x+1,value(x+1));
	}
	
	/**
	 * 计算在实际程序图像中,多项式的值
	 * @param x
	 * @return f(x)
	 */
	private int value(double x){
		int n=poly.length;
		double y=poly[n-1];
		x-=origin.Corner_X;//转化为应用程序x
		x/=unitPixels;
		for(int i=n-2;i>=0;i--){
			y*=x;
			y+=poly[i];
		}
		int ans=(int)(y*unitPixels);
		ans=origin.Corner_Y-ans;//转化为应用程序y
		return ans;
	}

	/**
	 * 设置原点在程序中的坐标
	 * @param x
	 * @param y
	 */
	public void setOrigin(int x,int y){
		origin.Corner_X=x;
		origin.Corner_Y=y;
		userOrigin=true;
	}
	
	/**
	 * 设置单位长度为n像素
	 * @param n
	 */
	public void setUnitPixels(int n){
		unitPixels=n;
	}
	
	/**
	 * 防止闪烁
	 */
	public void update(Graphics g){
		paint(g);
	}
	
	//main测试
	public static void main(String[] args){
		double[] poly={1,2,1};
		Figure fig=new Figure();
		fig.drawPoly(poly);
		fig.setVisible(true);
		
	}
	
	/**
	 * inner class 原点类
	 * @author liumengjun
	 */
	class Origin{
		int Corner_X;
		int Corner_Y;
		Origin(int x,int y){
			Corner_X=x;
			Corner_Y=y;
		}
	}
}
