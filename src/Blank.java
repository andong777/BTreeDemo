import javax.swing.JLabel;

// ��ʾ�հף����ڿ��ƽ���
public class Blank extends JLabel {

		public Blank(int n) {
			//nΪ���ƿն�����
			StringBuilder builder = new StringBuilder();
			builder.append("<html>");
			for (int i = 0; i < n; i++) {
				builder.append("<br/>");
			}
			builder.append("</html>");
			this.setText(builder.toString());
		}
	}