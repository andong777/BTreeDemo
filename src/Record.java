//���ڼ�¼����Ĺ���
public class Record {
	
	private State op;	//����
	private int data;	//������
	
	public Record(State op,int data){
		this.op = op;
		this.data = data;
	}
	
	public State getOp() {
		return op;
	}
	public void setOp(State op) {
		this.op = op;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
}
