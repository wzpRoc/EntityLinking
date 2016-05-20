/***********************************************************************************
 *名称：gazetteer.js
 *功能：词表管理
 *开发者：张强
 *时间：2013-07-15
 ***********************************************************************************/
/**
 * 清空函数
 */
function clear(){
	$("#gazetteerName").val("");
	$("#gazetteerItems").val("");
	$("#gazetteerRemarks").val("");
}

function save(){
	 var gazetteerName=$("#gazetteerName");
	 if(gazetteerName.val().length==0 || gazetteerName.val().trim() == ""){
		 alert("请填写名称");
		 return false;
	 }
	 
	 document.getElementById("form0").submit();
}

