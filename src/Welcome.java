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
		g.setFont(new Font("微软雅黑", 0, 20));

		g.drawString("如果您不想通过每次插入来生成B树，可以尝试点击“随机生成”", 10, 220);
		g.drawString("此外，您还可以使用“重置”来清空B树，使用“回放”来重新播放", 10, 180);
		g.drawString("请先选择需要演示的操作，然后点击“开始”按钮", 10, 140);
		g.drawString("在右侧可以设置B树的叉数，然后点击“应用”按钮保存更改", 10, 100);
		g.drawString("欢迎使用B树演示程序！", 100, 50);
		if (toPanit) {
			g.setColor(Color.BLUE);
			g.setFont(new Font("宋体", 0, 24));
			g.drawString("点击屏幕继续", 500, 350);
		}
	}

	public boolean isToPanit() {
		return toPanit;
	}

	public void setToPanit(boolean bool) {
		this.toPanit = bool;
	}

}
