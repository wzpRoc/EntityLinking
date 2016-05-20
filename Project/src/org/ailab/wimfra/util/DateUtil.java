package org.ailab.wimfra.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateUtil {

    // 7个星期时间对应的整数值，为了便于计算两天的时间差
    public static int SUNDAY = 1;
    public static int MONDAY = 2;
    public static int TUESDAY = 3;
    public static int WEDNSDAY = 4;
    public static int THURSDAY = 5;
    public static int FRIDAY = 6;
    public static int SATURDAY = 7;

    // 一个星期的天数
    public final static int WEEKDAYS = 7;

    // 一天的毫秒数
    public final static long MILLISOFDAY = 24 * 60 * 60 * 1000;

    // 时间格式1
    public final static String CSTDATEFORMAT = "EEE MMM dd HH:mm:ss z yyyy"; //"Fri Jun 24 15:15:46 CST 2011";
    // 时间格式2
    public final static String COMDATEFORMAT = "yyyy-MM-dd HH:mm:ss"; //"2011-07-28 12:29:33"

    // 中文日期格式
    // 年格式
    public final static String YEARPATTERN = "(\\d+)年";
    // 月格式
    public final static String MONTHPATTERN = "(\\d+)月";
    // 日格式
    public final static String DAYPATTERN = "(\\d+)日";
    // 正则
    // 年正则
    private static Pattern yearPattern = Pattern.compile(YEARPATTERN);
    // 月正则
    private static Pattern monthPattern = Pattern.compile(MONTHPATTERN);
    // 日正则
    private static Pattern dayPattern = Pattern.compile(DAYPATTERN);

    /**
     * 规格化日期函数，主要规格化的是 星期时间
     * 比如：将新闻中的“Tuesday”转化为GMT格式的时间
     *
     * @param time    提取到的时间
     * @param rssTime 数据库中拿出来的时间
     * @return 规格化后的时间
     */
    public static String normalizeTime(String time, String rssTime) {
        // 如果提取到的时间为空串，那么算法结束
//        if (time.equals("") || time.equals(SubTask.DEFAULTVALUE)) {
//
//            return rssTime;
//        }
        // 下面修改时间
        // 定义规格化后的时间
        String normalTime = "";
        // 匹配到的星期时间
        int dayOfWeek = -1;
        // 从rssTime中得到的星期时间
        int dayOfWeekRss = -1;

        // 按星期字符串对应到相应的日期数字
        if (time.indexOf("Monday") > -1) {
            dayOfWeek = MONDAY;
        } else if (time.indexOf("Tuesday") > -1) {
            dayOfWeek = TUESDAY;
        } else if (time.indexOf("Wednsday") > -1) {
            dayOfWeek = WEDNSDAY;
        } else if (time.indexOf("Thursday") > -1) {
            dayOfWeek = THURSDAY;
        } else if (time.indexOf("Friday") > -1) {
            dayOfWeek = FRIDAY;
        } else if (time.indexOf("Saturday") > -1) {
            dayOfWeek = SATURDAY;
        } else if (time.indexOf("Sunday") > -1) {
            dayOfWeek = SUNDAY;
        }

        // 如果不存在星期时间，那么直接返回rss时间
        if (dayOfWeek == -1)
            return rssTime;

        // 获取rss时间的星期时间
        // 由输入GMT时间字符串得到long值
        DateFormat Gmt = new SimpleDateFormat(COMDATEFORMAT, Locale.ENGLISH);
        long lRssTime = -1;
        try {
            lRssTime = Gmt.parse(rssTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            // 如果出现异常，返回rss时间
            return rssTime;
        }
        // 取得rss的星期时间
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(lRssTime);
        dayOfWeekRss = c.get(Calendar.DAY_OF_WEEK);

        // 计算两个时间的差值
        long lDelta = ((dayOfWeekRss + WEEKDAYS - dayOfWeek) % 7) * MILLISOFDAY;

        // 重新设置时间
        c.setTimeInMillis(lRssTime - lDelta);
        // 获得最终规格化后的GMT时间
        normalTime = Gmt.format(c.getTime());

        return normalTime;
    }

    /**
     * 规格化中文时间，主要是将“8月25日”这种时间转化一下成为标准时间
     *
     * @param time
     * @param rssTime
     * @return
     */
    public static String normalizeChineseTime(String time, String rssTime) {
        // 如果日期不存在 则返回发布时间
//        if (time.equals("") || time.equals(SubTask.DEFAULTVALUE)) {
//            return rssTime;
//        }

        // 找到年月日的数字
        int numYear = 0, numMonth = 0, numDay = 0;
        boolean bingo = false;    // 是否找到时间的标志位
        Matcher matcher;
        // 匹配年时间
        matcher = yearPattern.matcher(time);
        if (matcher.find()) {
        	// 解决“60年代”这类问题，2013-01-05添加
        	int matYear = Integer.parseInt(matcher.group(1));
        	if(matYear > 1800){
	            numYear = Integer.parseInt(matcher.group(1));
	            bingo = true;
        	}
        }
        // 匹配月时间
        matcher = monthPattern.matcher(time);
        if (matcher.find()) {
            numMonth = Integer.parseInt(matcher.group(1));
            bingo = true;
        }
        // 匹配日时间
        matcher = dayPattern.matcher(time);
        if (matcher.find()) {
            numDay = Integer.parseInt(matcher.group(1));
            bingo = true;
        }
        // 如果没有找到中文的年月日时间，则直接返回rss时间
        if (!bingo) {
            return rssTime;
        }

        // 获取rss时间的星期时间
        // 由输入GMT时间字符串得到long值
        DateFormat Gmt = new SimpleDateFormat(COMDATEFORMAT, Locale.ENGLISH);
        long lRssTime = -1;
        try {
            lRssTime = Gmt.parse(rssTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            // 如果出现异常，返回rss时间
            return rssTime;
        }
        // 取得rss的年月日时间
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(lRssTime);
        // 设置年月日事件，为了检查是否跨月跨年，设置顺序为日月年
        if (numDay == 0) {
            numDay = c.get(Calendar.DAY_OF_MONTH);
        }
        if (numMonth == 0) {	// 没有抽到月份
        	numMonth = c.get(Calendar.MONTH);
        	// 检查是否跨月了
        	if(numDay - c.get(Calendar.DAY_OF_MONTH)> 22)	// 适用于"12月1日"报道了"30日XXX"
        		numMonth --;
        } else {
            numMonth--;
        }
        if (numYear == 0) {	// 没有抽到年
        	numYear = c.get(Calendar.YEAR);
        	// 检查是否跨年
        	if(numMonth - c.get(Calendar.MONTH) > 10)
        		numYear --;
        }
        
        c.set(numYear, numMonth, numDay);
        // 获得最终规格化后的GMT时间
        String normalTime = Gmt.format(c.getTime());

        return normalTime;
    }

    /**
     * 日期的比较
     *
     * @param firstDate
     * @param secDate
     * @return 0:日期相等
     *          -1:firstdate before secDate
     *          1: firstdate after secDate
     */
    public static int compareDates(String firstDate, String secDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try
        {
            c1.setTime(df.parse(firstDate));
            c2.setTime(df.parse(secDate));
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("格式不正确");
        }

        int result = c1.compareTo(c2);

        if (result == 0)
            return 0;
        else if (result < 0)
            return -1;
        else
            return 1;
    }

    public static void main(String args[]) {
        String time = DateUtil.normalizeChineseTime("1964年", "2012-11-30 00:00:00");

		System.out.println(time);
//        try {
//            compareDates("20111012000000","20111014000000");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

}
