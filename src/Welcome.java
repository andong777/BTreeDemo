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
		g.setFont(new Font("����",0,20));
		
//		FontMetrics fm=g.getFontMetrics();		
//		int stringAscent=fm.getAscent();
		g.drawString("����ѡ����Ҫ��ʾ�Ĳ�����Ȼ����ȷ����ť", 10, 50);
		g.drawString("������ʾʱ��ÿ������֮�����ͣ�������Ļ����", 10, 100);
	}
}
