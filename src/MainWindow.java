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
	// 界面相关的变量
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
	// 存储数据的变量
	private int m = 2; // m叉B树中的m值
	private int data; // 来自文本域text的数据
	private int count = 0; // 计数树中已有的元素个数

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MyFrame() {
		// 设置窗体属性
		setTitle("B树演示程序");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		setSize(width / 2, height / 2);
		setLocation(width / 4, height / 4);
		// 这里等待加入图标
		// Image img = kit.getImage(".//pic//llq04.png");
		// setIconImage(img);
		setLayout(new BorderLayout());

		// 以下为窗体布局
		DeclarePanel = new JPanel();
		add(DeclarePanel, BorderLayout.NORTH);
		DemoPanel = new JPanel();
		add(DemoPanel, BorderLayout.CENTER);
		OptionPanel = new JPanel();
		add(OptionPanel, BorderLayout.EAST);
		// OptionPanel = new JPanel();

		// 以下为顶部面板布局
		TitleLabel = new JLabel("B树的插入、搜索、删除演示程序");
		InfoLabel = new JLabel("软件1班	安东制作		");
		InfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		TitleLabel.setForeground(Color.RED);
		TitleLabel.setFont(new Font("微软雅黑", 0, 24));
		InfoLabel.setFont(new Font("华文行楷", 0, 18));
		DeclarePanel.setLayout(new BorderLayout());
		DeclarePanel.add(TitleLabel, BorderLayout.CENTER);
		DeclarePanel.add(InfoLabel, BorderLayout.SOUTH);

		// 以下为右边面板布局
		Group = new ButtonGroup();
		insert = new JRadioButton("插入");
		search = new JRadioButton("搜索");
		delete = new JRadioButton("删除");
		StartButton = new JButton("确定");
		// 按钮的事件监听器，处理选择操作和接收数据
		StartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					data = Integer.parseInt(text.getText());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
//					JOptionPane.showMessageDialog(null,
//							"您输入的数据： " + text.getText() + " 不符合要求！\n请重新输入。");
					Message.IllegalInput(text.getText());
				}
				if (insert.isSelected()) {
					// InsertPanel = new InsertDemo();
					// DemoPanel.add(InsertPanel, BorderLayout.CENTER);
				} else if (search.isSelected()) {
					if (count == 0) {
//						JOptionPane
//								.showMessageDialog(null, "请先插入至少一个元素再执行其他操作");
						Message.AtLeastOneElement();
					} else {
						// SearchPanel = new SearchDemo();
						// DemoPanel.add(SearchPanel, BorderLayout.CENTER);
					}
				} else if (delete.isSelected()) {
					if (count == 0) {
//						JOptionPane
//								.showMessageDialog(null, "请先插入至少一个元素再执行其他操作");
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
		text.setText("请输入一个整数：");
		
		box = new JComboBox();
		box.setEditable(true);
		box.addItem("请选择B树的叉树m");
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
					//以后再写这里
					m = 2;
				}
			}
			
		});
		
		OptionPanel.setLayout(new BoxLayout(OptionPanel, BoxLayout.Y_AXIS));
		WordsLabel = new JLabel();
		WordsLabel.setText("<html><br/><br/><p>请选择操作：</p><br/></html>");
		OptionPanel.add(WordsLabel);
		OptionPanel.add(insert);
		OptionPanel.add(search);
		OptionPanel.add(delete);
		JLabel blank1 = new JLabel(); // 用于调整组件布局
		blank1.setText("<html><br/><br/></html>");
		JLabel blank2 = new JLabel();
		blank2.setText("<html><br/><br/></html>");
		OptionPanel.add(blank1);
		OptionPanel.add(text);
		OptionPanel.add(blank2);
		OptionPanel.add(StartButton);

		// 以下为主面板布局
		DemoPanel.setLayout(new BorderLayout());
		DemoPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.YELLOW), "演示",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
				new Color(0, 0, 0)));
		// Welcome WelcomePanel = new Welcome();
		DemoPanel.add(new Welcome(), BorderLayout.CENTER);
		// DemoPanel.repaint();

	}

}
