<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${ctx}/js/artDialog/skin/aero/aero.css" rel="stylesheet" type="text/css" />
<div id="div_popMessage" style="display: none">
    <div class="ui_dialog" style="display: block; left: 30%; top: 20%; z-index: 100005; position:fixed;">
        <table class="">
            <tbody>
            <tr>
                <td class="ui_border r0d0"></td>
                <td class="ui_border r0d1"></td>
                <td class="ui_border r0d2"></td>
            </tr>
            <tr>
                <td class="ui_border r1d0"></td>
                <td>
                    <table class="ui_dialog_main">
                        <tbody>
                        <tr>
                            <td class="ui_title_wrap">
                                <div class="ui_title">
                                    <div class="ui_title_text"><span class="ui_title_icon"></span>消息</div>
                                    <a class="ui_close" href="javascript:closePopMessage()">×</a></div>
                            </td>
                        </tr>
                        <tr>
                            <td id="td_content" class="ui_content_wrap" style="width: 500px; height: 120px;">

                            </td>
                        </tr>
                        <tr>
                            <td class="ui_bottom_wrap">
                                <div class="ui_bottom">
                                    <div class="ui_btns"></div>
                                    <div class="ui_resize"></div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
                <td class="ui_border r1d2"></td>
            </tr>
            <tr>
                <td class="ui_border r2d0"></td>
                <td class="ui_border r2d1"></td>
                <td class="ui_border r2d2"></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>