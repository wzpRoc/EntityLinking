/**********************************************************************************************************************************************
 *名称：entity.js
 *功能：处理与实体或者实体名称的相关操作
 *开发者：彭程
 *时间：2012-04-01
 **********************************************************************************************************************************************/

// 从后台得到实体列表
function getEntitiesByNameAndEntityType(name1, name2, entityType, fuzzy){
	$('#entityListBox').show();
	$('#tip_combine').show().html("正在加载，请稍候....");
	AjaxInterface.getEntitiesByNameAndType(name1, name2, entityType, fuzzy, function(blMessage){
		if(blMessage.success){
			var entityList = blMessage.data;
			for(var i = 0; i < entityList.length; i++){
				var entity = entityList[i];
				genEntityRecord(entity);
			}
			if(entityList.length>0){
				$('#tip_combine').show().html("请选择待合并的实体，最多选择两个实体进行合并！");
				$('#entityListBox :checkbox').click(function(){
					// 计算个数
					var checknum = $('#entityListBox :checked').size();
					var $_option = $('#entityListBox #option_combine');
					if(checknum < 2){
						$_option.hide();
					}
					else{
						$_option.show();
						if(checknum > 2){
							return false;
						}
					}
				});
			}
			else{
				$('#tip_combine').show().html("无匹配查询结果！");
			}
		}
	});
}
// 当前实体类型
var cEtypeText;
var cEtype;
var etypeToAction = {
	'p': 'personManage!edit',
	'o': 'organizationManage!edit',
	'l': 'locationManage!edit'
}
var EntityToDetailAction = {'p' : '../person?id=', 'l' : 'locationManage!actionInEdit?parentId=', 'o' : '../organization?id='};

// 根据用户输入，提交后台查询
function queryEntityByName(){
	// 输入文本
	var name1 = $('#entityName1').val();
	var name2 = $('#entityName2').val();
	name1 = name1 != "" ? name1 : name2;
	if(name1 == "")return false;
	// 实体类型
	var entityType = $('#entityType').val();
	cEtype = entityType;
	cEtypeText = $('#entityType option:selected').html();
	// 先清空当前的记录
	$('#entityList tr').first().siblings().remove();
	$('#entityListBox #option_combine').hide();
	var fuzzy = false;
	if($("#fuzzy:checked").size()>0){
		fuzzy = true;
	}
	getEntitiesByNameAndEntityType(name1, name2, entityType, fuzzy);
	
	return false;
}

// 添加一条查询记录
function genEntityRecord(entity){
	$("<tr>" +
			"<td class='ta_c'><input type='checkbox' id='" + entity.id +"'></checkbox></td>" +
			"<td class='ta_c'>" + entity.id + "</td>" +
			"<td class='ta_c'>" + entity.name + "</td>" +
			"<td class='ta_c'>" + cEtypeText + "</td>" +
			"<td class='ta_c'>" + entity.prompt + "</td>" +
			"<td class='ta_c td_edit'><a target='_blank' href='" + EntityToDetailAction[cEtype] + entity.id +"'>详情链接</td>" +
			"</td>").appendTo($('#entityList'));
}

// 合并操作
function combineEntity(){
	var $_a = $('#entityListBox #option_combine a');
	var id1 = $('#entityListBox :checked').first().attr('id');
	var id2 = $('#entityListBox :checked').last().attr('id');
	$('#entityListBox :checked').each(function(){
		$_self = $(this);
		var id = $_self.attr('id');
		var combineId = (id == id1) ? id2 : id1;
		var newAction = etypeToAction[cEtype];
		production(newAction, 'id', id, 'combineId', combineId);
	});
}
