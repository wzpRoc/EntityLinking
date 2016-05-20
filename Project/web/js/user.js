/**
 * Created with IntelliJ IDEA.
 * User: Jazz
 * Date: 13-12-7
 * Time: 下午11:02
 * To change this template use File | Settings | File Templates.
 */


function bindDevice(element, deviceNo, purchasingDate, isBindding) {

    if (deviceNo == "" || purchasingDate == "") {
        $("#bindMessage").text("输入有误或不完整，绑定无效！");
    }
    else {
        AjaxLogin.bindDevice(deviceNo, purchasingDate, isBindding, function (blMessage) {
            if (isBindding) {
                if (blMessage.success) {
                    $(element).val(1);
                    $(element).text('解除绑定');
                    $(element).siblings('#removeBtn').remove();
                    $("#bindMessage").text("绑定成功！");
                }
                else {
                    $("#bindMessage").text(blMessage.message);
                }
            }
            else {
                if (blMessage.success)
                    $(element).closest('div').remove();
                else
                    $("#bindMessage").text(blMessage.message);
            }
        });
    }
}

function setDeviceHtml(){
    AjaxLogin.getBindingDeviceInfo(function(deviceInfoStr){
        var deviceInfoArray = deviceInfoStr.split(";");
        if(deviceInfoArray == "")
            return;
        for(var x in deviceInfoArray){
            var index = deviceInfoArray[x].indexOf(",");
            if(index == -1)
                break;
            var sn = deviceInfoArray[x].substr(0, index);
            var date = deviceInfoArray[x].substr(index+1);

            addDeviceBinding(sn, date, 1, "解除绑定");
        }
    });

}

function addDeviceBinding(sn, date, bindingValue, stateStr){
    var htmlStr = "<div class='form-group row'>"+
        "<tab for='deviceType' class='col-sm-2 control-label'>设备序列号</tab>"+
        " <div class='col-sm-3'>"+"<input  type='text' class='form-control deviceNo' value='"+sn+"'/></div>"+
        "<tab for='devicePurchaseDate' class='col-sm-2 control-label'>购买日期</tab>"+
        "<div class='input-group date form_date col-sm-3' data-date='' data-date-format='yyyy-mm-dd'"+
        "data-link-field='dtp_input2' data-link-format='yyyy-mm-dd'>"+
        "<input type='text' class='form-control bindingDate' size='16' readonly='true' value='"+date+"'/>"+
        "<span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div>"+
        "<button type='button' class='btn btn-info  col-sm-1' value='"+bindingValue+"' onclick='changeBinding(this)'>"+stateStr+"</button>";

    if(bindingValue !=1)
        htmlStr += "<button type='button' id='removeBtn' class='btn btn-info  col-sm-1' value='"+bindingValue+"' onclick='$(this).closest(\"div\").remove();'>移除</button>";
    htmlStr += "</div>";

    var $row = $(htmlStr);
    $("#device").append($row);
    $(".form_date").on("click",function(){
        $(".form_date").datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        })
    }   );
}

function addDeviceInfoToUserInfo(){
    AjaxLogin.getBindingDeviceInfo(function(deviceInfoStr){
        var deviceInfoArray = deviceInfoStr.split(";");
        if(deviceInfoArray == "")
            return;
        for(var x in deviceInfoArray){
            var index = deviceInfoArray[x].indexOf(",");
            if(index == -1)
                break;
            var sn = deviceInfoArray[x].substr(0, index);
            var date = deviceInfoArray[x].substr(index+1);

            addDeviceHtmlToUserInfo(sn, date);
        }
    });
}

function addDeviceHtmlToUserInfo(sn, date){
    var $row = $("<div class='form-group row'><span class='col-sm-offset-1 col-sm-2 control-span'>序列号</span><div class='col-sm-2'><label cssClass='control-label'>"+sn+
        "</label></div><span class='col-sm-2 control-span'>购买日期</span><div class='col-sm-2'><label cssClass='control-label'>"+date+"</label></div></div>");

    $("#userInfo").append($row);
}