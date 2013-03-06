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
	// ������صı���
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

	// �洢���ݵı���
	private int m = 4; // m��B���е�mֵ
	private int data; // �����ı���text������
	private Queue<Record> records;	//��¼B�����ɵĹ���
	private BTree btree; // ����һ��B��

	// �����ػ�
	private void myPaintAll() {
		this.paintAll(getGraphics());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MyFrame() {

		// ����B��
		btree = null;
		//���ɼ�¼
		records = new LinkedList<Record>();
		// ���ô�������
		setTitle("B����ʾ����");
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

		// ����Ϊ���岼��
		DeclarePanel = new JPanel();
		add(DeclarePanel, BorderLayout.NORTH);
		DemoPanel = new JPanel();
		add(DemoPanel, BorderLayout.CENTER);
		OptionPanel = new JPanel();
		add(OptionPanel, BorderLayout.EAST);

		// ����Ϊ������岼��
		TitleLabel = new JLabel("B���Ĳ��롢������ɾ����ʾ����");
		TitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		InfoLabel = new JLabel("���1�� ���� �������� ");
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
		OptionButton = new JButton("Ӧ��");
		StartButton = new JButton("��ʼ");
		ResetButton = new JButton("����");
		ReplayButton = new JButton("�ط�");
		RandomButton = new JButton("�������");
		insert.setSelected(true);

		// ��ť���¼�������������ѡ������ͽ�������
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
				//�Զ�����ı��򣬻�ȡ����
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

		// ʹ��Box���֣�ʹ�������������������������
		OptionPanel.setLayout(new BoxLayout(OptionPanel, BoxLayout.Y_AXIS));

		OptionPanel.add(new Blank(2));
		OptionPanel.add(new JLabel("��ѡ��B���Ĳ�����"));
		OptionPanel.add(new Blank(1));
		OptionPanel.add(new JLabel("��Ĭ��Ϊ4��"));
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
		WordsLabel = new JLabel("��ѡ�������");
		OptionPanel.add(new Blank(2));
		OptionPanel.add(WordsLabel);
		OptionPanel.add(insert);
		OptionPanel.add(search);
		OptionPanel.add(delete);

		OptionPanel.add(new Blank(3));
		OptionPanel.add(new JLabel("�������������"));
		OptionPanel.add(new Blank(2));
		OptionPanel.add(text);
		OptionPanel.add(new Blank(1));
		OptionPanel.add(StartButton);
		OptionPanel.add(new Blank(3));

		// ����Ϊ����岼��
		DemoPanel.setLayout(new BorderLayout());
		DemoPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.YELLOW), "��ʾ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
				new Color(0, 0, 0)));

		welcome = new Welcome();
		DemoPanel.add(welcome, BorderLayout.CENTER);

		// ��ʼʱ���ܵ����ť�������򣬵����Ļ�����
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

		// �����������¼�
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

		// ������ʾ������˸�Ĺ���
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
