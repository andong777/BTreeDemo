
//给出B树的m值和直线所在节点的位置，求出对应坐标
public class Counter {
	/**
	 * 
	 */

	// private int HEIGHT; //两层节点间的距离
	private int m; // B树的叉数
	private int pos; // 要生成的坐标始端的位置，从1开始
	private final int LENGTH;
	private final int HEIGHT;

	public Counter(int m, int l, int h) {
		this.m = m;
		LENGTH = l;
		HEIGHT = h;
	}

	private int getPos(int posInArray) {

		pos = posInArray + 1;
		int dis;
		// m为奇数
		if (m % 2 == 1) {

			if (pos <= (m + 1) / 2) {
				dis = (int) (6 * pos / (m + 1.0) - 3);
			} else {
				dis = (int) ((6 * pos - 6) / (m - 1.0) - 3);
			}

		}
		// m为偶数
		else {

			if (pos <= m / 2) {
				dis = (int) ((4 * pos - 4) / (m - 2.0) - 3);
			} else {
				dis = (int) ((4 * pos - 8) / (m - 2.0) - 1);
			}
		}
		return dis * HEIGHT*2;
	}

	// 返回直线的始端
	public int getStartPos(int posInArray) {
		return LENGTH * posInArray;
	}

	// 返回直线的末端
	public int getEndPos(int posInArray) {
		// 传进来的posInArray为在数组中的位置，从0开始，所以要转换一下
		return getPos(posInArray) + getStartPos(posInArray);

	}

	// 返回下一个节点与上一个节点间的相对距离
	public int getNextPos(int posInArray, int num) {

		return (int) (getEndPos(posInArray) - num * LENGTH / 2.0);
	}
}
