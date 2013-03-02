import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

//�������ڴ洢���ɵ�B�����ݺͻ���B��ͼ��
public class BTree extends JPanel {
	// �洢B�����ݵı���
	private BTreeNode root; // ͷ���
	private int m; // m�����е�m
	private int mid; // �е�
	private BTreeNode p;
	// ������ʾ�ı���
	private final int rootX = 200; // ���ڵ����Ͻǵ�x����
	private final int rootY = 20; // ���ڵ����Ͻǵ�y����
	private Graphics2D g2;
	private int x; // �ڵ�ͼ�����Ͻǵ�x����
	private int y; // �ڵ�ͼ�����Ͻǵ�y����
	private final int HEIGHT = 10; // �ڵ�ͼ�εĸ߶�
	private final int LENGTH = 15; // �ڵ�ͼ��������ÿ��Ԫ�صĳ���

	public BTree(int m) {
		root = new BTreeNode();
		this.m = m;
		mid = (int) Math.ceil(m / 2);
		g2 = (Graphics2D) getGraphics();
	}

	// �����в���Ԫ��
	public void insert(int v) {
		// �������ͬԪ�أ�����ʧ�ܡ�
		if (shortSearch(v))
			Message.SameElement();
		else {

			// ���ͷ�����������Ҫ���ѣ�����һ���µĽڵ�
			if (root.numOfElements == m - 1) {
				BTreeNode newNode = new BTreeNode();
				// newNode.value[1] = root.value[mid];
				split(newNode, 1, root);
				root = newNode; // ��root����ָ��ͷ���
			}
			// �����һ��������в���
			p = root;

			if (p == null)
				return;

			while (!p.isLeaf) { // δ����Ҷ�ڵ�
				int i;
				for (i = 1; i <= p.numOfElements; i++) {
					if (p.value[i] > v) {
						break;
					}
				}
				BTreeNode temp;
				// p�½������ʵ�λ��
				if (i <= p.numOfElements)
					temp = p.children[i - 1];
				else
					temp = p.children[p.numOfElements]; // ��ֹԽ��

				if (temp.numOfElements == m - 1)
					split(p, i, temp);
				p = temp; // �½�һ��

			}
			// ����Ҷ�ڵ㣬�������Ԫ��
			p.add(v);

		}

	}

	// ����һ���ڵ� parentΪ���ѳ�����Ԫ�ص���Ľڵ�,posΪ�������λ�ã�childrenΪ�����ѵĽڵ�
	public void split(BTreeNode parent, int pos, BTreeNode children) {
		children.numOfElements = mid - 1;
		BTreeNode newNode = new BTreeNode();
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

		parent.value[pos] = children.value[mid];
		parent.children[pos - 1] = children;
		parent.children[pos] = newNode;
	}

