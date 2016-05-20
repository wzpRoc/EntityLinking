<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="queryEntityFrameCommon" class="none optionFrame">
	<div id="queryTitle" class="optionTitle">
		输入名称进行查询
        <div id="close">
            <a href="javascript:hideQueryEntityFrameCommon()">关闭</a>
        </div>
	</div>
	<div class='queryForm'>
		<input id="name" type='text' size='14' value='' autocomplete='off' /><button onclick='return queryCandidateByInput();'>查询</button><button onclick='return queryCandidateByInput(true);'>模糊查询</button>
	</div>
	<div class="resultFrame">
		<h4 id="queryTip"></h4>
		<ul id="candsList">
		</ul>
	</div>
</div>