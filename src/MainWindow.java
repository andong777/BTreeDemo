import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainWindow {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MyFrame frame;
				try {
					frame = new MyFrame();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}
}

class MyFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4299730854678831577L;
	// ������صı���
	private JPanel DeclarePanel;
	private JPanel DemoPanel;
	private JPanel OptionPanel;
	private JLabel TitleLabel;
	private JLabel InfoLabel;
	private JLabel WordsLabel;
	private JButton StartButton;
	private ButtonGroup Group;
	private JRadioButton insert;
	private JRadioButton search;
	private JRadioButton delete;
	private JTextField text;
	private JComboBox box;
	// private InsertDemo InsertPanel;
	// private SearchDemo SearchPanel;
	// private DeleteDemo DeletePanel;
	// �洢���ݵı���
	private int m = 2; // m��B���е�mֵ
	private int data; // �����ı���text������
	private int count = 0; // �����������е�Ԫ�ظ���

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MyFrame() {
		// ���ô�������
		setTitle("B����ʾ����");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		setSize(width / 2, height / 2);
		setLocation(width / 4, height / 4);
		// ����ȴ�����ͼ��
		// Image img = kit.getImage(".//pic//llq04.png");
		// setIconImage(img);
		setLayout(new BorderLayout());

		// ����Ϊ���岼��
		DeclarePanel = new JPanel();
		add(DeclarePanel, BorderLayout.NORTH);
		DemoPanel = new JPanel();
		add(DemoPanel, BorderLayout.CENTER);
		OptionPanel = new JPanel();
		add(OptionPanel, BorderLayout.EAST);
		// OptionPanel = new JPanel();

		// ����Ϊ������岼��
		TitleLabel = new JLabel("B���Ĳ��롢������ɾ����ʾ����");
		InfoLabel = new JLabel("���1��	��������		");
		InfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		TitleLabel.setForeground(Color.RED);
		TitleLabel.setFont(new Font("΢���ź�", 0, 24));
		InfoLabel.setFont(new Font("�����п�", 0, 18));
		DeclarePanel.setLayout(new BorderLayout());
		DeclarePanel.add(TitleLabel, BorderLayout.CENTER);
		DeclarePanel.add(InfoLabel, BorderLayout.SOUTH);

		// ����Ϊ�ұ���岼��
		Group = new ButtonGroup();
		insert = new JRadioButton("����");
		search = new JRadioButton("����");
		delete = new JRadioButton("ɾ��");
		StartButton = new JButton("ȷ��");
		// ��ť���¼�������������ѡ������ͽ�������
		StartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					data = Integer.parseInt(text.getText());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
//					JOptionPane.showMessageDialog(null,
//							"����������ݣ� " + text.getText() + " ������Ҫ��\n���������롣");
					Message.IllegalInput(text.getText());
				}
				if (insert.isSelected()) {
					// InsertPanel = new InsertDemo();
					// DemoPanel.add(InsertPanel, BorderLayout.CENTER);
				} else if (search.isSelected()) {
					if (count == 0) {
//						JOptionPane
//								.showMessageDialog(null, "���Ȳ�������һ��Ԫ����ִ����������");
						Message.AtLeastOneElement();
					} else {
						// SearchPanel = new SearchDemo();
						// DemoPanel.add(SearchPanel, BorderLayout.CENTER);
					}
				} else if (delete.isSelected()) {
					if (count == 0) {
//						JOptionPane
//								.showMessageDialog(null, "���Ȳ�������һ��Ԫ����ִ����������");
						Message.AtLeastOneElement();
					} else {
						// DeletePanel = new DeleteDemo();
						// DemoPanel.add(DeletePanel, BorderLayout.CENTER);
					}
				}

			}
		});
		Group.add(insert);
		Group.add(search);
		Group.add(delete);

		text = new JTextField();
		text.setText("������һ��������");
		
		box = new JComboBox();
		box.setEditable(true);
		box.addItem("��ѡ��B���Ĳ���m");
		box.addItem("2");
		box.addItem("3");
		box.addItem("4");
		box.addItem("5");
		box.addItem("6");
		box.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					m = (int) box.getSelectedItem();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//�Ժ���д����
					m = 2;
				}
			}
			
		});
		
		OptionPanel.setLayout(new BoxLayout(OptionPanel, BoxLayout.Y_AXIS));
		WordsLabel = new JLabel();
		WordsLabel.setText("<html><br/><br/><p>��ѡ�������</p><br/></html>");
		OptionPanel.add(WordsLabel);
		OptionPanel.add(insert);
		OptionPanel.add(search);
		OptionPanel.add(delete);
		JLabel blank1 = new JLabel(); // ���ڵ����������
		blank1.setText("<html><br/><br/></html>");
		JLabel blank2 = new JLabel();
		blank2.setText("<html><br/><br/></html>");
		OptionPanel.add(blank1);
		OptionPanel.add(text);
		OptionPanel.add(blank2);
		OptionPanel.add(StartButton);

		// ����Ϊ����岼��
		DemoPanel.setLayout(new BorderLayout());
		DemoPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.YELLOW), "��ʾ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
				new Color(0, 0, 0)));
		// Welcome WelcomePanel = new Welcome();
		DemoPanel.add(new Welcome(), BorderLayout.CENTER);
		// DemoPanel.repaint();

	}

}
