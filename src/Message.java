import javax.swing.JOptionPane;

//�����Ի�����
public class Message {
	//�Ƿ�����
	public static void IllegalInput(String s){
		JOptionPane.showMessageDialog(null,
				"����������ݣ� " + s + " ������Ҫ��\n���������롣");
	}
	//��Ҫ������һ��Ԫ��
	public static void AtLeastOneElement(){
		JOptionPane
		.showMessageDialog(null, "���Ȳ�������һ��Ԫ����ִ����������");
	}
	//������ͬԪ��
	public static void SameElement(){
		JOptionPane.showMessageDialog(null, "������ͬԪ�أ�����ʧ�ܣ�");
	}
	//δ�ҵ���Ԫ��
	public static void NoSuchElement(int v){
		JOptionPane.showMessageDialog(null, "δ�ҵ���СΪ"+v+"��Ԫ�أ���������");
	}
	//��������
	public static void EndSearch(){
		JOptionPane.showMessageDialog(null, "�ҵ���Ԫ�أ�����������");
	}
}
