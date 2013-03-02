import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Welcome extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3810116821400318933L;

	protected void paintComponent(Graphics g){
		g.setColor(Color.BLUE);
		g.setFont(new Font("宋体",0,20));
		
//		FontMetrics fm=g.getFontMetrics();		
//		int stringAscent=fm.getAscent();
		g.drawString("请先选择需要演示的操作，然后点击确定按钮", 10, 50);
		g.drawString("程序演示时，每步操作之后会暂停，点击屏幕继续", 10, 100);
	}
}
