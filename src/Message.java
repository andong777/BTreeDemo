import javax.swing.JOptionPane;

//�����Ի�����
public class Message {
	//�Ƿ�����
	public static void IllegalInput(String s){
		JOptionPane.showMessageDialog(null,
				"����������ݣ� ��" + s + "�� ������Ҫ��\n" +
						"�Ϸ�������Ϊ0~99��������\n���������롣");
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
		JOptionPane.showMessageDialog(null, "δ�ҵ���СΪ"+v+"��Ԫ�أ�");
	}
	//��������
	public static void EndSearch(){
		JOptionPane.showMessageDialog(null, "�ҵ���Ԫ�أ�����������");
	}
	//�������δ֪����
	public static void UnknownError(){
		JOptionPane.showMessageDialog(null, "������δ֪�����볢���������б���ʾ����");
	}
	//��ѡ��m��ֵ�ٽ��в���
	public static void SelectMFirst(){
		JOptionPane.showMessageDialog(null, "����ѡ��B���Ĳ�����Ȼ����Ӧ��");
	}
}
