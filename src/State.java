//���ڱ�ʾִ�в�����״̬
public enum State {
	wait, // �ȴ�����
	insert, search, delete, // ����...
	go, // ����ǰ����...���ڵ�
	inserted, searched, deleted, // ���...
	split,	//���ѽڵ�
	merge,	//�ϲ��ڵ�
	borrow;	//�����ֵ�һ����Ԫ��
}
