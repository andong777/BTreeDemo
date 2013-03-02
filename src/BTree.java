import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

//此类用于存储生成的B树数据和绘制B树图像
public class BTree extends JPanel {
	// 存储B树数据的变量
	private BTreeNode root; // 头结点
	private int m; // m叉树中的m
	private int mid; // 中点
	private BTreeNode p;
	// 控制显示的变量
	private final int rootX = 200; // 根节点左上角的x坐标
	private final int rootY = 20; // 根节点左上角的y坐标
	private Graphics2D g2;
	private int x; // 节点图形左上角的x坐标
	private int y; // 节点图形左上角的y坐标
	private final int HEIGHT = 10; // 节点图形的高度
	private final int LENGTH = 15; // 节点图形中容纳每个元素的长度

	public BTree(int m) {
		root = new BTreeNode();
		this.m = m;
		mid = (int) Math.ceil(m / 2);
		g2 = (Graphics2D) getGraphics();
	}

	// 向树中插入元素
	public void insert(int v) {
		// 如果有相同元素，插入失败。
		if (shortSearch(v))
			Message.SameElement();
		else {

			// 如果头结点已满，需要分裂，增加一个新的节点
			if (root.numOfElements == m - 1) {
				BTreeNode newNode = new BTreeNode();
				// newNode.value[1] = root.value[mid];
				split(newNode, 1, root);
				root = newNode; // 让root重新指向头结点
			}
			// 下面对一般情况进行插入
			p = root;

			if (p == null)
				return;

			while (!p.isLeaf) { // 未到达叶节点
				int i;
				for (i = 1; i <= p.numOfElements; i++) {
					if (p.value[i] > v) {
						break;
					}
				}
				BTreeNode temp;
				// p下降至合适的位置
				if (i <= p.numOfElements)
					temp = p.children[i - 1];
				else
					temp = p.children[p.numOfElements]; // 防止越界

				if (temp.numOfElements == m - 1)
					split(p, i, temp);
				p = temp; // 下降一层

			}
			// 到达叶节点，加入这个元素
			p.add(v);

		}

	}

	// 分裂一个节点 parent为分裂出来的元素到达的节点,pos为欲插入的位置，children为待分裂的节点
	public void split(BTreeNode parent, int pos, BTreeNode children) {
		children.numOfElements = mid - 1;
		BTreeNode newNode = new BTreeNode();
		// 转移后半部分元素至新节点
		for (int i = 1; i <= m - mid - 1; i++) {
			newNode.value[i] = children.value[mid + i];
		}
		// 转移后半部分孩子至新节点
		for (int i = 0; i < m - mid - 1; i++) {
			newNode.children[i] = children.children[mid + i];
		}
		// 修改newNode值
		newNode.numOfElements = m - 1 - mid;

		parent.value[pos] = children.value[mid];
		parent.children[pos - 1] = children;
		parent.children[pos] = newNode;
	}

	// 从树中搜索元素,并返回真值
	public boolean search(int v) {
		p = root;

		if (p == null)
			return false;

		while (!p.isLeaf) {
			int i;
			for (i = 1; i <= p.numOfElements; i++) {
				// 找到该元素
				if (p.value[i] == v) {

					Message.EndSearch();
					return true;
				}
				if (p.value[i] > v)
					break;
			}
			// p下降至合适的位置
			if (i <= p.numOfElements)
				p = p.children[i - 1];
			else
				p = p.children[p.numOfElements]; // 防止越界

		}
		for (int i = 1; i <= p.numOfElements; i++) {
			if (p.value[i] == v) {
				// 找到该元素

				Message.EndSearch();
				return true;
			}
		}
		// 仍然未找到，弹出对话框
		Message.NoSuchElement(v);
		return false;
	}

	// 不带动画的搜索
	public boolean shortSearch(int v) {
		p = root;

		if (p == null)
			return false;

		while (!p.isLeaf) {
			int i;
			for (i = 1; i <= p.numOfElements; i++) {
				// 找到该元素
				if (p.value[i] == v) {

					// Message.EndSearch();
					return true;
				}
				if (p.value[i] > v)
					break;
			}
			// p下降至合适的位置
			if (i <= p.numOfElements)
				p = p.children[i - 1];
			else
				p = p.children[p.numOfElements]; // 防止越界

		}
		for (int i = 1; i <= p.numOfElements; i++) {
			if (p.value[i] == v) {
				// 找到该元素

				// Message.EndSearch();
				return true;
			}
		}
		// 仍然未找到，弹出对话框
		// Message.NoSuchElement(v);
		return false;
	}

