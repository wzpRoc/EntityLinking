<#assign select = "org.ailab.irica.frontend.SelectDirective"?new()>
<script type="text/javascript" src="${ctx}/js/infoMng.js"></script>

<form name="form0" id="form0" method="post" action="${ctx}/${actionName}">
<@s.hidden name="enableSession"/>
<@s.hidden name="submitTag"/>
<@s.hidden name="dowhat"/>
<@s.hidden name="condition.receiverId"/>
<@s.hidden name="startDate"/>
<@s.hidden name="endDate"/>

    <div class="top_options mb10">
        发送者
        <@select keyword="condition.senderId" dataId="userIdName" addAll="true"/>
        &nbsp;&nbsp;&nbsp;&nbsp;
        状态
        <@select keyword="condition.received" valueAndTexts="0||未接收||1||已接收" addAll="true"/>
        <div style="float:right">
            <input type="submit" value="&nbsp;查询&nbsp;"/>
        </div>
    </div>
    
    
    <div class="mt10">
			<table class="usertopic-list" >
				<tbody >
					<tr  >
						<th width="50" style="line-height: 25px;">
							发送人
						</th>
						<th width="100">
							发送时间
						</th>
						<th width="300">
							内容
						</th>
						<th width="70">
							状态
						</th>
					</tr>
					 <#list list as userMessage>
							<tr>
								<td style="line-height: 25px;">
									${userMessage.senderName}
								</td>
								<td>
									${userMessage.sendingTimeInShort}
								</td>
								<td>
									${userMessage.content!''}
								</td>
								<td>
									${userMessage.receivedDesc}
								</td>
						</tr>
					</#list>
				</tbody>
			</table>
		<br/>
	</div >
    
    


        <#include "../advanced/paging_infoMng.ftl">
		<@pageNavigator curPage=page.currentPageNo maxPage=page.totalPageNum colspan=2 />
</form>


<script language="javascript">
    // 如果是查看未接收
    if(document.getElementById("form0").dowhat.value == "viewUnreceived"){
        // 那么把该用户未接收的全部置为已接收
        AjaxInterface.receiveUserMessage(userId, afterCallReceiveUserMessage);
    }

    function afterCallReceiveUserMessage() {

    }
</script>