package org.ailab.wimfra.frontend;

import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.*;
import org.ailab.wimfra.core.IValueAndLabel;
import org.ailab.wimfra.core.ValueAndLabel;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.BiString;
import org.ailab.wimfra.util.ClassUtil;
import org.ailab.wimfra.util.NumberUtil;
import org.ailab.wimfra.util.StringUtil;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

/**
 * FreeMarker user-defined directive that progressively transforms
 * the output of its nested content to Select-case.
 */
public class SelectDirective implements TemplateDirectiveModel {
    private StringBuffer sb;

    public void execute(Environment env,
                        Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {

        // name
        String name = getParamString(params, "name");

        // onchange
        String onchange = getParamString(params, "onchange");

        // id
        String id = getParamString(params, "id");

        // useCategoryTable
        boolean useCategoryTable = "true".equalsIgnoreCase(getParamString(params, "useCategoryTable"));

        // properties
        String properties = getParamString(params, "properties");

        // style
        String style = getParamString(params, "style");

        // dataId
        String dataId = getParamString(params, "dataId");

        // sql
        String sql = getParamString(params, "sql");

        // module
        String module = getParamString(params, "module");

        // 通用参数
        String generalParameters = getParamString(params, "generalParameters");
        Map<String, String> generalParameterMap = new HashMap<String, String>();
        if (generalParameters != null) {
            for (String generalParameter : generalParameters.split("\\|")) {
                int idxOfColon = generalParameter.indexOf(':');
                generalParameterMap.put(generalParameter.substring(0, idxOfColon), generalParameter.substring(idxOfColon + 1));
            }
        }

        String valueAndTexts = getParamString(params, "valueAndTexts");

        // 根据dataId获得列表数据
        List<IValueAndLabel> valueAndTextList;
        if (StringUtil.notEmpty(valueAndTexts)) {
            valueAndTextList = ValueAndLabelUtil.promptListFromText(valueAndTexts);
        } else if (StringUtil.notEmpty(sql)) {
            List<BiString> biStringList = null;
            try {
                biStringList = DBUtil.getBiStringList(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            valueAndTextList = new ArrayList<IValueAndLabel>();
            if (biStringList != null) {
                for (BiString biString : biStringList) {
                    valueAndTextList.add(new ValueAndLabel(biString.getS0(), biString.getS1()));
                }
            }
        } else if ("number".equals(dataId)) {
            // 开始、结束范围
            double valueFrom = Double.parseDouble(getParamString(params, "valueFrom"));
            double valueTo = Double.parseDouble(getParamString(params, "valueTo"));
            // 间隔
            String s_interval = getParamString(params, "interval");
            // 默认间隔为1
            double interval = s_interval == null ? 1 : Double.parseDouble(s_interval);

            // 小数位数
            String s_decimalPlaces = getParamString(params, "decimalPlaces");
            // 默认小数位数为0
            int decimalPlaces = s_decimalPlaces == null ? 0 : Integer.parseInt(s_decimalPlaces);

            valueAndTextList = createNumberValueAndTextList(valueFrom, valueTo, interval, decimalPlaces);
        } else {
            try {
                valueAndTextList = ValueAndLabelListCache.getValueAndLabelList(dataId, useCategoryTable, module, generalParameterMap);
            } catch (Exception e) {
                e.printStackTrace();
                valueAndTextList = null;
            }
            if (valueAndTextList == null) {
                throw new TemplateModelException("Can not find data by dataId: " + dataId);
            }
        }

        // 初始值
        String value = getParamString(params, "value");
        if (value == null) {
            if (name != null) {
                try {
                    value = getValueByName(env, name);
                } catch (Exception e) {
                    value = null;
                    e.printStackTrace();
                }
            }
        }

        // 是否增加“请选择"
        String addPleaseSelect = getParamString(params, "addPleaseSelect");
        boolean b_addPleaseSelect;
        if (addPleaseSelect == null) {
            b_addPleaseSelect = false;
        } else if ("true".equalsIgnoreCase(addPleaseSelect)) {
            b_addPleaseSelect = true;
        } else {
            b_addPleaseSelect = false;
        }

        // 是否增加全部
        String addAll = getParamString(params, "addAll");
        boolean b_addAll;
        if (addAll == null) {
            b_addAll = false;
        } else if ("true".equalsIgnoreCase(addAll)) {
            b_addAll = true;
        } else {
            b_addAll = false;
        }

        sb = new StringBuffer();
        sb.append("<SELECT");

        if (id != null) {
            sb.append(" id=\"").append(id).append("\"");
        }
        if (onchange != null) {
            sb.append(" onchange=\"").append(onchange).append("\"");
        }
        if (name != null) {
            sb.append(" name=\"").append(name).append("\"");
        }
        if (style != null) {
            sb.append(" style=\"").append(style).append("\"");
        }
        if (properties != null) {
            sb.append(" ").append(properties);
        }
        sb.append(">");

        if (b_addPleaseSelect) {
            String optionValue = "";
            String optionText = "请选择";

            sb.append("<OPTION value=\"").append(optionValue).append("\"");
            if (isEqualValues(value, optionValue)) {
                sb.append(" selected");
            }
            sb.append(">").append(optionText).append("</OPTION>");
        }

        if (b_addAll) {
            String optionValue = "";
            String optionText = "全部";

            sb.append("<OPTION value=\"").append(optionValue).append("\"");
            if (isEqualValues(value, optionValue)) {
                sb.append(" selected");
            }
            sb.append(">").append(optionText).append("</OPTION>");
        }

        boolean hasSelected = false;
        for (IValueAndLabel valueAndText : valueAndTextList) {
            String optionValue = valueAndText.getValue();
            String optionText = valueAndText.getLabel();

            sb.append("<OPTION value=\"").append(optionValue).append("\"");
            if (!hasSelected && isEqualValues(value, optionValue)) {
                hasSelected = true;
                sb.append(" selected");
            }
            sb.append(">").append(optionText).append("</OPTION>");
        }

        this.sb.append("</SELECT>");

        // If there is non-empty nested content:
        if (body != null) {
            // Executes the nested body. Same as <#nested> in FTL, except
            // that we use our own writer instead of the current output writer.
            body.render(new MyWriter(env.getOut()));
        } else {
            env.getOut().write(sb.toString());
            env.getOut().flush();
        }
    }

    /**
     * 是否是相等的两个值
     */
    private boolean isEqualValues(String value, String optionValue) {
        if(StringUtil.equals(optionValue, value)) {
            // 字符串相同
            return true;
        } else {
            if(NumberUtil.isDecimal(value) && NumberUtil.isDecimal(optionValue)) {
                return NumberUtil.isEquals(value, optionValue);
            } else {
                return false;
            }
        }
    }


    private static List<IValueAndLabel> createNumberValueAndTextList(double valueFrom, double valueTo, double interval, int decimalPlaces) {
        List<IValueAndLabel> valueAndTextList;
        int steps = (int) (Math.round(Math.abs(valueTo - valueFrom) / interval) + 1);
        valueAndTextList = new ArrayList<IValueAndLabel>(steps);
        if (valueFrom > valueTo && interval > 0) {
            interval = -interval;
        }
        for (int i = 0; i < steps; i++) {
            double num = valueFrom + interval * i;
            String str = NumberUtil.format(num, decimalPlaces, decimalPlaces);
            valueAndTextList.add(new ValueAndLabel(str, str));
        }
        return valueAndTextList;
    }


    private String getValueByName(Environment env, String name) throws TemplateModelException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String[] names = name.split("\\.");
        String varNameInAction = names[0];

        String value;
        final TemplateModel valueModel = ((StringModel) env.getDataModel().get("action")).get(varNameInAction);
        if (valueModel == null) {
            value = "";
        } else {
            if (names.length == 1) {
                value = valueModel.toString();
            } else {
                // 还有下级变量
                final Object valueObj = ClassUtil.getHierarchicalFieldValue(((StringModel) valueModel).getWrappedObject(), Arrays.copyOfRange(names, 1, names.length));
                value = valueObj == null ? null : valueObj.toString();
            }
        }
        return value;
    }


    private String getParamString(Map params, String paramName) {
        final Object paramObj = params.get(paramName);
        return paramObj == null ? null : paramObj.toString();
    }


    private class MyWriter extends Writer {

        private final Writer out;

        MyWriter(Writer out) {
            this.out = out;
        }

        public void write(char[] cbuf, int off, int len)
                throws IOException {
            out.write(sb.toString());
        }

        public void flush() throws IOException {
            out.flush();
        }

        public void close() throws IOException {
            out.close();
        }
    }


    public static void main(String[] args) {
//        createNumberValueAndTextList(0.1, 0.3, 0.01, 2);
        createNumberValueAndTextList(0, 11, 1, 0);
    }
}
