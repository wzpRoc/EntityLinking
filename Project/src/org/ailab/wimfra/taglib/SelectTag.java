package org.ailab.wimfra.taglib;


import org.ailab.wimfra.core.IValueAndLabel;
import org.ailab.wimfra.core.ValueAndLabel;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.frontend.ValueAndLabelListCache;
import org.ailab.wimfra.frontend.ValueAndLabelUtil;
import org.ailab.wimfra.util.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * select标签
 */
public class SelectTag extends TagSupport {
    protected Logger logger = Logger.getLogger(this.getClass());

    private String name;
    private String initValue;
    private String firstOption;
    private String otherProperty;
    private String addPleaseSelect;
    private String addSelectAll;
    private String isView;
    private String textValue;
    private String OPTIONList;
    private String dataId;
    private String valueAndTexts;
    private String sql;
    private String valueFrom;
    private String valueTo;
    private String interval;
    private String decimalPlaces;
    private String generalParameters;
    private String module;
    private String onchange;

    private void clear() {
        name = null;
        initValue = null;
        firstOption = null;
        otherProperty = null;
        addPleaseSelect = null;
        addSelectAll = null;
        isView = null;
        textValue = null;
        OPTIONList = null;
        dataId = null;
        valueAndTexts = null;
        sql = null;
        valueFrom = null;
        valueTo = null;
        interval = null;
        decimalPlaces = null;
        generalParameters = null;
        module = null;
        onchange = null;
    }

    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        if (initValue == null) {
            // 未指定
            if(StringUtil.notEmpty(name)) {
                // 尝试使用提交的值
                initValue = String.valueOf(pageContext.getRequest().getAttribute(name));
            }
        }

        if ("true".equalsIgnoreCase(isView)) {
            writeViewTag();
        } else {
            writeOptionList();
        }

        clear();

