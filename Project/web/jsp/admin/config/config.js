function save() {
    if(getOnlyElementByName("dto.module").value == "") {
        alert("请填写模块");
        return;
    }
    if(getOnlyElementByName("dto.name").value == "") {
        alert("请填写名称");
        return;
    }
    if(getOnlyElementByName("dto.value").value == "") {
        alert("请填写值");
        return;
    }

    document.mainForm.submit();
}