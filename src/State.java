//用于表示执行操作的状态
public enum State {
	wait, // 等待操作
	insert, search, delete, // 正在...
	go, // 正在前往第...个节点
	inserted, searched, deleted, // 完成...
	split,	//分裂节点
	merge,	//合并节点
	borrow;	//从右兄弟一个借元素
}
