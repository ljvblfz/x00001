
function createTree(url, treediv, isdisabled, tname, tvalue, son, groupid,
		checked) {

	doGetAjax(url, {}, function(data) {
		var d = new Date();
		var id = "tree_" + d.getTime();
		if (data.length > 0) {
			var html = '';
			html += '<ul   class="' + id + " " + (isdisabled ? 'disabled' : '')
					+ '  tree  treeFolder treeCheck  " oncheck="">' + '\n';
			html += '<li><a href="#" >所有权限</a> <ul>';
			$.each(data, function() {

				html = createSubTree(this, html, tname, tvalue, son, groupid);

			});
			html += '</ul> </li>';
			html += '</ul>\n';
			jQuery("#" + treediv).html(html);
			jQuery('.' + id).jTree();
		}

	});

	function createSubTree(menu, html, tname, tvalue, son, groupid) {

		html += ' <li><a href="#" external="true"  tname="'
				+ groupid
				+ '" tvalue="'
				+ menu[tvalue]
				+ '" '
				+ (menu[checked] ? (menu[checked] == 'true' ? 'checked' : ' ')
						: ' ') + '>' + menu[tname] + '</a>\n';
		if (menu[son]) {

			if (menu[son].length > 0) {
				html += '<ul >' + '\n';
				$.each(menu[son], function() {

					html = createSubTree(this, html, tname, tvalue, son);
				});
				html += '</ul>\n';
			}
		}
		html += '</li>\n';
		return html;
	}
}