        return EVAL_PAGE;
    }

    protected void writeViewTagOnly() {
        JspWriter out = pageContext.getOut();

        try {
            List collList = getList();

            for (int i = 0; collList != null && i < collList.size(); i++) {
                ValueAndLabel valueAndLabel = (ValueAndLabel) collList.get(i);
                if (valueAndLabel.getValue().equals(initValue)) {
                    out.println(valueAndLabel.getLabel());
                    break;
                }
            }
            // out.flush();
        } catch (Exception e) {
            logger.error(e);
        }
    }


    protected String[] retrieveOptionList() {
        String[] result = null;
        try {
            if (!StringUtil.trim(OPTIONList).equals("")) {
                result = OPTIONList.split("\\;");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void drawOptionList(String[] initValues) {
        JspWriter out = pageContext.getOut();
        try {
            String[] OPTIONListStr = retrieveOptionList();
            for (int i = 0; OPTIONListStr != null && i < OPTIONListStr.length; i++) {
                String[] listStr = OPTIONListStr[i].split("\\,");
                String objId = "";
                String objName = "";

                if (listStr.length == 1) {
                    objId = listStr[0];
                    objName = listStr[0];
                } else {
                    objId = listStr[0];
                    objName = listStr[1];
                }

                out.println("<OPTION value=\"" + objId + "\"");

                if (initValues != null)
                    for (int j = 0; j < initValues.length; j++) {
                        String sTmp = initValues[j];
                        sTmp = StringUtil.isEmpty(sTmp) ? "" : sTmp.trim();
                        if (objId.equals(sTmp)) {
                            out.println(" selected ");
                            break;
                        }
                    }
                out.println(">" + (objName == null ? "" : objName.replaceAll("\n", " ").replaceAll("\r", " ")) + "</OPTION>");
            }
            // out.flush();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    protected void writeOptionList() {
        JspWriter out = pageContext.getOut();

        try {
            out.println("<SELECT name=\"" + name + "\"");
            if (StringUtil.notEmpty(otherProperty)) {
                out.println(" " + otherProperty);
            }
            if (StringUtil.notEmpty(id)) {
                out.println(" id=\"" + id + "\"");
            }
            if (StringUtil.notEmpty(onchange)) {
                out.println(" onchange=\"" + onchange + "\"");
            }
            out.println(">");

            if (firstOption != null && !firstOption.equals("")) {
                out.println("<OPTION value=\"\">" + firstOption + "</OPTION>");
            }

            if ("true".equalsIgnoreCase(addPleaseSelect)) {
                out.println("<OPTION value=''>请选择</OPTION>");
            }
            if ("true".equalsIgnoreCase(addSelectAll)) {
                out.println("<OPTION value=''>全部</OPTION>");
            }

            String[] values = new String[0];
            if (initValue != null)
                values = initValue.split(",", -1);
            if (StringUtil.notEmpty(OPTIONList))
                drawOptionList(values);
            else {
                List collList = getList();
                for (int i = 0; collList != null && i < collList.size(); i++) {
                    IValueAndLabel valueAndLabel = (IValueAndLabel) collList.get(i);

                    String value = valueAndLabel.getValue();
                    String label = valueAndLabel.getLabel();

                    out.println("<OPTION value=\"" + value + "\"");

                    if (values != null)
                        for (int j = 0; j < values.length; j++) {
                            String sTmp = values[j];
                            sTmp = StringUtil.isEmpty(sTmp) ? "" : sTmp.trim();
                            if (value.equals(sTmp)) {
                                out.println(" selected ");
                                break;
                            }
                        }
                    out.println(">" + (label == null ? "" : label.replaceAll("\n", " ").replaceAll("\r", " ")) + "</OPTION>");
                }
            }
            out.println("</SELECT>");
        } catch (Exception e) {
            logger.error(e);
        }
    }

    protected void writeViewTag() {
        JspWriter out = pageContext.getOut();

        try {
            if (!StringUtil.trim(initValue).equals("")) {
                out.println(" <html:hidden property=\"" + name + "\" value=\"" + initValue + "\"/>");
                if (!StringUtil.trim(textValue).equals(""))
                    out.print(textValue);
                else
                    this.writeViewTagOnly();
            } else {
                out.println("<font color=red>wzp.newsML.test</font>");
            }

            // out.flush();
        } catch (Exception e) {
            try {
                //   out.flush();
                //   out.close();
            } catch (Exception ee) {
            }
            e.printStackTrace();
        }
    }

    public void release() {
        super.release();
    }

    public String getView() {
        return isView;
    }

    public void setView(String view) {
        isView = view;
    }


    public List getList() {
        try {
            // useCategoryTable
//            boolean useCategoryTable = "true".equalsIgnoreCase(getParamString(params, "useCategoryTable"));
            boolean useCategoryTable = false;

            // ���dataId����б����
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
                valueAndTextList = createNumberValueAndTextList(
                        Double.parseDouble(valueFrom),
                        Double.parseDouble(valueTo),
                        interval == null ? 1 : Double.parseDouble(interval),
                        decimalPlaces == null ? 0 : Integer.parseInt(decimalPlaces));
            } else {
                try {
                    // ͨ�ò���
                    Map<String, String> generalParameterMap = new HashMap<String, String>();
                    if (generalParameters != null) {
                        for (String generalParameter : generalParameters.split("\\|")) {
                            int idxOfColon = generalParameter.indexOf(':');
                            generalParameterMap.put(generalParameter.substring(0, idxOfColon), generalParameter.substring(idxOfColon + 1));
                        }
                    }
                    valueAndTextList = ValueAndLabelListCache.getValueAndLabelList(dataId, useCategoryTable, module, generalParameterMap);
                } catch (Exception e) {
                    e.printStackTrace();
                    valueAndTextList = null;
                }
                if (valueAndTextList == null) {
                    throw new Exception("Can not find data by dataId: " + dataId);
                }
            }

            return valueAndTextList;
        } catch (Exception e) {
            logger.error(e);
            return null;
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


    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getValueAndTexts() {
        return valueAndTexts;
    }

    public void setValueAndTexts(String valueAndTexts) {
        this.valueAndTexts = valueAndTexts;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(String valueFrom) {
        this.valueFrom = valueFrom;
    }

    public String getValueTo() {
        return valueTo;
    }

    public void setValueTo(String valueTo) {
        this.valueTo = valueTo;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(String decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getGeneralParameters() {
        return generalParameters;
    }

    public void setGeneralParameters(String generalParameters) {
        this.generalParameters = generalParameters;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }


    public String getOptionList() {
        return OPTIONList;
    }

    public void setOptionList(String OPTIONList) {
        this.OPTIONList = OPTIONList;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public String getAddPleaseSelect() {
        return addPleaseSelect;
    }

    public void setAddPleaseSelect(String addPleaseSelect) {
        this.addPleaseSelect = addPleaseSelect;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }

    public void setFirstOption(String firstOption) {
        this.firstOption = firstOption;
    }

    public void setOtherProperty(String otherProperty) {
        if (otherProperty != null) {
            this.otherProperty = otherProperty;
        }
    }

    public void setIsView(String isView) {
        this.isView = isView;
    }

    public String getOnchange() {
        return onchange;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public String getAddSelectAll() {
        return addSelectAll;
    }

    public void setAddSelectAll(String addSelectAll) {
        this.addSelectAll = addSelectAll;
    }
}
