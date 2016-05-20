/****************************************************************************************************
 * 功能：用于验证用户的输入是否合法
 * 开发者：张强
 * 时间：2013/5/13
 **************************************************************************************************/
//验证列表记录中 用户输入的“每页显示记录数”是否合法
$(document).ready(function(){
	var recordNumPerPage=$("input[name='page.recordNumPerPage']").attr("value");
	var flag=isInt(recordNumPerPage);
	alert(flag);
});
/**
 * 用正则表达式验证输入的字符是不是数字
 * @param str
 * @return
 */
function isInt(str){
	//以1-9开头  后跟0-9之间的数字
	var reg=new RegExp("^[1-9][0-9]*");
	return (reg.test(str));
}
