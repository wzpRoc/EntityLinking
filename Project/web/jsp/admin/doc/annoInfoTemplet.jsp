<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="display: none">
    This is template.

    <div id="div_annoInfoTemplate">
        <div class="div_annoInfo">
            <input class="annoInfoIdx" name="startIdx">
            <input class="annoInfoIdx" name="endIdx">
            <input name="mention">
            <input type="hidden" name="entityId">
            <input name="entityTitle"
                   readonly="true"
                   style="cursor:pointer"
                   title="请点击选择"
                   onclick="showQueryEntityFrame(this);"
            >
            <div class="div_deleteAnno" name="div_deleteAnno" onclick="deleteAnno(this)" title="点击删除标注"><a>╳</a></div>
        </div>
    </div>

</div>
