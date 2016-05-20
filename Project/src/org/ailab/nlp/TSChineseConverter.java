package org.ailab.nlp;

import org.ailab.wimfra.util.FileUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-3
 * Time: 下午12:37
 */
public class TSChineseConverter {
    private static Map<Character,Character> tsMap;
    private static Map<Character,Character> stMap;

    static {
        try {
            init("ts_map.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init(String path) throws Exception {
        final List<String> lineList = FileUtil.readLines(path);
        tsMap = new HashMap<Character, Character>();
        stMap = new HashMap<Character, Character>();

        for(int i_line=0; i_line<lineList.size(); i_line+=2) {
            String tLine = lineList.get(i_line);
            String sLine = lineList.get(i_line+1);
            for(int i_char=0; i_char<tLine.length(); i_char++) {
                char tChar = tLine.charAt(i_char);
                char sChar = sLine.charAt(i_char);
                tsMap.put(tChar, sChar);
                stMap.put(sChar, tChar);
            }
        }
    }


    public static String toSimplifiedChinese(String traditionalChinese) {
        if(traditionalChinese == null) {
            return null;
        }
        if("".equals(traditionalChinese)) {
            return "";
        }

        return convert(traditionalChinese, tsMap);
    }


    public static String toTraditionalChinese(String simplifiedChinese) {
        if(simplifiedChinese == null) {
            return null;
        }
        if("".equals(simplifiedChinese)) {
            return "";
        }

        return convert(simplifiedChinese, stMap);
    }


    private static String convert(String originalString, Map<Character, Character> map) {
        char[] targetCharArray = new char[originalString.length()];
        for(int i=0; i<originalString.length(); i++) {
            final Character character = map.get(originalString.charAt(i));
            if(character == null) {
                targetCharArray[i] = originalString.charAt(i);
            } else {
                targetCharArray[i] = character;
            }
        }

        return new String(targetCharArray);
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "習主席\n王識賢（1968年2月11日－），綽號：沙士（Jason）臺灣臺北市人，臺北市立體育學院學士，專長足球。現為知名演員、歌手，曾獲金曲獎臺語最佳男歌手。";
        System.out.println(s);
        System.out.println("-----------------------------------------");
        System.out.println(toSimplifiedChinese(s));
    }
}
