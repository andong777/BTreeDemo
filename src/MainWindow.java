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
	private final Welcome welcome;
	
	// �洢���ݵı���
	private int m = 4; // m��B���е�mֵ
	private int data; // �����ı���text������
	private int count = 0; // �����������е�Ԫ�ظ���
	
	private BTree btree;	//����һ��B��

	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	//�����ػ�
	private void myPaintAll()
	{
		this.paintAll(getGraphics());
	}
	
	public MyFrame() {
		
		//����B��
		btree = new BTree(m);
				
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
		StartButton = new JButton("ȷ��");
		insert.setSelected(true);
		StartButton.setEnabled(false);	//��ʼʱ���ܵ�������꿪ʼ��Ļ��ſ��Ե��
		
		// ��ť���¼�������������ѡ������ͽ�������
		StartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				box.setEnabled(false);
				try {
					data = Integer.parseInt(text.getText());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					// JOptionPane.showMessageDialog(null,
					// "����������ݣ� " + text.getText() + " ������Ҫ��\n���������롣");
					Message.IllegalInput(text.getText());
				}
				if (insert.isSelected()) {

					btree.insert(data);
					
				} else if (search.isSelected()) {
					if (count == 0) {
						// JOptionPane
						// .showMessageDialog(null, "���Ȳ�������һ��Ԫ����ִ����������");
						Message.AtLeastOneElement();
						
					} else {

						btree.search(data);
						
					}
				} else if (delete.isSelected()) {
					if (count == 0) {
						// JOptionPane
						// .showMessageDialog(null, "���Ȳ�������һ��Ԫ����ִ����������");
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
		
		//ʹ��Box���֣�ʹ�������������������������
		OptionPanel.setLayout(new BoxLayout(OptionPanel, BoxLayout.Y_AXIS));

		OptionPanel.add(new Blank(3));
		OptionPanel.add(new JLabel("��ѡ��B���Ĳ�����"));
		OptionPanel.add(new Blank(1));
		OptionPanel.add(new JLabel("��Ĭ��Ϊ4��"));
		OptionPanel.add(new Blank(1));
		OptionPanel.add(box);
		
		OptionPanel.add(new Blank(3));
		WordsLabel = new JLabel("��ѡ�������");
		OptionPanel.add(new Blank(2));
		OptionPanel.add(WordsLabel);
		OptionPanel.add(insert);
		OptionPanel.add(search);
		OptionPanel.add(delete);
		
		OptionPanel.add(new Blank(4));
		OptionPanel.add(new JLabel("�������������"));
		OptionPanel.add(new Blank(2));
		OptionPanel.add(text);
		OptionPanel.add(new Blank(4));
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
		DemoPanel.add(welcome,BorderLayout.CENTER);
		//�����������¼�
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
		
	
		
		//������ʾ������˸�Ĺ���
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