	// ����������Ԫ��,��������ֵ
	public boolean search(int v) {
		p = root;

		if (p == null)
			return false;

		while (!p.isLeaf) {
			int i;
			for (i = 1; i <= p.numOfElements; i++) {
				// �ҵ���Ԫ��
				if (p.value[i] == v) {

					Message.EndSearch();
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
		for (int i = 1; i <= p.numOfElements; i++) {
			if (p.value[i] == v) {
				// �ҵ���Ԫ��

				Message.EndSearch();
				return true;
			}
		}
		// ��Ȼδ�ҵ��������Ի���
		Message.NoSuchElement(v);
		return false;
	}

	// ��������������
	public boolean shortSearch(int v) {
		p = root;

		if (p == null)
			return false;

		while (!p.isLeaf) {
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
		for (int i = 1; i <= p.numOfElements; i++) {
			if (p.value[i] == v) {
				// �ҵ���Ԫ��

				// Message.EndSearch();
				return true;
			}
		}
		// ��Ȼδ�ҵ��������Ի���
		// Message.NoSuchElement(v);
		return false;
	}

	// ������ɾ��Ԫ��
	public void delete(int v) {
		p = root;

		if (p == null)
			return;

		// δ�ҵ���Ԫ��
		if (!shortSearch(v)) {
			Message.NoSuchElement(v);
			return;
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
		}
	}

	// ����ɾ���ĺ������ӷǸ��ڵ���ɾ��Ԫ��
	public void deleteUnderRoot(BTreeNode lnk, int v) {
		// ��ǰ�ڵ���Ҷ�ڵ㣬ֱ��ɾ�����Ԫ��
		if (lnk.isLeaf) {
			lnk.remove(v);
		} else {// �ڵ�ǰ�Ľڵ��ϣ�
			int i;	//�ҵ���һ����С��v��Ԫ�ص�λ��
			for (i = 1; i <= lnk.numOfElements; i++) {
				if (lnk.value[i] > v)
					break;
			}
			//���λ�þ���v�����������Ѱ��Ԫ�ش���֮��Ȼ����ɾ���Ǹ������滻��Ԫ��
			if (i <= lnk.numOfElements && lnk.value[i] == v) {
				BTreeNode left = lnk.children[i - 1];
				BTreeNode right = lnk.children[i];
				//���������Ԫ����������СԪ����������Ѱ�����Ԫ��
				if (left.numOfElements > mid - 1) {
					BTreeNode temp = largestInLeft(left);
					int largest = temp.value[temp.numOfElements];
					lnk.value[i] = largest;
					temp.remove(largest);
					//���������Ԫ����������СԪ����������Ѱ����СԪ��
				} else if (right.numOfElements > mid - 1) {
					BTreeNode temp = smallestInRight(right);
					int smallest = temp.value[1];
					lnk.value[i] = smallest;
					temp.remove(smallest);
				//�ϲ��������ڵ�
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
				//���Ҫǰ���Ľڵ�Ԫ����������СԪ���������Դ���������Ԫ��
				if (temp.numOfElements == mid - 1) {
					if (i < lnk.numOfElements) {
						right = lnk.children[i + 1];
						this.borrowElement(temp, lnk, i, right);
					}
					else{	//��Ԫ��ʧ�ܣ��ϲ��������ڵ�
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

	// �������ڲ���B���ڵ�Ĵ���
	class BTreeNode {
		// �洢�ڵ�ı���
		private int[] value; // Ԫ��ֵ
		private BTreeNode[] children; // �ӽڵ�
		private boolean isLeaf; // �Ƿ�ΪҶ�ڵ�
		private int numOfElements; // ��ǰ�ڵ��е�Ԫ����

		public BTreeNode() {
			isLeaf = true;
			numOfElements = 0;
			value = new int[m]; // ��1��ʼ�����ȱ
			children = new BTreeNode[m]; // ��iλ�ñ�ʾ��i��Ԫ�ش󣬱�i+1��С
			// ��ʼ������
			for (int i = 0; i < m - 1; i++)
				value[i] = 0;
			for (int i = 0; i < m; i++)
				children[i] = null;
		}

		// ���Ҷ�ӽڵ��м���Ԫ��
		public void add(int v) {
			int i;
			for (i = 1; i <= numOfElements; i++) {
				if (value[i] > v)
					break; // iΪvҪ�����λ��
			}
			// ����v����iλ
			for (int j = numOfElements; j >= i; j--) {
				value[j + 1] = value[j];
			}
			value[i] = v;

		}

		// �Ӹ�Ҷ�ӽڵ���ɾ��Ԫ�أ��������������Ƿ�������Ԫ��
		// ʹ��ʱ��ȷ���������Ԫ�أ���ȷ��ɾ����Ԫ����>=|m/2|
		public void remove(int v) {
			int i;
			for (i = 1; i <= numOfElements; i++) {
				if (value[i] == v)
					break;
			}
			// ����Ԫ�أ�û�к���
			for (int j = i; j <= numOfElements; j++)
				value[j] = value[j + 1];

			// ���Ԫ����-1
			numOfElements -= 1;
		}

	}
}
