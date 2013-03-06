
//����B����mֵ��ֱ�����ڽڵ��λ�ã������Ӧ����
public class Counter {
	/**
	 * 
	 */

	// private int HEIGHT; //����ڵ��ľ���
	private int m; // B���Ĳ���
	private int pos; // Ҫ���ɵ�����ʼ�˵�λ�ã���1��ʼ
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
		// mΪ����
		if (m % 2 == 1) {

			if (pos <= (m + 1) / 2) {
				dis = (int) (6 * pos / (m + 1.0) - 3);
			} else {
				dis = (int) ((6 * pos - 6) / (m - 1.0) - 3);
			}

		}
		// mΪż��
		else {

			if (pos <= m / 2) {
				dis = (int) ((4 * pos - 4) / (m - 2.0) - 3);
			} else {
				dis = (int) ((4 * pos - 8) / (m - 2.0) - 1);
			}
		}
		return dis * HEIGHT*2;
	}

	// ����ֱ�ߵ�ʼ��
	public int getStartPos(int posInArray) {
		return LENGTH * posInArray;
	}

	// ����ֱ�ߵ�ĩ��
	public int getEndPos(int posInArray) {
		// ��������posInArrayΪ�������е�λ�ã���0��ʼ������Ҫת��һ��
		return getPos(posInArray) + getStartPos(posInArray);

	}

	// ������һ���ڵ�����һ���ڵ�����Ծ���
	public int getNextPos(int posInArray, int num) {

		return (int) (getEndPos(posInArray) - num * LENGTH / 2.0);
	}
}
