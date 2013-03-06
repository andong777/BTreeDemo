import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.*;

public class MainWindow {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MyFrame frame;
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
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
	private JButton OptionButton;
	private JButton StartButton;
	private JButton ResetButton;
	private JButton ReplayButton;
	private JButton RandomButton;
	private ButtonGroup Group;
	private JRadioButton insert;
	private JRadioButton search;
	private JRadioButton delete;
	private JTextField text;
	private JComboBox box;
	private final Welcome welcome;

	// 存储数据的变量
	private int m = 4; // m叉B树中的m值
	private int data; // 来自文本域text的数据
	private Queue<Record> records;	//记录B树生成的过程
	private BTree btree; // 生成一颗B树

	// 用来重绘
	private void myPaintAll() {
		this.paintAll(getGraphics());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MyFrame() {

		// 生成B树
		btree = null;
		//生成记录
		records = new LinkedList<Record>();
		// 设置窗体属性
		setTitle("B树演示程序");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		setSize(width * 7 / 8, height * 7 / 8);
		setLocation(width / 16, height / 16);
		setResizable(false);

		Image img = kit.getImage(".//pic//icon.png");
		setIconImage(img);

		setLayout(new BorderLayout());

		// 以下为窗体布局
		DeclarePanel = new JPanel();
		add(DeclarePanel, BorderLayout.NORTH);
		DemoPanel = new JPanel();
		add(DemoPanel, BorderLayout.CENTER);
		OptionPanel = new JPanel();
		add(OptionPanel, BorderLayout.EAST);

		// 以下为顶部面板布局
		TitleLabel = new JLabel("B树的插入、搜索、删除演示程序");
		TitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		InfoLabel = new JLabel("软件1班 —— 安东制作 ");
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
		OptionButton = new JButton("应用");
		StartButton = new JButton("开始");
		ResetButton = new JButton("重置");
		ReplayButton = new JButton("回放");
		RandomButton = new JButton("随机生成");
		insert.setSelected(true);

		// 按钮的事件监听器，处理选择操作和接收数据
		RandomButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
			
		});
		ReplayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(records.isEmpty()){
					Message.AtLeastOneElement();
					return;
				}
				btree.reset();
				while(!records.isEmpty()){
					Record temp = records.remove();
					int data = temp.getData();
					State op = temp.getOp();
					switch(op){
					case insert:btree.insert(data);break;
					case delete:btree.delete(data);break;
					case search:btree.search(data);break;
					}
				}
			}
		});
		ResetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// btree.reset();
				btree = null;
				DemoPanel.removeAll();
				box.setEnabled(true);
				OptionButton.setEnabled(true);
				StartButton.setEnabled(true);
				RandomButton.setEnabled(true);
				text.setEnabled(true);
				insert.setEnabled(true);
				search.setEnabled(true);
				delete.setEnabled(true);
				DemoPanel.repaint();
				myPaintAll();
			}
		});
		OptionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					m = (int) box.getSelectedItem();
				} catch (Exception e1) {
					m = 4;
				}
				btree = new BTree(m);
				DemoPanel.add(btree, BorderLayout.CENTER);
				DemoPanel.repaint();
				myPaintAll();
				box.setEnabled(false);
				OptionButton.setEnabled(false);
			}
		});
		StartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				StartButton.setEnabled(false);
				insert.setEnabled(false);
				search.setEnabled(false);
				delete.setEnabled(false);
				text.setEnabled(true);
				if (btree == null) {
					Message.SelectMFirst();
				} else {
					try {
						data = Integer.parseInt(text.getText());
						if (insert.isSelected()) {
							records.add(new Record(State.insert,data));
							btree.insert(data);
						} else if (search.isSelected()) {
							records.add(new Record(State.search,data));
							btree.search(data);
						} else if (delete.isSelected()) {
							records.add(new Record(State.delete,data));
							btree.delete(data);
						}
					} catch (NumberFormatException e) {

						Message.IllegalInput(text.getText());
					}
				}
				StartButton.setEnabled(true);
				insert.setEnabled(true);
				search.setEnabled(true);
				delete.setEnabled(true);
				text.setEnabled(true);
				//自动清空文本框，获取焦点
				text.setText(null);
				text.requestFocus();
				repaint();
			}
		});

		Group.add(insert);
		Group.add(search);
		Group.add(delete);

		text = new JTextField();

		box = new JComboBox();
		// box.setEditable(false);
		// box.addItem(2);
		box.addItem(4);
//		box.addItem(3);
		box.addItem(5);
		box.addItem(6);

		// 使用Box布局，使得所有组件自上向下依次排列
		OptionPanel.setLayout(new BoxLayout(OptionPanel, BoxLayout.Y_AXIS));

		OptionPanel.add(new Blank(2));
		OptionPanel.add(new JLabel("请选择B树的叉数："));
		OptionPanel.add(new Blank(1));
		OptionPanel.add(new JLabel("（默认为4）"));
		OptionPanel.add(new Blank(1));
		OptionPanel.add(box);
		OptionPanel.add(new Blank(1));
		OptionPanel.add(OptionButton);
		OptionPanel.add(new Blank(1));
		OptionPanel.add(ResetButton);
		OptionPanel.add(new Blank(1));
		OptionPanel.add(ReplayButton);
		OptionPanel.add(new Blank(2));
		OptionPanel.add(RandomButton);

		OptionPanel.add(new Blank(1));
		WordsLabel = new JLabel("请选择操作：");
		OptionPanel.add(new Blank(2));
		OptionPanel.add(WordsLabel);
		OptionPanel.add(insert);
		OptionPanel.add(search);
		OptionPanel.add(delete);

		OptionPanel.add(new Blank(3));
		OptionPanel.add(new JLabel("请输入操作数："));
		OptionPanel.add(new Blank(2));
		OptionPanel.add(text);
		OptionPanel.add(new Blank(1));
		OptionPanel.add(StartButton);
		OptionPanel.add(new Blank(3));

		// 以下为主面板布局
		DemoPanel.setLayout(new BorderLayout());
		DemoPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.YELLOW), "演示",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
				new Color(0, 0, 0)));

		welcome = new Welcome();
		DemoPanel.add(welcome, BorderLayout.CENTER);

		// 开始时不能点击按钮和下拉框，点击屏幕后继续
		OptionButton.setEnabled(false);
		StartButton.setEnabled(false);
		RandomButton.setEnabled(false);
		ResetButton.setEnabled(false);
		ReplayButton.setEnabled(false);
		box.setEnabled(false);
		insert.setEnabled(false);
		search.setEnabled(false);
		delete.setEnabled(false);
		text.setEnabled(false);

		// 加入鼠标监听事件
		welcome.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				OptionButton.setEnabled(true);
				StartButton.setEnabled(true);
				ResetButton.setEnabled(true);
				RandomButton.setEnabled(true);
				ReplayButton.setEnabled(true);
				box.setEnabled(true);
				box.setEditable(true);
				insert.setEnabled(true);
				search.setEnabled(true);
				delete.setEnabled(true);
				text.setEnabled(true);

				// DemoPanel.remove(welcome);
				DemoPanel.removeAll();
				DemoPanel.repaint();
				welcome.addMouseListener(null);
			}
		});

		// 加入提示文字闪烁的功能
		new Thread(new Runnable() {

			@Override
			public void run() {
				boolean b = true;
				while (true) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					welcome.repaint();
					b = !b;
					welcome.setToPanit(b);
				}
			}

		}).start();

	}

}
