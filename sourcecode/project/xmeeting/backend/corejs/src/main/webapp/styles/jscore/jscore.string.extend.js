/**
 * 删除左右两端的空格
 */
String.prototype.trim = function() {
	return this.replace(/^\s*/, "").replace(/\s*$/, "");
}

/**
 * 删除左边的空格
 */
String.prototype.ltrim = function() {
	return this.replace(/(^\s*)/g, "");
}

/**
 * 删除右边的空格
 */
String.prototype.rtrim = function() {
	return this.replace(/(\s*$)/g, "");
}