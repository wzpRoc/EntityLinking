package org.ailab.wimfra.frontend;


import org.ailab.wimfra.core.IValueAndLabel;
import org.ailab.wimfra.core.ValueAndLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2012-4-18
 * Time: 15:29:22
 * Desc:
 */
public class ValueAndLabelUtil {
    /**
     * 形如：0||否||1||是
     */
    public static List<IValueAndLabel> promptListFromText(String valueAndTexts) {
        List<IValueAndLabel> valueAndTextList;
        if(valueAndTexts == null){
            valueAndTexts = "";
        }
        String[] a_valueAndText = valueAndTexts.split("\\|\\|");

        valueAndTextList = new ArrayList<IValueAndLabel>(a_valueAndText.length / 2);
        for (int i = 0; i < a_valueAndText.length / 2; i++) {
            valueAndTextList.add(new ValueAndLabel(a_valueAndText[2 * i], a_valueAndText[2 * i + 1]));
        }
        return valueAndTextList;
    }


    /**
     * 从DTO列表转换为VT列表
     */
    public static List<IValueAndLabel> dtoListToVTList(List dtoList) {
        List<IValueAndLabel> valueAndTextList = new ArrayList<IValueAndLabel>(dtoList.size());
        for (Object dto : dtoList) {
            IValueAndLabel valueAndText = (IValueAndLabel) dto;
            valueAndTextList.add(valueAndText);
        }
        return valueAndTextList;
    }
}