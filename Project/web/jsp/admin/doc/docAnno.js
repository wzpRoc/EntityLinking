//var annoDiv = document.getElementById("annoDiv");
var annoCount = 0;
init();

document.onmouseup = function (event) {
    if(event.button == 2) {
        return false;
    }

    var selection = window.getSelection();

    if(!selection.anchorNode) {
        return false;
    }

    var $startNode = $(selection.anchorNode.parentNode);
    if (typeof($startNode.attr("name")) == "undefined" || $startNode.attr("name") != "charDiv") {
        return true;
    }
    var startIdx = parseInt($startNode.attr("idx"));
    if (selection.anchorOffset == 1) {
        startIdx++;
    }

    var $endNode = $(selection.focusNode.parentNode);
    if (typeof($endNode.attr("name")) == "undefined" || $endNode.attr("name") != "charDiv") {
        return true;
    }
    var endIdx = parseInt($endNode.attr("idx"));
    if (selection.focusOffset == 1) {
        endIdx++;
    }

    if(startIdx == endIdx) {
        return false;
    }

    var mention = content.substring(startIdx, endIdx);
    // console.log(selectionText);

    var docEntity = {
        'startIdx': startIdx,
        'endIdx': endIdx,
        'mention': mention
    };
    addAnno($startNode[0], docEntity, true);

    return false;
};


function init() {
    if (docEntityList) {
        for (var i = 0; i < docEntityList.length; i++) {
            var docEntity = docEntityList[i];
            addAnno(null, docEntity, false);
        }
    }
}


function setDocEntity(annoInfo, docEntity) {
    annoInfo.find("[name=startIdx]").val(docEntity.startIdx);
    annoInfo.find("[name=endIdx]").val(docEntity.endIdx);
    annoInfo.find("[name=mention]").val(docEntity.mention);
    annoInfo.find("[name=entityId]").val(docEntity.entityId);
    annoInfo.find("[name=entityTitle]").val(docEntity.entityTitle);
}

function addAnno(positionObj, docEntity, showQuery) {
    renderAnnoInText(docEntity);

    $("#div_annoInfoList").append(
        $("#div_annoInfoTemplate").html()
    );

    var annoId = docEntity.startIdx + "_" + docEntity.endIdx;
    var annoInfo = $('#div_annoInfoList').find(".div_annoInfo:last");
    annoInfo.attr("id", annoId);
    setDocEntity(annoInfo, docEntity);
    if (showQuery) {
        return showQueryEntityFrameCommon(
            positionObj,
            docEntity.mention,
            annoInfo.find("[name=entityId]")[0],
            annoInfo.find("[name=entityTitle]")[0]
        );
    }
}

function deleteAnno(btn) {
    var $div_annoInfo = $(btn).closest("[class='div_annoInfo']");

    var docEntity = createDocEntity($div_annoInfo);
    renderAnnoInText(docEntity, true);

    $div_annoInfo.remove();
}

function renderAnnoInText(docEntity, isDelete) {
    for (var idx = docEntity.startIdx; idx < docEntity.endIdx; idx++) {
        var $charDiv_i = $("#div_annoText").find("#charDiv_" + idx);
        var cssClass;
        if (isDelete) {
            cssClass = "charDiv";
            $charDiv_i.attr("style", "margin-left:0");
            $charDiv_i.attr("style", "margin-right:0");
        } else {
            cssClass = "charDiv_isAnno";
            if (idx == docEntity.startIdx) {
                $charDiv_i.attr("style", "margin-left:3px");
            } else if (idx == docEntity.endIdx - 1) {
                $charDiv_i.attr("style", "margin-right:3px");
            }
        }
        $charDiv_i.attr("class", cssClass);
    }
}


function createDocEntity($div_annoInfo) {
    var docEntity = {};
    docEntity.startIdx = $div_annoInfo.find("[name=startIdx]").val();
    docEntity.endIdx = $div_annoInfo.find("[name=endIdx]").val();
    docEntity.mention = $div_annoInfo.find("[name=mention]").val();
    docEntity.entityId = $div_annoInfo.find("[name=entityId]").val();
    docEntity.entityTitle = $div_annoInfo.find("[name=entityTitle]").val();
    return docEntity;
}

function showQueryEntityFrame(obj) {
    var positionCtrl = obj;
    var mention = $(obj).closest('div').find('[name=mention]').val();
    var entityIdObj = $(obj).closest('div').find('[name=entityId]')[0];
    var entityTitleObj = $(obj).closest('div').find('[name=entityTitle]')[0];
    showQueryEntityFrameCommon(positionCtrl, mention, entityIdObj, entityTitleObj);
}


function save() {
    var doc = {};
    doc.id = $("[name='dto.id']").val();
    doc.pubdate = $("[name='dto.pubdate']").val();

    var docEntityList = [];
    var list_div_annoInfo = $("#div_annoInfoList").find(".div_annoInfo");
    for (var i = 0; i < list_div_annoInfo.length; i++) {

        var $div_annoInfo = $(list_div_annoInfo[i]);
        var docEntity = createDocEntity($div_annoInfo);
        docEntityList[i] = docEntity;
    }

    AjaxInterface.saveAnno(doc, docEntityList, userId, afterSave);
}


function afterSave(message) {
    if(confirm(message+"\n"+"是否关闭当前页面？")) {
        window.close();
    } else {
        //
    }
}