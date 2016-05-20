package org.ailab.nlp.parsing.chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2011-8-17
 * Time: 13:02:33
 * Desc:
 */
public class IctclasUtil {
    public static final String POS_PERSON = "nr";
    public static final String POS_LOCATION = "ns";
    public static final String POS_ORGNIZATION = "nt";
    public static final String POS_DATE = "t";

    public static final String POS_PUNCTUATION = "w";

//    public static final String path_map = "resources/IctclasTags.txt";
//    public static final String path_map = "IctclasTags.txt";
    private static ArrayList<IctclasCategoryStruct> structList;
    private static HashMap<Integer, IctclasCategoryStruct> idToStructMap;
    private static HashMap<String, IctclasCategoryStruct> categoryToStructMap;

    static{
        load();
    }


    /**
     * 从文件读入标注集
     */
    /*
    private static void load(){
        idToStructMap = new HashMap<Integer, IctclasCategoryStruct>();
        categoryToStructMap = new HashMap<String, IctclasCategoryStruct>();

         URL url = IctclasUtil.class.getClassLoader().getResource(path_map);
//        URL url = IctclasUtil.class.getResource(path_map);

        ArrayList<String> lineList = readLines(url.getPath());
        for(String line:lineList){
            if(line.startsWith("#")){
                // 注释
                continue;
            }
            String[] a = line.split("\t");
            int id=Integer.parseInt(a[0]);
            int level=Integer.parseInt(a[1]);
            String category=a[2];
            String category1=a[3];
            String category2=a[4];
            String category3=a[5];

            final IctclasCategoryStruct ictclasCategoryStruct = new IctclasCategoryStruct(id, category, level, category1, category2, category3);
            idToStructMap.put(id, ictclasCategoryStruct);
            categoryToStructMap.put(category, ictclasCategoryStruct);
        }
    }
    */

