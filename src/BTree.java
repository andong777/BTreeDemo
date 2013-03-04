import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	private final int rootX = 200; // ���ڵ����Ͻǵ�x����
	private final int rootY = 20; // ���ڵ����Ͻǵ�y����
	private final int HEIGHT = 10; // �ڵ�ͼ�εĸ߶�
	private final int LENGTH = 15; // �ڵ�ͼ��������ÿ��Ԫ�صĳ���

	// private List<BTreeNode> toPaint; // �����ƵĽڵ�

	public BTree(int m) {
		// toPaint = new ArrayList<BTreeNode>(20);
		data = 0; // �����壬��ͬ
		node = 0;
		state = State.wait;
		repaint();
		root = null;
		this.m = m;
		mid = (int) Math.ceil(m / 2);
	}

	/****************************** ����Ϊ������̵Ĵ��� ******************/
	// �����в���Ԫ��
	public void insert(int v) {
		// ������ʾ��
		state = State.insert;
		data = v;
		repaint();
		// �������ͬԪ�أ�����ʧ�ܡ�
		if (shortSearch(v))
			Message.SameElement();
		else {

			if (root == null) {

				root = new BTreeNode(rootX, rootY);
				// root.setPoint(rootX, rootY);
				root.add(v);
				// toPaint.add(root);
				repaint();

			} else {

				// ���ͷ�����������Ҫ���ѣ�����һ���µĽڵ�
				if (root.numOfElements == m - 1) {
					BTreeNode newNode = new BTreeNode(rootX, rootY); // ��������ȡ��ԭ���ڵ�
					// newNode.value[1] = root.value[mid];
					split(newNode, 1, root);
					int x = cnt.getNextPos(0, mid - 1);
					root.setPoint(x, rootY + HEIGHT * 2); // �޸�ԭ���ڵ��λ��
					root = newNode; // ��root����ָ��ͷ���
					root.isLeaf = false;
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
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					p.color = Color.BLACK;
					repaint();

					int i;
					for (i = 1; i <= p.numOfElements; i++) {
						p.ElementHighlightIdx = i;
						repaint();
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (p.value[i] > v) {
							break;
						}
					}
					p.ElementHighlightIdx = -1;
					repaint();

					BTreeNode temp;
					// p�½������ʵ�λ��
					if (i <= p.numOfElements) {
						temp = p.children[i - 1];
						p.lineHighlightIdx = i;
						repaint();
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						temp = p.children[p.numOfElements]; // ��ֹԽ��
						p.lineHighlightIdx = p.numOfElements;
						repaint();
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					p.lineHighlightIdx = -1;
					repaint();

					if (temp.numOfElements == m - 1)
						split(p, i, temp);
					p = temp; // �½�һ��

				}
				// ����Ҷ�ڵ㣬�������Ԫ��
				p.add(v);
				state = State.inserted;
				repaint();
			}

		}

	}

	// ����һ���ڵ� parentΪ���ѳ�����Ԫ�ص���Ľڵ�,posΪ�������λ�ã�childrenΪ�����ѵĽڵ�
	public void split(BTreeNode parent, int pos, BTreeNode children) {

		// �����Ԫ�ز��뵽parent��
		for (int i = pos; i <= parent.numOfElements; i++) {
			parent.value[i + 1] = parent.value[i];
		}
		parent.value[pos] = children.value[mid];
		parent.numOfElements += 1;

		int x = cnt.getNextPos(pos, parent.numOfElements);
		BTreeNode newNode = new BTreeNode(x + parent.getPoint().getX(), parent
				.getPoint().getY() + HEIGHT * 2);

		parent.children[pos - 1] = children;
		parent.children[pos] = newNode;

		// �������Ԫ�ش�children��ɾ��
		children.numOfElements = mid - 1;

		// ת�ƺ�벿��Ԫ�����½ڵ�
		for (int i = 1; i <= m - mid - 1; i++) {
			newNode.value[i] = children.value[mid + i];
		}
		// ת�ƺ�벿�ֺ������½ڵ�
		for (int i = 0; i < m - mid - 1; i++) {
			newNode.children[i] = children.children[mid + i];
		}
		// �޸�newNodeֵ
		newNode.numOfElements = m - 1 - mid;

	}

	// ����������Ԫ��,��������ֵ
	public void search(int v) {
		// ������ʾ��
		state = State.search;
		data = v;

		p = root;

		if (p == null)
			return;

		while (p != null) {
			int i;
			for (i = 1; i <= p.numOfElements; i++) {
				p.ElementHighlightIdx = i;
				repaint();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// �ҵ���Ԫ��
				if (p.value[i] == v) {
					p.ElementHighlightIdx = -1;
					repaint();
					Message.EndSearch();
					return;
				}
				if (p.value[i] > v)
					break;
			}
			// p�½������ʵ�λ��
			if (i <= p.numOfElements) {
				p.lineHighlightIdx = i;
				repaint();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				p = p.children[i - 1];
			} else {
				p.lineHighlightIdx = p.numOfElements;
				repaint();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				p = p.children[p.numOfElements]; // ��ֹԽ��
			}
			p.lineHighlightIdx = -1;
			state = State.searched;
			repaint();
		}

		// ��Ȼδ�ҵ��������Ի���
		Message.NoSuchElement(v);
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
		state = State.delete;
		data = v;

		p = root;

		if (p == null)
			return;

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
				}

				deleteUnderRoot(root, v);

			} else {

				deleteUnderRoot(root, v);

			}
			state = State.deleted;
			repaint();
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
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (lnk.value[i] > v)
					break;
			}
			lnk.ElementHighlightIdx = -1;
			repaint();
			// ���λ�þ���v�����������Ѱ��Ԫ�ش���֮��Ȼ����ɾ���Ǹ������滻��Ԫ��
			if (i <= lnk.numOfElements && lnk.value[i] == v) {
				BTreeNode left = lnk.children[i - 1];
				BTreeNode right = lnk.children[i];
				// ���������Ԫ����������СԪ����������Ѱ�����Ԫ��
				if (left.numOfElements > mid - 1) {
					BTreeNode temp = largestInLeft(left);
					int largest = temp.value[temp.numOfElements];
					lnk.value[i] = largest;
					temp.remove(largest);
					// ���������Ԫ����������СԪ����������Ѱ����СԪ��
				} else if (right.numOfElements > mid - 1) {
					BTreeNode temp = smallestInRight(right);
					int smallest = temp.value[1];
					lnk.value[i] = smallest;
					temp.remove(smallest);
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
					if (i < lnk.numOfElements) {
						right = lnk.children[i + 1];
						this.borrowElement(temp, lnk, i, right);
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

	// �ϲ�parent��posԪ�أ�left��right
	public void merge(BTreeNode parent, int pos, BTreeNode left, BTreeNode right) {
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

		// �޸�parent�ĺ��ӣ���pos֮�����ǰ�ƶ�һλ
		for (int i = pos; i <= parent.numOfElements; i++) {
			parent.children[i] = parent.children[i + 1];
		}
		// root-1
		root.numOfElements -= 1;
	}

	// ��������Ѱ�����Ԫ��,���������ڽڵ㣬�豣֤lnk��=null
	public BTreeNode largestInLeft(BTreeNode lnk) {
		BTreeNode temp = lnk;
		while (!temp.isLeaf) {
			temp = temp.children[temp.numOfElements];
		}
		return temp;
	}

	// ��������Ѱ����СԪ�أ����������ڽڵ㣬�豣֤lnk��=null
	public BTreeNode smallestInRight(BTreeNode lnk) {
		BTreeNode temp = lnk;
		while (!temp.isLeaf) {
			temp = temp.children[0];
		}
		return temp;
	}

	/****************************** ��ͼ���� **********************/
	public void paint(Graphics g) {

//		super.paint(g);
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
			g.drawString("����ǰ���� " + node + " ���ڵ�", 10, 50);
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
		}

		BTreeNode p = root;
		while (p != null) {
			// ���ڵ�p
			g.setColor(p.color);
			int x = p.getPoint().getX();
			int y = p.getPoint().getY();
			g.drawRect(p.getPoint().getX(), p.getPoint().getY(), LENGTH
					* p.numOfElements, HEIGHT);
			// StringBuilder builder = new StringBuilder();
			// int x = p.getPoint().getX()+LENGTH/2;

			// ���ڵ��е�Ԫ��
			int x2 = x + LENGTH / 2; // x2,y2ΪԪ�����ڵ�x���꣬x,y�Ա��ֲ���
			int y2 = y + HEIGHT / 2;

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
					x = cnt.getStartPos(i);
					x2 = cnt.getEndPos(i);
					y2 = y + HEIGHT;
					if (i == p.lineHighlightIdx)
						g.setColor(Color.RED);
					else
						g.setColor(Color.BLACK);
					g.drawLine(x, y, x2, y2);

				}
			}

		}

		// Iterator toPaintIterator = toPaint.iterator();
		// if (toPaintIterator.hasNext()) {
		//
		// BTreeNode cur = (BTreeNode) toPaintIterator.next(); // ָ��ǰҪ���ƵĽڵ�
		// BTreeNode last = cur; // ָ����һ���ڵ�
		// int x = cur.getPoint().getX();
		// int y = cur.getPoint().getY();
		// // ���ƽڵ�
		// g.setColor(cur.color);
		// g.drawRect(x, y, LENGTH, HEIGHT);
		//
		// while (toPaintIterator.hasNext()) {
		//
		// cur = (BTreeNode) toPaintIterator.next();
		//
		// // ���ƽڵ�
		// x = cur.getPoint().getX();
		// y = cur.getPoint().getY();
		// g.setColor(cur.color);
		// g.drawRect(x, y, LENGTH, HEIGHT);
		//
		// //���ƽڵ��е�Ԫ��
		// StringBuilder builder = new StringBuilder();
		// for(int i=1;i<=cur.numOfElements;i++){
		// builder.append(" "+cur.value[i]);
		// }
		// String s = builder.toString();
		// g.drawString(s, x, y+HEIGHT/2);
		//
		// // ���Ƶ�ǰ�ڵ����һ���ڵ�������
		// x = cur.midpoint().getX();
		// y = cur.midpoint().getY();
		// int x2 = last.midpoint().getX();
		// int y2 = last.midpoint().getY()+HEIGHT;
		// g.setColor(Color.BLACK);
		// g.drawLine(x, y, x2, y2);
		//
		// last = cur; // ����lastֵ
		// }
		// }

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

			// lineColor = new Color[m];
			// for (int i = 0; i < m; i++) {
			// lineColor[i] = Color.BLACK;
			// }
			color = Color.BLACK; // Ĭ�ϱ߿�Ϊ��ɫ
			this.lineHighlightIdx = -1; // Ĭ���޸�������ͬ
			this.ElementHighlightIdx = -1;

			isLeaf = true;
			numOfElements = 0;
			value = new int[m]; // ��1��ʼ����m-1�����ȱ
			children = new BTreeNode[m]; // ��0��ʼ��m-1λ�ã���iλ�ñ�ʾ��i��Ԫ�ش󣬱�i+1��С
			// ��ʼ������
			for (int i = 1; i <= m - 1; i++)
				value[i] = 0;
			for (int i = 0; i <= m - 1; i++)
				children[i] = null;
		}

		// ���Ҷ�ӽڵ��м���Ԫ��
		public void add(int v) {
			int i;
			for (i = 1; i <= numOfElements; i++) {
				this.ElementHighlightIdx = i;
				repaint();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (value[i] > v)
					break; // iΪvҪ�����λ��
			}
			this.ElementHighlightIdx = -1;
			repaint();
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
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (value[i] == v)
					break;
			}
			this.ElementHighlightIdx = -1;
			repaint();
			if (i > numOfElements)
				return;

			// ����Ԫ�أ�û�к���
			for (int j = i; j <= numOfElements; j++) {
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
