/**
 * 读取观点文章
 **/
function loadOpinionDoc() {

}


/**
 * 得到观点的日期
 */
function getOpinionPubDate(idx) {
    return pubDateList[idx % nrDay];
}


/**
 * 得到日期
 */
function getPubDate(idx) {
    return pubDateList[idx];
}


/**
 * 得到候选人短名
 */
function getCandidateShortName(candidateIdx) {
    return candidateShortNames[candidateIdx];
}


/**
 * 得到候选人索引
 */
function getCandidateIdx(opinionStatIdx) {
    // 每人两条线
    return parseInt(opinionStatIdx / (nrDay * 2));
}

/**
 * 得到观点极性符号
 */
function getOpinionSign(opinionStatIdx) {
    // 每人两条线
    return parseInt(opinionStatIdx / nrDay);
}

/**
 * 将idx转换为personId_polaritySign_pubDate
 */
function parseOpinionStatIdx(opinionStatIdx) {
    var map = new Object();

    var pubDate = getOpinionPubDate(opinionStatIdx);
    map['pubDate'] = pubDate;

    var temp = parseInt(opinionStatIdx / nrDay);
    var candidateIdx;
    var polaritySign;
    switch (temp) {
        case 0:
            candidateIdx = 0;
            polaritySign = 1;
            break;
        case 1:
            candidateIdx = 0;
            polaritySign = -1;
            break;
        case 2:
            candidateIdx = 1;
            polaritySign = 1;
            break;
        case 3:
            candidateIdx = 1;
            polaritySign = -1;
            break;
        default:
            alert("error occurred in idxToPersonId_polaritySign_pubDate, idx=" + idx + ", temp=" + temp);
    }
    map['candidateIdx'] = candidateIdx;
    map['polaritySign'] = polaritySign;

    var personId = candidateIds[candidateIdx];
    map['personId'] = personId;

    map['personId_polaritySign_pubDate'] = personId + "_" + polaritySign + "_" + pubDate;

    return map;
}


/**
 * 查看观点列表
 * @param opinionStatIdx
 */
function showOpinionList(opinionStatIdx) {
    // alert(opinionStatIdx);
    var map = parseOpinionStatIdx(opinionStatIdx);
    var personId_polaritySign_pubDate = map['personId_polaritySign_pubDate'];
    var opinionList = map_personId_polaritySign_pubDate_to_opinionList[personId_polaritySign_pubDate];

    var candidateIdx = map['candidateIdx'];
    var candidateShortName = candidateShortNames[candidateIdx];

    var polaritySign = map['polaritySign'];
    var polaritySignName = getPolaritySignName(polaritySign);

    var pubDate = map['pubDate'];

    var title = "关于" + candidateShortName + "的" + polaritySignName + "报道(" + pubDate + ")";

    var html = "";
    for (var i_opinion in opinionList) {
        var opinion = opinionList[i_opinion];
        html += "<a onclick='openDocDetail(" + opinion.docId + ")'>" + opinion.title + "</a><BR>";
    }

    var moreLink = ctx + "/electionOpinionList" +
            "?clearSession=1" +
            "&dowhat=query" +
            "&condition.pubDateBegin=" + pubDate +
            "&condition.pubDateEnd=" + pubDate +
            "&condition.tag" + candidateIdx + "=1" +
            "&condition.polarity" + candidateIdx + "=" + getPolarityCondition(polaritySign)
        ;

    return "<DIV class='tooltips'>"
        + "<DIV class='tooltips_title'>" + title + "</DIV>"
        + "<DIV class='tooltips_content'>" + html + "<DIV>"
//            + "<DIV class='tooltips_more'><a target='blank' href='" + moreLink + "'>更多>></a><DIV>"
        + "</DIV>";
}


/**
 * 得到极性名称
 */
function getPolaritySignName(polaritySign) {
    switch (polaritySign) {
        case  1:
            return "正面";
        case -1:
            return "负面";
        case  0:
            return "中立";
        default:
            return "未知：" + polaritySign;
    }

}


/**
 * 得到极性条件
 */
function getPolarityCondition(polaritySign) {
    switch (polaritySign) {
        case  1:
            return ">0";
        case -1:
            return "<0";
        case  0:
            return "=0";
        default:
            return "";
    }

}


/**
 * 查看新闻列表
 */
function showDocList(docStatIdx) {
    // alert(docStatIdx);
    var candidateIdx = parseInt(docStatIdx / nrDay);
    var candidateShortName = candidateShortNames[candidateIdx];

    var entityType = 'p';
    var entityId = candidateIds[candidateIdx];
    var pubDate = getPubDate(docStatIdx % nrDay);
    var languageIds = '';
    var maxRecordNum = 10;
    AjaxInterface.getDocListByPubDateAndEntity(languageIds, pubDate, pubDate, entityType, entityId, maxRecordNum, callback_showDocList);

    return "<DIV id='tooltips'>正在加载数据，请稍候……</DIV>";

    /**
     * 回调函数
     * @param docList
     */
    function callback_showDocList(docList) {
        var title = "关于" + candidateShortName + "的报道(" + pubDate + ")";

        var html = "";
        for (var i_doc in docList) {
            var doc = docList[i_doc];
            html += "<a onclick='openDocDetail(" + doc.id + ")'>" + doc.title + "</a><BR>";
        }

        var moreLink = ctx + "/docList" +
            "?condition.entityType=" + entityType +
            "&condition.entityId=" + entityId +
            "&startDate=" + pubDate +
            "&endDate=" + pubDate;

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
 * 查看新闻列表
 */
function showTopicList(topicStatIdx) {
    // alert(topicStatIdx);
    var candidateIdx = parseInt(topicStatIdx / nrDay);
    var candidateShortName = candidateShortNames[candidateIdx];

    var entityType = 'p';
    var entityId = candidateIds[candidateIdx];
    var pubDate = getPubDate(topicStatIdx % nrDay);
    var languageIds = '';
    var maxRecordNum = 10;
    AjaxInterface.getTopicListByPubDateAndEntity(languageIds, pubDate, pubDate, entityType, entityId, maxRecordNum, callback_showTopicList);

    return "<DIV id='tooltips'>正在加载数据，请稍候……</DIV>";

    /**
     * 回调函数
     * @param topicList
     */
    function callback_showTopicList(topicList) {
        var title = "关于" + candidateShortName + "的报道(" + pubDate + ")";

        var html = "";
        for (var i_topic in topicList) {
            var topic = topicList[i_topic];
            html += "<a onclick='openTopicDetail(" + topic.id + ")'>" + topic.title + "</a>";
            if (parseInt(topic.nrDoc) > 1) {
                html += "<span class='nrDoc'>" + topic.nrDoc + "篇报道</span>";
            }
            html += "<BR>";
        }

        var moreLink = ctx + "/topicList" +
            "?condition.entityType=" + entityType +
            "&condition.entityId=" + entityId +
            "&startDate=" + pubDate +
            "&endDate=" + pubDate;

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


function showTab(showWhat) {
    getElementById("showWhat").value = showWhat;
    getElementById("form0").submit();
}

