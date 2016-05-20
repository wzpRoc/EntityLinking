package org.ailab.wimfra.frontend;

import org.ailab.entityLinking_old.wim.AnnoState;
import org.ailab.tem.wim.user.UserBL;
import org.ailab.tem.wim.user.UserRole;
import org.ailab.wimfra.core.IValueAndLabelListProvider;
import org.ailab.wimfra.core.IValueAndLabel;
import org.ailab.wimfra.core.ValueAndLabel;
import org.ailab.wimfra.util.StringUtil;
import org.ailab.wimfra.util.time.TimeUtil;

import java.util.*;

/**
 * User: Lu Tingming
 * Date: 2012-4-18
 * Time: 15:29:22
 * Desc:
 */
public class ValueAndLabelListCache {
    private static Map<String, IValueAndLabelListProvider> map_dataId_to_valueAndTextListProvider;
    private static Map<String, List<IValueAndLabel>> map_dataId_to_valueAndTextList;

    static {
        init();
    }

    public static void init() {

        map_dataId_to_valueAndTextListProvider = new HashMap<String, IValueAndLabelListProvider>();
//        map_dataId_to_valueAndTextListProvider.put("ruleSet", new RuleSetBL());

        map_dataId_to_valueAndTextList = new HashMap<String, List<IValueAndLabel>>();

        register("user", new UserBL());

        map_dataId_to_valueAndTextList.put("yesOrNo", getYesOrNoValueAndTextList());

        map_dataId_to_valueAndTextList.put("hour", getHourValueAndTextList());
        map_dataId_to_valueAndTextList.put("minute", getMinuteValueAndTextList());

        map_dataId_to_valueAndTextList.put("order", newList(
                new ValueAndLabel("ASC", "顺序"),
                new ValueAndLabel("DESC", "倒序")
        ));

        map_dataId_to_valueAndTextList.put("productState", newList(
                new ValueAndLabel("0", "禁用"),
                new ValueAndLabel("1", "启用")
        ));

         map_dataId_to_valueAndTextList.put("sex", newList(
                new ValueAndLabel("1", "男"),
                new ValueAndLabel("2", "女")
        ));

        map_dataId_to_valueAndTextList.put("degree", newList(
                new ValueAndLabel("1", "本科生"),
                new ValueAndLabel("2", "研究生"),
                new ValueAndLabel("3", "博士及以上"),
                new ValueAndLabel("4", "其他")
        ));

        map_dataId_to_valueAndTextList.put("certificateType", newList(
                new ValueAndLabel("1", "身份证"),
                new ValueAndLabel("2", "军官证"),
                new ValueAndLabel("3", "其他")
        ));

        map_dataId_to_valueAndTextList.put("deviceTag", newList(
                new ValueAndLabel("0", "未绑定"),
                new ValueAndLabel("1", "已绑定")
        ));

        map_dataId_to_valueAndTextList.put("bindState", newList(
                new ValueAndLabel("0", "未绑定"),
                new ValueAndLabel("1", "已绑定")
        ));

        map_dataId_to_valueAndTextList.put("state", newList(
                new ValueAndLabel("0", "未验证"),
                new ValueAndLabel("1", "已验证")
        ));

        map_dataId_to_valueAndTextList.put("annoState", newList(
                new ValueAndLabel(AnnoState.notAnnotated.getValue(), AnnoState.notAnnotated.getName()),
                new ValueAndLabel(AnnoState.machineAnnotated.getValue(), AnnoState.machineAnnotated.getName()),
                new ValueAndLabel(AnnoState.manuallyAnnotated.getValue(), AnnoState.manuallyAnnotated.getName())
        ));

        map_dataId_to_valueAndTextList.put("userRole", newList(
                new ValueAndLabel(UserRole.ADMINISTRATOR.getId(), UserRole.ADMINISTRATOR.getName()),
                new ValueAndLabel(UserRole.ANNOTATOR_ADMINISTRATOR.getId(), UserRole.ANNOTATOR_ADMINISTRATOR.getName()),
                new ValueAndLabel(UserRole.ANNOTATOR.getId(), UserRole.ANNOTATOR.getName()),
                new ValueAndLabel(UserRole.GENERAL.getId(), UserRole.GENERAL.getName()),
                new ValueAndLabel(UserRole.GUEST.getId(), UserRole.GUEST.getName())
        ));

    }


    /**
     * 注册一个列表到对应的dataId
     */
    public static void register(String dataId, List<IValueAndLabel> valueAndTextList) {
        map_dataId_to_valueAndTextList.put(dataId, valueAndTextList);
    }


