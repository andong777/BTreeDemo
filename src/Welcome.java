import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Welcome extends JPanel {
	/**
	 * 
	 */
	private boolean toPanit = true;
	// private int line;
	private static final long serialVersionUID = 3810116821400318933L;

	public void paint(Graphics g) {

		super.paint(g);
		g.setColor(Color.BLACK);
		g.setFont(new Font("΢���ź�", 0, 20));

		g.drawString("���������ͨ��ÿ�β���������B�������Գ��Ե����������ɡ�", 10, 220);
		g.drawString("���⣬��������ʹ�á����á������B����ʹ�á��طš������²���", 10, 180);
		g.drawString("����ѡ����Ҫ��ʾ�Ĳ�����Ȼ��������ʼ����ť", 10, 140);
		g.drawString("���Ҳ��������B���Ĳ�����Ȼ������Ӧ�á���ť�������", 10, 100);
		g.drawString("��ӭʹ��B����ʾ����", 100, 50);
		if (toPanit) {
			g.setColor(Color.BLUE);
			g.setFont(new Font("����", 0, 24));
			g.drawString("�����Ļ����", 500, 350);
		}
	}

	public boolean isToPanit() {
		return toPanit;
	}

	public void setToPanit(boolean bool) {
		this.toPanit = bool;
	}

}
