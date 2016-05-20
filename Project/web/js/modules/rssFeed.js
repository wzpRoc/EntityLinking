function checkAndSave() {
    document.getElementById("form0").submit();
}

function addSimilar() {
    var f = document.getElementById("form0");
    document.getElementsByName("dowhat")[0].value="add";
    document.getElementsByName("dto.title")[0].value="";
    document.getElementsByName("dto.url")[0].value="";
    f.action = ctx+"/advanced/rssFeedDetail!get";
    f.submit();
}


