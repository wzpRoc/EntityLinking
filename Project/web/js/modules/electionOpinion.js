function clearAndSelectPolarity(groupId, polarityValue, color) {
    clearPolarity(groupId);
    selectOnePolarity(groupId, polarityValue, color);
}


function polarityClicked(groupId, polarityValue, color) {
    // clearAndSelectPolarity(groupId, polarityValue, color);
    selectPolarityFromP1(groupId, polarityValue);
    document.getElementById("dto.tag"+groupId+"1").checked = true;
    document.getElementById("dto.polarity"+groupId).value = polarityValue;
}


function clearPolarity(groupId) {
    for(var polarity = -3; polarity<=3; polarity++){
        getPolarityElement(groupId, polarity).style.backgroundColor = "#eeeeee";
        getPolarityElement(groupId, polarity).style.color = "#888888";
    }
}


function selectPolarityFromP1(groupId, polarity) {
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
            selectOnePolarity(groupId, i_polarity);
        } else {
            getPolarityElement(groupId, i_polarity).style.backgroundColor = "#eeeeee";
        }
        getPolarityElement(groupId, i_polarity).style.color = "#888888";
    }

    var color;
    if(polarity == 0) {
        color = "#000000"
    } else if(polarity < 0) {
        color = "#000000"
    } else {
        color = "#ffffff"
    }
    getPolarityElement(groupId, polarity).style.color = color;
}


function selectOnePolarity(groupId, polarityValue) {
    var obj = getPolarityElement(groupId, polarityValue);
    var color = obj.attributes["colorOnSelected"].value;
    obj.style.backgroundColor = color;
}


function getPolarityElement(groupId, polarity) {
    return document.getElementById("span_polarity_"+groupId+"_"+polarity);
}


function tagOnclick(obj, groupId){
    if(obj.value == 0) {
        // 无观点
        clearPolarity(groupId);
    } else {
        polarityClicked(groupId, 0, "#FFFFFF");
    }
}


