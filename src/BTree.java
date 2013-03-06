import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//�������ڴ洢���ɵ�B�����ݺͻ���B��ͼ��
public class BTree extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5189121418491891693L;
	// �洢B�����ݵı���
	private BTreeNode root; // ͷ���
	private int m; // m�����е�m
	private int mid; // �е�
	private BTreeNode p;
	private Counter cnt;
	private int data; // ��¼��ǰ�Ĳ�����
	private int node; // ��¼Ҫǰ���Ľڵ���
	// ������ʾ�ı���
	private State state; // ��ʾ��ǰִ�в�����״̬��������ʾ��ʾ��
	private final int TIME = 1500;
	private final int rootX = 500; // ���ڵ����Ͻǵ�x����
	private final int rootY = 40; // ���ڵ����Ͻǵ�y����
	private final int HEIGHT = 20; // �ڵ�ͼ�εĸ߶�
	private final int LENGTH = 20; // �ڵ�ͼ��������ÿ��Ԫ�صĳ���
	private Stack<BTreeNode> stack; // ���ڴ�Žڵ��ջ

	// private JFrame f;
	// private Graphics g;

	public BTree(int m) {

		// f = frame;
		data = 0; // �����壬��ͬ
		node = 0;
		state = State.wait;
		root = null;
		this.m = m;
		cnt = new Counter(m, LENGTH, 2 * HEIGHT);
		stack = new Stack<BTreeNode>();
		mid = (int) Math.ceil((m + 0.0) / 2);
		// root = new BTreeNode(rootX, rootY);
		// root.add(100);
		// root.add(20);
		// stack.push(root);
		repaint();

	}

	// public void myPaint(){
	// repaint();
	// f.paintAll(g);
	//
	// }
	public void reset() {
		root = null;
		state = State.wait;
		data = 0;
		node = 0;
		repaint();

	}

	public void setM(int m) {
		this.m = m;
	}

	/****************************** ����Ϊ������̵Ĵ��� ******************/
	// �����в���Ԫ��
	public void insert(int v) {
		// ������ʾ��
		state = State.insert;
		data = v;
		repaint();
		paintAll(getGraphics());

		// �������ͬԪ�أ�����ʧ�ܡ�
		if (shortSearch(v))
			Message.SameElement();
		else if (root == null) {

			root = new BTreeNode(rootX, rootY);
			root.add(v);
			repaint();

		} else {

			// ���ͷ�����������Ҫ���ѣ�����һ���µĽڵ�
			if (root.numOfElements == m - 1) {
				BTreeNode newNode = new BTreeNode(rootX, rootY); // ��������ȡ��ԭ���ڵ�
				// newNode.value[1] = root.value[mid];
				root.numOfElements = mid - 1;
				split(newNode, 1, root);
				int x = cnt.getNextPos(0, mid - 1);
				root.setPoint(rootX + x, rootY + HEIGHT * 3); // �޸�ԭ���ڵ��λ��
				root = newNode; // ��root����ָ��ͷ���
				root.isLeaf = false;
				Recount();
				repaint();

			}
			// �����һ��������в���
			p = root;

			if (p == null)
				return;

			while (!p.isLeaf) { // δ����Ҷ�ڵ�

				// ����Ч��
				p.color = Color.RED;
				repaint();
				paintAll(getGraphics());

				try {
					Thread.sleep(TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				p.color = Color.BLACK;
				repaint();
				paintAll(getGraphics());

				int i;
				for (i = 1; i <= p.numOfElements; i++) {
					p.ElementHighlightIdx = i;
					repaint();
					paintAll(getGraphics());
					try {
						Thread.sleep(TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (p.value[i] > v) {
						break;
					}
				}
				p.ElementHighlightIdx = -1;
				repaint();
				paintAll(getGraphics());

				BTreeNode temp;
				state = State.go;
				// p�½������ʵ�λ��
				if (i <= p.numOfElements) {
					temp = p.children[i - 1];
					p.lineHighlightIdx = i-1;
					node = i-1;
					repaint();
					paintAll(getGraphics());

					try {
						Thread.sleep(TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					temp = p.children[p.numOfElements]; // ��ֹԽ��
					p.lineHighlightIdx = p.numOfElements;
					node = p.numOfElements;
					repaint();
					paintAll(getGraphics());
					try {
						Thread.sleep(TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				p.lineHighlightIdx = -1;
				repaint();
				paintAll(getGraphics());

				if (temp.numOfElements == m - 1) {
					split(p, i, temp);
					Recount();
					p = root;
				} else
					p = temp;

			}
			// ����Ҷ�ڵ㣬�������Ԫ��
			p.add(v);
		}

		state = State.inserted;
		repaint();
		paintAll(getGraphics());

	}

	// ����һ���ڵ� parentΪ���ѳ�����Ԫ�ص���Ľڵ�,posΪ�������λ�ã�childrenΪ�����ѵĽڵ�
	public void split(BTreeNode parent, int pos, BTreeNode children) {

		state = State.split;
		// �����Ԫ�ز��뵽parent��,���ƶ����ӵ�λ��
		for (int i = pos; i <= parent.numOfElements; i++) {
			parent.value[i + 1] = parent.value[i];
			parent.children[i + 1] = parent.children[i];
		}
		BTreeNode newNode = new BTreeNode(0, 0);
		parent.children[pos - 1] = children;
		parent.children[pos] = newNode;

		parent.value[pos] = children.value[mid];
		parent.numOfElements += 1;

		// �������Ԫ�ش�children��ɾ��
		children.numOfElements = mid - 1;

		// ת�ƺ�벿��Ԫ�����½ڵ�
		for (int i = 1; i <= m - mid - 1; i++) {
			newNode.value[i] = children.value[mid + i];
		}
		// ת�ƺ�벿�ֺ������½ڵ�
		for (int i = 0; i <= m - mid - 1; i++) {
			newNode.children[i] = children.children[mid + i];
		}
		// �޸�newNodeֵ
		newNode.numOfElements = m - 1 - mid;

		newNode.isLeaf = (newNode.children[0] == null);

	}

	// ����������Ԫ��,��������ֵ
	public void search(int v) {
		// ������ʾ��

		p = root;
		if (p == null) {
			Message.AtLeastOneElement();
			return;
		}

		state = State.search;
		data = v;
		while (p != null) {
			int i;
			for (i = 1; i <= p.numOfElements; i++) {
				p.ElementHighlightIdx = i;
				repaint();
				paintAll(getGraphics());
				try {
					Thread.sleep(TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// �ҵ���Ԫ��
				if (p.value[i] == v) {
					p.ElementHighlightIdx = -1;
					p.lineHighlightIdx = -1;
					state = State.searched;
					repaint();
					paintAll(getGraphics());
					// Message.EndSearch();
					return;
				}
				if (p.value[i] > v)
					break;
			}
			state = State.go;
			// p�½������ʵ�λ��
			if (i <= p.numOfElements) {
				p.lineHighlightIdx = i - 1;
				node = i;
				repaint();
				paintAll(getGraphics());
				try {
					Thread.sleep(TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				p.lineHighlightIdx = -1;
				repaint();
				paintAll(getGraphics());
				p = p.children[i - 1];
			} else {
				p.lineHighlightIdx = p.numOfElements;
				node = p.numOfElements;
				repaint();
				paintAll(getGraphics());
				try {
					Thread.sleep(TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				p.lineHighlightIdx = -1;
				repaint();
				paintAll(getGraphics());
				p = p.children[p.numOfElements]; // ��ֹԽ��
			}

		}

		// ��Ȼδ�ҵ��������Ի���
		Message.NoSuchElement(v);

		state = State.searched;
		repaint();
		paintAll(getGraphics());
	}

	// ��������������
	public boolean shortSearch(int v) {
		p = root;

		if (p == null)
			return false;

		while (p != null) {
			int i;
			for (i = 1; i <= p.numOfElements; i++) {
				// �ҵ���Ԫ��
				if (p.value[i] == v) {

					// Message.EndSearch();
					return true;
				}
				if (p.value[i] > v)
					break;
			}
			// p�½������ʵ�λ��
			if (i <= p.numOfElements)
				p = p.children[i - 1];
			else
				p = p.children[p.numOfElements]; // ��ֹԽ��

		}
		return false;
	}

	/****************************** ����Ϊɾ�����̵Ĵ��� **************************/
	// ������ɾ��Ԫ��
	public void delete(int v) {
		// ������ʾ��

		p = root;
		if (p == null) {
			Message.AtLeastOneElement();
			return;
		}

		state = State.delete;
		data = v;
		// δ�ҵ���Ԫ��
		if (!shortSearch(v)) {
			Message.NoSuchElement(v);
		} else {
			// ���ֻ��һ�㣬ֱ��ɾ��
			if (root.isLeaf)
				root.remove(v);
			else if (root.numOfElements == 1) {
				BTreeNode left = root.children[0];
				BTreeNode right = root.children[1];
				// ���ڵ��������ӽڵ��Ϊ����Ԫ�ص��������ʱ�ϲ��������ڵ�
				if (left != null && right != null
						&& left.numOfElements == mid - 1
						&& right.numOfElements == mid - 1) {
					merge(root, 1, left, right);
					root = left;
					Recount();
				}

				deleteUnderRoot(root, v);
				Recount();
			} else {
				System.out.println("ok");
				deleteUnderRoot(root, v);
				Recount();
			}
			state = State.deleted;
			repaint();
			paintAll(getGraphics());
		}
	}

	// ����ɾ���ĺ������ӷǸ��ڵ���ɾ��Ԫ��
	public void deleteUnderRoot(BTreeNode lnk, int v) {
		// ��ǰ�ڵ���Ҷ�ڵ㣬ֱ��ɾ�����Ԫ��
		if (lnk.isLeaf) {
			lnk.remove(v);
		} else {// �ڵ�ǰ�Ľڵ��ϣ�
			int i; // �ҵ���һ����С��v��Ԫ�ص�λ��
			for (i = 1; i <= lnk.numOfElements; i++) {
				lnk.ElementHighlightIdx = i;
				repaint();
				paintAll(getGraphics());
				try {
					Thread.sleep(TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (lnk.value[i] >= v)
					break;
			}
			lnk.ElementHighlightIdx = -1;
			repaint();
			paintAll(getGraphics());
			// ���λ�þ���v�����������Ѱ��Ԫ�ش���֮��Ȼ����ɾ���Ǹ������滻��Ԫ��
			if (i <= lnk.numOfElements && lnk.value[i] == v) {
				BTreeNode left = lnk.children[i - 1];
				BTreeNode right = lnk.children[i];
				// ���������Ԫ����������СԪ����������Ѱ�����Ԫ��
				if (left != null && left.numOfElements > mid - 1) {
					BTreeNode p = left;
					while (p.children[0] != null) {
						p = p.children[p.numOfElements];
					}
					int original = lnk.value[i];
					int largest = p.value[p.numOfElements];
					p.value[p.numOfElements] = lnk.value[i];
					lnk.value[i] = largest;
					 deleteUnderRoot(p, original);
//					delete(original);
					// System.out.println(original);
					// p.remove(original);
					// ���������Ԫ����������СԪ����������Ѱ����СԪ��
				} else if (right != null && right.numOfElements > mid - 1) {
					BTreeNode p = right;
					while (p.children[0] != null) {
						p = p.children[0];
					}
					int original = lnk.value[i];
					int smallest = p.value[1];
					p.value[1] = lnk.value[i];
					lnk.value[i] = smallest;
					 deleteUnderRoot(p, original);
//					delete(original);
					// p.remove(original);
					// �ϲ��������ڵ�
				} else {
					merge(lnk, i, left, right);
					deleteUnderRoot(left, v);
				}
			}
			// ��������Ѱ��
			else {
				// children��i-1��Ϊָ��Ӧ���½���λ��
				BTreeNode right = null;
				BTreeNode temp = lnk.children[i - 1];
				// ���Ҫǰ���Ľڵ�Ԫ����������СԪ���������Դ���������Ԫ��
				if (temp.numOfElements == mid - 1) {
					if (i <= lnk.numOfElements) {
						right = lnk.children[i];
						if (right.numOfElements > mid - 1) {
							System.out.println("right num:"
									+ right.numOfElements);
							this.borrowElement(temp, lnk, i, right);
						} else {
							this.merge(lnk, i, temp, right);
						}
					} else { // ��Ԫ��ʧ�ܣ��ϲ��������ڵ�
						this.merge(lnk, i, temp, right);

					}
				}

				deleteUnderRoot(temp, v);
			}

		}
	}

	// ���ֵܽڵ��н�Ԫ�أ��豣֤���ֵܽڵ㲻Ϊ��
	public void borrowElement(BTreeNode self, BTreeNode parent, int pos,
			BTreeNode brother) {
		state = State.borrow;
		self.numOfElements += 1;

		self.value[self.numOfElements] = parent.value[pos]; // parent��posԪ���½�
		parent.value[pos] = brother.value[1]; // ���ֵܽڵ�����СԪ��������pos��
		// ����Ԫ��
		for (int i = 2; i <= brother.numOfElements; i++) {
			brother.value[i - 1] = brother.value[i];
		}
		if (!brother.isLeaf) { // �ƶ�����
			self.children[self.numOfElements] = brother.children[0]; // �ƶ��ֵܽڵ���СԪ�ص�����
			// �ƶ�ʣ�ຢ��
			for (int i = 1; i <= brother.numOfElements; i++) {
				brother.children[i - 1] = brother.children[i];
			}
		}

		brother.numOfElements -= 1;

	}

	// �ϲ�parent��posԪ�أ�left��right��left��ȥ
	public void merge(BTreeNode parent, int pos, BTreeNode left, BTreeNode right) {
		state = State.merge;
		// ��parent���Ƶ�left֮��
		left.value[left.numOfElements + 1] = parent.value[pos];
		// ��right���Ƶ�parent֮��
		for (int i = 1; i <= right.numOfElements; i++) {
			left.value[left.numOfElements + 1 + i] = right.value[i];
		}
		// ���right����Ҷ�ڵ㣬��Ҫ���ƺ��ӹ�ȥ
		if (!right.isLeaf) {
			for (int i = 0; i <= right.numOfElements; i++) {
				left.children[left.numOfElements + 1 + i] = right.children[i];
			}
		}
		// �޸�left��Ԫ����
		left.numOfElements += 1 + right.numOfElements;
		// �޸�parent�ĺ��ӣ���pos֮�����ǰ�ƶ�һλ
		for (int i = pos; i < parent.numOfElements; i++) {
			parent.value[i] = parent.value[i + 1];
			parent.children[i] = parent.children[i + 1];
		}
		// �޸�parent��Ԫ����
		parent.numOfElements -= 1;
	}

	// ��������Ѱ�����Ԫ��,���������ڽڵ㣬�豣֤lnk��=null
	public BTreeNode largestInLeft(BTreeNode lnk) {
		state = State.largest;
		BTreeNode temp = lnk;
		while (temp.children[0] != null) {
			temp = temp.children[temp.numOfElements];
		}
		return temp;
	}

	// ��������Ѱ����СԪ�أ����������ڽڵ㣬�豣֤lnk��=null
	public BTreeNode smallestInRight(BTreeNode lnk) {
		state = State.smallest;
		BTreeNode temp = lnk;
		while (temp.children[0] != null) {
			temp = temp.children[0];
		}
		return temp;
	}

	/****************************** ��ͼ���� **********************/
	public void paint(Graphics g) {
		// ������
		stack.push(root);
		BTreeNode temp;
		while (!stack.empty()) {
			temp = stack.pop();
			if (temp != null) {
				for (int i = 0; i <= temp.numOfElements; i++) {
					stack.push(temp.children[i]);
				}
				for (int i = 1; i <= temp.numOfElements; i++) {
					System.out.print(temp.value[i] + " ");
				}
				System.out.println("total:" + temp.numOfElements);
			}
		}

		super.paint(g);
		// ����ʾ��
		g.setColor(Color.BLUE);
		g.setFont(new Font("����", 0, 20));
		switch (state) {
		case wait:
			g.drawString("�ȴ�����", 10, 50);
			break;
		case insert:
			g.drawString("���ڲ��� �� " + data, 10, 50);
			break;
		case search:
			g.drawString("�������� �� " + data, 10, 50);
			break;
		case delete:
			g.drawString("����ɾ�� �� " + data, 10, 50);
			break;
		case go:
			g.drawString("����ǰ���� " + (node + 1) + " ���ڵ�", 10, 50);
			break;
		case inserted:
			g.drawString("��ɲ��룡", 10, 50);
			break;
		case searched:
			g.drawString("���������", 10, 50);
			break;
		case deleted:
			g.drawString("���ɾ����", 10, 50);
			break;
		case split:
			g.drawString("���ѽڵ�", 10, 50);
			break;
		case merge:
			g.drawString("�ϲ��ڵ�", 10, 50);
			break;
		case borrow:
			g.drawString("�����ֵܽ�һ��Ԫ��", 10, 50);
			break;
		case largest:
			g.drawString("���ڴ���������Ѱ�����ֵ", 10, 50);
		case smallest:
			g.drawString("���ڴ���������Ѱ����Сֵ", 10, 50);
		}

		BTreeNode p;
		stack.push(root);
		while (!stack.empty()) {
			p = stack.pop();
			if (p == null)
				return;

			int x = p.getPoint().getX();
			int y = p.getPoint().getY();

			if (p.numOfElements > 0) {
				// ���ڵ�p
				g.setColor(p.color);
				g.drawRect(x, y, LENGTH * p.numOfElements, HEIGHT);

				// ���ڵ��е�Ԫ��
				int x2 = x + LENGTH / 8; // x2,y2ΪԪ�����ڵ�x���꣬x,y�Ա��ֲ���
				int y2 = y + HEIGHT * 7 / 8;

				for (int i = 1; i <= p.numOfElements; i++) {
					if (i == p.ElementHighlightIdx)
						g.setColor(Color.RED);
					else
						g.setColor(Color.BLACK);
					g.drawString(p.value[i] + "", x2, y2);
					x2 += LENGTH;
				}
				// ���ýڵ����ӽڵ������
				if (!p.isLeaf) {
					// x,y����Ϊֱ��ʼ�����꣬x2,y2Ϊֱ��ĩ������
					y = y + HEIGHT;
					for (int i = 0; i <= p.numOfElements; i++) {
						stack.push(p.children[i]);
						x2 = x + cnt.getEndPos(i);
						y2 = y + 2 * HEIGHT;
						if (i == p.lineHighlightIdx)
							g.setColor(Color.RED);
						else
							g.setColor(Color.BLACK);
						g.drawLine(x + cnt.getStartPos(i), y, x2, y2);

					}
				}

			}
		}

	}

	// ������λ���Զ����¼������еĽڵ������
	public void Recount() {
		if (root.numOfElements == 0) {
			root = null;
			return;
		}
		BTreeNode p = null;
		BTreeNode pp = null;
		stack.push(root);
		while (!stack.empty()) {
			p = stack.pop();
			if (p != null) {

				if (p == root) {
					p.setPoint(rootX, rootY);

				}

				for (int i = 0; i <= p.numOfElements; i++) {
					pp = p.children[i];
					if (pp == null) {
						break;
					}
					int x = cnt.getNextPos(i, pp.numOfElements);
					pp.setPoint(x + p.getPoint().getX(), p.getPoint().getY()
							+ 3 * HEIGHT);
					stack.push(pp);
				}

			}

		}
	}

	/*************************** �ڵ��� **********************/
	class BTreeNode {
		// �洢�ڵ�ı���
		private int[] value; // Ԫ��ֵ
		private BTreeNode[] children; // �ӽڵ�
		private boolean isLeaf; // �Ƿ�ΪҶ�ڵ�
		private int numOfElements; // ��ǰ�ڵ��е�Ԫ����
		// ������ʾ�ı���
		private Color color; // �߿���ɫ
		private int lineHighlightIdx; // ��Ǹ�����ֱ�ߵ������±꣨��0��ʼ�����޸���Ϊ-1
		private int ElementHighlightIdx; // ��Ǹ�����Ԫ�ص��±꣬�޸���Ϊ-1
		// ����
		private int x;
		private int y;

		public BTreeNode(int x, int y) {

			this.x = x;
			this.y = y;

			color = Color.BLACK; // Ĭ�ϱ߿�Ϊ��ɫ
			this.lineHighlightIdx = -1; // Ĭ���޸�������ͬ
			this.ElementHighlightIdx = -1;

			isLeaf = true;
			numOfElements = 0;
			value = new int[m + 1]; // ��1��ʼ����m-1�����ȱ
			children = new BTreeNode[m + 1]; // ��0��ʼ��m-1λ�ã���iλ�ñ�ʾ��i��Ԫ�ش󣬱�i+1��С
			// ��ʼ������
			for (int i = 1; i <= m - 1; i++)
				value[i] = 0;
			for (int i = 0; i <= m - 1; i++)
				children[i] = null;
		}

		// ���Ҷ�ӽڵ��м���Ԫ��
		public void add(int v) {
			int i;
			this.color = Color.RED;
			repaint();
			paintAll(getGraphics());
			for (i = 1; i <= numOfElements; i++) {
				this.ElementHighlightIdx = i;
				repaint();
				paintAll(getGraphics());
				try {
					Thread.sleep(TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (value[i] > v)
					break; // iΪvҪ�����λ��
			}
			this.ElementHighlightIdx = -1;
			color = Color.BLACK;
			repaint();
			paintAll(getGraphics());
			// ����v����iλ
			for (int j = numOfElements; j >= i; j--) {
				// �����ƶ��Ĵ���

				value[j + 1] = value[j];
			}
			value[i] = v;

			// ���Ԫ����+1
			numOfElements += 1;

		}

		// �Ӹ�Ҷ�ӽڵ���ɾ��Ԫ�أ�ʹ��ʱ��ȷ���������Ԫ����ɾ����Ԫ����>=|m/2|
		public void remove(int v) {
			int i;
			for (i = 1; i <= numOfElements; i++) {
				this.ElementHighlightIdx = i;
				repaint();
				paintAll(getGraphics());
				try {
					Thread.sleep(TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (value[i] == v)
					break;
			}
			this.ElementHighlightIdx = -1;
			repaint();
			paintAll(getGraphics());
			if (i > numOfElements)
				return;

			// ����Ԫ�أ�û�к���
			for (int j = i; j < numOfElements; j++) {
				// �����ƶ��Ĵ���

				value[j] = value[j + 1];
			}

			// ���Ԫ����-1
			numOfElements -= 1;
		}

		// ��������ķ���
		public void setPoint(int x, int y) {
			this.x = x;
			this.y = y;
		}

		// ���ؽڵ�����Ͻ�����
		public Point getPoint() {

			return new Point(x, y);
		}

	}
}
