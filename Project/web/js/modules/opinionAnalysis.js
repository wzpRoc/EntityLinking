function poPollPollster_onchange(obj_select) {
    var pollsterCode = getSelectedValue(obj_select);
    if (pollsterCode == "0") {
        document.getElementById("line_paPoll_0").style.display = "";
        document.getElementById("paPoll.dateRange_0").style.display = "";
        document.getElementById("line_paPoll_1").style.display = "none";
        document.getElementById("paPoll.dateRange_1").style.display = "none";
        drawPaPll_0();
    } else {
        document.getElementById("line_paPoll_0").style.display = "none";
        document.getElementById("paPoll.dateRange_0").style.display = "none";
        document.getElementById("line_paPoll_1").style.display = "";
        document.getElementById("paPoll.dateRange_1").style.display = "";
        drawPaPll_1();
    }
}


/**
 * 查看新闻列表
 */
function showDocList(docStatIdx) {
    // alert(docStatIdx);
    var pubDate = pubDateList[docStatIdx];
    var dateBegin = dateBeginList[docStatIdx];
    var dateEnd = dateEndList[docStatIdx];
//    var languageIds = '2,3';
    var languageIds = '';
    var maxRecordNum = 10;
    var moreLink;
//    if (objectId == 86) {
//        AjaxInterface.getDocListByPubDateAndEntity(languageIds, dateBegin, dateEnd, entityType, entityId, maxRecordNum, callback_showDocList);
//        moreLink = ctx + "/docList" +
////                   "?languageId=2,3" +
//                   "?languageId=" +
//                   "&condition.entityType=" + entityType +
//                   "&condition.entityId=" + entityId +
//                   "&dateMode=section" +
//                   "&startDate=" + dateBegin +
//                   "&endDate=" + dateEnd;
//    } else {
        AjaxInterface.getDocListByPubDateAndSubject(languageIds, dateBegin, dateEnd, subjectId, 86, maxRecordNum, callback_showDocList);
        moreLink = ctx + "/docList" +
                   "?languageId=" +
//                   "?languageId=2,3" +
                   "&condition.entityId=" + subjectId +
                   "&dateMode=section" +
                   "&startDate=" + dateBegin +
                   "&endDate=" + dateEnd;
//    }

    return "<DIV id='tooltips'>正在加载数据，请稍候……</DIV>";

    /**
     * 回调函数
     * @param docList
     */
    function callback_showDocList(docList) {
        var title = "关于" + entityName + "的报道(" + getDateDesc(dateBegin, dateEnd) + ")";

        var html = "";
        for (var i_doc in docList) {
            var doc = docList[i_doc];
            html +=
                "<a onclick='openDocDetail(" + doc.id + ")'>" + doc.title + "</a>" +
                    "<span> " + doc.siteName + "</span>" +
                    "<BR>";
        }

        var tooltips = document.getElementById("tooltips");
        if (tooltips) {
            tooltips.innerHTML = "<DIV class='tooltips'>"
                    + "<DIV class='tooltips_title'>" + title + "</DIV>"
                    + "<DIV class='tooltips_content'>" + html + "<DIV>"
                    + "<DIV class='tooltips_more'><a target='blank' href=" + moreLink + ">更多>></a><DIV>"
                    + "</DIV>";
        }
    }
}


function getDateDesc(dateBegin, dateEnd) {
    if (dateBegin == dateEnd) {
        return dateBegin;
    } else {
        var a_dateBegin = dateBegin.split("-");
        var a_dateEnd = dateEnd.split("-");
        var start = 0;
        if (a_dateBegin[0] == a_dateEnd[0]) {
            start = 5;
            if (a_dateBegin[1] == a_dateEnd[1]) {
                start = 8;
            }
        }
        return dateBegin + " - " + dateEnd.substring(start);
    }
}

/**
 * 查看观点列表
 * @param opinionStatIdx
 */
function showOpinionList(opinionStatIdx) {
    // alert(opinionStatIdx);
    var nrLabel = dateBeginList.length;
    var pubDate = pubDateList[opinionStatIdx % nrLabel];
    var polaritySign = opinionStatIdx < nrLabel ? 1 : -1;
    var polaritySignName = getPolaritySignName(polaritySign);
    var dateBegin = dateBeginList[opinionStatIdx % nrLabel];
    var dateEnd = dateEndList[opinionStatIdx % nrLabel];
//    var languageIds = '2,3';
    var languageIds = '';
    var maxRecordNum = 10;
    AjaxInterface.getListByOpinionAnnoTaskIdAndPolaritySign(opinionAnnoTaskId, polaritySign, dateBegin, dateEnd, languageIds, maxRecordNum, callback_showOpinionList);

    return "<DIV id='tooltips'>正在加载数据，请稍候……</DIV>";

    /**
     * 回调函数
     * @param docOpinionList
     */
    function callback_showOpinionList(docOpinionList) {
        var title = "关于" + entityName + "的" + polaritySignName + "报道(" + getDateDesc(dateBegin, dateEnd) + ")";

        var html = "";
        for (var i_docOpinion in docOpinionList) {
            var docOpinion = docOpinionList[i_docOpinion];
            html +=
                "<a onclick='openDocDetail(" + docOpinion.docId + ")'>" + docOpinion.docTitle + "</a>" +
                    "<span> " + docOpinion.siteName + "</span>" +
                    "<BR>";
        }

        var moreLink = ctx + "/advanced/docOpinionList" +
                       "?clearSession=1" +
                       "&dowhat=query" +
                       "&condition.opinionAnnoTaskId=" + opinionAnnoTaskId +
                       "&languageId=" + languageIds +
                       "&dateMode=section" +
                       "&condition.polarity=" + polaritySign +
                       "&condition.pubDateBegin=" + dateBegin +
                       "&condition.pubDateEnd=" + dateEnd;

        var tooltips = document.getElementById("tooltips");
        if (tooltips) {
            tooltips.innerHTML = "<DIV class='tooltips'>"
                    + "<DIV class='tooltips_title'>" + title + "</DIV>"
                    + "<DIV class='tooltips_content'>" + html + "<DIV>"
                    + "<DIV class='tooltips_more'><a target='blank' href=" + moreLink + ">更多>></a><DIV>"
                    + "</DIV>";
        }
    }
}


/**
 *得到
 */
function getPolaritySignName(polaritySign) {
    switch (polaritySign) {
        case  1: return "正面";
        case -1: return "负面";
        case  0: return "中立";
        default: return "未知：" + polaritySign;
    }
}


function showTab(showWhat) {
    getElementById("showWhat").value = showWhat;
    getElementById("form0").submit();
}