    /**
     * 注册一个列表到对应的dataId
     */
    public static void register(String dataId, IValueAndLabel[] valueAndTexts) {
        List<IValueAndLabel> valueAndTextList = new ArrayList<IValueAndLabel>(valueAndTexts.length);
        for(IValueAndLabel iValueAndLabel : valueAndTexts) {
            valueAndTextList.add(iValueAndLabel);
        }
        map_dataId_to_valueAndTextList.put(dataId, valueAndTextList);
    }


    /**
     * 注册一个列表到对应的dataId
     * @param dataId
     * @param valueAndLabelListProvider
     */
    public static void register(String dataId, IValueAndLabelListProvider valueAndLabelListProvider) {
        map_dataId_to_valueAndTextListProvider.put(dataId, valueAndLabelListProvider);
    }


    public static List<IValueAndLabel> getValueAndLabelList(String dataId, boolean useCategoryTable, String module, Map<String, String> generalParameterMap) throws Exception {
        List<IValueAndLabel> valueAndTextList;
        if ("yearAndMonth".equals(dataId)) {
            valueAndTextList = new ArrayList<IValueAndLabel>();
            int nrYear = Integer.parseInt(generalParameterMap.get("nrYear"));
            Calendar cld = Calendar.getInstance();
            int currentYear = cld.get(Calendar.YEAR);
            int currentMonth = cld.get(Calendar.MONTH)+1;
            for (int i = 0; i < nrYear; i++) {
                int year = currentYear - i;
                int maxMonth = year == currentYear ? currentMonth : 12;
                String valueForYear = TimeUtil.formatYyyy_mm_dd(year, 1, 1) + "," + TimeUtil.formatYyyy_mm_dd(year, 12, 31);
                String textForYear = year + "年";
                valueAndTextList.add(new ValueAndLabel(valueForYear, textForYear));
                for (int i_month = maxMonth; i_month > 0; i_month--) {
                    String valueForMonth = TimeUtil.formatYyyy_mm_dd(year, i_month, 1) + "," + TimeUtil.formatYyyy_mm_dd(year, i_month, TimeUtil.getLastDayOfMonth(year, i_month));
                    String textForMonth = year + "年" + i_month + "月";
                    valueAndTextList.add(new ValueAndLabel(valueForMonth, textForMonth));
                }
            }
        } else {
            // 从“值-文本”列表提供器取数据
            valueAndTextList = getValueAndLabelList(dataId);
        }

        return valueAndTextList;
    }


    /**
     * 根据dataId，从“值-文本”列表提供器取数据
     */
    public static List<IValueAndLabel> getValueAndLabelList(String dataId) throws Exception {
        List<IValueAndLabel> valueAndTextList;IValueAndLabelListProvider valueAndTextListProvider = map_dataId_to_valueAndTextListProvider.get(dataId);
        if (valueAndTextListProvider != null) {
            valueAndTextList = valueAndTextListProvider.getValueAndTextList();
        } else {
            // 从缓存取数据
            valueAndTextList = map_dataId_to_valueAndTextList.get(dataId);
        }
        return valueAndTextList;
    }


    private static List<IValueAndLabel> getYesOrNoValueAndTextList() {
        List<IValueAndLabel> list = new ArrayList<IValueAndLabel>(2);
        list.add(new ValueAndLabel("1", "是"));
        list.add(new ValueAndLabel("0", "否"));

        return list;
    }

    private static List<IValueAndLabel> getHourValueAndTextList() {
        List<IValueAndLabel> list = new ArrayList<IValueAndLabel>(24);
        for(int i=0; i<24; i++) {
            list.add(new ValueAndLabel(String.valueOf(i), StringUtil.formatNumber(i, 2, '0')));
        }

        return list;
    }

    private static List<IValueAndLabel> getMinuteValueAndTextList() {
        List<IValueAndLabel> list = new ArrayList<IValueAndLabel>(60);
        for(int i=0; i<60; i++) {
            list.add(new ValueAndLabel(String.valueOf(i), StringUtil.formatNumber(i, 2, '0')));
        }

        return list;
    }


    private static List<IValueAndLabel> newList(IValueAndLabel... valueAndTexts){
    	List<IValueAndLabel> list = new ArrayList<IValueAndLabel>(valueAndTexts.length);
        for(IValueAndLabel valueAndText : valueAndTexts) {
            list.add(valueAndText);
        }
    	return list;
    }



}