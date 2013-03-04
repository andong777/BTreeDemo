import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

//此类用于存储生成的B树数据和绘制B树图像
public class BTree extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5189121418491891693L;
	// 存储B树数据的变量
	private BTreeNode root; // 头结点
	private int m; // m叉树中的m
	private int mid; // 中点
	private BTreeNode p;
	private Counter cnt;
	private int data; // 记录当前的操作数
	private int node; // 记录要前往的节点标号
	// 控制显示的变量
	private State state; // 表示当前执行操作的状态，用于显示提示语
	private final int rootX = 200; // 根节点左上角的x坐标
	private final int rootY = 20; // 根节点左上角的y坐标
	private final int HEIGHT = 10; // 节点图形的高度
	private final int LENGTH = 15; // 节点图形中容纳每个元素的长度

	// private List<BTreeNode> toPaint; // 待绘制的节点

	public BTree(int m) {
		// toPaint = new ArrayList<BTreeNode>(20);
		data = 0; // 无意义，下同
		node = 0;
		state = State.wait;
		repaint();
		root = null;
		this.m = m;
		mid = (int) Math.ceil(m / 2);
	}

	/****************************** 以下为插入过程的代码 ******************/
	// 向树中插入元素
	public void insert(int v) {
		// 更新提示语
		state = State.insert;
		data = v;
		repaint();
		// 如果有相同元素，插入失败。
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

				// 如果头结点已满，需要分裂，增加一个新的节点
				if (root.numOfElements == m - 1) {
					BTreeNode newNode = new BTreeNode(rootX, rootY); // 这个点最后将取代原根节点
					// newNode.value[1] = root.value[mid];
					split(newNode, 1, root);
					int x = cnt.getNextPos(0, mid - 1);
					root.setPoint(x, rootY + HEIGHT * 2); // 修改原根节点的位置
					root = newNode; // 让root重新指向头结点
					root.isLeaf = false;
					repaint();
				}
				// 下面对一般情况进行插入
				p = root;

				if (p == null)
					return;

				while (!p.isLeaf) { // 未到达叶节点

					// 高亮效果
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
					// p下降至合适的位置
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
						temp = p.children[p.numOfElements]; // 防止越界
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
					p = temp; // 下降一层

				}
				// 到达叶节点，加入这个元素
				p.add(v);
				state = State.inserted;
				repaint();
			}

		}

	}

	// 分裂一个节点 parent为分裂出来的元素到达的节点,pos为欲插入的位置，children为待分裂的节点
	public void split(BTreeNode parent, int pos, BTreeNode children) {

		// 将这个元素插入到parent中
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

		// 将多余的元素从children中删除
		children.numOfElements = mid - 1;

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

	}

	// 从树中搜索元素,并返回真值
	public void search(int v) {
		// 更新提示语
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
				// 找到该元素
				if (p.value[i] == v) {
					p.ElementHighlightIdx = -1;
					repaint();
					Message.EndSearch();
					return;
				}
				if (p.value[i] > v)
					break;
			}
			// p下降至合适的位置
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
				p = p.children[p.numOfElements]; // 防止越界
			}
			p.lineHighlightIdx = -1;
			state = State.searched;
			repaint();
		}

		// 仍然未找到，弹出对话框
		Message.NoSuchElement(v);
	}

	// 不带动画的搜索
	public boolean shortSearch(int v) {
		p = root;

		if (p == null)
			return false;

		while (p != null) {
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
		return false;
	}

	/****************************** 以下为删除过程的代码 **************************/
	// 从树中删除元素
	public void delete(int v) {
		// 更新提示语
		state = State.delete;
		data = v;

		p = root;

		if (p == null)
			return;

		// 未找到此元素
		if (!shortSearch(v)) {
			Message.NoSuchElement(v);
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
			state = State.deleted;
			repaint();
		}
	}

	// 辅助删除的函数，从非根节点中删除元素
	public void deleteUnderRoot(BTreeNode lnk, int v) {
		// 当前节点是叶节点，直接删除这个元素
		if (lnk.isLeaf) {
			lnk.remove(v);
		} else {// 在当前的节点上？
			int i; // 找到第一个不小于v的元素的位置
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
			// 这个位置就是v，则从子树中寻找元素代替之，然后在删除那个用来替换的元素
			if (i <= lnk.numOfElements && lnk.value[i] == v) {
				BTreeNode left = lnk.children[i - 1];
				BTreeNode right = lnk.children[i];
				// 如果左子树元素数大于最小元素数，从中寻找最大元素
				if (left.numOfElements > mid - 1) {
					BTreeNode temp = largestInLeft(left);
					int largest = temp.value[temp.numOfElements];
					lnk.value[i] = largest;
					temp.remove(largest);
					// 如果右子树元素数大于最小元素数，从中寻找最小元素
				} else if (right.numOfElements > mid - 1) {
					BTreeNode temp = smallestInRight(right);
					int smallest = temp.value[1];
					lnk.value[i] = smallest;
					temp.remove(smallest);
					// 合并这三个节点
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
				// 如果要前往的节点元素数等于最小元素数，尝试从右子树借元素
				if (temp.numOfElements == mid - 1) {
					if (i < lnk.numOfElements) {
						right = lnk.children[i + 1];
						this.borrowElement(temp, lnk, i, right);
					} else { // 借元素失败，合并这三个节点
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

	/****************************** 绘图方法 **********************/
	public void paint(Graphics g) {

//		super.paint(g);
		// 画提示语
		g.setColor(Color.BLUE);
		g.setFont(new Font("宋体", 0, 20));
		switch (state) {
		case wait:
			g.drawString("等待操作", 10, 50);
			break;
		case insert:
			g.drawString("正在插入 ： " + data, 10, 50);
			break;
		case search:
			g.drawString("正在搜索 ： " + data, 10, 50);
			break;
		case delete:
			g.drawString("正在删除 ： " + data, 10, 50);
			break;
		case go:
			g.drawString("正在前往第 " + node + " 个节点", 10, 50);
			break;
		case inserted:
			g.drawString("完成插入！", 10, 50);
			break;
		case searched:
			g.drawString("完成搜索！", 10, 50);
			break;
		case deleted:
			g.drawString("完成删除！", 10, 50);
			break;
		case split:
			g.drawString("分裂节点", 10, 50);
			break;
		case merge:
			g.drawString("合并节点", 10, 50);
			break;
		case borrow:
			g.drawString("从右兄弟借一个元素", 10, 50);
			break;
		}

		BTreeNode p = root;
		while (p != null) {
			// 画节点p
			g.setColor(p.color);
			int x = p.getPoint().getX();
			int y = p.getPoint().getY();
			g.drawRect(p.getPoint().getX(), p.getPoint().getY(), LENGTH
					* p.numOfElements, HEIGHT);
			// StringBuilder builder = new StringBuilder();
			// int x = p.getPoint().getX()+LENGTH/2;

			// 画节点中的元素
			int x2 = x + LENGTH / 2; // x2,y2为元素所在的x坐标，x,y仍保持不变
			int y2 = y + HEIGHT / 2;

			for (int i = 1; i <= p.numOfElements; i++) {
				if (i == p.ElementHighlightIdx)
					g.setColor(Color.RED);
				else
					g.setColor(Color.BLACK);
				g.drawString(p.value[i] + "", x2, y2);
				x2 += LENGTH;
			}
			// 画该节点与子节点的连线
			if (!p.isLeaf) {
				// x,y现在为直线始端坐标，x2,y2为直线末端坐标
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
		// BTreeNode cur = (BTreeNode) toPaintIterator.next(); // 指向当前要绘制的节点
		// BTreeNode last = cur; // 指向上一个节点
		// int x = cur.getPoint().getX();
		// int y = cur.getPoint().getY();
		// // 绘制节点
		// g.setColor(cur.color);
		// g.drawRect(x, y, LENGTH, HEIGHT);
		//
		// while (toPaintIterator.hasNext()) {
		//
		// cur = (BTreeNode) toPaintIterator.next();
		//
		// // 绘制节点
		// x = cur.getPoint().getX();
		// y = cur.getPoint().getY();
		// g.setColor(cur.color);
		// g.drawRect(x, y, LENGTH, HEIGHT);
		//
		// //绘制节点中的元素
		// StringBuilder builder = new StringBuilder();
		// for(int i=1;i<=cur.numOfElements;i++){
		// builder.append(" "+cur.value[i]);
		// }
		// String s = builder.toString();
		// g.drawString(s, x, y+HEIGHT/2);
		//
		// // 绘制当前节点和上一个节点间的连线
		// x = cur.midpoint().getX();
		// y = cur.midpoint().getY();
		// int x2 = last.midpoint().getX();
		// int y2 = last.midpoint().getY()+HEIGHT;
		// g.setColor(Color.BLACK);
		// g.drawLine(x, y, x2, y2);
		//
		// last = cur; // 更新last值
		// }
		// }

	}

	/*************************** 节点类 **********************/
	class BTreeNode {
		// 存储节点的变量
		private int[] value; // 元素值
		private BTreeNode[] children; // 子节点
		private boolean isLeaf; // 是否为叶节点
		private int numOfElements; // 当前节点中的元素数
		// 控制显示的变量
		private Color color; // 边框颜色
		private int lineHighlightIdx; // 标记高亮的直线的数组下标（从0开始），无高亮为-1
		private int ElementHighlightIdx; // 标记高亮的元素的下标，无高亮为-1
		// 坐标
		private int x;
		private int y;

		public BTreeNode(int x, int y) {

			this.x = x;
			this.y = y;

			// lineColor = new Color[m];
			// for (int i = 0; i < m; i++) {
			// lineColor[i] = Color.BLACK;
			// }
			color = Color.BLACK; // 默认边框为黑色
			this.lineHighlightIdx = -1; // 默认无高亮，下同
			this.ElementHighlightIdx = -1;

			isLeaf = true;
			numOfElements = 0;
			value = new int[m]; // 从1开始，到m-1，零空缺
			children = new BTreeNode[m]; // 从0开始到m-1位置，第i位置表示比i处元素大，比i+1处小
			// 初始化数组
			for (int i = 1; i <= m - 1; i++)
				value[i] = 0;
			for (int i = 0; i <= m - 1; i++)
				children[i] = null;
		}

		// 向该叶子节点中加入元素
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
					break; // i为v要加入的位置
			}
			this.ElementHighlightIdx = -1;
			repaint();
			// 插入v到第i位
			for (int j = numOfElements; j >= i; j--) {
				// 加入移动的代码

				value[j + 1] = value[j];
			}
			value[i] = v;

			// 最后将元素数+1
			numOfElements += 1;

		}

		// 从该叶子节点中删除元素，使用时须确保存在这个元素且删除后元素数>=|m/2|
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

			// 左移元素，没有孩子
			for (int j = i; j <= numOfElements; j++) {
				// 加入移动的代码

				value[j] = value[j + 1];
			}

			// 最后将元素数-1
			numOfElements -= 1;
		}

		// 设置坐标的方法
		public void setPoint(int x, int y) {
			this.x = x;
			this.y = y;
		}

		// 返回节点的左上角坐标
		public Point getPoint() {

			return new Point(x, y);
		}

	}
}
