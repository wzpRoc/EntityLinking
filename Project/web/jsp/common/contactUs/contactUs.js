function saveComment() {
    var firstName = getOnlyElementByName('dto.firstName').value;
    var secondName = getOnlyElementByName('dto.secondName').value;
    var telephone = getOnlyElementByName('dto.telephone').value;
    var email = getOnlyElementByName('dto.email').value;
    var content = getOnlyElementByName('dto.content').value;
    AjaxInterface.addContactComment(firstName, secondName, telephone, email, content, addComment_callback);
}

/**
 * 联系我们的回调函数
 * @param blMessage
 */
function addComment_callback(blMessage) {
    if(blMessage.success) {
        // 成功
        alert(blMessage.message);
    } else {
        // 失败
        alert(blMessage.message);
    }

}