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
	private final Welcome welcome;
	
	// 存储数据的变量
	private int m = 4; // m叉B树中的m值
	private int data; // 来自文本域text的数据
	private int count = 0; // 计数树中已有的元素个数
	
	private BTree btree;	//生成一颗B树

	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	//用来重绘
	private void myPaintAll()
	{
		this.paintAll(getGraphics());
	}
	
	public MyFrame() {
		
		//生成B树
		btree = new BTree(m);
				
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
		StartButton = new JButton("确定");
		insert.setSelected(true);
		StartButton.setEnabled(false);	//开始时不能点击，看完开始屏幕后才可以点击
		
		// 按钮的事件监听器，处理选择操作和接收数据
		StartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				box.setEnabled(false);
				try {
					data = Integer.parseInt(text.getText());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					// JOptionPane.showMessageDialog(null,
					// "您输入的数据： " + text.getText() + " 不符合要求！\n请重新输入。");
					Message.IllegalInput(text.getText());
				}
				if (insert.isSelected()) {

					btree.insert(data);
					
				} else if (search.isSelected()) {
					if (count == 0) {
						// JOptionPane
						// .showMessageDialog(null, "请先插入至少一个元素再执行其他操作");
						Message.AtLeastOneElement();
						
					} else {

						btree.search(data);
						
					}
				} else if (delete.isSelected()) {
					if (count == 0) {
						// JOptionPane
						// .showMessageDialog(null, "请先插入至少一个元素再执行其他操作");
						Message.AtLeastOneElement();
						
					} else {

						btree.delete(data);
						
					}
				}

			}
		});
		
		Group.add(insert);
		Group.add(search);
		Group.add(delete);

		text = new JTextField();

		box = new JComboBox();
		box.setEditable(false);
		box.addItem("2");
		box.addItem("3");
		box.addItem("4");
		box.addItem("5");
		box.addItem("6");
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					m = (int) box.getSelectedItem();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					m = 4;
				}
			}

		});
		
		//使用Box布局，使得所有组件自上向下依次排列
		OptionPanel.setLayout(new BoxLayout(OptionPanel, BoxLayout.Y_AXIS));

		OptionPanel.add(new Blank(3));
		OptionPanel.add(new JLabel("请选择B树的叉数："));
		OptionPanel.add(new Blank(1));
		OptionPanel.add(new JLabel("（默认为4）"));
		OptionPanel.add(new Blank(1));
		OptionPanel.add(box);
		
		OptionPanel.add(new Blank(3));
		WordsLabel = new JLabel("请选择操作：");
		OptionPanel.add(new Blank(2));
		OptionPanel.add(WordsLabel);
		OptionPanel.add(insert);
		OptionPanel.add(search);
		OptionPanel.add(delete);
		
		OptionPanel.add(new Blank(4));
		OptionPanel.add(new JLabel("请输入操作数："));
		OptionPanel.add(new Blank(2));
		OptionPanel.add(text);
		OptionPanel.add(new Blank(4));
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
		DemoPanel.add(welcome,BorderLayout.CENTER);
		//加入鼠标监听事件
		welcome.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				StartButton.setEnabled(true);
//				DemoPanel.remove(welcome);
				DemoPanel.removeAll();
//				DemoPanel.repaint();
				DemoPanel.add(btree,BorderLayout.CENTER);
				DemoPanel.repaint();
				myPaintAll();
			}
		});
		
	
		
		//加入提示文字闪烁的功能
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean b = true;
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					welcome.repaint();
//					DemoPanel.repaint();
					b = !b;
					welcome.setToPanit(b);
				}
			}
			
			
		}).start();

	}
	
	

}
