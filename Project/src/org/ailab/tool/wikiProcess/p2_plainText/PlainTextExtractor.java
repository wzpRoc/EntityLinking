package org.ailab.tool.wikiProcess.p2_plainText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Desc: 维基百科页面中文本抽取器
 */
public class PlainTextExtractor {
    private static Pattern pattern_comment = Pattern.compile("<!--.*?-->", Pattern.DOTALL);
    private static Pattern pattern_div = Pattern.compile("<div[ >].*?</div>", Pattern.DOTALL);
    private static Pattern pattern_ref_closed = Pattern.compile("<ref [^>]*?/>");
    private static Pattern pattern_ref = Pattern.compile("<ref[ >].*?</ref>", Pattern.DOTALL);
    //    private static Pattern pattern_bracePipe = Pattern.compile("\\{\\|.*?\\|\\}");
    private static Pattern pattern_emptyLines1 = Pattern.compile("(^\\s*){2,}", Pattern.DOTALL);
    private static Pattern pattern_emptyLines2 = Pattern.compile("(\\n\\s*){2,}", Pattern.DOTALL);
    private static Pattern pattern_file1 = Pattern.compile("^(\\[\\[)?File:.*");
    private static Pattern pattern_file2 = Pattern.compile("\\n(\\[\\[)?File:.*");
    private static Pattern pattern_image1 = Pattern.compile("^(\\[\\[)?Image:.*");
    private static Pattern pattern_image2 = Pattern.compile("\\n(\\[\\[)?Image:.*");
    //    private static Pattern pattern_star = Pattern.compile("\\n\\* \\[.*");
    private static Pattern pattern_seeAlso = Pattern.compile("^==See also==.*", Pattern.DOTALL);


    /**
     * 获得普通文本
     */
    public static String getPlainText(String wikiText) {
        String text = wikiText;

        // .*?里面的问号是指最短匹配

        text = pattern_seeAlso.matcher(text).replaceAll("\n");
        // [[File:
        text = pattern_file1.matcher(text).replaceAll("");
        text = pattern_file2.matcher(text).replaceAll("");
        // [[Image:
        text = pattern_image1.matcher(text).replaceAll("");
        text = pattern_image2.matcher(text).replaceAll("");

        text = text.replaceAll("__NOTOC__\n", "");
        text = text.replaceAll("\n(\\{\\||\\|-).*", "");

        // 双大括号 {{...}}
        text = removeBraceBrace(text);
        // div
        text = pattern_div.matcher(text).replaceAll("");
        // 表格 {|...|}
        // text = pattern_bracePipe.matcher(text).replaceAll("");
        // 注释
        text = pattern_comment.matcher(text).replaceAll("");
        // ref引用
        text = pattern_ref_closed.matcher(text).replaceAll("");
        text = pattern_ref.matcher(text).replaceAll("");

        // 样式
        text = text.replaceAll("style=\".*?\"", "");
        text = text.replaceAll("v?align=\"?\\w+\"?", "");
        text = text.replaceAll("width=\"?\\d+\\w*\"?", "");
        text = text.replaceAll("(col|row)span *=\"?\\w+?\"?", "");

        // 孤零零的后标签
        text = text.replaceAll("</?.*?>", " ");

        // 内部特殊链接
        text = text.replaceAll("\\[\\[[^\\]]*?:.*?\\]\\]", " ");

        // 内部链接（锚文本与链接不同）
        text = text.replaceAll("\\[\\[([^\\]]*?)\\|([^\\]]*?)\\]\\]", "$2");

        // 内部链接（锚文本与链接相同）
        text = text.replaceAll("\\[\\[([^\\]]*?)\\]\\]", "$1");

        // ...加粗
        // ..斜体
        text = text.replaceAll("'''?", "");

        // HTML转义
        text = text.replace("&nbsp;", " ");
        text = text.replace("&ndash;", "–");

        text = text.replaceAll("\\([ ;,]*\\)", "");

        // 空行
        text = pattern_emptyLines1.matcher(text).replaceAll("\n");
        text = pattern_emptyLines2.matcher(text).replaceAll("\n");
        // 空格
        text = text.replaceAll(" +", " ");

        text = text.trim();

        return text;
    }

