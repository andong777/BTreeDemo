import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Welcome extends JPanel {
	/**
	 * 
	 */
	private boolean toPanit = true;

	private static final long serialVersionUID = 3810116821400318933L;

	public void paint(Graphics g) {
		
		super.paint(g);
		g.setColor(Color.BLUE);
		g.setFont(new Font("����", 0, 20));

		// FontMetrics fm=g.getFontMetrics();
		// int stringAscent=fm.getAscent();
		g.drawString("����ѡ����Ҫ��ʾ�Ĳ�����Ȼ����ȷ����ť", 10, 50);
		if (toPanit) {
			g.drawString("�����Ļ����", 500, 300);
		}
	}

	public boolean isToPanit() {
		return toPanit;
	}

	public void setToPanit(boolean bool) {
		this.toPanit = bool;
	}


}