	// 从树中删除元素
	public void delete(int v) {
		p = root;

		if (p == null)
			return;

		// 未找到此元素
		if (!shortSearch(v)) {
			Message.NoSuchElement(v);
			return;
		} else {
			// 如果只有一层，直接删除
			if (root.isLeaf)
				root.remove(v);
			else if (root.numOfElements == 1) {
				BTreeNode left = root.children[0];
				BTreeNode right = root.children[1];
				// 根节点与它的子节点均为最少元素的情况，此时合并这三个节点
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

	// 辅助删除的函数，从非根节点中删除元素
	public void deleteUnderRoot(BTreeNode lnk, int v) {
		// 当前节点是叶节点，直接删除这个元素
		if (lnk.isLeaf) {
			lnk.remove(v);
		} else {// 在当前的节点上？
			int i;	//找到第一个不小于v的元素的位置
			for (i = 1; i <= lnk.numOfElements; i++) {
				if (lnk.value[i] > v)
					break;
			}
			//这个位置就是v，则从子树中寻找元素代替之，然后在删除那个用来替换的元素
			if (i <= lnk.numOfElements && lnk.value[i] == v) {
				BTreeNode left = lnk.children[i - 1];
				BTreeNode right = lnk.children[i];
				//如果左子树元素数大于最小元素数，从中寻找最大元素
				if (left.numOfElements > mid - 1) {
					BTreeNode temp = largestInLeft(left);
					int largest = temp.value[temp.numOfElements];
					lnk.value[i] = largest;
					temp.remove(largest);
					//如果右子树元素数大于最小元素数，从中寻找最小元素
				} else if (right.numOfElements > mid - 1) {
					BTreeNode temp = smallestInRight(right);
					int smallest = temp.value[1];
					lnk.value[i] = smallest;
					temp.remove(smallest);
				//合并这三个节点
				} else {
					merge(lnk, i, left, right);
					deleteUnderRoot(left, v);

				}
			}
			// 继续向下寻找
			else {
				// children【i-1】为指针应该下降的位置
				BTreeNode right = null;
				BTreeNode temp = lnk.children[i - 1];
				//如果要前往的节点元素数等于最小元素数，尝试从右子树借元素
				if (temp.numOfElements == mid - 1) {
					if (i < lnk.numOfElements) {
						right = lnk.children[i + 1];
						this.borrowElement(temp, lnk, i, right);
					}
					else{	//借元素失败，合并这三个节点
						this.merge(lnk, i, temp, right);
						
					}
				}

				deleteUnderRoot(temp, v);

			}

		}
	}

	// 从兄弟节点中借元素，需保证右兄弟节点不为空
	public void borrowElement(BTreeNode self, BTreeNode parent, int pos,
			BTreeNode brother) {
		self.numOfElements += 1;

		self.value[self.numOfElements] = parent.value[pos]; // parent中pos元素下降
		parent.value[pos] = brother.value[1]; // 右兄弟节点中最小元素上升至pos处
		// 左移元素
		for (int i = 2; i <= brother.numOfElements; i++) {
			brother.value[i - 1] = brother.value[i];
		}
		if (!brother.isLeaf) { // 移动孩子
			self.children[self.numOfElements] = brother.children[0]; // 移动兄弟节点最小元素的左孩子
			// 移动剩余孩子
			for (int i = 1; i <= brother.numOfElements; i++) {
				brother.children[i - 1] = brother.children[i];
			}
		}

		brother.numOfElements -= 1;

	}

	// 合并parent的pos元素，left，right
	public void merge(BTreeNode parent, int pos, BTreeNode left, BTreeNode right) {
		// 把parent复制到left之后
		left.value[left.numOfElements + 1] = parent.value[pos];
		// 把right复制到parent之后
		for (int i = 1; i <= right.numOfElements; i++) {
			left.value[left.numOfElements + 1 + i] = right.value[i];
		}
		// 如果right不是叶节点，还要复制孩子过去
		if (!right.isLeaf) {
			for (int i = 0; i <= right.numOfElements; i++) {
				left.children[left.numOfElements + 1 + i] = right.children[i];
			}
		}

		// 修改parent的孩子，即pos之后的向前移动一位
		for (int i = pos; i <= parent.numOfElements; i++) {
			parent.children[i] = parent.children[i + 1];
		}
		// root-1
		root.numOfElements -= 1;
	}

	// 从左子树寻找最大元素,并返回所在节点，需保证lnk！=null
	public BTreeNode largestInLeft(BTreeNode lnk) {
		BTreeNode temp = lnk;
		while (!temp.isLeaf) {
			temp = temp.children[temp.numOfElements];
		}
		return temp;
	}

	// 从右子树寻找最小元素，并返回所在节点，需保证lnk！=null
	public BTreeNode smallestInRight(BTreeNode lnk) {
		BTreeNode temp = lnk;
		while (!temp.isLeaf) {
			temp = temp.children[0];
		}
		return temp;
	}

	// 下面是内部类B树节点的代码
	class BTreeNode {
		// 存储节点的变量
		private int[] value; // 元素值
		private BTreeNode[] children; // 子节点
		private boolean isLeaf; // 是否为叶节点
		private int numOfElements; // 当前节点中的元素数

		public BTreeNode() {
			isLeaf = true;
			numOfElements = 0;
			value = new int[m]; // 从1开始，零空缺
			children = new BTreeNode[m]; // 第i位置表示比i处元素大，比i+1处小
			// 初始化数组
			for (int i = 0; i < m - 1; i++)
				value[i] = 0;
			for (int i = 0; i < m; i++)
				children[i] = null;
		}

		// 向该叶子节点中加入元素
		public void add(int v) {
			int i;
			for (i = 1; i <= numOfElements; i++) {
				if (value[i] > v)
					break; // i为v要加入的位置
			}
			// 插入v到第i位
			for (int j = numOfElements; j >= i; j--) {
				value[j + 1] = value[j];
			}
			value[i] = v;

		}

		// 从该叶子节点中删除元素，这个方法不检查是否存在这个元素
		// 使用时须确保存在这个元素，须确保删除后元素数>=|m/2|
		public void remove(int v) {
			int i;
			for (i = 1; i <= numOfElements; i++) {
				if (value[i] == v)
					break;
			}
			// 左移元素，没有孩子
			for (int j = i; j <= numOfElements; j++)
				value[j] = value[j + 1];

			// 最后将元素数-1
			numOfElements -= 1;
		}

	}
}