    /**
     * 移除双大括号 {{...}
     */
    public static String removeBraceBrace(String text) {
        char[] cs = text.toCharArray();
        List<Integer> startIdxList = new ArrayList<Integer>();
        List<Integer> endIdxList = new ArrayList<Integer>();

        int depth = 0;
        int topLevelBBStartIdx = -1;
        for (int i = 1; i < cs.length; i++) {
            char c = cs[i];
            char lastChar = cs[i - 1];
            if (lastChar == '{' && c == '{') {
                depth++;
                if (depth == 1) {
                    topLevelBBStartIdx = i - 1;
                    startIdxList.add(topLevelBBStartIdx);
                }
                i++;
            } else if (lastChar == '}' && c == '}') {
                depth--;
                if (depth == 0) {
                    // finished
                    if (topLevelBBStartIdx == -1) {
                        throw new RuntimeException("从未开始，却已结束");
                    }
                    topLevelBBStartIdx = -1;
                    endIdxList.add(i);
                }
                i++;
            }
        }

        StringBuilder sb = new StringBuilder();
        int lastEndIdx = 0;
        for (int i = 0; i < startIdxList.size() && i<endIdxList.size(); i++) {
            int startIdx = startIdxList.get(i);
            sb.append(text.substring(lastEndIdx, startIdx));
            lastEndIdx = endIdxList.get(i) + 1;
        }
        if (lastEndIdx < cs.length - 1) {
            sb.append(text.substring(lastEndIdx));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String wikiText =
                "----\n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        " \n" +
                        "|}\n" +
                        "|}\n" +
                        "The , or TX, is a Japanese railway line of the Metropolitan Intercity Railway Company which links Akihabara Station in Chiyoda, Tokyo and Tsukuba Station in Tsukuba, Ibaraki. The route was inaugurated on August 24, 2005.\n" +
                        "==Speed==\n" +
                        "The line has a top speed of 130 km/h. Rapid service has reduced the time required for the trip from Akihabara to Tsukuba from the previous 1 hour 30 minutes (by the Jōban Line, arriving in Tsuchiura, about 15 km from Tsukuba) or 70 minutes (by bus, under optimal traffic conditions) to 45 minutes; from Tokyo, the trip requires 50 – 55 minutes. The line has no grade crossings.\n" +
                        "An automatic train operation system allows a single individual to operate the train.\n" +
                        "==Electrification and rolling stock==\n" +
                        "To prevent interference with the geomagnetic measurements of the Japan Meteorological Agency at its laboratory in Ishioka, the portion of the line from Moriya to Tsukuba operates on alternating current. For this reason, the trains include TX-1000 series DC-only trains, which can operate only between Akihabara and Moriya, and TX-2000 series dual-voltage AC/DC trains, which can operate over the entire line.\n" +
                        "Volume production of the rolling stock began in January 2004, following the completion in March 2003 of two (TX-1000 and TX-2000 series) six-car trains for trial operation and training. The full fleet of 84 TX-1000s (14 six-car trains) and 96 TX-2000s (16 six-car trains) was delivered by January 2005.\n" +
                        "==Stations==\n" +
                        "* L: \n" +
                        "* S: \n" +
                        "* C: \n" +
                        "* R: \n" +
                        "Trains stop at stations marked \"●\" and pass stations marked \"-\".\n" +
                        "! No.\n" +
                        "! Station name\n" +
                        "! Distance (km)\n" +
                        "! Elec.\n" +
                        "! L\n" +
                        "! S\n" +
                        "! C\n" +
                        "! R\n" +
                        "! Transfers\n" +
                        "! | Location\n" +
                        "| 01 || Akihabara || | 0.0 || 5\" | DC || | ● || | ● || | ● || | ● || ■ Chūō-Sōbu Line ■ Keihin-Tōhoku Line ■ Yamanote Line Tokyo Metro Hibiya Line (H-15) || Chiyoda || | Tokyo\n" +
                        "| 02 || Shin-Okachimachi || | 1.6 || | ● || | ● || | ● || | ● || Toei Ōedo Line (E-10) || | Taitō\n" +
                        "| 03 || Asakusa || | 3.1 || | ● || | ● || | ● || | ● || Tokyo Metro Ginza Line (at Tawaramachi (G-18))\n" +
                        "| 04 || Minami-Senju || | 5.6 || | ● || | ● || | ● || | ● || ■ ■ Jōban Line (Rapid) Tokyo Metro Hibiya Line (H-20) || Arakawa\n" +
                        "| 05 || Kita-Senju || | 7.5 || | ● || | ● || | ● || | ● || ■ ■ Jōban Line (Rapid) ■ Tobu Skytree Line Tokyo Metro Chiyoda Line (C-18), Tokyo Metro Hibiya Line (H-21) || | Adachi\n" +
                        "| 06 || Aoi || | 10.6 || | ● || | - || | - || | - || \n" +
                        "| 07 || Rokuchō || | 12.0 || | ● || | - || | ● || | - ||\n" +
                        "| 08 || Yashio || | 15.6 || | ● || | ● || | ● || | - || || Yashio || | Saitama\n" +
                        "| 09 || Misato-chūō || | 19.3 || | ● || | ● || | - || | - || || Misato\n" +
                        "| 10 || Minami-Nagareyama || | 22.1 || | ● || | ● || | ● || | ● || ■ Musashino Line || | Nagareyama || | Chiba\n" +
                        "| 11 || Nagareyama-centralpark || | 24.3 || | ● || | - || | - || | - || \n" +
                        "| 12 || Nagareyama-ōtakanomori || | 26.5 || | ● || | ● || | ● || | ● || ■ Tobu Urban Park Line\n" +
                        "| 13 || Kashiwanoha-campus || | 30.0 || | ● || | ● || | ● || | - || || | Kashiwa\n" +
                        "| 14 || Kashiwa-Tanaka || | 32.0 || | ● || | - || | - || | - ||\n" +
                        "| 15 || Moriya || | 37.7 || | ● || | ● || | ● || | ● || ■ Jōsō Line || Moriya || | Ibaraki\n" +
                        "| 16 || Miraidaira || | 44.3 || | AC || | ● || | ● || | - || | - || || Tsukubamirai\n" +
                        "| 17 || Midorino || | 48.6 || | ● || | ● || | - || | - || || | Tsukuba\n" +
                        "| 18 || Bampaku-kinenkōen || | 51.8 || | ● || | ● || | - || | - ||\n" +
                        "| 19 || Kenkyū-gakuen || | 55.6 || | ● || | ● || | ● || | - ||\n" +
                        "| 20 || Tsukuba || | 58.3 || | ● || | ● || | ● || | ● ||\n" +
                        "|}\n" +
                        "==History==\n" +
                        "The was founded on 15 March 1991 to construct the Tsukuba Express, which was initially going to be called the . The reason for the line was to relieve crowding on the Jōban Line operated by East Japan Railway Company (JR East), which had reached the limit of its capacity. However, with the economic downturn in Japan, the goal shifted to development along the line. Also, the initial plan called for a line from Tokyo Station to Moriya, but expenses forced the planners to start the line at Akihabara instead of Tokyo Station, and pressure from the government of Ibaraki Prefecture resulted in moving the extension from Moriya to Tsukuba into Phase I of the construction.\n" +
                        "The original schedule called for the line to begin operating in 2000, but delays in construction pushed the opening date to summer 2005. The line eventually opened on 24 August 2005.\n" +
                        "From the start of the revised timetable on 15 October 2012, new services were introduced in the morning (inbound services) and evening (outbound services) peak periods.\n" +
                        "In September 2013, a number of municipalities along the Tsukuba Express line in Ibaraki Prefecture submitted a proposal to complete the extension of the line to Tokyo Station at the same time as a new airport-to-airport line proposed as part of infrastructure improvements for the 2020 Summer Olympics.\n" +
                        "==Ridership figures==\n" +
                        "! Fiscal year \n" +
                        "! Passengers carried (in millions) \n" +
                        "! Days operated \n" +
                        "! Passengers per day\n" +
                        "| 2005 \n" +
                        "| 34.69 \n" +
                        "| 220 \n" +
                        "| 150,000\n" +
                        "| 2006 \n" +
                        "| 70.69 \n" +
                        "| 365 \n" +
                        "| 195,000\n" +
                        "| 2007 \n" +
                        "| 84.85\n" +
                        "| 366 \n" +
                        "| 234,000\n" +
                        "| 2008 \n" +
                        "| 93.21\n" +
                        "| 365 \n" +
                        "| 258,000\n" +
                        "| 2009 \n" +
                        "| 97.79 \n" +
                        "| 365 \n" +
                        "| 270,300\n" +
                        "| 2010 \n" +
                        "| 102.22 \n" +
                        "| 365 \n" +
                        "| 283,000\n" +
                        "| 2011 \n" +
                        "| 104.89 \n" +
                        "| 366 \n" +
                        "| 290,000\n" +
                        "| 2012 \n" +
                        "| 110.66\n" +
                        "| 365 \n" +
                        "| 306,000\n" +
                        "| 2013\n" +
                        "| 117.17 \n" +
                        "| 365\n" +
                        "| 324,000\n" +
                        "|}\n" +
                        "(Source:\n" +
                        ")\n" +
                        "==See also==\n" +
                        "List of railway companies in Japan\n" +
                        "==References==\n" +
                        "==External links==\n" +
                        "* [http://www.mir.co.jp/en/ Official Site]";
        String text = getPlainText(wikiText);
        System.out.println(text);
    }
}