    private static void load(){
        structList = new ArrayList<IctclasCategoryStruct>();
        idToStructMap = new HashMap<Integer, IctclasCategoryStruct>();
        categoryToStructMap = new HashMap<String, IctclasCategoryStruct>();

        // "#ID\t级别\t词性\t一级词性\t二级词性\t三级词性\t描述\n" +
        List<IctclasCategoryStruct> list = new ArrayList<IctclasCategoryStruct>();
        list.add(new IctclasCategoryStruct(21, 1, "n", "n", "", "", "名词"));
        list.add(new IctclasCategoryStruct(21, 2, "n_ne", "n", "", "", "名词"));
        list.add(new IctclasCategoryStruct(24, 2, "nr", "n", "nr", "", "人名"));
        list.add(new IctclasCategoryStruct(25, 2, "nr1", "n", "nr", "nr1", "汉语姓氏"));
        list.add(new IctclasCategoryStruct(26, 2, "nr2", "n", "nr", "nr2", "汉语名字"));
        list.add(new IctclasCategoryStruct(28, 2, "nrj", "n", "nr", "nrj", "日语人名"));
        list.add(new IctclasCategoryStruct(27, 2, "nrf", "n", "nr", "nrf", "音译人名"));
        list.add(new IctclasCategoryStruct(29, 2, "ns", "n", "ns", "", "地名"));
        list.add(new IctclasCategoryStruct(30, 2, "nsf", "n", "ns", "nsf", "音译地名"));
        list.add(new IctclasCategoryStruct(31, 2, "nt", "n", "nt", "", "机构团体名"));
        list.add(new IctclasCategoryStruct(32, 2, "nz", "n", "nz", "", "其它专名"));
        list.add(new IctclasCategoryStruct(23, 2, "nl", "n", "nl", "", "名词性惯用语"));
        list.add(new IctclasCategoryStruct(22, 2, "ng", "n", "ng", "", "名词性语素"));
        list.add(new IctclasCategoryStruct(52, 1, "t", "t", "", "", "时间词"));
        list.add(new IctclasCategoryStruct(53, 2, "tg", "t", "tg", "", "时间词性语素"));
        list.add(new IctclasCategoryStruct(51, 1, "s", "s", "", "", "处所词"));
        list.add(new IctclasCategoryStruct(15, 1, "f", "f", "", "", "方位词"));
        list.add(new IctclasCategoryStruct(68, 1, "v", "v", "", "", "动词"));
        list.add(new IctclasCategoryStruct(69, 2, "vd", "v", "vd", "", "副动词"));
        list.add(new IctclasCategoryStruct(74, 2, "vn", "v", "vn", "", "名动词"));
        list.add(new IctclasCategoryStruct(75, 2, "vshi", "v", "vshi", "", "动词“是”"));
        list.add(new IctclasCategoryStruct(77, 2, "vyou", "v", "vyou", "", "动词“有”"));
        list.add(new IctclasCategoryStruct(70, 2, "vf", "v", "vf", "", "趋向动词"));
        list.add(new IctclasCategoryStruct(76, 2, "vx", "v", "vx", "", "形式动词"));
        list.add(new IctclasCategoryStruct(72, 2, "vi", "v", "vi", "", "不及物动词（内动词）"));
        list.add(new IctclasCategoryStruct(73, 2, "vl", "v", "vl", "", "动词性惯用语"));
        list.add(new IctclasCategoryStruct(71, 2, "vg", "v", "vg", "", "动词性语素"));
        list.add(new IctclasCategoryStruct(2, 1, "a", "a", "", "", "形容词"));
        list.add(new IctclasCategoryStruct(3, 2, "ad", "a", "ad", "", "副形词"));
        list.add(new IctclasCategoryStruct(6, 2, "an", "a", "an", "", "名形词"));
        list.add(new IctclasCategoryStruct(4, 2, "ag", "a", "ag", "", "形容词性语素"));
        list.add(new IctclasCategoryStruct(5, 2, "al", "a", "al", "", "形容词性惯用语"));
        list.add(new IctclasCategoryStruct(7, 1, "b", "b", "", "", "区别词"));
        list.add(new IctclasCategoryStruct(8, 2, "bl", "b", "bl", "", "区别词性惯用语"));
        list.add(new IctclasCategoryStruct(95, 1, "z", "z", "", "", "状态词"));
        list.add(new IctclasCategoryStruct(40, 1, "r", "r", "", "", "代词"));
        list.add(new IctclasCategoryStruct(42, 2, "rr", "r", "rr", "", "人称代词"));
        list.add(new IctclasCategoryStruct(47, 2, "rz", "r", "rz", "", "指示代词"));
        list.add(new IctclasCategoryStruct(49, 2, "rzt", "r", "rz", "rzt", "时间指示代词"));
        list.add(new IctclasCategoryStruct(48, 2, "rzs", "r", "rz", "rzs", "处所指示代词"));
        list.add(new IctclasCategoryStruct(50, 2, "rzv", "r", "rz", "rzv", "谓词性指示代词"));
        list.add(new IctclasCategoryStruct(43, 2, "ry", "r", "ry", "", "疑问代词"));
        list.add(new IctclasCategoryStruct(45, 2, "ryt", "r", "ry", "ryt", "时间疑问代词"));
        list.add(new IctclasCategoryStruct(44, 2, "rys", "r", "ry", "rys", "处所疑问代词"));
        list.add(new IctclasCategoryStruct(46, 2, "ryv", "r", "ry", "ryv", "谓词性疑问代词"));
        list.add(new IctclasCategoryStruct(41, 2, "rg", "r", "rg", "", "代词性语素"));
        list.add(new IctclasCategoryStruct(18, 1, "m", "m", "", "", "数词"));
        list.add(new IctclasCategoryStruct(19, 2, "mg", "m", "mg", "", "????"));
        list.add(new IctclasCategoryStruct(20, 2, "mq", "m", "mq", "", "数量词"));
        list.add(new IctclasCategoryStruct(37, 1, "q", "q", "", "", "量词"));
        list.add(new IctclasCategoryStruct(39, 2, "qv", "q", "qv", "", "动量词"));
        list.add(new IctclasCategoryStruct(38, 2, "qt", "q", "qt", "", "时量词"));
        list.add(new IctclasCategoryStruct(11, 1, "d", "d", "", "", "副词"));
        list.add(new IctclasCategoryStruct(12, 2, "dg", "d", "dg", "", "副词"));
        list.add(new IctclasCategoryStruct(13, 2, "dl", "d", "dl", "", "副词"));
        list.add(new IctclasCategoryStruct(34, 1, "p", "p", "", "", "介词"));
        list.add(new IctclasCategoryStruct(35, 2, "pba", "p", "pba", "", "介词“把”"));
        list.add(new IctclasCategoryStruct(36, 2, "pbei", "p", "pbei", "", "介词“被”"));
        list.add(new IctclasCategoryStruct(9, 1, "c", "c", "", "", "连词"));
        list.add(new IctclasCategoryStruct(10, 2, "cc", "c", "cc", "", "并列连词"));
        list.add(new IctclasCategoryStruct(54, 1, "u", "u", "", "", "助词"));
        list.add(new IctclasCategoryStruct(54, 1, "un", "u", "", "", "助词"));
        list.add(new IctclasCategoryStruct(66, 2, "uzhe", "u", "uzhe", "", "着"));
        list.add(new IctclasCategoryStruct(61, 2, "ule", "u", "ule", "", "了 喽"));
        list.add(new IctclasCategoryStruct(60, 2, "uguo", "u", "uguo", "", "过"));
        list.add(new IctclasCategoryStruct(55, 2, "ude1", "u", "ude1", "", "的 底"));
        list.add(new IctclasCategoryStruct(56, 2, "ude2", "u", "ude2", "", "地"));
        list.add(new IctclasCategoryStruct(57, 2, "ude3", "u", "ude3", "", "得"));
        list.add(new IctclasCategoryStruct(64, 2, "usuo", "u", "usuo", "", "所"));
        list.add(new IctclasCategoryStruct(58, 2, "udeng", "u", "udeng", "", "等 等等 云云"));
        list.add(new IctclasCategoryStruct(58, 2, "uden", "u", "udeng", "", "等 等等 云云"));
        list.add(new IctclasCategoryStruct(65, 2, "uyy", "u", "uyy", "", "一样 一般 似的 般"));
        list.add(new IctclasCategoryStruct(59, 2, "udh", "u", "udh", "", "的话"));
        list.add(new IctclasCategoryStruct(63, 2, "uls", "u", "uls", "", "来讲 来说 而言 说来"));
        list.add(new IctclasCategoryStruct(67, 2, "uzhi", "u", "uzhi", "", "之"));
        list.add(new IctclasCategoryStruct(62, 2, "ulian", "u", "ulian", "", "连 （“连小学生都会”）"));
        list.add(new IctclasCategoryStruct(62, 2, "ulia", "u", "ulian", "", "连 （“连小学生都会”）"));
        list.add(new IctclasCategoryStruct(14, 1, "e", "e", "", "", "叹词"));
        list.add(new IctclasCategoryStruct(94, 1, "y", "y", "", "", "语气词"));
        list.add(new IctclasCategoryStruct(33, 1, "o", "o", "", "", "拟声词"));
        list.add(new IctclasCategoryStruct(16, 1, "h", "h", "", "", "前缀"));
        list.add(new IctclasCategoryStruct(17, 1, "k", "k", "", "", "后缀"));
        list.add(new IctclasCategoryStruct(93, 1, "x", "x", "", "", "字符串"));
        list.add(new IctclasCategoryStruct(-1, 2, "xx", "x", "xx", "", "非语素字"));
        list.add(new IctclasCategoryStruct(-2, 2, "xu", "x", "xu", "", "网址URL"));
        list.add(new IctclasCategoryStruct(78, 1, "w", "w", "", "", "标点符号"));
        list.add(new IctclasCategoryStruct(84, 2, "wkz", "w", "wkz", "", "左括号 全角：（ 〔  ［  ｛  《 【  〖 〈   半角：( [ { <"));
        list.add(new IctclasCategoryStruct(83, 2, "wky", "w", "wky", "", "右括号 全角：） 〕  ］ ｝ 》  】 〗 〉 半角： ) ] { >"));
        list.add(new IctclasCategoryStruct(92, 2, "wyz", "w", "wyz", "", "左引号 全角：“ ‘ 『"));
        list.add(new IctclasCategoryStruct(91, 2, "wyy", "w", "wyy", "", "右引号 全角：” ’ 』"));
        list.add(new IctclasCategoryStruct(82, 2, "wj", "w", "wj", "", "句号 全角：。"));
        list.add(new IctclasCategoryStruct(90, 2, "ww", "w", "ww", "", "问号 全角：？ 半角：?"));
        list.add(new IctclasCategoryStruct(89, 2, "wt", "w", "wt", "", "叹号 全角：！ 半角：!"));
        list.add(new IctclasCategoryStruct(80, 2, "wd", "w", "wd", "", "逗号 全角：  半角："));
        list.add(new IctclasCategoryStruct(81, 2, "wf", "w", "wf", "", "分号 全角：； 半角： ;"));
        list.add(new IctclasCategoryStruct(86, 2, "wn", "w", "wn", "", "顿号 全角：、"));
        list.add(new IctclasCategoryStruct(85, 2, "wm", "w", "wm", "", "冒号 全角：： 半角： :"));
        list.add(new IctclasCategoryStruct(88, 2, "ws", "w", "ws", "", "省略号 全角：……  …"));
        list.add(new IctclasCategoryStruct(87, 2, "wp", "w", "wp", "", "破折号 全角：——   －－   ——－   半角：---  ----"));
        list.add(new IctclasCategoryStruct(79, 2, "wb", "w", "wb", "", "百分号千分号 全角：％ ‰   半角：%"));
        list.add(new IctclasCategoryStruct(-3, 2, "wh", "w", "wh", "", "单位符号 全角：￥ ＄ ￡  °  ℃  半角：$"));
        list.add(new IctclasCategoryStruct(-4, 1, "url", "url", "", "", ""));
        list.add(new IctclasCategoryStruct(-5, 1, "emai", "emai", "", "", ""));
        list.add(new IctclasCategoryStruct(-999, 1, "", "", "", "", ""));

        for(IctclasCategoryStruct ictclasCategoryStruct : list){
            idToStructMap.put(ictclasCategoryStruct.id, ictclasCategoryStruct);
            categoryToStructMap.put(ictclasCategoryStruct.category, ictclasCategoryStruct);
        }
    }

    /**
     * 根据id得到结构体
     * @param id
     * @return
     */
    public static IctclasCategoryStruct getStructById(int id){
        return idToStructMap.get(id);
    }

    /**
     * 根据category得到结构体
     * @param category
     * @return
     */
    public static IctclasCategoryStruct getStructByCategory(String category){
        return categoryToStructMap.get(category);
    }



    /**
     * 读入文件内容到行的列表
     *
     * @param path
     * @return
     */
    private static ArrayList<String> readLines(String path) {
        ArrayList<String> list = new ArrayList<String>();
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            //reader.skip(1);
            String strLine;
            while (true) {
                strLine = reader.readLine();
                //System.out.println(strLine);
                if (strLine == null) break;
                list.add(strLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return list;
    }
}
