function moveUp(_a) {
	// 通过链接对象获取表格行的引用
	var _row = _a.parentNode.parentNode;
	var currentIndex = $($(_row).children()[0]).text();
	$($(_row).children()[0]).text(parseInt(currentIndex) - 1);
	$($(_row.previousSibling).children()[0]).text(currentIndex);

	// 如果不是第一行，则与上一行交换顺序
	if (_row.previousSibling)
		swapNode(_row, _row.previousSibling);

} // end of moveUp

// 使表格行下移，接收参数为链接对象
function moveDown(_a) {
	// 通过链接对象获取表格行的引用
	var _row = _a.parentNode.parentNode;
	var currentIndex = $($(_row).children()[0]).text();
	$($(_row).children()[0]).text(parseInt(currentIndex) + 1);
	$($(_row.nextSibling).children()[0]).text(currentIndex);
	// 如果不是最后一行，则与下一行交换顺序
	if (_row.nextSibling)
		swapNode(_row, _row.nextSibling);
} // end of moveDown

// 定义通用的函数交换两个结点的位置
function swapNode(node1, node2) {
	// 获取父结点
	var _parent = node1.parentNode;
	// 获取两个结点的相对位置
	var _t1 = node1.nextSibling;
	var _t2 = node2.nextSibling;
	// 将node2插入到原来node1的位置
	if (_t1)
		_parent.insertBefore(node2, _t1);
	else
		_parent.appendChild(node2);
	// 将node1插入到原来node2的位置
	if (_t2)
		_parent.insertBefore(node1, _t2);
	else
		_parent.appendChild(node1);
} // end of swapNode
