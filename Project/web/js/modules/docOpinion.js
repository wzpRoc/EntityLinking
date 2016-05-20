function clearAndSelectPolarity(aspectIndex, polarityValue, color) {
    clearPolarity(aspectIndex);
    selectOnePolarity(aspectIndex, polarityValue, color);
}


function polarityClicked(aspectIndex, polarityValue, color) {
    // clearAndSelectPolarity(aspectIndex, polarityValue, color);
    selectPolarityFromP1(aspectIndex, polarityValue);
    getElementById_alertIfNull("dto.aspectList["+aspectIndex+"].tag1").checked = true;
    getElementById_alertIfNull("dto.aspectList["+aspectIndex+"].polarity").value = polarityValue;
}


function clearPolarity(aspectIndex) {
    for(var polarity = -3; polarity<=3; polarity++){
        getPolarityElement(aspectIndex, polarity).style.backgroundColor = "#eeeeee";
        getPolarityElement(aspectIndex, polarity).style.color = "#888888";
    }
}


function selectPolarityFromP1(aspectIndex, polarity) {
    var start;
    var end;
    if(polarity == 0){
        start = 0;
        end =0;
    } else if(polarity<0){
        start = polarity;
        end = -1;
    } else {
        start = 1;
        end = polarity;
    }

    for(var i_polarity = -3; i_polarity<=3; i_polarity++){
        if(i_polarity >= start && i_polarity <= end){
            selectOnePolarity(aspectIndex, i_polarity);
        } else {
            getPolarityElement(aspectIndex, i_polarity).style.backgroundColor = "#eeeeee";
        }
        getPolarityElement(aspectIndex, i_polarity).style.color = "#888888";
    }

    var color;
    if(polarity == 0) {
        color = "#000000"
    } else if(polarity < 0) {
        color = "#000000"
    } else {
        color = "#ffffff"
    }
    getPolarityElement(aspectIndex, polarity).style.color = color;
}


function selectOnePolarity(aspectIndex, polarityValue) {
    var obj = getPolarityElement(aspectIndex, polarityValue);
    var color = obj.attributes["colorOnSelected"].value;
    obj.style.backgroundColor = color;
}


function getPolarityElement(aspectIndex, polarity) {
    return document.getElementById("span_polarity_"+aspectIndex+"_"+polarity);
}


function tagOnclick(obj, aspectIndex){
    if(obj.value == 0) {
        // 无观点
        clearPolarity(aspectIndex);
    } else {
        polarityClicked(aspectIndex, 0, "#FFFFFF");
    }
}

function mysubmit(){
    var s = "";
    for(var aspectIndex=0; aspectIndex<nrAspect; aspectIndex++){
        var aspectId = getElementById_alertIfNull("dto.aspectList["+aspectIndex+"].aspectId").value;
        var tag = getElementById_alertIfNull("dto.aspectList["+aspectIndex+"].tag1").checked ? 1 : 0;
        var polarity = getElementById_alertIfNull("dto.aspectList["+aspectIndex+"].polarity").value;

        if(s.length>0){
            s+=";"
        }
        s+= aspectId+","+tag+","+polarity;
    }
    document.getElementById("result").value = s;
    document.form0.submit();
}


/**
 * 更新文章
 */
function updateDocFromSubject() {
    var opinionAnnoTaskId = getSelectedOneRecordId();
    if(opinionAnnoTaskId == null) {
        return;
    }

    // 获得subjectId
    var subjectId = getSelectedValueBySelectId("select_subjectId_"+opinionAnnoTaskId);
    AjaxInterface.importDocFromSubjectIntoOpinionAnnoTask(opinionAnnoTaskId, subjectId, callback_updateDocForOpinionAnnoTask);

    showPopMessage("正在更新，可能需要花费较长时间，请耐心等待……");
}


/**
 * 更新文章的回调函数
 * @param blMessage
 */
function callback_updateDocForOpinionAnnoTask(blMessage) {
    if (blMessage.success) {
        // getOnlyElementByName("subject.nrDoc").value = blMessage.data;
        if(blMessage.message!="") {
            showPopMessage(blMessage.message);
        } else {
            showPopMessage("更新成功！");
        }
    } else {
        alert(blMessage.message);
    }
}



/**
 * 导入新闻/智库文章
 */
function extractOpinion() {
    var opinionAnnoTaskId = getSelectedOneRecordId();
    if(opinionAnnoTaskId == null) {
        return;
    }

    AjaxInterface.extractOpinion(
        opinionAnnoTaskId,
        getElementById("batchProcessMode").value,
        getElementById("dateBegin").value,
        getElementById("dateEnd").value,
        getElementById("additionalCondition").value,
        callback_extractOpinion
    );

    showPopMessage("正在处理，可能需要花费较长时间，请耐心等待……");
}


/**
 * 更新文章的回调函数
 * @param blMessage
 */
function callback_extractOpinion(blMessage) {
    if (blMessage.success) {
        // getOnlyElementByName("subject.nrDoc").value = blMessage.data;
        if(blMessage.message!="") {
            showPopMessage(blMessage.message.replace(new RegExp(/\n/g), "<BR>"));
        } else {
            showPopMessage("处理结束！");
        }
    } else {
        showPopMessage(blMessage.message);
    }
}