import javax.swing.JOptionPane;

//弹出对话框类
public class Message {
	//非法输入
	public static void IllegalInput(String s){
		JOptionPane.showMessageDialog(null,
				"您输入的数据： “" + s + "” 不符合要求！\n" +
						"合法的数据为0~99的整数。\n请重新输入。");
	}
	//需要至少有一个元素
	public static void AtLeastOneElement(){
		JOptionPane
		.showMessageDialog(null, "请先插入至少一个元素再执行其他操作");
	}
	//已有相同元素
	public static void SameElement(){
		JOptionPane.showMessageDialog(null, "已有相同元素，插入失败！");
	}
	//未找到此元素
	public static void NoSuchElement(int v){
		JOptionPane.showMessageDialog(null, "未找到大小为"+v+"的元素！");
	}
	//搜索结束
	public static void EndSearch(){
		JOptionPane.showMessageDialog(null, "找到此元素，搜索结束！");
	}
	//程序出现未知错误
	public static void UnknownError(){
		JOptionPane.showMessageDialog(null, "程序发生未知错误，请尝试重新运行本演示程序！");
	}
	//先选择m的值再进行操作
	public static void SelectMFirst(){
		JOptionPane.showMessageDialog(null, "请先选择B树的叉数，然后点击应用");
	}
}
