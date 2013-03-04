import javax.swing.JLabel;

// 显示空白，用于控制界面
public class Blank extends JLabel {

		public Blank(int n) {
			//n为控制空多少行
			StringBuilder builder = new StringBuilder();
			builder.append("<html>");
			for (int i = 0; i < n; i++) {
				builder.append("<br/>");
			}
			builder.append("</html>");
			this.setText(builder.toString());
		}
	}