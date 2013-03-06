//用于记录插入的过程
public class Record {
	
	private State op;	//操作
	private int data;	//操作数
	
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
